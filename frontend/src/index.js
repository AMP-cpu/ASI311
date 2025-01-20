import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { ThemeProvider, createTheme } from "@mui/material/styles";
import './index.css';
import reportWebVitals from './reportWebVitals';
import { Home } from './components/Home/Home';
import { Accueil } from './pages/Accueil/Accueil';
import { Movie } from './pages/Movie/Movie';
import { Navbar } from './components/Navbar/Navbar';
import Footer from './components/Footer/Footer';
import { NotFound } from './pages/NotFound/NotFound'; // Import the NotFound component

import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <Navbar />
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/accueil" element={<Accueil />} />
        <Route path="/movie/:movieId" element={<Movie />} />
        
        <Route path="*" element={<NotFound />} />
      </Routes>
    </Router>
    <Footer />
  </React.StrictMode>
);

reportWebVitals();
