<<<<<<< HEAD
import React from 'react';
=======
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useCookies } from 'react-cookie'; // Import useCookies hook from react-cookie
import PostDetails from './PostDetails';
import './Community.style.css';

type Reply = {
  id: string | null;
  content: string;
  createdAt: string;
  username: string;
};

type Post = {
  id: string;
  title: string | null;
  content: string | null;
  createdAt: string;
  username: string;
  replies: Reply[]; // replies 필드 추가
};
// const dummyData = [
//   {
//     "id": "66054169daf7e755ebbaf60c",
//     "title": "대박",
//     "content": "이 게임 대박이네요",
//     "createdAt": "2024-03-28T19:45:30.235322",
//     "username": "임서연53",
//     "replies": []
// },
// {
//     "id": "6607989de0c5d03e220381da",
//     "title": null,
//     "content": null,
//     "createdAt": "2024-03-30T13:44:13.820054192",
//     "username": "지윤백",
//     "replies": []
// },
// {
//     "id": "6608254af0e4eb149a0856d2",
//     "title": null,
//     "content": null,
//     "createdAt": "2024-03-30T23:44:26.617129946",
//     "username": "지윤백",
//     "replies": []
// },
// {
//     "id": "660a107829ee483ba48e90f2",
//     "title": "안녕",
//     "content": "나온",
//     "createdAt": "2024-04-01T10:40:08.316679029",
//     "username": "Unknown User",
//     "replies": []
// },
// {
//     "id": "660a656731dd9b465e094ee8",
//     "title": "string",
//     "content": "string",
//     "createdAt": "2024-04-01T16:42:30.9329976",
//     "username": "string",
//     "replies": []
// },
// {
//     "id": "660a68d70223e272e6690f20",
//     "title": "뭐여",
//     "content": "엥",
//     "createdAt": "2024-04-01T16:57:11.6968354",
//     "username": "Unknown User",
//     "replies": []
// },
// {
//     "id": "660a6982a4091a772746c5e2",
//     "title": "ddd",
//     "content": "dfsfdsfg",
//     "createdAt": "2024-04-01T17:00:02.1168768",
//     "username": "Unknown User",
//     "replies": []
// },
// {
//     "id": "660a6e5d752c8540430ff01a",
//     "title": "ㄴㅇ",
//     "content": "ㅇ",
//     "createdAt": "2024-04-01T17:20:45.10799378",
//     "username": "지윤백",
//     "replies": []
// },
// {
//     "id": "660a7395752c8540430ff01b",
//     "title": "ㅇㄴㄹ",
//     "content": "ㅇㄴㄹ",
//     "createdAt": "2024-04-01T17:43:01.238580833",
//     "username": "지윤백",
//     "replies": []
// },
// {
//     "id": "660a9d3884dae0451bc5a338",
//     "title": "안녕하세요",
//     "content": "반가워요\n",
//     "createdAt": "2024-04-01T20:40:40.179718831",
//     "username": "지윤백",
//     "replies": []
// },
// {
//     "id": "660ab6bed2ce7907c4a67cb9",
//     "title": "ㅣㅏㅏㅣ",
//     "content": "ㅐㅔㅏ",
//     "createdAt": "2024-04-01T22:29:34.794098264",
//     "username": "지윤백",
//     "replies": []
// }
// ];

const Community: React.FC = () => {
  const [posts, setPosts] = useState<Post[]>([]);
  const [selectedPost, setSelectedPost] = useState<Post | null>(null);
  const [isCreatingPost, setIsCreatingPost] = useState(false);
  const [newPostTitle, setNewPostTitle] = useState('');
  const [newPostContent, setNewPostContent] = useState('');
  const [cookies] = useCookies(['accessToken']); // Retrieve cookies


  const handleEdit = (post: Post) => {
    // Implementation for editing a post
    console.log('Editing post', post);
  };
  
  const handleDelete = (postId: string) => {
    // Implementation for deleting a post
    console.log('Deleting post with ID', postId);
  };
  useEffect(() => {
    const fetchPosts = async () => {
      try {
        const response = await axios.get(`${process.env.REACT_APP_API}/board`);
        // title과 content가 null이 아닌 게시물만 필터링합니다.
        const validPosts = response.data.filter((post: Post) => post.title != null && post.content != null);
        setPosts(validPosts);
      } catch (error) {
        console.error('Failed to fetch posts:', error);
      }
    };
  
    fetchPosts();
  }, []);
  const addPost = () => {
    setIsCreatingPost(true);
  };

  const handlePostSubmit = async () => {
    try {
      const response = await axios.post(`${process.env.REACT_APP_API}/board`, {
        title: newPostTitle,
        content: newPostContent,
      }, {
        headers: {
          Authorization: `Bearer ${cookies.accessToken}`, // Use the accessToken from cookies
        },
      });
      setPosts([...posts, response.data]);
      setIsCreatingPost(false);
      setNewPostTitle('');
      setNewPostContent('');
    } catch (error) {
      console.error('Failed to create post:', error);
    }
  };

  const handlePostClick = (post: Post) => {
    setSelectedPost(post);
  };

  if (selectedPost) {
    return (
      <PostDetails 
        post={selectedPost} 
        onBack={() => setSelectedPost(null)}
        onEdit={handleEdit}
        onDelete={handleDelete}
      />
    );
  }

  if (isCreatingPost) {
    return (
      <div className="game-guide-container">
        <div className="post-creation-form">
          <h2>글 작성</h2>
          <input
            type="text"
            placeholder="글 제목"
            value={newPostTitle}
            onChange={(e) => setNewPostTitle(e.target.value)}
            className="post"
          />
          <textarea
            placeholder="글 내용"
            value={newPostContent}
            onChange={(e) => setNewPostContent(e.target.value)}
            className="post"
          />
          <div className="button-group">
            <button onClick={handlePostSubmit} className="btn btn-submit">글 작성 완료</button>
            <button onClick={() => setIsCreatingPost(false)} className="btn btn-cancel">취소</button>
          </div>
        </div>
      </div>
    );
  }
>>>>>>> BEDev

const Community = () => {
  return (
<<<<<<< HEAD
    <div>
      <h1>커뮤니티ㄴ</h1>
      <p>여기에 게임 가이드에 대한 내용이 들어갑니다.</p>
=======
    <div className="game-guide-container">
      <h1>커뮤니티</h1>
      <div className="create-post-link" onClick={addPost}>글 작성</div>
      <div className="posts-wrapper"> {/* 스크롤 가능한 새로운 div 추가 */}
        <div className="posts-container">
          {posts.map((post) => (
            <div key={post.id} className="post" onClick={() => handlePostClick(post)}>
              <h2>{post.title}</h2>
              <p>{post.content}</p>
            </div>
          ))}
        </div>
      </div>
>>>>>>> BEDev
    </div>
  );
  
};

export default Community;