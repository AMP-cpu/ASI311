package com.ensta.myfilmlist.service.impl;

import com.ensta.myfilmlist.model.Realisateur;
import com.ensta.myfilmlist.service.MyFilmsService;
import com.ensta.myfilmlist.service.exception.serviceException;

public class MyFilmsServiceImpl implements MyFilmsService {

    @Override
    public Realisateur updateRealisateurCelebre(Realisateur realisateur) throws serviceException {
       //Vérifier que le realisateur n'est pas null
        if(realisateur == null){
            throw new serviceException("Le realisateur doit exister");
        }
        // Vérifier que la liste des films n'est pas nulle
        if(realisateur.getFilmRealises() == null){
            throw new serviceException("la liste des films ne peut pas etre vide");
        }

        try {
            //mettre à jour le statut celèbre en fonction du nombre de films réalisés
            boolean celebre = realisateur.getFilmRealises().size() >= 3;
            realisateur.setCelebre(celebre);
            // retourner le réalisateur mis à jour
            return realisateur;
        } catch (Exception e) {
            // en cas d'erreur, lancer un serviceException
            throw new serviceException("Une Erreur est survenue lors de la mise en jour du statut celebre.",e);

        }


    }
}
