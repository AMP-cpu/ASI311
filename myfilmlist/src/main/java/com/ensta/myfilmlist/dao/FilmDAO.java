package com.ensta.myfilmlist.dao;

import com.ensta.myfilmlist.model.Film;
import java.util.List;

public interface FilmDAO {
    List<Film> findAll();
    Film save(Film film);
}

