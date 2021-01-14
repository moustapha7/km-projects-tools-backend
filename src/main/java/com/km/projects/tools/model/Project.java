package com.km.projects.tools.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;


@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="PROJECT_ID")
    private long id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank
    @Size(min = 5, max = 50)
    private String description;


    private Date dateDebut;


    private Date dateFin;


    private long estimationJour;

    private long estimationHeure;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID", nullable = true)
    private Team team;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID", nullable = true)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_OWNER", nullable = true)
    private User userpo;

    @ManyToOne
    @JoinColumn(name = "TEACH_LEAD", nullable = true)
    private User userteach;

    @ManyToOne
    @JoinColumn(name = "PROJECT_TYPE_ID", nullable = true)
    private  ProjectType projectType;

    @ManyToOne
    @JoinColumn(name = "STATUS_PROJECT_ID", nullable = true)
    private StatusProject statusProject;


    public Project() {
    }

    public Project(long id, String name,  String description,  Date dateDebut, Date dateFin,  long estimationJour,
                   long estimationHeure, Team team, Client client, User userpo, User userteach, ProjectType projectType, StatusProject statusProject) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.estimationJour = estimationJour;
        this.estimationHeure = estimationHeure;
        this.team = team;
        this.client = client;
        this.userpo = userpo;
        this.userteach = userteach;
        this.projectType = projectType;
        this.statusProject = statusProject;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public long getEstimationJour() {
        return estimationJour;
    }

    public void setEstimationJour(long estimationJour) {
        this.estimationJour = estimationJour;
    }

    public long getEstimationHeure() {
        return estimationHeure;
    }

    public void setEstimationHeure(long estimationHeure) {
        this.estimationHeure = estimationHeure;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public User getUserpo() {
        return userpo;
    }

    public void setUserpo(User userpo) {
        this.userpo = userpo;
    }

    public User getUserteach() {
        return userteach;
    }

    public void setUserteach(User userteach) {
        this.userteach = userteach;
    }

    public ProjectType getProjectType() {
        return projectType;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }

    public StatusProject getStatusProject() {
        return statusProject;
    }

    public void setStatusProject(StatusProject statusProject) {
        this.statusProject = statusProject;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", estimationJour=" + estimationJour +
                ", estimationHeure=" + estimationHeure +
                ", team=" + team +
                ", client=" + client +
                ", userpo=" + userpo +
                ", userteach=" + userteach +
                ", projectType=" + projectType +
                ", statusProject=" + statusProject +
                '}';
    }
}
