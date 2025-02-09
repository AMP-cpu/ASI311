import React, { useEffect, useState } from "react";
import axios from "axios";
import "./Accueil.css";
import { MovieModal } from "../../components/MovielModal/MovielModal"; // Corrected import

const API_KEY = "17eb4502"; // OMDb API key
const userId = 1; // Replace with dynamic user ID retrieval if needed

export const Accueil = () => {
  const [movies, setMovies] = useState([]);
  const [favoriteMovies, setFavoriteMovies] = useState([]);
  const [omdbData, setOmdbData] = useState({});
  const [selectedMovie, setSelectedMovie] = useState(null);
  const [searchQuery, setSearchQuery] = useState("");

  useEffect(() => {
    axios.get("http://localhost:8080/film")
      .then(response => {
        setMovies(response.data);
        response.data.forEach(movie => fetchMovieDetails(movie.titre));
      })
      .catch(error => console.error("Error fetching movies:", error));

    axios.get(`http://localhost:8080/film/favorite/${userId}`)
      .then(response => {
        setFavoriteMovies(response.data);
        response.data.forEach(movie => fetchMovieDetails(movie.titre));
      })
      .catch(error => console.error("Error fetching favorite movies:", error));
  }, []);

  const fetchMovieDetails = async (movieTitle) => {
    try {
      const response = await axios.get(
        `https://www.omdbapi.com/?apikey=${API_KEY}&t=${encodeURIComponent(movieTitle)}`
      );
      if (response.data.Response === "True") {
        setOmdbData(prevData => ({ ...prevData, [movieTitle]: response.data }));
      }
    } catch (error) {
      console.error("Error fetching movie details:", error);
    }
  };

  const filteredMovies = movies.filter(movie =>
    movie.titre.toLowerCase().includes(searchQuery.toLowerCase())
  );

  const handleMovieClick = (movie) => {
    const movieDetails = omdbData[movie.titre] || {};
    setSelectedMovie({ ...movie, ...movieDetails });
  };

  return (
    <div className="accueil">
      <h1>Welcome to the Movie Gallery</h1>
      <div className="search-container">
        <input
          type="text"
          placeholder="Search for a movie..."
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
          className="search-bar"
        />
      </div>

      <h2>Movies</h2>
      <div className="movie-carousel">
        {filteredMovies.length > 0 ? (
          filteredMovies.map(movie => (
            <div key={movie.id} className="movie-card" onClick={() => handleMovieClick(movie)}>
              {omdbData[movie.titre]?.Poster ? (
                <img src={omdbData[movie.titre].Poster} alt={movie.titre} className="movie-poster" />
              ) : (
                <div className="placeholder">No Image Available</div>
              )}
              <p>{movie.titre}</p>
            </div>
          ))
        ) : (
          <p>No movies found.</p>
        )}
      </div>

      <h2>Favorite Movies</h2>
      <div className="movie-carousel">
        {favoriteMovies.length > 0 ? (
          favoriteMovies.map(movie => (
            <div key={movie.id} className="movie-card" onClick={() => handleMovieClick(movie)}>
              {omdbData[movie.titre]?.Poster ? (
                <img src={omdbData[movie.titre].Poster} alt={movie.titre} className="movie-poster" />
              ) : (
                <div className="placeholder">No Image Available</div>
              )}
              <p>{movie.titre}</p>
            </div>
          ))
        ) : (
          <p>No favorite movies found.</p>
        )}
      </div>

      <MovieModal selectedMovie={selectedMovie} closeModal={() => setSelectedMovie(null)} />
    </div>
  );
};

export default Accueil;
