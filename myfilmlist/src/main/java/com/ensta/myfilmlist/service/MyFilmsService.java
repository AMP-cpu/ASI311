package com.ensta.myfilmlist.service;
import com.ensta.myfilmlist.model.Realisateur;
import com.ensta.myfilmlist.service.exception.serviceException;
import com.ensta.myfilmlist.dto.FilmDTO;


import java.util.List;

public interface MyFilmsService {
    Realisateur updateRealisateurCelebre(Realisateur realisateur) throws serviceException;
    List<FilmDTO> findAllFilms() throws serviceException;

}
