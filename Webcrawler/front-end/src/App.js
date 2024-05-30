import logo from "./logo.svg";
import "./App.css";
import "./Components/Search/Search";
import Search from "./Components/Search/Search";
import "bootstrap/dist/css/bootstrap.min.css";
import Home from "./Components/Home/Home";
import List from "./Components/List/List";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/" element={<Home />} />{" "}
          <Route path="/list" element={<List />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
