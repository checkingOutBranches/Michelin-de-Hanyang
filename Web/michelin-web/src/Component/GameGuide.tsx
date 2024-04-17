import React, { useState } from 'react';
import './GameGuide.style.css';
import loginGif from '../Assets/Images/로그인.gif';
import tutorialGif from '../Assets/Images/튜토리얼.gif';
import HuntingGround from '../Assets/Images/사냥터 진입.gif';
import HuntingGround2 from '../Assets/Images/물고기채집.gif';
import Tavern from '../Assets/Images/주막 집입.gif';  // 주막 
import BuisnessDone from '../Assets/Images/주막영업결과.gif';  // 주막 이미지
import BuisnessStart from '../Assets/Images/영업시작.gif';  // 주막 이미지
import MarketBuy from '../Assets/Images/주막구매.gif';  // 장터 이미지
import MarketSell from '../Assets/Images/주막판매.gif';  // 장터 이미지
import Quest from '../Assets/Images/퀘스트.gif';  // 장터 이미지
import Setting from '../Assets/Images/esc.gif';  // 장터 이미지

const GameGuide = () => {
  const [activeTab, setActiveTab] = useState('tavern');  // 초기 탭 설정
  return (
    <div className="game-guide">
      <h1>게임 가이드</h1>
      
      {/* 로그인 단계 */}
      <div className="guide-section">
        <h2>1. 로그인</h2>
        <p>게임에 접속하기 위해 로그인을 진행합니다.</p>
        <img src={loginGif} alt="로그인 단계" />
      </div>
      
      {/* 튜토리얼 단계 */}
      <div className="guide-section">
        <h2>2. 튜토리얼</h2>
        <p>첫 접속 시 나타나는 게임 튜토리얼 단계입니다.</p>
        <img src={tutorialGif} alt="튜토리얼 단계" />
      </div>
      {/* 튜토리얼 단계 */}
      <div className="guide-section">
        <h2>3. 메인에서 주막, 사냥터, 장터로</h2>
      </div>

      {/* 탭 네비게이션 */}
      <div className="tabs">
        <button onClick={() => setActiveTab('tavern')}>주막 [밤]</button>
        <button onClick={() => setActiveTab('huntingGround')}>사냥터 [낮]</button>
        <button onClick={() => setActiveTab('market')}>장터</button>
      </div>

      {activeTab === 'tavern' && (          
      <div className="control-section">
        <h3>게임 조작법</h3>
        <p><span className="key">Z</span>요리 선택하기</p>
        <p><span className="key">X</span> 요리 전달하기</p>
        <p>*주막에서는 시간이 다 되거나, 영업종료를 누르면 낮으로 변경됩니다.</p>
        <p>*오늘의 메뉴를 등록하지 않으면, 영업시작을 할 수 없습니다.</p>
      </div>
      )}
      {/* 주막 가이드 */}
      {activeTab === 'tavern' && (
        <div className="guide-section">
          <h2>메인화면에서 주막으로</h2>
          <p>밤 시간동안만 주막에 진입이 되며, 레시피를 배운 후 오늘의 메뉴를 설정 할 수 있습니다.</p>
          <img src={Tavern} alt="주막" />
        </div>
      )}


      {activeTab === 'tavern' && (
        <div className="guide-section">
          <h2>영업 시작</h2>
          <p>오늘의 메뉴 등록 후, 영업을 시작하여 손님이 원하는 음식을 서빙하며 영업을 진행합니다.</p>
          <img src={BuisnessStart} alt="주막" />
        </div>
      )}

      {activeTab === 'tavern' && (
        <div className="guide-section">
          <h2>영업 종료</h2>
          <p>영업 종료 후, 팔린 메뉴의 수량과 수익을 확인 할 수 있습니다.</p>
          <img src={BuisnessDone} alt="주막" />
        </div>
      )}

      {activeTab === 'huntingGround' && (          
      <div className="control-section">
        <h3>게임 조작법</h3>
        <p><span className="key">Z</span> 말 타기</p>
        <p><span className="key">X</span> 공격</p>
        <p><span className="key">C</span> 물고기 채집</p>
        <p><span className="key">SPACEBAR</span> 물건 주우기</p>
        <p>*사냥터에서는 시간이 다 되거나, 나가거나, 죽으면 밤시간으로 변경됩니다.</p>
      </div>
      )}

      {/* 사냥터 가이드 */}
      {activeTab === 'huntingGround' && (          
        <div className="guide-section">
          
          <h2>메인화면에서 사냥터로</h2>
          <p>낮시간은 사냥터에서 사냥을 합니다.</p>
          <img src={HuntingGround} alt="사냥터 진입" />
        </div>
      )}
      {activeTab === 'huntingGround' && (
      <div className="guide-section">
        <h2>초급, 중급, 고급 사냥맵</h2>
        <p>초급맵, 중급맵, 고급맵으로 나뉘어진 맵에서 각기 다른 레벨의 몬스터를 사냥합니다.</p>
        <img src={HuntingGround2} alt="사냥터 진입" />
      </div>
      )}


      {/* 장터 가이드 */}
      {activeTab === 'market' && (
        <div className="guide-section">
          <h2>메인화면에서 장터로</h2>
          <p>장터에서는 아이템을 구매할 수 있다.</p>
          <img src={MarketBuy} alt="장터" />
        </div>
        )}

        {/* 장터 가이드 */}
      {activeTab === 'market' && (
        <div className="guide-section">
          <h2>메인화면에서 장터로</h2>
          <p>장터에서는 아이템을 구매할 수 있다.</p>
          <img src={MarketSell} alt="장터" />
        </div>
        )}

        {/* 퀘스트 */}
        <div className="guide-section">
        <h2>4. 퀘스트 </h2>
        <p>해당 조건 만족 시 퀘스트 완료 조건이 등장하며, 칭호가 업그레이드 됩니다.</p>
        <img src={Quest} alt="퀘스트 단계" />
      </div>

      {/* 게임 설정 ESC */}
      <div className="guide-section">
        <h2>5. 게임 종료 및 설정 </h2>
        <p>ESC 버튼을 누르면 저장하기, 무음모드, 게임종료가 있습니다.</p>
        <img src={Setting} alt="퀘스트 단계" />
      </div>
    </div>
  );
};

export default GameGuide;
