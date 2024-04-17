<<<<<<< HEAD
import React, { useState, useEffect } from 'react';
import './MainPage.style.css'
import FrameImage from '../Assets/Images/Frame1.png'
import BottomFrame from '../Assets/Images/FrameBottom.png'
import Frame4 from '../Assets/Images/Frame4.png'
import NavBar from '../Component/NavBar';
import { Routes, Route } from 'react-router-dom';
import GameGuide from '../Component/GameGuide'; 
import GameIntro from '../Component/GameIntro';
import Community from '../Component/Community';



const MainPage = () => {
    const [content, setContent] = useState({ image: Frame4});


    return (
        <>
            <div className="back">
                <img src={FrameImage} alt="Frame" className="frame top-left"/> 

                <section className='section'>
                <NavBar setContent={setContent} />
                    <div 
                        className="content-box" 
                        style={{ backgroundImage: `url(${content.image})` }}
                    >
                    </div>
                </section>
                <Routes>
                    <Route path="/" element={<GameIntro />} />
                    <Route path="/guides" element={<GameGuide />} />
                    <Route path="/community" element={<Community />} />
                </Routes>

            </div>
            <div className="diamond-button-frame-container">
                <div className="diamond-button-container">
                    <div className="diamond-button-text">게임 다운로드</div>
                </div>
            </div>

            <div className="bottom-frame-container">
                <img src={BottomFrame} alt="Frame" className="bottom-frame"/>
            </div>

            <footer className="footer">
                <div>@Michelin de Hanyang</div>
            </footer>
        </>
    );
=======
import React, { useState, FormEvent, useEffect } from "react";
import "./MainPage.style.css";
import FrameImage from "../Assets/Images/Frame1.png";
import BottomFrame from "../Assets/Images/FrameBottom.png";
import Frame4 from "../Assets/Images/Frame4.png";
import NavBar from "../Component/NavBar";
import { Routes, Route, Link } from "react-router-dom"; // Import Link from react-router-dom
import GameGuide from "../Component/GameGuide";
import GameIntro from "../Component/GameIntro";
import Community from "../Component/Community";
import Ranking from "../Component/Ranking";
import Support from "../Component/Support";
import TopTitle from "../Assets/Images/TopTitle.png"; // Importing the TopTitle image
import axios from 'axios';
import { useCookies } from 'react-cookie';
import useUserStore from '../store'; // Zustand store import


interface UserInfo {
  name: string;
  level: number;
  id: string;
}

const MainPage = () => {
  const [content, setContent] = useState({ image: Frame4 });
  const [id, setId] = useState('');
  const [name, setName] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [isSigningUp, setIsSigningUp] = useState(false); // State to toggle between login and signup
  const [accessToken, setAccessToken] = useState('');
  const [refreshToken, setRefreshToken] = useState('');
  const [cookies, setCookie] = useCookies(['accessToken', 'refreshToken']);
  const { setUserInfo, userInfo: storedUserInfo } = useUserStore(); // Zustand 상태 및 액션 사용
  const { userInfo } = useUserStore();
  const [isIdUnique, setIsIdUnique] = useState(false); // 아이디 중복 상태


  const checkIdDuplicate = async () => {
    if (!id) {
      alert('아이디를 입력해주세요.');
      setIsIdUnique(false); // 아이디 입력을 강제하기 위해
      return;
    }
    try {
      const response = await axios.post(`${process.env.REACT_APP_API}/auth/check/${id}`);
      console.log(response.data)
      if (response.data) {
        alert('사용 가능한 아이디입니다.');
        setIsIdUnique(true); // ID가 사용 가능할 때 상태를 true로 설정
      } else {
        alert('이미 사용 중인 아이디입니다.');
        setIsIdUnique(false); // ID가 중복일 때 상태를 false로 설정

      }
    } catch (error) {
      console.error('ID 중복 검사 에러:', error);
      alert('아이디 중복 검사 중 오류가 발생했습니다.');
    }
  };

  useEffect(() => {
    console.log(userInfo); // 콘솔에서 userInfo 상태 출력
  }, [userInfo]);

  useEffect(() => {
    if (cookies.accessToken && !storedUserInfo) {

    }
  }, [cookies.accessToken, storedUserInfo, setUserInfo]);
  
  const handleDownload = () => {
    window.location.href = 'https://j10a609.p.ssafy.io/download/new_build.zip';
  };

  const handleLogin = async (event: FormEvent) => {
    event.preventDefault();
    try {
      const response = await axios.post(`${process.env.REACT_APP_API}/auth/login`, {
        id,
        password,
      });
      const { accessToken, refreshToken } = response.data.token;
      const userData = response.data.data;
  
      setCookie('accessToken', accessToken, { path: '/' });
      setCookie('refreshToken', refreshToken, { path: '/' });
      setUserInfo(userData); // Zustand에 사용자 정보 저장
    } catch (error) {
      console.error('Login error:', error);
    }
  };
  

  
  const handleSignUp = async (event: FormEvent) => {
    event.preventDefault();
  
    // 필수 필드 검증
    if (!id || !name || !password || !confirmPassword) {
      alert('모든 필드를 채워주세요.');
      return;
    }
  
    // 비밀번호 일치 확인
    if (password !== confirmPassword) {
      alert('비밀번호가 일치하지 않습니다.');
      return;
    }
  
    // 아이디 중복 검사 확인
    if (!isIdUnique) {
      alert('아이디 중복 검사를 진행해주세요.');
      return;
    }
  
    try {
      await axios.post(`${process.env.REACT_APP_API}/auth/signup`, {
        id,
        password,
        name,
      });
      alert('Signup successful. Please login.');
      setIsSigningUp(false); // Switch back to the login form after successful signup
    } catch (error) {
      console.error('Signup error:', error);
    }
  };

  const handleLogout = async () => {
    try {
      await axios.post(`${process.env.REACT_APP_API}/auth/logout`, {}, {
        headers: {
          Authorization: `Bearer ${cookies.accessToken}`, // accessToken을 헤더에 추가
        },
      });
  
      // 로그아웃 성공 후 쿠키 삭제 및 사용자 정보 초기화
      setCookie('accessToken', '', { path: '/', expires: new Date(0) });
      setCookie('refreshToken', '', { path: '/', expires: new Date(0) });
      setUserInfo(null);
    } catch (error) {
      console.error('Logout error:', error);
    }
  };

  return (
    <>
      <div className="back">
        <img src={FrameImage} alt="Frame" className="frame top-left" />
        <img src={TopTitle} alt="TopTitle" className="frame top-right" /> {/* Adding TopTitle image */}
        <section className="section">
          <NavBar setContent={setContent} />
          <div
            className="content-box"
            style={{ backgroundImage: `url(${content.image})` }}
          ></div>
        </section>
        <Routes>
          <Route path="/" element={<GameIntro />} />
          <Route path="/guides" element={<GameGuide />} />
          <Route path="/community" element={<Community />} />
          <Route path="/ranking" element={<Ranking />} />
          <Route path="/support" element={<Support />} />
        </Routes>
      </div>
      <div className="button-login-wrapper">
        <div className="diamond-button-frame-container">
          <div className="diamond-button-container" onClick={handleDownload}>
            <div className="diamond-button-text">게임 다운로드</div>
          </div>
        </div>
        <div className="button-login-wrapper">
        {userInfo ? (
        <div className="login-container">
          <p>{userInfo.username}님, 환영합니다!</p> {/* username 사용 */}
          <p>레벨: {userInfo.lv}</p> {/* level 사용 */}
          <p>유저 ID: {userInfo.id}</p>
          <button onClick={handleLogout}>로그아웃</button> {/* 로그아웃 버튼 */}
        </div>
      ) : (
        <div className="login-container">
        {isSigningUp ? (
          <form className="contentInput" onSubmit={handleSignUp}>
          <div className="inputWithIcon">
            <input type="text" value={id} onChange={(e) => setId(e.target.value)} placeholder="아이디" required />
            <button type="button" onClick={checkIdDuplicate}>중복 검사</button>
            <input type="text" value={name} onChange={(e) => setName(e.target.value)} placeholder="이름" required />
            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="비밀번호" required />
            <input type="password" value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} placeholder="비밀번호 확인" required />
            <button type="submit" disabled={!id || !name || !password || !confirmPassword || !isIdUnique}>회원가입</button>
          </div>
        </form>
        
        ) : (
          <form className="contentInput" onSubmit={handleLogin}>
            <div className="inputWithIcon">
              <input type="text" value={id} onChange={(e) => setId(e.target.value)} placeholder="아이디" required />
              <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="비밀번호" required />
              <button type="submit">LOGIN</button>
              <button type="button" onClick={() => setIsSigningUp(true)}>회원가입</button>
            </div>
          </form>
        )}
      </div>
    )}
  </div>
      </div>
      <div className="bottom-frame-container">
        <img src={BottomFrame} alt="Frame" className="bottom-frame" />
      </div>
      <footer className="footer">
        <div>@Michelin de Hanyang</div>
      </footer>
    </>
  );
>>>>>>> BEDev
};

export default MainPage;