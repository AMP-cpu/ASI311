package com.ensta.myfilmlist.dao.impl;

import com.ensta.myfilmlist.dao.UtilisateurDAO;
import com.ensta.myfilmlist.model.Realisateur;
import com.ensta.myfilmlist.model.Utilisateur;
import com.ensta.myfilmlist.persistence.ConnectionManager;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

public class JbdcUtilisateurDAO implements UtilisateurDAO {
    private static final String CREATE_UTILISATEUR_QUERY = "INSERT INTO Utilisateur (email, password, nom, prenom) VALUES (?, ?, ?, ?)";
    private JdbcTemplate jdbcTemplate = ConnectionManager.getJdbcTemplate();

    @Override
    public Optional<Utilisateur> findByEmail(String email) {
        String sql = "SELECT * FROM utilisateur WHERE utilisateur.email = ?";

        try {
            Utilisateur utilisateur = jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(Utilisateur.class),
                    email
            );
            return Optional.ofNullable(utilisateur);
        } catch (EmptyResultDataAccessException e) {
            // Aucun résultat trouvé, retourner un Optional vide
            return Optional.empty();
        }
    }

    @Override
    public Utilisateur save(Utilisateur utilisateur) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator creator = conn -> {
            PreparedStatement statement = conn.prepareStatement(CREATE_UTILISATEUR_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, utilisateur.getEmail());
            statement.setString(2, utilisateur.getPassword());
            statement.setString(3, utilisateur.getNom());
            statement.setString(4, utilisateur.getPrenom());
            return statement;
        };
        jdbcTemplate.update(creator, keyHolder);
        utilisateur.setId(keyHolder.getKey().longValue());
        return utilisateur;
    }
}
