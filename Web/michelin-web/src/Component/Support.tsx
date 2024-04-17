import React, { useState, FormEvent  } from 'react';
import axios from 'axios';
import './Support.style.css';

const Support = () => {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [content, setContent] = useState('');

  const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault(); // 폼 제출 시 페이지 리로드 방지

    try {
      await axios.post(`${process.env.REACT_APP_API}/users/inquiry`, {
        name: name,
        email: email,
        content: content,
      });
      alert('문의가 성공적으로 전송되었습니다.');
      // 폼 초기화
      setName('');
      setEmail('');
      setContent('');
    } catch (error) {
      console.error('문의 전송 에러:', error);
      alert('문의를 전송하는 데 실패했습니다.');
    }
  };
  return (
    <div className="support-container">
      <h1>고객 지원</h1>
      <hr className="header-divider" /> {/* Horizontal line under the header */}
      <div className="faq-section">
        <h2>자주 묻는 질문</h2>
        <ul className="faq-list">
          <li>
            <details>
              <summary>게임 시작 방법이 궁금해요.</summary>
              <p>웹에서 회원가입 진행하신후, 게임 다운로드를 통해 저희 게임을 다운받으시고 플레이하시면 됩니다.</p>
            </details>
          </li>
          <li>
            <details>
              <summary>혹시 엔딩이 있는 게임인가요?</summary>
              <p>현재 max 레벨은 50이며, 해당 레벨 달성 시 두가지 엔딩을 통해서 게임을 완료하실 수 있습니다.</p>
            </details>
            <details>
              <summary>혹시 요리의 개수는 몇개로 지정되어있나요?</summary>
              <p>게임의 재미요소를 위해 정확한 요리의 개수는 공개하고 있지 않지만, 초급 중급 고급 세가지 분류로 요리를 나누었으며, 해당해서 10개 이내로 준비 되어있습니다. </p>
            </details>
            <details>
              <summary>영어 지원은 안되나요?</summary>
              <p>현재 서비스 준비중에 있습니다.</p>
            </details>
          </li>
          {/* Add more FAQs here */}
        </ul>
      </div>
      <div className="inquiry-section">
        <h2>1:1 문의</h2>
        <form onSubmit={handleSubmit}>
          <label htmlFor="name">이름:</label>
          <input type="text" id="name" name="name" value={name} onChange={(e) => setName(e.target.value)} />

          <label htmlFor="email">이메일:</label>
          <input type="email" id="email" name="email" value={email} onChange={(e) => setEmail(e.target.value)} />

          <label htmlFor="message">문의 내용:</label>
          <textarea id="message" name="message" value={content} onChange={(e) => setContent(e.target.value)}></textarea>

          <button type="submit">문의 보내기</button>
        </form>
      </div>
    </div>
  );
};


export default Support;
