package com.ensta.myfilmlist.dao;

import com.ensta.myfilmlist.model.Film;
import java.util.List;
import java.util.Optional;

public interface FilmDAO {
    List<Film> findAll();
    Film save(Film film);
    Optional<Film> findById(long id);

    void delete(Film film);

    List<Film> findByRealisateurId(long realisateurId);
}

