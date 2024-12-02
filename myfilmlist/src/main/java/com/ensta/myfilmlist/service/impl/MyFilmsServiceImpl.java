package com.ensta.myfilmlist.service.impl;

import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.dao.RealisateurDAO;
import com.ensta.myfilmlist.dao.impl.JdbcFilmDAO;
import com.ensta.myfilmlist.dao.impl.JdbcRealisateurDAO;
import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.mapper.FilmMapper;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Realisateur;
import com.ensta.myfilmlist.service.MyFilmsService;
import com.ensta.myfilmlist.service.exception.serviceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MyFilmsServiceImpl implements MyFilmsService {

    // Instanciation de FilmDAO
    private FilmDAO filmDAO = new JdbcFilmDAO();
    private RealisateurDAO realisateurDAO = new JdbcRealisateurDAO();

    @Override
    public Realisateur updateRealisateurCelebre(Realisateur realisateur) throws serviceException {
        // Vérifier que le réalisateur n'est pas null
        if (realisateur == null) {
            throw new serviceException("Le réalisateur doit exister.");
        }

        // Vérifier que la liste des films réalisés n'est pas nulle
        if (realisateur.getFilmRealises() == null) {
            throw new serviceException("La liste des films ne peut pas être vide.");
        }

        try {
            // Mettre à jour le statut "célèbre" en fonction du nombre de films réalisés
            boolean celebre = realisateur.getFilmRealises().size() >= 3;
            realisateur.setCelebre(celebre);

            // Retourner le réalisateur mis à jour
            return realisateur;
        } catch (Exception e) {
            // En cas d'erreur, lever une ServiceException
            throw new serviceException("Une erreur est survenue lors de la mise à jour du statut célèbre.", e);
        }
    }

    @Override
    public List<FilmDTO> findAllFilms() throws serviceException {
        try {
            // Appelle le DAO pour récupérer tous les films
            List<Film> films = filmDAO.findAll();
            Optional<Realisateur> realisateur = realisateurDAO.findById(1);
            List<Realisateur> realisateurs = realisateurDAO.findAll();
            Realisateur realisateur1 = realisateurDAO.findByNomAndPrenom("Cameron", "James");

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
            throw new serviceException("Erreur lors de la récupération des films", e);
        }
    }
}
