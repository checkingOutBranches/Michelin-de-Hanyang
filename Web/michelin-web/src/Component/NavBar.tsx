import React from 'react';
import './NavBar.style.css'; // 스타일시트
import Frame4 from '../Assets/Images/Frame4.png'
import { Link } from 'react-router-dom';


interface NavBarProps {
    setContent: React.Dispatch<React.SetStateAction<{ image: string;}>>;
  }

const NavBar: React.FC<NavBarProps> = ({ setContent }) => {
    return (
        <nav className="navbar">
            <ul className="nav-links">
                <li className="nav-item" onClick={() => setContent({ image: Frame4, })}>
                    <Link to="/" className="nav-link">게임 소개</Link>
                </li>
                <li className="nav-item" onClick={() => setContent({ image: Frame4, })}>
                    <Link to="/guides" className="nav-link">게임 가이드</Link>
                </li>
                <li className="nav-item" onClick={() => setContent({ image: Frame4})}>
                    <Link to="/community" className="nav-link">커뮤니티</Link>
                </li>
                <li className="nav-item" onClick={() => setContent({ image: Frame4})}>
                    <Link to="/ranking" className="nav-link">랭킹</Link>
                </li>
                <li className="nav-item" onClick={() => setContent({ image: Frame4, })}>
                    <Link to="/support" className="nav-link">고객지원</Link>
                </li>
            </ul>
        </nav>
    );
};

export default NavBar;