package com.ensta.myfilmlist.service.impl;

import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.dao.RealisateurDAO;
import com.ensta.myfilmlist.dao.impl.JdbcFilmDAO;
import com.ensta.myfilmlist.dao.impl.JdbcRealisateurDAO;
import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.dto.RealisateurDTO;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.mapper.RealisateurMapper;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Realisateur;
import com.ensta.myfilmlist.service.MyFilmsService;
import com.ensta.myfilmlist.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MyFilmsServiceImpl implements MyFilmsService {
    // Instanciation de FilmDAO
    private FilmDAO filmDAO = new JdbcFilmDAO();
    private RealisateurDAO realisateurDAO = new JdbcRealisateurDAO();

    @Override
    public Realisateur updateRealisateurCelebre(Realisateur realisateur) throws ServiceException {
        // Vérifier que le réalisateur n'est pas null
        if (realisateur == null) {
            throw new ServiceException("Le réalisateur doit exister.");
        }

        // Vérifier que la liste des films réalisés n'est pas nulle
        if (realisateur.getFilmRealises() == null) {
            throw new ServiceException("La liste des films ne peut pas être vide.");
        }

        try {
            // Mettre à jour le statut "célèbre" en fonction du nombre de films réalisés
            boolean celebre = realisateur.getFilmRealises().size() >= 3;
            realisateur.setCelebre(celebre);

            // Retourner le réalisateur mis à jour
            return realisateur;
        } catch (Exception e) {
            // En cas d'erreur, lever une ServiceException
            throw new ServiceException("Une erreur est survenue lors de la mise à jour du statut célèbre.", e);
        }
    }

    @Override
    public List<FilmDTO> findAllFilms() throws ServiceException {
        try {
            // Appelle le DAO pour récupérer tous les films
            List<Film> films = filmDAO.findAll();

            // Convertir les objets Film en FilmDTO
            List<FilmDTO> filmDTOs = new ArrayList<>();
            for (Film film : films) {
                if (film != null) {
                    filmDTOs.add(FilmMapper.convertFilmToFilmDTO(film)); // Conversion via FilmMapper
                }
            }
            return filmDTOs;

        } catch (Exception e) {
            // En cas d'erreur, lever une ServiceException
            throw new ServiceException("Erreur lors de la récupération des films", e);
        }
    }

    @Override
    public FilmDTO createFilm(FilmForm filmForm) throws ServiceException {
        // Appelle le DAO pour récupérer tous les films
        Optional<Realisateur> realisateur = realisateurDAO.findById(filmForm.getRealisateurId());

        if (realisateur.isEmpty())
            throw new ServiceException("Realisateur de ce film n'existe pas.");

        try {
            Film film = FilmMapper.convertFilmFormToFilm(filmForm);
            film.setRealisateur(realisateur.get());
            film = filmDAO.save(film);
            return FilmMapper.convertFilmToFilmDTO(film);
        } catch (Exception e) {
            // En cas d'erreur, lever une ServiceException
            throw new ServiceException("Erreur lors de la création du film", e);
        }
    }

    @Override
    public List<RealisateurDTO> findAllRealisateurs() throws ServiceException {
        try {
            // Appelle le DAO pour récupérer tous les films
            List<Realisateur> realisateurs = realisateurDAO.findAll();

            // Convertir les objets Film en FilmDTO
            List<RealisateurDTO> realisateurDTOS = new ArrayList<>();
            for (Realisateur realisateur : realisateurs) {
                if (realisateur != null) {
                    realisateurDTOS.add(RealisateurMapper.convertRealisateurToRealisateurDTO(realisateur)); // Conversion via FilmMapper
                }
            }
            return realisateurDTOS;
        } catch (Exception e) {
            // En cas d'erreur, lever une ServiceException
            throw new ServiceException("Erreur lors de la récupération des realisateurs", e);
        }
    }

    @Override
    public RealisateurDTO findRealisateurByNomAndPrenom(String nom, String prenom) throws ServiceException {
        Realisateur realisateur = realisateurDAO.findByNomAndPrenom(nom, prenom);

        return RealisateurMapper.convertRealisateurToRealisateurDTO(realisateur);
    }
}
