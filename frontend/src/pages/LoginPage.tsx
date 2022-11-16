import { Button, Box, TextField, Typography } from '@mui/material';
import { useState } from 'react';
import { useCookies } from 'react-cookie';
import { useNavigate } from 'react-router-dom';
import { failedRequestLogger } from '../helper/RequestHelper';
import { PostRequestBasic } from '../types/RequestTypes';

export const LoginPage = () => {
  const [cookies, setCookie] = useCookies();
  const navigate = useNavigate();
  const [email, setEmail] = useState<string>('');
  const [password, setPassword] = useState<string>('');

  const loginRequest = async () => {
    let requestBody = new URLSearchParams();
    requestBody.append('email', email);
    requestBody.append('password', password);

    const requestOptions: PostRequestBasic = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8',
        Accept: 'application/json',
      },
      body: requestBody,
    };

    const response = await fetch('/user/account/login', requestOptions);

    if (response.status === 200) {
      setCookie('token', response.headers.get('token'));
      setCookie('refreshToken', response.headers.get('refreshToken'));
      setCookie('userId', response.headers.get('userId'));
      navigate('/');
    } else {
      failedRequestLogger('/user/account/login', 'POST', response);
      alert('Login Failed. Incorrect Email or Password');
    }
  };

  return (
    <Box
      sx={{
        marginTop: '60px',
        display: 'flex',
        justifyContent: 'center',
        flexDirection: 'column',
        alignItems: 'center',
      }}
    >
      <Typography variant='h2'>Login</Typography>
      <TextField
        sx={{ minWidth: '350px', marginTop: '10px' }}
        value={email}
        onChange={(e) => {
          setEmail(e.target.value);
        }}
        variant='standard'
        label='Email'
      ></TextField>
      <TextField
        sx={{ minWidth: '350px', marginTop: '10px' }}
        value={password}
        type='password'
        onChange={(e) => {
          setPassword(e.target.value);
        }}
        variant='standard'
        label='Password'
      ></TextField>
      <Button sx={{ marginTop: '10px' }}>
        <Typography
          variant='body1'
          sx={{ textTransform: 'none' }}
          onClick={() => {
            loginRequest();
          }}
        >
          Login
        </Typography>
      </Button>
    </Box>
  );
};
