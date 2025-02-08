import React, { useState, useEffect } from "react";
import "./MovieModal.css";

export const MovieModal = ({ selectedMovie, closeModal, isAdmin }) => {
  const userId = localStorage.getItem('userId');
  const [isFavorite, setIsFavorite] = useState(false);
  const [averageRating, setAverageRating] = useState(null);
  const [personalRating, setPersonalRating] = useState(null);
  const [newRating, setNewRating] = useState(""); // State for the input rating

  useEffect(() => {
    if (selectedMovie && userId) {
      fetchFavoriteStatus();
      fetchAverageRating();
      fetchPersonalRating();
    }
  }, [selectedMovie, userId]);

  const fetchFavoriteStatus = async () => {
    try {
      const response = await fetch(`http://localhost:8080/film/favorite/${selectedMovie.id}/${userId}`);
      if (response.ok) {
        setIsFavorite(true);
      } else {
        console.log("Failed to fetch favorite status:", response.statusText);
      }
    } catch (error) {
      console.error("Error fetching favorite status:", error);
    }
  };

  const fetchAverageRating = async () => {
    try {
      const response = await fetch(`http://localhost:8080/film/note/average/${selectedMovie.id}`);
      if (!response.ok) {
        console.error("Failed to fetch average rating:", response.statusText);
        return;
      }
      const data = await response.json();
      setAverageRating(data);
    } catch (error) {
      console.error("Error fetching average rating:", error);
    }
  };

  const fetchPersonalRating = async () => {
    try {
      const response = await fetch(`http://localhost:8080/film/note/personal/${selectedMovie.id}/${userId}`);
      console.log(selectedMovie.id);
      console.log(userId);
      if (!response.ok) {
        console.error("Failed to fetch personal rating:", response.statusText);
        return;
      }
      const data = await response.json();
      console.log(data)
      setPersonalRating(data);
    } catch (error) {
      console.error("Error fetching personal rating:", error);
    }
  };

  const handleFavoriteToggle = async () => {
    try {
      const method = isFavorite ? "DELETE" : "POST";
      const response = await fetch(`http://localhost:8080/film/favorite/${selectedMovie.id}/${userId}`, {
        method,
      });
      if (response.ok) setIsFavorite(!isFavorite);
      else {
        console.log("Failed to update favorite status:", response.statusText);
      }
    } catch (error) {
      console.error("Error updating favorite status:", error);
    }
  };

  // ✅ Handle rating submission from input
  const handleRatingSubmit = async () => {
    const ratingValue = parseInt(newRating, 10);
    if (ratingValue >= 0 && ratingValue <= 20) {
      try {
        const response = await fetch(`http://localhost:8080/film/note/eval/${selectedMovie.id}/${userId}/${ratingValue}`, {
          method: "POST",
        });
        if (response.ok) {
          setPersonalRating(ratingValue);
          fetchAverageRating();
        } else {
          console.error("Failed to submit rating:", response.statusText);
        }
      } catch (error) {
        console.error("Error submitting rating:", error);
      }
    } else {
      alert("Please enter a rating between 0 and 20.");
    }
  };

  if (!selectedMovie || !userId) return null;

  return (
    <div className="modal-overlay" onClick={closeModal}>
      <div className="modal-content" style={{ backgroundImage: `url(${selectedMovie.Poster})` }} onClick={(e) => e.stopPropagation()}>
        <span className="close-btn" onClick={closeModal}>&times;</span>

        <div className="modal-info">
          <h2>{selectedMovie.Title} ({selectedMovie.Year})</h2>
          <p>{selectedMovie.Director}</p>
        </div>

        <div className="modal-body">
          <img src={selectedMovie.Poster} alt={selectedMovie.Title} className="modal-poster" />
          <div className="synopsis">
            <p>{selectedMovie.Plot === "N/A" ? "" : selectedMovie.Plot}</p>

            <div className="genres">
              {selectedMovie.Genre && selectedMovie.Genre.split(',').map((genre, index) => (
                <span key={index} className="genre">{genre.trim()}</span>
              ))}
            </div>

            <div className="ratings">
              <p>⭐ Average Rating: {averageRating ? averageRating : 0}</p>
              <p>⭐ Your Rating: {personalRating}</p>
              
              {/* Rating Input */}
              <div className="rating-input">
                <label>Enter your rating (0-20):</label>
                <input
                  type="number"
                  min="0"
                  max="20"
                  value={newRating}
                  onChange={(e) => setNewRating(e.target.value)}
                  placeholder={personalRating}
                />
                <button onClick={handleRatingSubmit}>Submit Rating</button>
              </div>
            </div>

            <div className="icon-buttons">
              <button className="icon-btn bookmark-btn" onClick={handleFavoriteToggle}>
                {isFavorite ? "💖 Remove Favorite" : "🔖 Add to Favorites"}
              </button>

              {isAdmin && isFavorite && (
                <button className="icon-btn delete-btn" onClick={handleFavoriteToggle}>
                  ❌ Remove (Admin)
                </button>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
