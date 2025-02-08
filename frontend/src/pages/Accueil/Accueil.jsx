import React, { useEffect, useState } from "react";
import axios from "axios";
import "./Accueil.css";
import { MovieModal } from "../../components/MovielModal/MovielModal"; // Corrected import

const API_KEY = "17eb4502"; // OMDb API key

export const Accueil = () => {
  const [movies, setMovies] = useState([]);
  const [omdbData, setOmdbData] = useState({});
  const [selectedMovie, setSelectedMovie] = useState(null);
  const [searchQuery, setSearchQuery] = useState(""); // State for the search query

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

  // Fetch movie details from OMDb API
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

  // Filtered movies based on the search query
  const filteredMovies = movies.filter((movie) =>
    movie.titre.toLowerCase().includes(searchQuery.toLowerCase())
  );

  // Merge movie data with OMDb data
  const handleMovieClick = (movie) => {
    const movieDetails = omdbData[movie.titre] || {};
    setSelectedMovie({
      ...movie,
      ...movieDetails, // Merge movie data with OMDb data
    });
  };

  return (
    <div className="accueil">
      <h1>Welcome to the Movie Gallery</h1>

      {/* Search bar */}
      <div className="search-container">
        <input
          type="text"
          placeholder="Search for a movie..."
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)} // Update search query on change
          className="search-bar"
        />
      </div>

      <div className="movie-carousel-container">
        <h1>Favorite movies</h1>
        <div id="movie-carousel" className="movie-carousel">
          {filteredMovies.length > 0 ? (
            filteredMovies.map((movie) => (
              <div
                key={movie.id}
                className="movie-card"
                onClick={() => handleMovieClick(movie)}
              >
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
                    {movie.realisateur.prenom} {movie.realisateur.nom}{" "}
                    {movie.realisateur.celebre ? "🏆" : ""}
                  </p>
                </div>
              </div>
            ))
          ) : (
            <p>No movies found matching your search.</p>
          )}
        </div>
      </div>

      {/* Render the MovieModal */}
      <MovieModal
        selectedMovie={selectedMovie}
        closeModal={() => setSelectedMovie(null)}
      />
    </div>
  );
};

export default Accueil;
