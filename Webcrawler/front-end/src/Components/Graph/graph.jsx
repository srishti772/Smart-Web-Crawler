import React, { useState, useEffect } from "react";
import Graph from "react-graph-vis";

function GraphComponent({ edges, nodes, selectedNode, handleNodeClick }) {
  const [selectedNodeId, setSelectedNodeId] = useState(selectedNode); // State to track the selected node ID

  useEffect(() => {
    setSelectedNodeId(selectedNode); // Update selected node ID state when selectedNode prop changes
  }, [selectedNode]);

  const handleNodeChange = (node) => {
    console.log("inside handleNodeChange " + selectedNode);
    setSelectedNodeId(node);
    handleNodeClick(node);
  };

  const colorPalette = ["#ee3e96", "#00a776", "#00aeef", "#d9e021", "#faa21b"];

  // Function to assign colors to nodes based on index
  const assignColor = (index) => {
    const colorIndex = index % colorPalette.length;
    return colorPalette[colorIndex];
  };

  const graphData = {
    nodes: nodes.map((node) => ({
      id: node.id,
      label: node.label.toString(),
      title: node.title,
      color: selectedNodeId === node.id ? "#000000" : assignColor(node.id), // Highlight selected node
    })),
    edges: edges,
  };

  const options = {
    layout: {
      hierarchical: false,
      directed: false,
    },
    edges: {
      arrows: {
        to: { enabled: false },
        from: { enabled: false },
      },
      color: "#bfbfbf",
      width: 2,
      length: 40,
    },
    nodes: {
      borderWidth: 2, // Set the width of the node border
      borderColor: "#fff",
      font: {
        color: "#ffffff", // Set font color to white
        size: 14,
        face: "arial",
        bold: true, // Set font to bold
      },
    },
    height: "600px",
  };

  const events = {
    select: function (event) {
      var { nodes, edges } = event;
      handleNodeChange(nodes[0]); // Update selected node ID state
    },
  };

  return (
    <div>
      <Graph graph={graphData} options={options} events={events} />
    </div>
  );
}

export default GraphComponent;
