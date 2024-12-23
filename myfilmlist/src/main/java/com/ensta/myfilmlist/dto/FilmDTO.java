package com.ensta.myfilmlist.dto;

/**
 * Contient les donnees d'un Film.
 */
public class FilmDTO {

	private long id;

	private String titre;

	private int duree;

	private RealisateurDTO realisateurDTO;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public void setRealisateur(RealisateurDTO realisateurDTO) {this.realisateurDTO = realisateurDTO; }
	public RealisateurDTO getRealisateur() { return this.realisateurDTO; }

	@Override
	public String toString() {
		return "FilmDTO [id=" + id + ", titre=" + titre + ", duree=" + duree + "]";
	}
}
