package com.ensta.myfilmlist.dao;

import com.ensta.myfilmlist.model.UtilisateurFilm;

import java.util.List;
import java.util.Optional;

public interface UtilisateurFilmDAO {
    List<UtilisateurFilm> findByUtilisateurId(long utilisateurId);
    double findFilmMoyenneNote(long filmId);
    UtilisateurFilm save(UtilisateurFilm utilisateurFilm);
    UtilisateurFilm update(UtilisateurFilm utilisateurFilm);
}
