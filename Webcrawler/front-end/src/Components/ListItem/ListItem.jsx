import React, { useState } from "react";
import "./ListItem.css";
import OverlayTrigger from "react-bootstrap/OverlayTrigger";
import Tooltip from "react-bootstrap/Tooltip";
import {
  CheckCircleFill,
  XCircleFill,
  ArrowDownCircleFill,
  Link45deg,
  FileEarmarkFont,
} from "react-bootstrap-icons";

function ListItem({
  id,
  path,
  level,
  title,
  status,
  message,
  onClick,
  selected,
}) {
  const [isSelected, setIsSelected] = useState(selected);
  const handleClick = () => {
    onClick(id); // Pass the clicked item's ID to the parent component
  };
  return (
    <div
      id={`listItem_${id}`}
      className={`listitem${selected ? "-selected" : ""}`}
      onClick={handleClick}
    >
      <div class="item-container">
        <div class="item" style={{ width: "480px", overflow: "hidden" }}>
          <h4>
            {" "}
            <b>{id} .</b> {title}
          </h4>
          <OverlayTrigger
            overlay={<Tooltip id="tooltip-disabled">{path}</Tooltip>}
          >
            <span className="d-inline-block">
              <a href={path}>{path}</a>
            </span>
          </OverlayTrigger>
        </div>

        <div class="item">
          <h6>Status&nbsp;</h6>

          <div>
            {status === 200 ? (
              <CheckCircleFill size={20} color="green" />
            ) : (
              <XCircleFill size={20} color="red" />
            )}{" "}
            <OverlayTrigger
              overlay={<Tooltip id="tooltip-disabled">{message}</Tooltip>}
            >
              <span className="d-inline-block"> {status}</span>
            </OverlayTrigger>
          </div>
        </div>

        <div class="item">
          <h6>Depth</h6>

          <div>
            <ArrowDownCircleFill size={20} color="#008bbf" /> {level}
          </div>
        </div>
      </div>
    </div>
  );
}

export default ListItem;
