package com.km.projects.tools.model;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="PROJECT_ID")
    private long id;

    private String name;
    private String description;

    private Date dateDebut;
    private Date dateFin;
    private int estimationJour;
    private int estimationHeure;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID", nullable = true)
    private Team team;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID", nullable = true)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_OWNER", nullable = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "PROJECT_TYPE_ID", nullable = true)
    private  ProjectType projectType;

    @ManyToOne
    @JoinColumn(name = "STATUS_PROJECT_ID", nullable = true)
    private StatusProject statusProject;


    public Project() {
    }

    public Project(long id, String name, String description, Date dateDebut, Date dateFin, int estimationJour,
                   int estimationHeure, Team team, Client client, User user, ProjectType projectType,
                   StatusProject statusProject) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.estimationJour = estimationJour;
        this.estimationHeure = estimationHeure;
        this.team = team;
        this.client = client;
        this.user = user;
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

    public int getEstimationJour() {
        return estimationJour;
    }

    public void setEstimationJour(int estimationJour) {
        this.estimationJour = estimationJour;
    }

    public int getEstimationHeure() {
        return estimationHeure;
    }

    public void setEstimationHeure(int estimationHeure) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
                ", user=" + user +
                ", projectType=" + projectType +
                ", statusProject=" + statusProject +
                '}';
    }
}
