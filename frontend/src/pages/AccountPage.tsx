import { Box, Button, TextField, Typography } from '@mui/material';
import React, { useEffect, useState } from 'react';
import { useCookies } from 'react-cookie';
import { useNavigate } from 'react-router-dom';
import { handleRefreshToken } from '../helper/TokenHelper';
import { GetRequestBasicToken, PutRequestBasicToken } from '../types/RequestTypes';
import { EditUserAccountType } from '../types/UserAccountTypes';

export const AccountPage = () => {
  const [cookies, setCookie, removeCookie] = useCookies();
  const [originalFirstName, setOriginalFirstName] = useState<string>('');
  const [originalLastName, setOriginalLastName] = useState<string>('');
  const [originalEmail, setOriginalEmail] = useState<string>('');
  const [originalPassword, setOriginalPassword] = useState<string>('');

  const [firstName, setFirstName] = useState<string>('');
  const [editFirstName, setEditFirstName] = useState<boolean>(false);
  const [lastName, setLastName] = useState<string>('');
  const [editLastName, setEditLastName] = useState<boolean>(false);
  const [email, setEmail] = useState<string>('');
  const [editEmail, setEditEmail] = useState<boolean>(false);
  const [password, setPassword] = useState<string>('');
  const [editPassword, setEditPassword] = useState<boolean>(false);
  const [confirmPassword, setConfirmPassword] = useState<string>('');

  useEffect(() => {
    getUserAccountDetailsRequest();
  }, []);

  const getUserAccountDetailsRequest = async () => {
    const requestOptions: GetRequestBasicToken = {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
        token: cookies.token,
        refreshToken: cookies.refreshToken,
      },
    };

    const response = await fetch(`/user/account/get/account/id/${cookies.userId}`, requestOptions);

    if (response.status === 200) {
      const data = await response.json();
      setFirstName(data.firstName);
      setLastName(data.lastName);
      setEmail(data.email);
    } else {
      if (await handleRefreshToken(cookies, setCookie)) {
        window.location.reload();
      } else {
        alert('Failed to get user account details');
      }
    }
  };

  const editUserAccountDetailsRequest = async () => {
    const requestBody: EditUserAccountType = buildEditUserAccountDetailsBody();

    const requestOptions: PutRequestBasicToken = {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
        token: cookies.token,
        refreshToken: cookies.refreshToken,
      },
      body: JSON.stringify(requestBody),
    };

    const response = await fetch('/user/account/edit', requestOptions);

    if (response.status === 200) {
    } else {
      if (await handleRefreshToken(cookies, setCookie)) {
        window.location.reload();
      } else {
        alert('Failed to update user account details');
      }
    }
  };

  const buildEditUserAccountDetailsBody = (): EditUserAccountType => {
    let body: EditUserAccountType = {
      userId: cookies.userId,
    };

    if (firstName !== '') {
      body.firstName = firstName;
    }
    if (lastName !== '') {
      body.lastName = lastName;
    }
    if (email !== '') {
      body.email = email;
    }
    if (password !== '') {
      body.password = password;
    }

    return body;
  };

  return (
    <Box
      sx={{
        marginTop: '80px',
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center',
      }}
    >
      <Typography sx={{ marginBottom: '40px' }} variant='h2'>
        Account
      </Typography>
      <Box
        sx={{
          marginBottom: '20px',
          display: 'flex',
          flexDirection: 'row',
          justifyContent: 'center',
          alignItems: 'center',
        }}
      >
        <Box
          sx={{
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'center',
            alignItems: 'left',
          }}
        >
          <Typography variant='body2'>First Name:</Typography>
          {editFirstName ? (
            <TextField
              sx={{ marginRight: '40px', marginTop: '10px', minWidth: '300px' }}
              variant='standard'
              value={firstName}
              onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                setFirstName(e.target.value);
              }}
            ></TextField>
          ) : (
            <Typography sx={{ marginRight: '40px', marginTop: '10px', minWidth: '300px' }} variant='body1'>
              {firstName}
            </Typography>
          )}
        </Box>
        {editFirstName ? (
          <Button
            sx={{ textTransform: 'none' }}
            onClick={() => {
              setEditFirstName(false);
              editUserAccountDetailsRequest();
            }}
          >
            <Typography variant='body1'>Save</Typography>
          </Button>
        ) : (
          <Button sx={{ textTransform: 'none' }} onClick={() => setEditFirstName(true)}>
            <Typography variant='body1'>Edit</Typography>
          </Button>
        )}
      </Box>
      <Box
        sx={{
          marginBottom: '20px',
          display: 'flex',
          flexDirection: 'row',
          justifyContent: 'center',
          alignItems: 'center',
        }}
      >
        <Box
          sx={{
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'center',
            alignItems: 'left',
          }}
        >
          <Typography variant='body2'>Last Name:</Typography>
          {editLastName ? (
            <TextField
              sx={{ marginRight: '40px', marginTop: '10px', minWidth: '300px' }}
              variant='standard'
              value={lastName}
              onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                setLastName(e.target.value);
              }}
            ></TextField>
          ) : (
            <Typography sx={{ marginRight: '40px', marginTop: '10px', minWidth: '300px' }} variant='body1'>
              {lastName}
            </Typography>
          )}
        </Box>
        {editLastName ? (
          <Button
            sx={{ textTransform: 'none' }}
            onClick={() => {
              setEditLastName(false);
              editUserAccountDetailsRequest();
            }}
          >
            <Typography variant='body1'>Save</Typography>
          </Button>
        ) : (
          <Button sx={{ textTransform: 'none' }} onClick={() => setEditLastName(true)}>
            <Typography variant='body1'>Edit</Typography>
          </Button>
        )}
      </Box>
      <Box
        sx={{
          marginBottom: '20px',
          display: 'flex',
          flexDirection: 'row',
          justifyContent: 'center',
          alignItems: 'center',
        }}
      >
        <Box
          sx={{
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'center',
            alignItems: 'left',
          }}
        >
          <Typography variant='body2'>Email:</Typography>
          {editEmail ? (
            <TextField
              sx={{ marginRight: '40px', marginTop: '10px', minWidth: '300px' }}
              variant='standard'
              value={email}
              onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                setEmail(e.target.value);
              }}
            ></TextField>
          ) : (
            <Typography sx={{ marginRight: '40px', marginTop: '10px', minWidth: '300px' }} variant='body1'>
              {email}
            </Typography>
          )}
        </Box>
        {editEmail ? (
          <Button
            sx={{ textTransform: 'none' }}
            onClick={() => {
              setEditEmail(false);
              editUserAccountDetailsRequest();
            }}
          >
            <Typography variant='body1'>Save</Typography>
          </Button>
        ) : (
          <Button sx={{ textTransform: 'none' }} onClick={() => setEditEmail(true)}>
            <Typography variant='body1'>Edit</Typography>
          </Button>
        )}
      </Box>
      <Box
        sx={{
          marginBottom: '20px',
          display: 'flex',
          flexDirection: 'row',
          justifyContent: 'center',
          alignItems: 'center',
        }}
      >
        <Box
          sx={{
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'center',
            alignItems: 'left',
          }}
        >
          <Typography variant='body2'>Password:</Typography>
          {editPassword ? (
            <Box>
              <TextField
                sx={{ marginRight: '40px', marginTop: '10px', minWidth: '300px' }}
                variant='standard'
                value={password}
                onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                  setPassword(e.target.value);
                }}
              ></TextField>
              <Typography sx={{ marginRight: '40px', marginTop: '30px', minWidth: '300px' }} variant='body2'>
                Confirm Password:
              </Typography>
              <TextField
                sx={{ marginRight: '40px', marginTop: '10px', minWidth: '300px' }}
                variant='standard'
                value={confirmPassword}
                onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                  setConfirmPassword(e.target.value);
                }}
              ></TextField>
            </Box>
          ) : (
            <Typography sx={{ marginRight: '40px', marginTop: '10px', minWidth: '300px' }} variant='body1'>
              {password === '' ? '**********' : password}
            </Typography>
          )}
        </Box>
        {editPassword ? (
          <Button
            sx={{ textTransform: 'none' }}
            onClick={() => {
              setEditPassword(false);
              editUserAccountDetailsRequest();
            }}
          >
            <Typography variant='body1'>Save</Typography>
          </Button>
        ) : (
          <Button sx={{ textTransform: 'none' }} onClick={() => setEditPassword(true)}>
            <Typography variant='body1'>Edit</Typography>
          </Button>
        )}
      </Box>
    </Box>
  );
};
