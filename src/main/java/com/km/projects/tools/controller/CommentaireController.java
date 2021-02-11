package com.km.projects.tools.controller;


import com.km.projects.tools.exception.ResourceNotFoundException;
import com.km.projects.tools.model.Commentaire;
import com.km.projects.tools.service.CommentaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class CommentaireController {

    @Autowired
    private CommentaireService commentaireService;

    @GetMapping("/comments")
    public List<Commentaire> getAllComments()
    {
        return commentaireService.getAllComments();
    }

    @PostMapping("/comments")
    public Commentaire createComments(@Validated @RequestBody Commentaire commentaire)
    {
        return commentaireService.createComments(commentaire);
    }

    @DeleteMapping("comments/{id}")
    public Map<String, Boolean> deleteComment(@PathVariable(value = "id") long commentId) throws ResourceNotFoundException
    {
        return commentaireService.deleteComment(commentId);
    }

    @GetMapping("/nombreComments")
    public long getNombreComments()
    {
        return  commentaireService.getNombreComments();

    }

}
