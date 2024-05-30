import React, { useState, useEffect, useRef } from "react";
import ListItem from "../ListItem/ListItem";
import Graph from "../Graph/graph";
import "./List.css";
import Form from "react-bootstrap/Form";
import { useLocation } from "react-router-dom";
import Menu from "../Menu/Menu";

function List() {
  const location = useLocation();
  const { data } = location.state || {};
  const initialListData = data.searchHistory || [];
  const [listdata, setListData] = useState(initialListData);
  const [searchHistory, setSearchHistory] = useState(data.searchHistory || []);
  const [sortOption, setSortOption] = useState("");
  const [selectedListItemId, setSelectedListItemId] = useState(0);
  const listRef = useRef(null); // Reference to the list container

  const handleListItemClick = (id) => {
    console.log("inside list item");
    setSelectedListItemId(id);

    scrollToItem(id);
  };

  const scrollToItem = (id) => {
    const item = listRef.current.querySelector(`#listItem_${id}`);
    console.log("inside scroll", item); // Log the clicked item's ID
    if (item) {
      item.scrollIntoView({ behavior: "smooth", block: "start" });
    }
  };

  // Function to fetch data from backend based on sort option
  const fetchData = async () => {
    try {
      const url = new URL("http://localhost:8080/sort");
      console.log("", sortOption);
      url.searchParams.append("sortOption", sortOption);
      const response = await fetch(url, {
        method: "POST",
      });
      const responseData = await response.json();
      console.log("sorted", responseData);

      setSearchHistory(responseData); // Assuming response.data is an array of list items
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  // useEffect hook to fetch data when sortOption changes
  useEffect(() => {
    if (sortOption.trim() !== "") {
      fetchData();
    }
  }, [sortOption]); // This effect will re-run whenever sortOption changes

  return (
    <>
      <Menu />
      <div className="result-container">
        <div>
          <Graph
            edges={data.edges}
            nodes={data.nodes}
            selectedNode={selectedListItemId}
            handleNodeClick={handleListItemClick}
          />
        </div>

        <div style={{ marginTop: "1%" }}>
          <h3>Results</h3>
          <div class="subtitle-pane">
            <div
              class="filter-options"
              style={{ flexGrow: "1", flexBasis: "auto" }}
            >
              Sort by : &nbsp; <br />
              <Form.Select
                aria-label="Select"
                className="custom-select"
                value={sortOption}
                onChange={(e) => {
                  setSortOption(e.target.value);
                }}
              >
                <option value="" disabled hidden></option>
                <option value="depth">Depth</option>
                <option value="name">Name</option>
                <option value="url">Url</option>
              </Form.Select>
            </div>
            Urls Scanned : {searchHistory.length} <br />
            Search Algorithm : {data.searchCriteria}
          </div>

          <br />

          <hr />
          <div class="list-container" ref={listRef}>
            {searchHistory.map((item) => (
              <ListItem
                key={item.id}
                id={item.id}
                path={item.path}
                level={item.level}
                title={item.title}
                status={item.status}
                message={item.message}
                onClick={handleListItemClick} // Pass the function reference
                selected={selectedListItemId === item.id}
              />
            ))}
          </div>
        </div>
      </div>
    </>
  );
}

export default List;
