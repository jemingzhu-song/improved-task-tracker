import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import { Link } from 'react-router-dom';
import { styled } from '@mui/system';

export const NavigationBar = () => {
  const StyledLink = styled(Link)({
    textDecoration: 'none',
    color: '#000000',
  });
  return (
    <AppBar sx={{ boxShadow: 'none' }} color='secondary' position='static'>
      <Toolbar sx={{ display: 'flex', justifyContent: 'space-between' }}>
        <Typography variant='h1' component='div'>
          <StyledLink to='/'>🚀 Traction</StyledLink>
        </Typography>
        <Box>
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
      </Toolbar>
    </AppBar>
  );
};
