package com.ensta.myfilmlist.service;
import com.ensta.myfilmlist.dto.RealisateurDTO;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.form.RealisateurForm;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Realisateur;
import com.ensta.myfilmlist.exception.ServiceException;
import com.ensta.myfilmlist.dto.FilmDTO;


import java.util.List;

public interface MyFilmsService {
    Realisateur updateRealisateurCelebre(Realisateur realisateur) throws ServiceException;
    int calculerDureeTotale(List<Film> films);
    double calculerNoteMoyenne(double[] notes);
    List<FilmDTO> findAllFilms() throws ServiceException;
    FilmDTO createFilm(FilmForm filmForm) throws ServiceException;
    List<RealisateurDTO> findAllRealisateurs() throws ServiceException;
    RealisateurDTO findRealisateurByNomAndPrenom(String nom, String prenom) throws ServiceException;
    FilmDTO findFilmById(long id) throws ServiceException;
    void deleteFilm(long id) throws ServiceException;
    RealisateurDTO createRealisateur(RealisateurForm realisateurForm) throws ServiceException;
    RealisateurDTO findRealisateurById(long id) throws ServiceException;
}
