package com.ensta.myfilmlist.persistence.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ensta.myfilmlist.dto.FilmDTO;
import com.ensta.myfilmlist.exception.ControllerException;
import com.ensta.myfilmlist.persistence.controller.FilmResource;
import com.ensta.myfilmlist.service.MyFilmsService;

import java.util.List;

@RestController
@RequestMapping("/film")
public class FilmResourceImpl implements FilmResource {

    @Autowired
    private MyFilmsService myFilmsService;

    @Override
    public ResponseEntity<List<FilmDTO>> getAllfilms() throws ControllerException {
        try {
            List<FilmDTO> films = myFilmsService.findAllFilms();
            return ResponseEntity.ok(films);
       
        } catch (Exception e) {
            throw new ControllerException("Error in searching for films.", e);
        }
    }

}
