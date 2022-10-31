import { Button, Box, TextField, Typography } from '@mui/material';
import { useState } from 'react';

export const LoginPage = () => {
  const [email, setEmail] = useState<string>('');
  const [password, setPassword] = useState<string>('');

  const loginRequest = async () => {
    const loginDetails = {
      email: email,
      password: password,
    };

    const requestOptions = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
      },
      body: JSON.stringify(loginDetails),
    };

    const response = await fetch('/auth/login', requestOptions);

    if (response.status === 200) {
      console.log('Success');
    } else {
      console.log('/auth/login POST Request Failed');
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
