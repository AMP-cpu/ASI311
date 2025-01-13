package com.ensta.myfilmlist.model;

public class UtilisateurFilm {
    private long id;
    private Utilisateur utilisateur;
    private Film film;
    private int note;
    private boolean isFavoris;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public int getNote() {
        return note;
    }
    public void setNote(int note) {
        this.note = note;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Film getFilm() {
        return film;
    }
    public void setFilm(Film film) {
        this.film = film;
    }

    public boolean isFavoris() {
        return isFavoris;
    }
    public void setIsFavoris(boolean favoris) {
        isFavoris = favoris;
    }
}
