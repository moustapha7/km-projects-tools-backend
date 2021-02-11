package com.km.projects.tools.service;


import com.km.projects.tools.exception.ResourceNotFoundException;
import com.km.projects.tools.model.Commentaire;
import com.km.projects.tools.repository.CommentaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentaireService {

    @Autowired
    private CommentaireRepository commentaireRepository;



    public List<Commentaire> getAllComments() {
        List<Commentaire> commentaires = new ArrayList<>();
        commentaireRepository.findAll().forEach(commentaires::add);
        return commentaires;
    }


    public Commentaire createComments( Commentaire commentaire)
    {

        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication(). getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal. toString();
        }

        commentaire.setCreatedOn(Instant.now());
        commentaire.setUsername(username);

        return commentaireRepository.save(commentaire);
    }


    public Map<String, Boolean> deleteComment( long commentId) throws ResourceNotFoundException
    {
        Commentaire commentaire = commentaireRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("commentaire non trouvé"));

        commentaireRepository.delete(commentaire);
        Map<String, Boolean> response = new HashMap<>();
        response.put("commentaire deleted", Boolean.TRUE);
        return response;

    }

    public long getNombreComments()
    {
        return  commentaireRepository.count();

    }
}
