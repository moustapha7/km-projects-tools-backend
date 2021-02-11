package com.km.projects.tools.controller;


import com.km.projects.tools.exception.ResourceNotFoundException;
import com.km.projects.tools.model.Team;
import com.km.projects.tools.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class TeamController {

    @Autowired
    private TeamService  teamService;

    @GetMapping("/teams")
    public List<Team> getAllTeams()
    {
        return teamService.getAllTeams();
    }

    @GetMapping("teams/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable(value = "id") long teamId) throws ResourceNotFoundException
    {
        return  teamService.getTeamById(teamId);
    }

    @PostMapping("/teams")
    public Team createTeam(@Validated @RequestBody Team team)
    {
        return teamService.createTeam(team);
    }

    @DeleteMapping("teams/{id}")
    public Map<String, Boolean> deleteTeam(@PathVariable(value = "id") long teamId) throws ResourceNotFoundException
    {
        return teamService.deleteTeam(teamId);
    }

    @PutMapping("teams/{id}")
    public  ResponseEntity<Team> updateTeam(@PathVariable(value = "id") long id, @RequestBody Team team) throws ResourceNotFoundException
    {
        return teamService.updateTeam(id, team);
    }

    @GetMapping("/nombreTeams")
    public long getNombreTeams()
    {
        return  teamService.getNombreTeams();

    }




}
