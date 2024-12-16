import React, { useEffect, useState } from "react";
import Slider from "react-slick";
import axios from "axios";
import "./Accueil.css";

export const Accueil = () => {
  const [movies, setMovies] = useState([]);
  const [selectedMovie, setSelectedMovie] = useState(null); // Track the selected movie
  const [autoplay, setAutoplay] = useState(true); // Control slider autoplay

  // Fetch movies from your database
  useEffect(() => {
    axios
      .get("http://localhost:8080/film") 
      .then((response) => {
        setMovies(response.data); 
      })
      .catch((error) => {
        console.error("Error fetching movies:", error);
      });
  }, []);

  // Slider settings
  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 4, // Number of movies displayed at a time
    slidesToScroll: 1,
    autoplay: autoplay, // Use autoplay state
    autoplaySpeed: 2000,
  };

  // Handle movie click
  const handleMovieClick = (movie) => {
    setAutoplay(false); // Stop the slider autoplay
    setSelectedMovie(movie); // Set the clicked movie as the selected one
  };

  return (
    <div className="accueil">
      <h1>Welcome to the Movie Gallery</h1>

      {selectedMovie ? (
        // Display selected movie details
        <div className="movie-details">
          <button onClick={() => { setSelectedMovie(null); setAutoplay(true); }}>
            Back to Gallery
          </button>
          <h2>{selectedMovie.title}</h2>
          <img
            src={selectedMovie.posterUrl}
            alt={selectedMovie.title}
            className="movie-detail-poster"
          />
          <p>{selectedMovie.description}</p>
        </div>
      ) : (
        <Slider {...settings}>
          {movies.map((movie) => (
            <div
              key={movie.id}
              className="movie-card"
              onClick={() => handleMovieClick(movie)} // Handle click
            >
              <img
                src={movie.posterUrl} // Assuming movie has a posterUrl field
                alt={movie.title}
                className="movie-poster"
              />
              <h3 className="movie-title">'{movie.title}'</h3>
            </div>
          ))}
        </Slider>
      )}
    </div>
  );
};

export default Accueil;
