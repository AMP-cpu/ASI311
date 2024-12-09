package com.ensta.myfilmlist.form;

import java.time.LocalDate;

public class RealisateurForm {
    private String prenom;
    private String nom;
    private LocalDate dateNaissance;

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }
}
