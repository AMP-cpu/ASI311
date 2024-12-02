package com.ensta.myfilmlist.service;
import com.ensta.myfilmlist.dto.RealisateurDTO;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.model.Realisateur;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.dto.FilmDTO;


import java.util.List;

public interface MyFilmsService {
    Realisateur updateRealisateurCelebre(Realisateur realisateur) throws ServiceException;
    List<FilmDTO> findAllFilms() throws ServiceException;
    FilmDTO createFilm(FilmForm filmForm) throws ServiceException;
    List<RealisateurDTO> findAllRealisateurs() throws ServiceException;
    RealisateurDTO findRealisateurByNomAndPrenom(String nom, String prenom) throws ServiceException;
}
