import React, { useEffect, useState } from "react";
import axios from "axios";
import "./Accueil.css";
import { MovieModal } from "../../components/MovielModal/MovielModal"; // Corrected import

const API_KEY = "17eb4502"; // OMDb API key
const userId = localStorage.getItem('userId')
const admin = localStorage.getItem('is_admin')
console.log(admin)

export const Accueil = () => {
  const [movies, setMovies] = useState([]);
  const [favoriteMovies, setFavoriteMovies] = useState([]);
  const [omdbData, setOmdbData] = useState({});
  const [directors, setDirectors] = useState([]);
  const [selectedMovie, setSelectedMovie] = useState(null);
  const [searchQuery, setSearchQuery] = useState("");
  const [newMovie, setNewMovie] = useState({
    titre: "",
    duree: 0,
    realisateurId: 0,
  });
  const [newDirector, setNewDirector] = useState({
    prenom: "",
    nom: "",
    dateNaissance: "",
  });
  const [isFormVisible, setIsFormVisible] = useState(false); // State for movie form visibility
  const [isDirectorFormVisible, setIsDirectorFormVisible] = useState(false); // State for director form visibility

  useEffect(() => {
    axios
      .get("http://localhost:8080/film")
      .then((response) => {
        setMovies(response.data);
        response.data.forEach((movie) => fetchMovieDetails(movie.titre));
      })
      .catch((error) => console.error("Error fetching movies:", error));

    axios
      .get(`http://localhost:8080/film/favorite/${userId}`)
      .then((response) => {
        setFavoriteMovies(response.data);
        response.data.forEach((movie) => fetchMovieDetails(movie.titre));
      })
      .catch((error) =>
        console.error("Error fetching favorite movies:", error)
      );
  }, []);

  const fetchMovieDetails = async (movieTitle) => {
    try {
      const response = await axios.get(
        `https://www.omdbapi.com/?apikey=${API_KEY}&t=${encodeURIComponent(
          movieTitle
        )}`
      );
      if (response.data.Response === "True") {
        setOmdbData((prevData) => ({
          ...prevData,
          [movieTitle]: response.data,
        }));
      }
    } catch (error) {
      console.error("Error fetching movie details:", error);
    }
  };

  const filteredMovies = movies.filter((movie) =>
    movie.titre.toLowerCase().includes(searchQuery.toLowerCase())
  );

  const handleMovieClick = (movie) => {
    const movieDetails = omdbData[movie.titre] || {};
    setSelectedMovie({ ...movie, ...movieDetails });
  };

   // Handle new movie form submission
   const handleNewMovieSubmit = async (e) => {
    e.preventDefault();

    try {
      // Sending the new movie data to the server in the required format
      const response = await axios.post("http://localhost:8080/film", newMovie);
      const addedMovie = response.data;

      // Fetch the new movie's details from OMDb and add it to the state
      fetchMovieDetails(addedMovie.titre);

      setMovies([...movies, addedMovie]); // Add the new movie to the list
      setNewMovie({
        titre: "",
        duree: 0,
        realisateurId: 0,
      }); // Reset the form fields
      setIsFormVisible(false); // Hide the form after submission
    } catch (error) {
      console.error("Error adding new movie:", error);
    }
  };

  // Handle new director form submission
const handleNewDirectorSubmit = async (e) => {
  e.preventDefault();

  const newDirectorData = {
    prenom: "newDirector.prenom",
    nom: "newDirector.nom",
    dateNaissance: "newDirector.dateNaissance",
  };

  try {
    // Sending the new director data to the server in the required format
    const response = await axios.post("http://localhost:8080/realisateur", newDirector);
    const addedDirector = response.data;

    // Add the new director to the state if necessary, or just clear the form
    setDirectors([...directors, addedDirector]);

    // Reset the form fields
    setNewDirector({
      prenom: "",
      nom: "",
      dateNaissance: "",
    });

    // Hide the form after submission
    setIsDirectorFormVisible(false);
  } catch (error) {
    console.error("Error adding new director:", error);
  }
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
          filteredMovies.map((movie) => (
            <div
              key={movie.id}
              className="movie-card"
              onClick={() => handleMovieClick(movie)}
            >
              {omdbData[movie.titre]?.Poster ? (
                <img
                  src={omdbData[movie.titre].Poster}
                  alt={movie.titre}
                  className="movie-poster"
                />
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
      {favoriteMovies.length>0 && (
        <>
        <h2>Favorite Movies</h2>
              <div className="movie-carousel">
                {favoriteMovies.length > 0 ? (
                  favoriteMovies.map((movie) => (
                    <div
                      key={movie.id}
                      className="movie-card"
                      onClick={() => handleMovieClick(movie)}
                    >
                      {omdbData[movie.titre]?.Poster ? (
                        <img
                          src={omdbData[movie.titre].Poster}
                          alt={movie.titre}
                          className="movie-poster"
                        />
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
        </>
      )}
      

      <MovieModal selectedMovie={selectedMovie} closeModal={() => setSelectedMovie(null)} />

      {localStorage.getItem('is_admin')=="true" && (
      <>
        {!isFormVisible && (
          <div className="centered-button">
            <button className="add-movie-button" onClick={() => setIsFormVisible(true)}>
              Add New Movie
            </button>
          </div>
        )}

        {isFormVisible && (
          <div className="new-movie-form">
            <h2>Add a New Movie</h2>
            <button className="close-button" onClick={() => setIsFormVisible(false)}>
              Close
            </button>
            <form onSubmit={handleNewMovieSubmit}>
              <input
                type="text"
                placeholder="Movie Title"
                value={newMovie.titre}
                onChange={(e) => setNewMovie({ ...newMovie, titre: e.target.value })}
                required
              />
              <input
                type="number"
                placeholder="Duration (minutes)"
                value={newMovie.duree}
                onChange={(e) => setNewMovie({ ...newMovie, duree: parseInt(e.target.value) })}
                required
              />
              <input
                type="number"
                placeholder="Director ID"
                value={newMovie.realisateurId}
                onChange={(e) => setNewMovie({ ...newMovie, realisateurId: parseInt(e.target.value) })}
                required
              />
              <button type="submit">Add Movie</button>
            </form>
          </div>
        )}

        {!isDirectorFormVisible && (
          <div className="centered-button">
            <button className="add-movie-button" onClick={() => setIsDirectorFormVisible(true)}>
              Add New Director
            </button>
          </div>
        )}

        {isDirectorFormVisible && (
          <div className="new-movie-form">
            <h2>Add a New Director</h2>
            <button className="close-button" onClick={() => setIsDirectorFormVisible(false)}>
              Close
            </button>
            <form onSubmit={handleNewDirectorSubmit}>
              <input
                type="text"
                placeholder="Director First Name"
                value={newDirector.prenom}
                onChange={(e) => setNewDirector({ ...newDirector, prenom: e.target.value })}
                required
              />
              <input
                type="text"
                placeholder="Director Last Name"
                value={newDirector.nom}
                onChange={(e) => setNewDirector({ ...newDirector, nom: e.target.value })}
                required
              />
              <input
                type="date"
                placeholder="Date of Birth"
                value={newDirector.dateNaissance}
                onChange={(e) => setNewDirector({ ...newDirector, dateNaissance: e.target.value })}
                required
              />
              <button type="submit">Add Director</button>
            </form>
          </div>
        )}
      </>
    )}

    </div>
  );
};

export default Accueil;
