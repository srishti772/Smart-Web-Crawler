import React, { useState, useRef, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import ListGroup from "react-bootstrap/ListGroup";
import Form from "react-bootstrap/Form";
import InputGroup from "react-bootstrap/InputGroup";
import Button from "react-bootstrap/Button";
import Spinner from "react-bootstrap/Spinner";
import "./Search.css";

const Search = () => {
  const [selectedOption, setSelectedOption] = useState(""); // State for selected option
  const [inputValue, setInputValue] = useState(""); // State for input value
  const [loading, setLoading] = useState(false);
  const [errors, setErrors] = useState({}); // State for validation errors
  const [validated, setValidated] = useState(false);
  const [button, setButton] = useState(false);
  const [response, setResponse] = useState(false);
  const details = useRef(null);

  const navigate = useNavigate();

  useEffect(() => {
    setButton(
      !details.current[0].checkValidity() || !details.current[1].checkValidity()
    );
  }, [details, selectedOption, inputValue]);

  const handleGoClick = async () => {
    // Logic to handle the Go button click
    setLoading(true);
    setResponse("");
    try {
      const url = new URL("http://localhost:8080/crawl");
      url.searchParams.append("selectedOption", selectedOption);
      url.searchParams.append("inputValue", inputValue);

      const response = await fetch(url, {
        method: "POST",
      });
      const data = await response.json();
      console.log("Response from backend:", data);
      // Check if searchHistory is not null and not empty
      if (data.searchHistory && data.searchHistory.length > 0) {
        navigate("/list", { state: { data } });
      } else {
        console.log("Search history is empty. Not redirecting.");
        setResponse("No Data");
        // Handle the case when search history is empty, maybe show an error message
      }
      setLoading(false); // Set loading to false when response is received
    } catch (error) {
      console.log(error);
      setLoading(false);
      setResponse("" + error.message);
    }
    console.log("Selected Option:", selectedOption);
    console.log("Input Value:", inputValue);
  };

  return (
    <div className="container">
      <Form
        ref={details}
        noValidate
        validated={validated}
        onSubmit={(e) => {
          e.preventDefault();
        }}
        onFocus={() => {
          setValidated(true);
        }}
      >
        <InputGroup size="lg">
          <InputGroup.Text id="inputGroup-sizing-sm">Search by</InputGroup.Text>

          <Form.Select
            aria-label="Select"
            className="selectpicker"
            value={selectedOption}
            onChange={(e) => {
              setSelectedOption(e.target.value);
            }}
            disabled={loading}
            required
          >
            <option value="" disabled={true} hidden={true}></option>
            <option value="BFS">BFS</option>
            <option value="DFS">DFS</option>
          </Form.Select>

          <InputGroup.Text id="inputGroup-sizing-lg">Enter URL</InputGroup.Text>

          <Form.Control
            aria-label="Large"
            aria-describedby="inputGroup-sizing-sm"
            value={inputValue}
            onChange={(e) => {
              setInputValue(e.target.value);
            }}
            pattern="(https?://){1}.+"
            disabled={loading}
            required
            placeholder="enter a url to start"
            feedback="invalid url."
            feedbackType="invalid"
          />

          <Button
            variant="outline-secondary"
            id="button-addon2"
            onClick={handleGoClick}
            disabled={loading || button}
          >
            Go
          </Button>
        </InputGroup>
      </Form>

      {loading && (
        <div style={{ marginTop: "1em" }}>
          {" "}
          <Spinner animation="border" size="sm" /> <br />
          Crawling...
        </div>
      )}

      <br />
      {response && (
        <h6
          style={{
            color: "red",
            width: "100%",
            textAlign: "center",
            marginTop: "1em",
          }}
        >
          {response}
        </h6>
      )}

      <br />
    </div>
  );
};

export default Search;
