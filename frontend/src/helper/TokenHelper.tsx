import { GetRequestBasicToken } from '../types/RequestTypes';

export const handleRefreshToken = async (
  cookies: { [x: string]: any },
  setCookie: Function
): Promise<boolean> => {
  const requestOptions: GetRequestBasicToken = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Accept: 'application/json',
      token: cookies.token,
      refreshToken: cookies.refreshToken,
    },
  };

  const response = await fetch('/user/account/token/refresh', requestOptions);

  if (response.status === 200) {
    if (response.headers.get('token') !== undefined) {
      setCookie('token', response.headers.get('token'));
      return true;
    } else {
      return false;
    }
  } else {
    return false;
  }
};
