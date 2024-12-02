package com.ensta.myfilmlist.persistence.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.exception.ControllerException;

import io.swagger.models.Response;


public interface FilmResource {

    @GetMapping
    ResponseEntity<List<FilmDTO>> getAllfilms() throws ControllerException;
    
    //@GetMapping
    //ResponseEntity<FilmDTO> getFilmById(long id) throws ControllerException;
}
