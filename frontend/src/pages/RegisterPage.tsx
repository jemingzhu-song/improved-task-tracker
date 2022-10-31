import { Button, Box, TextField, Typography } from '@mui/material';
import { useState } from 'react';

export const RegisterPage = () => {
  const [firstName, setFirstName] = useState<string>('');
  const [lastName, setLastName] = useState<string>('');
  const [email, setEmail] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [confirmPassword, setConfirmPassword] = useState<string>('');

  const registerRequest = async () => {
    const registerDetails = {
      firstName: firstName,
      lastName: lastName,
      email: email,
      password: password,
    };

    const requestOptions = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
      },
      body: JSON.stringify(registerDetails),
    };

    const response = await fetch('/auth/register', requestOptions);

    if (response.status === 200) {
      console.log('Success');
    } else {
      console.log('/auth/register POST Request Failed');
    }
  };

  return (
    <Box sx={{ marginTop: '60px', display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
      <Typography variant='h2'>Register</Typography>
      <TextField
        sx={{ minWidth: '350px', marginTop: '10px' }}
        value={firstName}
        onChange={(e) => {
          setFirstName(e.target.value);
        }}
        variant='standard'
        label='First Name'
      ></TextField>
      <TextField
        sx={{ minWidth: '350px', marginTop: '10px' }}
        value={lastName}
        onChange={(e) => {
          setLastName(e.target.value);
        }}
        variant='standard'
        label='Last Name'
      ></TextField>
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
      <TextField
        sx={{ minWidth: '350px', marginTop: '10px' }}
        value={confirmPassword}
        type='password'
        onChange={(e) => {
          setConfirmPassword(e.target.value);
        }}
        variant='standard'
        label='Confirm Password'
      ></TextField>
      <Button
        sx={{ marginTop: '10px' }}
        onClick={() => {
          registerRequest();
        }}
      >
        <Typography variant='body1' sx={{ textTransform: 'none' }}>
          Register
        </Typography>
      </Button>
    </Box>
  );
};
