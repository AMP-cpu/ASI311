package com.ensta.myfilmlist.dao.impl;

import com.ensta.myfilmlist.dao.FilmDAO;
import com.ensta.myfilmlist.model.Film;
import com.ensta.myfilmlist.persistence.ConnectionManager;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcFilmDAO implements FilmDAO {
    private DataSource dataSource = ConnectionManager.getDataSource();

    @Override
    public List<Film> findAll() {
        List<Film> films = new ArrayList<>();
        String sql = "SELECT id, titre, duree FROM films";


        System.out.println("Exécution de la requête : " + sql);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {


            while (resultSet.next()) {
                Film film = new Film();
                film.setId(resultSet.getLong("id"));
                film.setTitre(resultSet.getString("titre"));
                film.setDuree(resultSet.getInt("duree"));
                films.add(film);
            }



            System.out.println("Nombre de films récupérés : " + films.size());

        } catch (SQLException e) {

            System.err.println("Erreur lors de l'exécution de la requête : " + e.getMessage());
            e.printStackTrace();
        }

        return films;
    }
}
