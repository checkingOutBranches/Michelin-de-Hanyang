import axios, { AxiosResponse } from "axios";
import { getCookie } from "../cookie";

export const UserApi = axios.create({
  baseURL: process.env.REACT_APP_API,
});

UserApi.interceptors.request.use(
  (config) => {
    config.headers["Content-Type"] = "application/json";
    config.headers["Authorization"] = `Bearer ${getCookie("accessToken")}`;
    return config;
  },
  (error) => {
    console.log(error);
    return Promise.reject(error);
  }
);

interface UserCredentials {
  userId: string;
  password: string;
}

interface LoginResponse {
  data: {
    accessToken: string;
  };
}

// export const loginUserApi = async (userData) => {
//   return await UserApi.post(`/login`, userData);
// };