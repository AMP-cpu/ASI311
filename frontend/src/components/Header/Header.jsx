import React from "react";
import { Navbar } from "../Navbar/Navbar";
import { UnderNavbar } from "../UnderNavbar/UnderNavbar";
import "./Header.css";

export const Header = () => {
  return (
    <div className="header">
      <Navbar />
      <UnderNavbar />
    </div>
  );
};
