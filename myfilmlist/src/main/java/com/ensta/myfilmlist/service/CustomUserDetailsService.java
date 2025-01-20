package com.ensta.myfilmlist.service;

import com.ensta.myfilmlist.dao.UtilisateurDAO;
import com.ensta.myfilmlist.model.Utilisateur;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurDAO utilisateurDAO;

    public CustomUserDetailsService(UtilisateurDAO utilisateurDAO) {
        this.utilisateurDAO = utilisateurDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Recherche de l'utilisateur par email
        Utilisateur utilisateur = utilisateurDAO.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));

        // Transformation en objet UserDetails pour Spring Security
        return User.builder()
                .username(utilisateur.getEmail())
                .password(utilisateur.getPassword()) // Le mot de passe doit être encodé
                .roles("USER") // Ajouter les rôles si nécessaire
                .build();
    }
}
