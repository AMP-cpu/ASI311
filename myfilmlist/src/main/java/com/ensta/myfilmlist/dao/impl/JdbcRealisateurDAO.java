package com.ensta.myfilmlist.dao.impl;

import com.ensta.myfilmlist.dao.RealisateurDAO;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.model.Realisateur;
import com.ensta.myfilmlist.persistence.ConnectionManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JdbcRealisateurDAO implements RealisateurDAO {
    private JdbcTemplate jdbcTemplate = ConnectionManager.getJdbcTemplate();

    @Override
    public List<Realisateur> findAll() {
        String sql = "SELECT * FROM realisateur";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Realisateur.class));
    }

    @Override
    public Optional<Realisateur> findById(long id) {
        String sql = "SELECT * FROM realisateur WHERE realisateur.id = ?";

        try {
            Realisateur realisateur = jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(Realisateur.class),
                    id
            );
            return Optional.ofNullable(realisateur);
        } catch (EmptyResultDataAccessException e) {
            // Aucun résultat trouvé, retourner un Optional vide
            return Optional.empty();
        }
    }

    @Override
    public Realisateur findByNomAndPrenom(String nom, String prenom) {
        String sql = "SELECT * FROM realisateur WHERE realisateur.prenom = ? AND realisateur.nom = ?";

        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(Realisateur.class),
                    prenom, nom
            );
        } catch (EmptyResultDataAccessException e) {
            // Aucun résultat trouvé, retourner un Optional vide
            return null;
        }
    }
}
