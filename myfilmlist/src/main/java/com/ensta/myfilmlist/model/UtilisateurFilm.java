package com.ensta.myfilmlist.model;

public class UtilisateurFilm {
    private long id;
    private long userId;
    private long filmId;
    private int note;
    private boolean isFavoris;

    public UtilisateurFilm(long id, long userId, long filmId,int note,boolean isFavoris) {
        this.id = id;
        this.userId = userId;
        this.filmId = filmId;
        this.note = note;
        this.isFavoris = isFavoris;
    }

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

    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getFilmId() {
        return filmId;
    }
    public void setFilmId(long filmId) {
        this.filmId = filmId;
    }

    public boolean isFavoris() {
        return isFavoris;
    }
    public void setIsFavoris(boolean favoris) {
        isFavoris = favoris;
    }
}
