package com.ensta.myfilmlist.persistence.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;

import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.exception.ControllerException;
import com.ensta.myfilmlist.form.FilmForm;

import io.swagger.annotations.Api;
import io.swagger.models.Response;
import io.swagger.v3.oas.annotations.tags.Tag;

@Api(tags = "Film")
@Tag(name = "Film", description = "Opération sur les films")
public interface FilmResource {

    @GetMapping
    ResponseEntity<List<FilmDTO>> getAllfilms() throws ControllerException;
    
    @GetMapping("/{id}")
    ResponseEntity<FilmDTO> getFilmById(@Valid @PathVariable("id") long id) throws ControllerException;

    @PostMapping
    ResponseEntity<FilmDTO> createFilm(@Valid @RequestBody FilmForm filmForm) throws ControllerException;

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteFilm(@Valid @PathVariable("id") long id) throws ControllerException;
    
}
