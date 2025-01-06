import React, { useState } from "react";
import "./Login.css"; // Create this CSS file for styling
import CloseIcon from '@mui/icons-material/Close';

const Login = ({ onClose }) => {
  return (
    <div className="login-popup">
      <div className="login-content">
        <span className="close" onClick={onClose}>&times;</span>
        <form>
          <label>
            <input type="text" placeholder="Username" name="username" />
          </label>
          <label>
            <input type="password" placeholder="Password" name="password" />
          </label>
          <button type="submit">Login</button>
        </form>
      </div>
    </div>
  );
};

export default Login;
