import React from "react";
import "./MovieModal.css";

export const MovieModal = ({ selectedMovie, closeModal }) => {
  if (!selectedMovie) return null;

  return (
    <div className="modal-overlay" onClick={closeModal}>
      <div
        className="modal-content"
        style={{ backgroundImage: `url(${selectedMovie.Poster})` }}
        onClick={(e) => e.stopPropagation()}
      >
        <span className="close-btn" onClick={closeModal}>&times;</span>

        {/* Movie Title and Director at the Top */}
        <div className="modal-info">
          <h2>{selectedMovie.Title} ({selectedMovie.Year})</h2>
          <p>{selectedMovie.Director}</p>
        </div>

        {/* Movie Poster and Synopsis Side by Side */}
        <div className="modal-body">
          <img src={selectedMovie.Poster} alt={selectedMovie.Title} className="modal-poster" />
          <div className="synopsis">
          <p>{selectedMovie.Plot == "N/A" ? "No description available" : selectedMovie.Plot }</p>

            {/* Map and display genres */}
            <div className="genres">
              {selectedMovie.Genre && selectedMovie.Genre.split(',').map((genre, index) => (
                <span key={index} className="genre">{genre.trim()}</span>
              ))}
            </div>

            {/* Icon Buttons for Like, Dislike, and Bookmark */}
            <div className="icon-buttons">
              <button className="icon-btn like-btn">👍</button>
              <button className="icon-btn dislike-btn">👎</button>
              <button className="icon-btn bookmark-btn">🔖</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
