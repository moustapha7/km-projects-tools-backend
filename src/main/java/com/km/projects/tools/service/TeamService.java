package com.km.projects.tools.service;


import com.km.projects.tools.exception.ResourceNotFoundException;
import com.km.projects.tools.model.Team;
import com.km.projects.tools.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;


    public List<Team> getAllTeams()
    {
        List<Team> teams = new ArrayList<>();
        teamRepository.findAll().forEach(teams::add);
        return teams;
    }


    public ResponseEntity<Team> getTeamById( long teamId) throws ResourceNotFoundException
    {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("team non trouvé"));
        return  ResponseEntity.ok().body(team);

    }


    public Team createTeam(Team team)
    {
        return teamRepository.save(team);
    }


    public Map<String, Boolean> deleteTeam( long teamId) throws ResourceNotFoundException
    {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("team non trouvé"));

        teamRepository.delete(team);
        Map<String, Boolean> response = new HashMap<>();
        response.put("team deleted", Boolean.TRUE);
        return response;

    }


    public  ResponseEntity<Team> updateTeam(long id, Team team) throws  ResourceNotFoundException
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
           throw new ResourceNotFoundException("team not found");
        }

    }

    public long getNombreTeams()
    {
        return  teamRepository.count();

    }


}
