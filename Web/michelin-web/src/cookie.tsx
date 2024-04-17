import Cookies from "js-cookie";

export const setCookie = (name: string, value: string, options?: Cookies.CookieAttributes): void => {
  Cookies.set(name, value, { path: '/', ...options });
};

export const getCookie = (name: string): string | undefined => {
  return Cookies.get(name);
};

export const removeCookie = (name: string): void => {
  Cookies.remove(name, { path: '/' });
};
