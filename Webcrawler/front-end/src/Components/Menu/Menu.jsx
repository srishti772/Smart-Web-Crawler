import React from "react";
import "./Menu.css";
import Home from "../../Images/home.png"

function Menu() {
  return (
    <div class="menu-container">
      {" "}
    
     <div> <img src={Home} style={{width:"30px"}} alt= "home" /> </div>
     <div>   <h6>    <a href="/">  WebCrawler by QueryQuirks   </a></h6></div>
    
   
    </div>
  );
}

export default Menu;
