package com.ensta.myfilmlist.service;

import com.ensta.myfilmlist.model.Realisateur;
import com.ensta.myfilmlist.service.exception.serviceException;

public interface MyFilmsService {
    Realisateur updateRealisateurCelebre(Realisateur realisateur) throws serviceException;
}
