export type RequestMethod = 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE';

export type GetRequestBasic = {
  method: 'GET';
  headers: {
    'Content-Type': string;
    Accept: string;
  };
};

export type GetRequestBasicToken = {
  method: 'GET';
  headers: {
    'Content-Type': string;
    Accept: string;
    token: string;
    refreshToken: string;
  };
};

export type PostRequestBasic = {
  method: 'POST';
  headers: {
    'Content-Type': string;
    Accept: string;
  };
  body: any;
};

export type PostRequestBasicToken = {
  method: 'POST';
  headers: {
    'Content-Type': string;
    Accept: string;
    token: string;
    refreshToken: string;
  };
  body: any;
};

export type PutRequestBasicToken = {
  method: 'PUT';
  headers: {
    'Content-Type': string;
    Accept: string;
    token: string;
    refreshToken: string;
  };
  body: any;
};

export type PostRequestEmptyBody = {
  method: 'POST';
  headers: {
    'Content-Type': string;
    Accept: string;
  };
};

export type PostRequestEmptyBodyToken = {
  method: 'POST';
  headers: {
    'Content-Type': string;
    Accept: string;
    token: string;
  };
};

export type DeleteRequestEmptyBodyToken = {
  method: 'DELETE';
  headers: {
    'Content-Type': string;
    Accept: string;
    token: string;
    refreshToken: string;
  };
};
