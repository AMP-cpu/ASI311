package com.ensta.myfilmlist.dao.impl;

import com.ensta.myfilmlist.dao.UtilisateurFilmDAO;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.UtilisateurFilm;
import com.ensta.myfilmlist.persistence.ConnectionManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUtilisateurFilmDAO implements UtilisateurFilmDAO {
    private static final String CREATE_UTILISATEUR_FILM_QUERY = "INSERT INTO UtilisateurFilm (utilisateur_id, film_id, note, is_favoris) VALUES (?, ?, ?, ?)";
    private JdbcTemplate jdbcTemplate = ConnectionManager.getJdbcTemplate();
    @Override
    public List<Film> findByUserId(long userId) {
        String sql = "SELECT f.id AS film_id, f.titre AS film_titre, f.duree AS film_duree, " +
                "r.id AS realisateur_id, r.nom AS realisateur_nom, r.prenom AS realisateur_prenom, " +
                "r.date_naissance AS realisateur_date_naissance, r.celebre AS realisateur_celebre " +
                "FROM Film f " +
                "INNER JOIN Realisateur r ON f.realisateur_id = r.id " +
                "INNER JOIN UtilisateurFilm uf ON uf.film_id = f.id " +
                "WHERE uf.utilisateur_id = ?";

        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> JdbcFilmDAO.getFilmFrom(rs));
    }

    @Override
    public Optional<UtilisateurFilm> findByUserAndFilmId(long userId, long filmId) {
        String sql = "SELECT uf.utilisateur_id, uf.film_id, uf.note FROM UtilisateurFilm uf WHERE uf.utilisateur_id = ? AND uf.film_id = ?";

        try {
            UtilisateurFilm utilisateurFilm = jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{userId, filmId},
                    (rs, rowNum) -> new UtilisateurFilm(
                            rs.getLong("id"),
                            rs.getLong("utilisateur_id"),
                            rs.getLong("film_id"),
                            rs.getInt("note"),
                            rs.getBoolean("is_favoris")
                    )
            );
            return Optional.of(utilisateurFilm);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Double> findFilmAverageNote(long filmId) {
        String sql = "SELECT AVG(uf.note) as average_rating FROM UtilisateurFilm uf WHERE uf.film_id = ?";
        return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                        sql,
                        new Object[]{filmId},
                        (rs, rowNum) -> rs.getDouble("average_rating")
                )
        );
    }

    @Override
    public UtilisateurFilm save(UtilisateurFilm utilisateurFilm) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator creator = conn -> {
            PreparedStatement statement = conn.prepareStatement(CREATE_UTILISATEUR_FILM_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, utilisateurFilm.getUserId());
            statement.setLong(2, utilisateurFilm.getFilmId());
            statement.setInt(3, utilisateurFilm.getNote());
            statement.setBoolean(4, utilisateurFilm.isFavoris());
            return statement;
        };
        jdbcTemplate.update(creator, keyHolder);
        utilisateurFilm.setId(keyHolder.getKey().longValue());
        return utilisateurFilm;
    }

    @Override
    public UtilisateurFilm update(UtilisateurFilm utilisateurFilm) {
        String sql = "UPDATE UtilisateurFilm SET note = ?, is_favoris = ? WHERE utilisateur_id = ? AND film_id = ?";

        jdbcTemplate.update(
                sql,
                utilisateurFilm.getNote(),
                utilisateurFilm.isFavoris(),
                utilisateurFilm.getUserId(),
                utilisateurFilm.getFilmId()
        );

        return utilisateurFilm;
    }
}
