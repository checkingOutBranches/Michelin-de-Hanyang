import { create } from 'zustand';

interface UserInfo {
    username: string;
    lv: number;
    id: string;
}

interface UserStore {
  userInfo: UserInfo | null;
  setUserInfo: (userInfo: UserInfo | null) => void;
}

// 세션 스토리지에서 사용자 정보를 불러오는 함수
const loadUserInfoFromStorage = (): UserInfo | null => {
  const storedUserInfo = sessionStorage.getItem('userInfo');
  return storedUserInfo ? JSON.parse(storedUserInfo) : null;
}

const useUserStore = create<UserStore>((set) => ({
  userInfo: loadUserInfoFromStorage(), // 초기 상태 설정
  setUserInfo: (userInfo) => {
    sessionStorage.setItem('userInfo', JSON.stringify(userInfo)); // 세션 스토리지에 저장
    set({ userInfo });
  },
}));

export default useUserStore;
