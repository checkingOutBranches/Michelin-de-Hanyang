import React, {useEffect, useState} from 'react';
import './PostDetails.style.css';
import { useNavigate } from 'react-router-dom'; // useNavigate 훅 추가
import communityFrameImage from '../Assets/Images/CommunityFrame.png';
import axios from 'axios';
import { useCookies } from 'react-cookie'; // Import useCookies hook from react-cookie
import useUserStore from '../store'; // Zustand store import

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
  replies: Reply[];
};

type PostDetailsProps = {
  post: Post;
  onBack: () => void;
  onEdit: (post: Post) => void; 
  onDelete: (postId: string) => void; 
};

const PostDetails: React.FC<PostDetailsProps> = ({ post, onBack }) => {
  const navigate = useNavigate(); // useNavigate 훅 사용
  const creationDate = new Date(post.createdAt).toLocaleDateString();
  const creationTime = new Date(post.createdAt).toLocaleTimeString();
  const [isEditing, setIsEditing] = useState(false);
  const [editableTitle, setEditableTitle] = useState(post.title || '');
  const [editableContent, setEditableContent] = useState(post.content || '');
  const [postDetails, setPostDetails] = useState<Post>(post);
  const [replyContent, setReplyContent] = useState(''); // 댓글 내용을 위한 상태
  const [cookies] = useCookies(['accessToken']); // Retrieve cookies
  const { userInfo } = useUserStore();


  useEffect(() => {
    if (!isEditing) {
      setEditableTitle(postDetails.title || '');
      setEditableContent(postDetails.content || '');
    }
  }, [isEditing, postDetails]);

  useEffect(() => {
    const fetchPostDetails = async () => {
      try {
        const response = await axios.get(`${process.env.REACT_APP_API}/board/${post.id}`);
        setPostDetails(response.data); // API로부터 받은 데이터를 상태에 저장
        console.log(response.data); // 데이터 로깅으로 정상적인 데이터 수신 확인
      } catch (error) {
        console.error('Failed to fetch post details:', error);
      }
    };
  
    fetchPostDetails();
  }, [post]);

// 댓글 작성 핸들러
const handleReplySubmit = async (event: React.FormEvent) => {
  event.preventDefault();
  if (!replyContent) return; // 댓글 내용이 비어있으면 리턴

  try {
    const response = await axios.post(`${process.env.REACT_APP_API}/board/${post.id}/replies`, {
      reply: replyContent,
    }, {
      headers: {
        Authorization: `Bearer ${cookies.accessToken}`, // Use the accessToken from cookies
      },
    });

    // 댓글 작성 후 댓글 목록을 업데이트하기 위해 포스트 상세 정보를 다시 가져옵니다.
    navigate('/community'); // 삭제 후 커뮤니티 페이지로 리다이렉션
    setReplyContent(''); // 댓글 작성 후 입력 필드 초기화
  } catch (error) {
    console.error('Failed to post reply:', error);
  }
};
const handleEdit = async (event?: React.FormEvent) => {
    if (event) {
      event.preventDefault();
    }
    if (!isEditing) {
      setIsEditing(true);
    } else {
      try {
        const response = await axios.patch(`${process.env.REACT_APP_API}/board/${postDetails.id}`, {
          title: editableTitle,
          content: editableContent,
        }, {
          headers: {
            Authorization: `Bearer ${cookies.accessToken}`,
          },
        });
        setIsEditing(false);
        navigate('/community'); // 삭제 후 커뮤니티 페이지로 리다이렉션
      } catch (error) {
        console.error('Failed to edit post:', error);
      }
    }
  };

  const handleDelete = async () => {
    const confirmDelete = window.confirm("Are you sure you want to delete this post?");
    if (confirmDelete) {
      try {
        await axios.delete(`${process.env.REACT_APP_API}/board/${postDetails.id}`, {
          headers: {
            Authorization: `Bearer ${cookies.accessToken}`,
          },
        });
        navigate('/community'); // 삭제 후 커뮤니티 페이지로 리다이렉션
      } catch (error) {
        console.error('Failed to delete post:', error);
      }
    }
  };

  return (
    <div className="post-details-container" style={{ backgroundImage: `url(${communityFrameImage})` }}>
      <button onClick={onBack}>Back</button>
      <div>
        {userInfo?.username === post.username && (
          <>
            <button onClick={handleEdit}>수정</button>
            <button onClick={handleDelete}>삭제</button>
          </>
        )}
      </div>
      {isEditing ? (
        <>
          <form onSubmit={handleEdit}>
            <input
              type="text"
              value={editableTitle}
              onChange={(e) => setEditableTitle(e.target.value)}
            />
            <textarea
              value={editableContent}
              onChange={(e) => setEditableContent(e.target.value)}
            />
            <div className="button-row"> {/* 버튼을 같은 행에 위치시키기 위한 div */}
              <button type="submit" className="save-cancel-btn">저장</button>
              <button onClick={() => setIsEditing(false)} className="save-cancel-btn">취소</button>
            </div>
          </form>
        </>
      ) : (
        <>
      {/* <button onClick={() => onDelete(post.id)}>Delete</button> */}
      <div className="post-header">
        {post.title && <h1>[ {post.title} ]</h1>}
        <p className="author-details">작성자 이름: {post.username} , 작성 일자: {creationDate} , 작성 시각: {creationTime}</p>
      </div>
      {post.content && (
        <div className="content-container">
          <p>{post.content}</p>
        </div>
      )}
        </>
      )}
{!isEditing && (
  <>
    <div className="reply-form">
      <form onSubmit={handleReplySubmit}>
        <textarea
          value={replyContent}
          onChange={(e) => setReplyContent(e.target.value)}
          placeholder="댓글을 작성하세요"
        />
        <button type="submit">댓글 작성</button>
      </form>
    </div>
    <hr className="comment-separator" />
    <div className="comments-section">
        {postDetails.replies && postDetails.replies.length > 0 && (
          <>
            {postDetails.replies.map((reply) => (
              <div key={reply.id} className="comment">
                <p className="comment-author">댓글 작성자: {reply.username}</p>
                <p className="comment-date">댓글 작성 시각: {new Date(reply.createdAt).toLocaleDateString()} {new Date(reply.createdAt).toLocaleTimeString()}</p>
                <p className="comment-content">{reply.content}</p>
              </div>
            ))}
          </>
        )}
    </div>
  </>
)}
      </div>
  );
};

export default PostDetails;
