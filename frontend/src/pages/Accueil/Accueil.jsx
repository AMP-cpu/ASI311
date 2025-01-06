import React, { useEffect, useState } from "react";
import axios from "axios";
import "./Accueil.css";

export const Accueil = () => {
  const [movies, setMovies] = useState([]);
  const [selectedMovie, setSelectedMovie] = useState(null);

  // Fetch movies from your database
  useEffect(() => {
    axios
      .get("http://localhost:8080/film")
      .then((response) => {
        console.log("Movies fetched:", response.data);
        setMovies(response.data);
      })
      .catch((error) => {
        console.error("Error fetching movies:", error);
      });
  }, []);

  // Handle movie click
  const handleMovieClick = (movie) => {
    setSelectedMovie(movie); // Set the clicked movie as the selected one
  };

  return (
    <div className="accueil">
      <h1>Welcome to the Movie Gallery</h1>
        <ul className="movie-list">
          {movies.map((movie) => (
            <li
              key={movie.id}
              className="movie-item"
              onClick={() => handleMovieClick(movie)}
            >
              <p className="movie-title">{movie.titre}</p>
              <p className="movie-director">
                {movie.realisateur.prenom} {movie.realisateur.nom}{" "}
                {/* Show trophy icon if filmRealises is empty */}
                <span role="img" aria-label="trophy">
                  🏆
                </span>
              </p>
              <p className="savoir-plus">En savoir plus &gt;&gt;</p>
            </li>
          ))}
        </ul>
        {selectedMovie ? (
        // Display black overlay with movie details
        <div className="overlay" onClick={() => setSelectedMovie(null)}>
          <div className="movie-details">
            {/* <button onClick={() => setSelectedMovie(null)}>Back to Gallery</button> */}
            <h2>{selectedMovie.titre}</h2>
            {/* <p>{selectedMovie.description}</p> */}
            <h3>Réalisateur</h3>
            {selectedMovie.realisateur.prenom} {selectedMovie.realisateur.nom}{" "}
                {/* Show trophy icon if filmRealises is empty */}
                <span role="img" aria-label="trophy">
                  🏆
                </span>
          </div>
        </div>
      ) : null}
    </div>
  );
};

export default Accueil;
