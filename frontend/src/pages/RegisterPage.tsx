import { Button, Box, TextField, Typography } from '@mui/material';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { failedRequestLogger } from '../helper/RequestHelper';
import { PostRequestBasic } from '../types/RequestTypes';

export const RegisterPage = () => {
  const navigate = useNavigate();
  const [firstName, setFirstName] = useState<string>('');
  const [lastName, setLastName] = useState<string>('');
  const [email, setEmail] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [confirmPassword, setConfirmPassword] = useState<string>('');

  const registerRequest = async () => {
    if (registerValidation() === false) {
      return;
    }

    const registerDetails = {
      firstName: firstName,
      lastName: lastName,
      email: email,
      password: password,
      tasks: [],
      roles: [],
    };

    const requestOptions: PostRequestBasic = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
      },
      body: JSON.stringify(registerDetails),
    };

    const response = await fetch('/user/account/register', requestOptions);

    if (response.status === 200) {
      console.log('Success');
      navigate('/login');
    } else {
      failedRequestLogger('/user/account/register', 'POST', response);
    }
  };

  const registerValidation = (): boolean => {
    if (firstName === null || firstName.length < 1 || firstName.length > 50) {
      alert('Please enter a first name between 1 and 50 characters');
      return false;
    }
    if (lastName === null || lastName.length < 1 || lastName.length > 50) {
      alert('Please enter a last name between 1 and 50 characters');
      return false;
    }
    if (
      email.match(
        /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      ) === null
    ) {
      alert('Please enter a valid email');
      return false;
    }
    if (password !== confirmPassword) {
      alert('Passwords do not match');
      return false;
    }
    return true;
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
