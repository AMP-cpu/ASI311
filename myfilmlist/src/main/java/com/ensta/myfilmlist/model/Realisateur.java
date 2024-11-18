package com.ensta.myfilmlist.model;

import java.time.LocalDate;
import java.util.List;

public class Realisateur {

    private long id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private List<Film> filmRealises;
    private boolean celebre;

    public Realisateur() {
    }

    public Realisateur(long id, String nom, String prenom, LocalDate dateNaissance, boolean celebre) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.celebre = celebre;
    }

}
