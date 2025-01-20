package com.ensta.myfilmlist.persistence.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.exception.ControllerException;
import com.ensta.myfilmlist.form.FilmForm;
import com.ensta.myfilmlist.persistence.controller.FilmResource;
import com.ensta.myfilmlist.service.MyFilmsService;

import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;

import java.util.List;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/film")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from your React app
public class FilmResourceImpl implements FilmResource {

    @Autowired
    private MyFilmsService myFilmsService;

    @ApiOperation(value = "Lister les films", notes = "Permet de renvoyer la liste de tous les films.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des films a été renvoyée correctement")
    })
    @Override
    public ResponseEntity<List<FilmDTO>> getAllfilms() throws ControllerException {
        try {
            List<FilmDTO> films = myFilmsService.findAllFilms();
            return ResponseEntity.ok(films);
        } catch (Exception e) {
            throw new ControllerException("Error in searching for films.", e);
        }
    }

    @ApiOperation(value = "Liste un film par son ID", notes = "Permet de renvoyer un film par son ID.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le film a été renvoyé correctement"),
            @ApiResponse(code = 404, message = "Le film n'a pas été trouvé")
    })
    @Override
    public ResponseEntity<FilmDTO> getFilmById(long ID) throws ControllerException {
        try {
            FilmDTO film = myFilmsService.findFilmById(ID);

            if (film == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(film);
        } catch (Exception e) {
            throw new ControllerException("Error in searching for films.", e);
        }
    }

    @ApiOperation(value = "Cree un nouveau film", notes = "Cree un nouveau film en passant ses attributs par le body.", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Le film a été crée correctement"),
            @ApiResponse(code = 400, message = "Le film n'a pas pu etre cree")
    })
    @Override
    @Transactional
    public ResponseEntity<FilmDTO> createFilm(@RequestBody FilmForm filmForm) throws ControllerException {
        try {
            FilmDTO createdFilm = myFilmsService.createFilm(filmForm);

            return ResponseEntity.status(201).body(createdFilm);
        } catch (Exception e) {
            throw new ControllerException("Error in creating the film.", e);
        }
    }

    @ApiOperation(value = "Supprime un film par son ID", notes = "Permet de supprimer un film par son ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Le film a été supprimé correctement"),
            @ApiResponse(code = 404, message = "Le film n'a pas été trouvé")
    })
    @Override
    @Transactional
    public ResponseEntity<?> deleteFilm(long ID) throws ControllerException {
        try {
            FilmDTO film = myFilmsService.findFilmById(ID);

            if (film == null) {
                return ResponseEntity.notFound().build();
            }

            myFilmsService.deleteFilm(film.getId());

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ControllerException("Error in deleting film.", e);
        }
    }
}
