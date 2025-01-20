import {React, useState} from "react";
import "./Home.css";
import Login from "../Login/Login.jsx"

export const Home = () => {
  const [showLogin, setShowLogin] = useState(false);

  const toggleLogin = () => {
    setShowLogin(!showLogin);
  };
  return (
    <div className="header">
      <div className="container">
        <h2>ALL NEW EVERYTHING</h2>

        <button onClick={toggleLogin} className="button">EXPLORE</button>
        {showLogin && <Login onClose={toggleLogin} />}
      </div>
    </div>
  );
};

// 17eb4502
