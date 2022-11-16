import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import { Link, useNavigate } from 'react-router-dom';
import { styled } from '@mui/system';
import { Cookies, useCookies } from 'react-cookie';
import { PostRequestEmptyBodyToken } from '../types/RequestTypes';
import { failedRequestLogger } from '../helper/RequestHelper';

export const NavigationBar = () => {
  const [cookies, setCookie, removeCookie] = useCookies();
  const navigate = useNavigate();

  const StyledLink = styled(Link)({
    textDecoration: 'none',
    color: '#000000',
  });

  const logoutRequest = async () => {
    const requestOptions: PostRequestEmptyBodyToken = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
        token: cookies.token,
      },
    };

    const response = await fetch('/user/account/logout', requestOptions);

    removeCookie('token');
    removeCookie('refreshToken');
    removeCookie('userId');
    navigate('/');

    if (response.status == 200) {
    } else {
      failedRequestLogger('/user/account/logout', 'POST', response);
    }
  };

  return (
    <AppBar sx={{ boxShadow: 'none' }} color='secondary' position='static'>
      <Toolbar sx={{ display: 'flex', justifyContent: 'space-between' }}>
        <Typography variant='h1' component='div'>
          <StyledLink to='/'>🚀 Traction</StyledLink>
        </Typography>
        {cookies.token === undefined || cookies.token === '' ? (
          <Box>
            <StyledLink to='/'>
              <Button sx={{ textTransform: 'none' }} color='primary'>
                <Typography variant='body1'>Home</Typography>
              </Button>
            </StyledLink>
            <StyledLink to='/login'>
              <Button sx={{ textTransform: 'none' }} color='primary'>
                <Typography variant='body1'>Login</Typography>
              </Button>
            </StyledLink>
            <StyledLink to='/register'>
              <Button sx={{ textTransform: 'none' }} color='primary'>
                <Typography variant='body1'>Register</Typography>
              </Button>
            </StyledLink>
          </Box>
        ) : (
          <Box>
            <StyledLink to='/'>
              <Button sx={{ textTransform: 'none' }} color='primary'>
                <Typography variant='body1'>Home</Typography>
              </Button>
            </StyledLink>
            <StyledLink to='/report'>
              <Button sx={{ textTransform: 'none' }} color='primary'>
                <Typography variant='body1'>Report</Typography>
              </Button>
            </StyledLink>
            <StyledLink to='/account'>
              <Button sx={{ textTransform: 'none' }} color='primary'>
                <Typography variant='body1'>Account</Typography>
              </Button>
            </StyledLink>
            <StyledLink to='/'>
              <Button sx={{ textTransform: 'none' }} color='primary' onClick={logoutRequest}>
                <Typography variant='body1'>Logout</Typography>
              </Button>
            </StyledLink>
          </Box>
        )}
      </Toolbar>
    </AppBar>
  );
};
