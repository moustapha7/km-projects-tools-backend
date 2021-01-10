package com.km.projects.tools.controller;


import com.km.projects.tools.exception.ResourceNotFoundException;
import com.km.projects.tools.model.Team;
import com.km.projects.tools.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @GetMapping("/teams")
    public List<Team> getAllTeams()
    {
        List<Team> teams = new ArrayList<>();
        teamRepository.findAll().forEach(teams::add);
        return teams;
    }

    @GetMapping("teams/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable(value = "id") long teamId) throws ResourceNotFoundException
    {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("team non trouvé"));
        return  ResponseEntity.ok().body(team);

    }

    @PostMapping("/teams")
    public Team createTeam(@Validated @RequestBody Team team)
    {
        return teamRepository.save(team);
    }

    @DeleteMapping("teams/{id}")
    public Map<String, Boolean> deleteTeam(@PathVariable(value = "id") long teamId) throws ResourceNotFoundException
    {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("team non trouvé"));

        teamRepository.delete(team);
        Map<String, Boolean> response = new HashMap<>();
        response.put("team deleted", Boolean.TRUE);
        return response;

    }

    @PutMapping("teams/{id}")
    public  ResponseEntity<Team> updateTeam(@PathVariable(value = "id") long id, @RequestBody Team team)
    {
        Optional<Team> teamInfo = teamRepository.findById(id);

        if (teamInfo.isPresent())
        {
            Team team1= teamInfo.get();
            team1.setName(team.getName());
            team1.setDescription(team.getDescription());

            team1.setUser(team.getUser());

            return new ResponseEntity<>(teamRepository.save(team1), HttpStatus.OK);

        }
        else
        {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/nombreTeams")
    public long getNombreTeams()
    {
        return  teamRepository.count();

    }




}
