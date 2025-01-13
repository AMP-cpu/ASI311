package com.ensta.myfilmlist.dao.impl;

import com.ensta.myfilmlist.dao.UtilisateurFilmDAO;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Realisateur;
import com.ensta.myfilmlist.model.UtilisateurFilm;
import com.ensta.myfilmlist.persistence.ConnectionManager;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JdbcUtilisateurFilmDAO implements UtilisateurFilmDAO {
    private static final String CREATE_UTILISATEUR_FILM_QUERY = "INSERT INTO UtilisateurFilm (utilisateur_id, film_id, note, is_favoris) VALUES (?, ?, ?, ?)";
    private JdbcTemplate jdbcTemplate = ConnectionManager.getJdbcTemplate();
    @Override
    public List<Film> findByUtilisateurId(long utilisateurId) {
//        String sql = "SELECT f.id, f.titre, f.duree FROM Film f" +
//                "INNER JOIN UtilisateurFilm uf ON f.id = uf.film_id " +
//                "WHERE uf.utilisateur_id = ?";

//        return jdbcTemplate.query(sql, new Object[]{utilisateurId}, (rs, rowNum) -> mapFilmWithRealisateur(rs));
    }

    private UtilisateurFilm mapUtilisateurFilm(ResultSet rs) throws SQLException {
        UtilisateurFilm userFilm = new UtilisateurFilm();
        userFilm.setId(rs.getLong("film_id"));
        userFilm.setTitre(rs.getString("film_titre"));
        userFilm.setDuree(rs.getInt("film_duree"));

        Realisateur realisateur = new Realisateur();
        realisateur.setId(rs.getLong("realisateur_id"));
        realisateur.setNom(rs.getString("realisateur_nom"));
        realisateur.setPrenom(rs.getString("realisateur_prenom"));
        realisateur.setDateNaissance(rs.getDate("realisateur_date_naissance").toLocalDate());
        realisateur.setCelebre(rs.getBoolean("realisateur_celebre"));

        film.setRealisateur(realisateur);
        return film;
    }

    @Override
    public double findFilmMoyenneNote(long filmId) {
        return 0.0;
    }

    @Override
    public UtilisateurFilm save(UtilisateurFilm utilisateurFilm) {

    }

    @Override
    public UtilisateurFilm update(UtilisateurFilm utilisateurFilm) {

    }
}
