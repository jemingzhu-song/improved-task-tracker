import { Box } from '@mui/material';
import { useCookies } from 'react-cookie';
import { HomePageLoggedIn } from './HomePageLoggedIn';
import { HomePageLoggedOut } from './HomePageLoggedOut';

export const HomePage = () => {
  const [cookies, setCookie] = useCookies();

  return (
    <Box sx={{ marginTop: '80px' }}>
      {cookies.token === undefined || cookies.token === '' ? <HomePageLoggedOut /> : <HomePageLoggedIn />}
    </Box>
  );
};
