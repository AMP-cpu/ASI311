CREATE TABLE IF NOT EXISTS Realisateur(id INT primary key auto_increment, nom VARCHAR(100), prenom VARCHAR(100), date_naissance TIMESTAMP, celebre BOOLEAN);
INSERT INTO Realisateur(nom, prenom, date_naissance, celebre) VALUES('Cameron', 'James', '1954-08-16', true);
INSERT INTO Realisateur(nom, prenom, date_naissance, celebre) VALUES('Jackson', 'Peter', '1961-10-31', false);
INSERT INTO Realisateur(nom, prenom, date_naissance, celebre) VALUES('Spielberg', 'Steven', '1946-12-18', false);

CREATE TABLE IF NOT EXISTS Film(id INT primary key auto_increment, titre VARCHAR(100), duree INT, realisateur_id INT);
INSERT INTO Film(titre, duree, realisateur_id) VALUES('Avatar', 162, 1);
INSERT INTO Film(titre, duree, realisateur_id) VALUES('The Terminator', 178, 1);
INSERT INTO Film(titre, duree, realisateur_id) VALUES('Titanic', 179, 1);
INSERT INTO Film(titre, duree, realisateur_id) VALUES('True Lies', 179, 1);
INSERT INTO Film(titre, duree, realisateur_id) VALUES('Piranha II', 179, 1);

INSERT INTO Film(titre, duree, realisateur_id) VALUES('Le retour du roi', 201, 2);

INSERT INTO Film(titre, duree, realisateur_id) VALUES('Jaws', 201, 3);
INSERT INTO Film(titre, duree, realisateur_id) VALUES('Saving Private Ryan', 201, 3);
INSERT INTO Film(titre, duree, realisateur_id) VALUES('Lincoln', 201, 3);
INSERT INTO Film(titre, duree, realisateur_id) VALUES('Bridge of Spies', 201, 3);


CREATE TABLE IF NOT EXISTS Utilisateur(id INT primary key auto_increment, email VARCHAR(100), password VARCHAR(100), nom VARCHAR(100), prenom VARCHAR(100), is_admin BOOLEAN);
INSERT INTO Utilisateur(email, password, nom, prenom, is_admin) VALUES('salima@salima.com', 'salima', 'Azouz', 'Salima', TRUE);
INSERT INTO Utilisateur(email, password, nom, prenom, is_admin) VALUES('leo@leo.com', 'leo','Picoli', 'Leonardo', FALSE);

CREATE TABLE IF NOT EXISTS UtilisateurFilm (
    utilisateur_id INT,
    film_id INT,
    note INT NULL,
    is_favorite BOOLEAN NULL,
    PRIMARY KEY (utilisateur_id, film_id),
    FOREIGN KEY (utilisateur_id) REFERENCES Utilisateur(id),
    FOREIGN KEY (film_id) REFERENCES Film(id)
);
CREATE INDEX idx_utilisateur ON UtilisateurFilm(utilisateur_id);
CREATE INDEX idx_film ON UtilisateurFilm(film_id);

-- INSERT INTO UtilisateurFilm(utilisateur_id, film_id, note, is_favorite) VALUES(1, 1, 3, false);
-- INSERT INTO UtilisateurFilm(utilisateur_id, film_id, note, is_favorite) VALUES(1, 2, 20, true);
-- INSERT INTO UtilisateurFilm(utilisateur_id, film_id, note, is_favorite) VALUES(2, 1, 15, true);
-- INSERT INTO UtilisateurFilm(utilisateur_id, film_id, note, is_favorite) VALUES(2, 2, 19, true);



