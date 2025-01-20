package com.ensta.myfilmlist.dao;

import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.UtilisateurFilm;

import java.util.List;

public interface UtilisateurFilmDAO {
    List<Film> findByUtilisateurId(long utilisateurId);
    double findFilmMoyenneNote(long filmId);
    UtilisateurFilm save(UtilisateurFilm utilisateurFilm);
    UtilisateurFilm update(UtilisateurFilm utilisateurFilm);
}
