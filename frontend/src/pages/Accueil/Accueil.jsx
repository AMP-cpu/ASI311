import React, { useEffect, useState } from "react";
import axios from "axios";
import "./Accueil.css";
import { MovieModal } from "../../components/MovielModal/MovielModal"; // Corrected import

const API_KEY = "17eb4502"; // OMDb API key

export const Accueil = () => {
  const [movies, setMovies] = useState([]);
  const [directors, setDirectors] = useState([]); // State for directors
  const [omdbData, setOmdbData] = useState({});
  const [selectedMovie, setSelectedMovie] = useState(null);
  const [searchQuery, setSearchQuery] = useState(""); // State for the search query
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
        response.data.forEach((movie) => {
          fetchMovieDetails(movie.titre);
        });
      })
      .catch((error) => {
        console.error("Error fetching movies:", error);
      });
    // Fetch directors
    axios
      .get("http://localhost:8080/realisateur")
      .then((response) => {
        setDirectors(response.data);
      })
      .catch((error) => {
        console.error("Error fetching directors:", error);
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

  const handleNewDirectorSubmit = async (e) => {
    e.preventDefault();
  
    const newDirectorData = {
      prenom: newDirector.prenom, // Director's first name
      nom: newDirector.nom, // Director's last name
      dateNaissance: newDirector.dateNaissance.toString(), // Date of birth in the correct format (YYYY-MM-DD)
    };
  
    try {
      // Sending the new director data to the server in the required format
      const response = await axios.post("http://localhost:8080/realisateur", newDirectorData);
      const addedDirector = response.data; // Response should contain the newly added director
  
      // Optionally, update the list of directors if needed (similar to the movies list update)
      setDirectors([...directors, addedDirector]);
  
      // Reset the form fields after successful submission
      setNewDirector({
        prenom: "",
        nom: "",
        dateNaissance: "",
      });
      setIsDirectorFormVisible(false); // Hide the form after submission
  
    } catch (error) {
      console.error("Error adding new director:", error);
    }
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

      {/* Button to toggle movie form visibility - Centered in the screen */}
      {!isFormVisible && (
        <div className="centered-button">
          <button
            className="add-movie-button"
            onClick={() => setIsFormVisible(true)} // Show form
          >
            Add New Movie
          </button>
        </div>
      )}

      {/* New Movie Form - Display only when isFormVisible is true */}
      {isFormVisible && (
        <div className="new-movie-form">
          <h2>Add a New Movie</h2>
          <button
            className="close-button"
            onClick={() => setIsFormVisible(false)} // Hide the form
          >
            Close
          </button>
          <form onSubmit={handleNewMovieSubmit}>
            <input
              type="text"
              placeholder="Movie Title"
              value={newMovie.titre}
              onChange={(e) =>
                setNewMovie({ ...newMovie, titre: e.target.value })
              }
              required
            />
            <input
              type="number"
              placeholder="Duration (minutes)"
              value={newMovie.duree}
              onChange={(e) =>
                setNewMovie({ ...newMovie, duree: parseInt(e.target.value) })
              }
              required
            />
            <input
              type="number"
              placeholder="Director ID"
              value={newMovie.realisateurId}
              onChange={(e) =>
                setNewMovie({ ...newMovie, realisateurId: parseInt(e.target.value) })
              }
              required
            />
            <button type="submit">Add Movie</button>
          </form>
        </div>
      )}

      {/* Button to toggle director form visibility */}
      {!isDirectorFormVisible && (
        <div className="centered-button">
          <button
            className="add-movie-button"
            onClick={() => setIsDirectorFormVisible(true)} // Show form
          >
            Add New Director
          </button>
        </div>
      )}

      {/* New Director Form - Display only when isDirectorFormVisible is true */}
      {isDirectorFormVisible && (
        <div className="new-movie-form">
          <h2>Add a New Director</h2>
          <button
            className="close-button"
            onClick={() => setIsDirectorFormVisible(false)} // Hide the form
          >
            Close
          </button>
          <form onSubmit={handleNewDirectorSubmit}>
            <input
              type="text"
              placeholder="Director First Name"
              value={newDirector.prenom}
              onChange={(e) =>
                setNewDirector({ ...newDirector, prenom: e.target.value })
              }
              required
            />
            <input
              type="text"
              placeholder="Director Last Name"
              value={newDirector.nom}
              onChange={(e) =>
                setNewDirector({ ...newDirector, nom: e.target.value })
              }
              required
            />
            <input
              type="date"
              placeholder="Date of Birth"
              value={newDirector.dateNaissance}
              onChange={(e) =>
                setNewDirector({
                  ...newDirector,
                  dateNaissance: e.target.value,
                })
              }
              required
            />
            <button type="submit">Add Director</button>
          </form>
        </div>
      )}
    </div>
  );
};

export default Accueil;
