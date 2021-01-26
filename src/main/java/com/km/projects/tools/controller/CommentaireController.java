package com.km.projects.tools.controller;


import com.km.projects.tools.exception.ResourceNotFoundException;
import com.km.projects.tools.model.Commentaire;
import com.km.projects.tools.model.Team;
import com.km.projects.tools.repository.CommentaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class CommentaireController {

    @Autowired
    private CommentaireRepository commentaireRepository;

    @GetMapping("/comments")
    public List<Commentaire> getAllComments() {
        List<Commentaire> commentaires = new ArrayList<>();
        commentaireRepository.findAll().forEach(commentaires::add);
        return commentaires;

    }

    @PostMapping("/comments")
    public Commentaire createComments(@Validated @RequestBody Commentaire commentaire)
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

    @DeleteMapping("comments/{id}")
    public Map<String, Boolean> deleteComment(@PathVariable(value = "id") long commentId) throws ResourceNotFoundException
    {
        Commentaire commentaire = commentaireRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("commentaire non trouvé"));

        commentaireRepository.delete(commentaire);
        Map<String, Boolean> response = new HashMap<>();
        response.put("commentaire deleted", Boolean.TRUE);
        return response;

    }

    @GetMapping("/comments/nombre")
    public long getNombreComments()
    {
        return  commentaireRepository.count();

    }

}
