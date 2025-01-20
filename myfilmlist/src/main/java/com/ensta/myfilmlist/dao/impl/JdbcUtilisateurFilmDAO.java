package com.ensta.myfilmlist.dao.impl;

import com.ensta.myfilmlist.dao.UtilisateurFilmDAO;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.UtilisateurFilm;
import com.ensta.myfilmlist.persistence.ConnectionManager;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcUtilisateurFilmDAO implements UtilisateurFilmDAO {
    private static final String CREATE_UTILISATEUR_FILM_QUERY = "INSERT INTO UtilisateurFilm (utilisateur_id, film_id, note, is_favoris) VALUES (?, ?, ?, ?)";
    private JdbcTemplate jdbcTemplate = ConnectionManager.getJdbcTemplate();
    @Override
    public List<Film> findByUtilisateurId(long utilisateurId) {
        String sql = "SELECT f.id AS film_id, f.titre AS film_titre, f.duree AS film_duree, " +
                "r.id AS realisateur_id, r.nom AS realisateur_nom, r.prenom AS realisateur_prenom, " +
                "r.date_naissance AS realisateur_date_naissance, r.celebre AS realisateur_celebre " +
                "FROM Film f " +
                "INNER JOIN Realisateur r ON f.realisateur_id = r.id " +
                "INNER JOIN UtilisateurFilm uf ON uf.film_id = f.id " +
                "WHERE uf.utilisateur_id = ?";

        return jdbcTemplate.query(sql, new Object[]{utilisateurId}, (rs, rowNum) -> JdbcFilmDAO.getFilmFrom(rs));
    }

    @Override
    public double findFilmMoyenneNote(long filmId) {
        String sql = "SELECT AVG(uf.note) as note_moyenne FROM UtilisateurFilm uf WHERE uf.film_id = ?";
        return 0.0;
    }

    @Override
    public UtilisateurFilm save(UtilisateurFilm utilisateurFilm) {
        return new UtilisateurFilm();
    }

    @Override
    public UtilisateurFilm update(UtilisateurFilm utilisateurFilm) {
        return new UtilisateurFilm();
    }
}
