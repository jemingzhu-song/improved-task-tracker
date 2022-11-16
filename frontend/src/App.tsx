import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { createTheme, ThemeProvider, styled } from '@mui/material/styles';
import { NavigationBar } from './components/NavigationBar';
import { LoginPage } from './pages/LoginPage';
import { RegisterPage } from './pages/RegisterPage';
import { HomePage } from './pages/HomePage';
import { AccountPage } from './pages/AccountPage';
import { ReportPage } from './pages/ReportPage';

function App() {
  const theme = createTheme({
    palette: {
      primary: {
        main: '#000000',
      },
      secondary: {
        main: '#F7F6F3',
      },
      error: {
        main: '#F3938A',
      },
      info: {
        main: '#A2D2FF',
      },
      success: {
        main: '#67DC98',
      },
    },
    typography: {
      fontFamily: 'Nunito',
      h1: {
        fontSize: 24,
        fontWeight: 700,
      },
      h2: {
        fontSize: 30,
        fontWeight: 600,
      },
      h3: {
        fontSize: 24,
        fontWeight: 600,
      },
      body1: {
        fontSize: 18,
        fontWeight: 500,
      },
      body2: {
        fontSize: 18,
        fontWeight: 700,
      },
      subtitle1: {
        fontSize: 14,
        fontWeight: 500,
      },
    },
  });

  return (
    <div className='App'>
      <Router>
        <ThemeProvider theme={theme}>
          <NavigationBar />
          <Routes>
            <Route path='/' element={<HomePage />} />
            <Route path='/login' element={<LoginPage />} />
            <Route path='/register' element={<RegisterPage />} />
            <Route path='/account' element={<AccountPage />} />
            <Route path='/report' element={<ReportPage />} />
          </Routes>
        </ThemeProvider>
      </Router>
    </div>
  );
}

export default App;
