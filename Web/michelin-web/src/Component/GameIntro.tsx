import React, { useState } from 'react';
import './GameIntro.style.css';
import GameBackground from '../Assets/Images/GameBackground.png';
import GameBackground2 from '../Assets/Images/GameBackground2.png'; 
import GameBackground3 from '../Assets/Images/GameBackground3.png'; 
import GameBackground4 from '../Assets/Images/GameBackground4.png'; 
import GameBackground5 from '../Assets/Images/GameBackground5.png'; 
import ContentFrame from '../Assets/Images/ContentFrame.png';
import ContentFrame2 from '../Assets/Images/ContentFrame2.png';
import GameName from '../Assets/Images/Michelin De 한양.png';

const GameIntro = () => {
  const [textIndex, setTextIndex] = useState(0);
  const [section, setSection] = useState(0); 
  const [accumulatedTexts, setAccumulatedTexts] = useState<string[]>([]);

  const textContent = [
  [
    "당신은 현대에서 유명한 미슐랭 스타 요리사였습니다.",
    "사업 확장과 다양한 프로젝트에 손을 뻗으며 성공의 정점에 있었으나, 운명은 잔혹했습니다.",
    "과도한 욕심과 압박은 결국 사업의 실패로 이어졌고, 그 충격으로 사랑하는 아버지마저 잃게 됩니다.",
    "아버지의 유품 중 하나인 신비로운 손목시계를 건드리던 중, 예상치 못한 시간 여행을 하게 되며 조선 시대로 가게 됩니다.",
    "이제 당신은 조선 시대의 허름한 주막에서 시작해, 다시 한번 요리사로서의 명성을 되찾기 위한 여정을 시작합니다."
  ],
  [
    "새로운 시대에서의 첫 도전은 자연에서 직접 재료를 채집하고 사냥하는 것입니다.",
    "조선 시대의 냉혹한 현실 속에서 당신은 낮 동안 재료를 모으고, 밤이 되기 전에 요리 준비를 마쳐야 합니다.",
    "밤시간동안에는 호랑이가 등장해 제대로된 재료 획득이 어려웁니다."
  ],
  [
    "채집과 사냥으로 얻은 재료들로 조선 시대의 요리를 재현해봅니다.",
    "당신의 손끝에서 탄생하는 밥, 김치, 생선구이부터 비빔밥, 불고기, 갈비찜까지, 각 요리는 당신이 전해주는 이야기와 함께 살아납니다.",
    "요리는 단지 배를 채우는 행위가 아닌, 문화를 전달하고 이어가는 소중한 과정임을 깨닫게 됩니다."
  ],
  [
    "조선 시대에서도 당신의 요리 실력은 주목받으며, 매월 열리는 천하제일요리대회에 참가하게 됩니다.",
    "이곳에서 당신은 다른 요리사들과의 경쟁을 통해 궁중 요리사로서의 자리를 노려볼 수 있으며, 이는 단순한 영예를 넘어선 도전입니다."
  ],
  [
    "이 게임은 단지 역사적 배경 속에서 요리를 하는 것이 아니라, 한국의 전통 문화와 한식의 아름다움을 세계에 전파하는 목적을 갖고 있습니다.",
    "플레이어는 메타버스와 게임의 결합을 통해, 조선 시대의 요리사로서의 삶을 체험하며, 한국의 맛과 문화를 깊이 있게 이해할 기회를 갖게 됩니다.",
    "지금 바로 <Michelin de 한양>을 플레이 해보세요."
  ],
];

  const titles = [
    "[ 조선 시대의 맛을 재현하다: 전통 한식의 여정에 오신 것을 환영합니다 ]",
    "[ 하루의 시작: 채집과 사냥 ]",
    "[ 요리의 마법: 전통 한식 만들기 ]",
    "[ 천하제일요리대회: 궁중 요리사를 향한 도전 ]",
    "[ 웹 게임으로 전통 한식 탐험하기 ]",

  ];

  const handleNext = () => {
    const nextTextIndex = textIndex + 1;
  
    if (nextTextIndex < textContent[section].length) {

      setTextIndex(nextTextIndex);
      setAccumulatedTexts([...accumulatedTexts, textContent[section][textIndex]]);
    } else if (section + 1 < textContent.length) {
      setSection(section + 1);
      setTextIndex(0);
      setAccumulatedTexts([]);
    }
  };

  const getBackgroundImage = () => {
    switch (section) {
      case 0: return GameBackground4;
      case 1: return GameBackground2;
      case 2: return GameBackground3;
      case 4: return GameBackground5;
      default: return GameBackground;
    }
  };

  return (
    <div 
      className="game-intro-container" 
      onClick={handleNext} 
      style={{ backgroundImage: `url(${getBackgroundImage()})` }}
    >
      <img src={ContentFrame} alt="Frame" className="box-frame"/>
      <img src={ContentFrame2} alt="Content Frame" className="content-frame-2"/>
      <h1><img src={GameName} alt="Game Name" style={{ marginTop: '-20px', width: '90%' }}/></h1>
      <h2 className='title'>{titles[section]}</h2>
      <div className="text-box">
        {accumulatedTexts.map((text, index) => (
          <p key={index}>{text}</p>
        ))}
        <p>
          {section === textContent.length - 1 && textIndex === textContent[section].length - 1 ? (
            <span className="highlight">{textContent[section][textIndex]}</span>
          ) : (
            textContent[section][textIndex]
          )}
        </p>
      </div>
    </div>
  );

};

export default GameIntro;