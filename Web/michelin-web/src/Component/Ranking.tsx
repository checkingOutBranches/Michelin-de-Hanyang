import React, { useState, useEffect } from 'react';
import './Ranking.style.css';
import axios from 'axios';

interface RankingData {
  id: string;
  questSuccess: number;
  lv: number;
  username: string;
  exp: number;
}

const questTitles = ['견습생', '초급요리사', '중급요리사', '고급요리사', '궁중요리사'];

const Ranking = () => {
  const [rankings, setRankings] = useState<RankingData[]>([]);
  const [selectedTab, setSelectedTab] = useState(0);

  const fetchRankings = async () => {
    try {
      let combinedRankings: RankingData[] = [];
      for (let i = 4; i >= 0; i--) {
        const response = await axios.get<RankingData[]>(`${process.env.REACT_APP_API}/users/questSuccess/${i}`);
        combinedRankings = [...combinedRankings, ...response.data];
      }
      combinedRankings.sort((a, b) => b.lv - a.lv);
      setRankings(combinedRankings);
    } catch (error) {
      console.error('Failed to fetch rankings:', error);
    }
  };

  useEffect(() => {
    fetchRankings();
  }, []);

  const filteredRankings = rankings.filter(ranking => ranking.questSuccess === selectedTab);

  return (
    <div className="ranking-container">
      <h1>게임 랭킹</h1>
      <div className="rankingtabs" style={{ position: 'relative', top: '-20px', zIndex: 2 }}>
        {questTitles.map((title, index) => (
          <button
            key={index}
            className={selectedTab === index ? 'active' : ''}
            onClick={() => setSelectedTab(index)}
          >
            {title}
          </button>
        ))}
      </div>
      <div className="ranking-inner-container">
        {filteredRankings.map((ranking, index) => (
          <div key={index} className="ranking-item">
            <div className="rank">{index + 1}등</div>
            <div className="name">{ranking.username}</div>
            <div className="score-bar">레벨: {ranking.lv}</div>
            <div className="score-bar">퀘스트 완료: {ranking.questSuccess}</div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Ranking;
