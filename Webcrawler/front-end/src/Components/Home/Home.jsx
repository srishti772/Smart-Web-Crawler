import React , {useState} from "react";
import "./Home.css";
import Search from "../Search/Search";
import List from "../List/List";

function Home() {
  

  return (
    <div className="home-container">
      <div className="home-content">
        <div>
          <h1><span style={{color:"#4e81ee"}}>W</span>
          <span style={{color:"#e1183a"}}>e</span>
          <span style={{color:"#f5c228"}}>b </span>
          <span style={{color:"#4e81ee"}}>C</span>
          <span style={{color:"#24bf5a"}}>r</span>
          <span style={{color:"#e1183a"}}>a</span>
          <span style={{color:"#4e81ee"}}>w</span>
          <span style={{color:"#f5c228"}}>l</span>
          <span style={{color:"#24bf5a"}}>e</span>
          <span style={{color:"#e1183a"}}>r</span>
         </h1>
          <p>by query quirks</p>
        </div>
        <h1></h1>
        <Search />
      </div>
    </div>
  );
}

export default Home;
