import React from 'react';
import './GameIntro.style.css'; // Import the CSS file for styles
import GameBackground from '../Assets/Images/GameBackground.png'; // Adjust the path as necessary
import ContentFrame from '../Assets/Images/ContentFrame.png'; // Adjust the path as necessary
import ContentFrame2 from '../Assets/Images/ContentFrame2.png'; // Adjust the path as necessary

const GameIntro = () => {
  return (
    <div className="game-intro-container">
      <img src={ContentFrame} alt="Frame" className="box-frame"/>
      <img src={ContentFrame2} alt="Content Frame" className="content-frame-2"/>
      <h1>게임 소개</h1>
      <p>여기에 게임 가이드에 대한 내용이 들어갑니다.</p>
    </div>
  );
};

export default GameIntro;