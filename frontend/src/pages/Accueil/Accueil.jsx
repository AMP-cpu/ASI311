import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import "./Accueil.css";

const API_KEY = "17eb4502"; // OMDb API key

export const Accueil = () => {
  const [movies, setMovies] = useState([]);
  const [omdbData, setOmdbData] = useState({});
  const [scrollingLeft, setScrollingLeft] = useState(false);
  const [scrollingRight, setScrollingRight] = useState(false);

  useEffect(() => {
    let scrollInterval;

    if (scrollingLeft) {
      scrollInterval = setInterval(() => {
        const carousel = document.getElementById("movie-carousel");
        carousel.scrollLeft -= 10; // Adjust this value to control scroll speed
      }, 20); // Adjust the interval for scroll speed
    } else if (scrollingRight) {
      scrollInterval = setInterval(() => {
        const carousel = document.getElementById("movie-carousel");
        carousel.scrollLeft += 10; // Adjust this value to control scroll speed
      }, 20); // Adjust the interval for scroll speed
    }

    // Cleanup on mouse leave
    return () => clearInterval(scrollInterval);
  }, [scrollingLeft, scrollingRight]);

  useEffect(() => {
    axios
      .get("http://localhost:8080/film")
      .then((response) => {
        setMovies(response.data);
        response.data.forEach((movie) => {
          fetchMovieDetails(movie.titre);
        });
      })
      .catch((error) => {
        console.error("Error fetching movies:", error);
      });
  }, []);

  const handleMouseEnterLeft = () => {
    setScrollingLeft(true);
    setScrollingRight(false);
  };

  const handleMouseEnterRight = () => {
    setScrollingRight(true);
    setScrollingLeft(false);
  };

  const handleMouseLeave = () => {
    setScrollingLeft(false);
    setScrollingRight(false);
  };

  // Fetch additional movie details (including images) from OMDb API
  const fetchMovieDetails = async (movieTitle) => {
    try {
      const response = await axios.get(
        `https://www.omdbapi.com/?apikey=${API_KEY}&t=${encodeURIComponent(movieTitle)}`
      );

      if (response.data.Response === "True") {
        setOmdbData((prevData) => ({
          ...prevData,
          [movieTitle]: response.data,
        }));
      } else {
        console.log("Movie not found");
      }
    } catch (error) {
      console.error("Error fetching movie details:", error);
    }
  };

  // Scroll functions for left and right
  const scrollLeft = () => {
    const carousel = document.getElementById("movie-carousel");
    carousel.scrollLeft -= 10; // Adjust this value to control scroll speed
  };

  const scrollRight = () => {
    const carousel = document.getElementById("movie-carousel");
    carousel.scrollLeft += 10; // Adjust this value to control scroll speed
  };
  

  return (
    <div className="accueil">
      <h1>Welcome to the Movie Gallery</h1>
      <div className="movie-carousel-container">
        <h1>Favorite movies</h1>
        <div
          id="movie-carousel"
          className="movie-carousel"
        >
          {movies.map((movie) => (
            <Link to={`/movie/${movie.id}`} key={movie.id} className="movie-card">
              {omdbData[movie.titre] && omdbData[movie.titre].Poster ? (
                <img
                  src={omdbData[movie.titre].Poster}
                  alt={movie.titre}
                  className="movie-poster"
                />
              ) : (
                <div className="placeholder">No Image Available</div>
              )}
              <div className="movie-info">
                <p className="movie-title">{movie.titre}</p>
                <p className="movie-director">
                  {movie.realisateur.prenom} {movie.realisateur.nom} 🏆
                </p>
              </div>
            </Link>
          ))}
        </div>

        <div
          className="arrow-left"
          onMouseEnter={handleMouseEnterLeft}
          onMouseLeave={handleMouseLeave}
        >
          &#8592;
        </div>

        <div
          className="arrow-right"
          onMouseEnter={handleMouseEnterRight}
          onMouseLeave={handleMouseLeave}
        >
          &#8594;
        </div>

      </div>
    </div>
  );
};

export default Accueil;
