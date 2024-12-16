import React from 'react';
import ReactDOM from 'react-dom/client'; // Add this import
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './index.css';
import reportWebVitals from './reportWebVitals';
import { Home } from './components/Home/Home';
import { Accueil } from './pages/Accueil/Accueil';
import { Navbar } from './components/Navbar/Navbar';
import Footer from './components/Footer/Footer';
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <Navbar />
    <Router>
      <Routes>
        {/* Define your routes here */}
        <Route path="/" element={<Home />} />
        <Route path="/accueil" element={<Accueil />} />
        {/* <Route path="/login" element={<Login />} /> */}
        {/* <Route path="/account" element={<ProtectedAccount />} /> */}
        {/* <Route path="/register" element={<Register />} /> */}
      </Routes>
    </Router>
    <Footer />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
