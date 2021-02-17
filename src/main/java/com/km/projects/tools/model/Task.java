package com.km.projects.tools.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="TASK_ID")
    private long id;

    private String name;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateDebut;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateFin;

    private long estimationJour;
    private long estimationHeure;


    @ManyToOne
    @JoinColumn(name = "DEVELOPPEUR_ID", nullable = true)
    private  Developpeur developpeur;

    @ManyToOne
    @JoinColumn(name = "STATUS_Task_ID", nullable = true)
    private StatusTask statusTask;

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID", nullable = true)
    private Project project;


    public Task() {
    }

    public Task(long id, String name, String description, Date dateDebut, Date dateFin, long estimationJour,
                long estimationHeure, Developpeur developpeur, StatusTask statusTask,  Project project) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.estimationJour = estimationJour;
        this.estimationHeure = estimationHeure;
        this.developpeur = developpeur;
        this.statusTask = statusTask;
        this.project = project;
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

    public Developpeur getDeveloppeur() {
        return developpeur;
    }

    public void setDeveloppeur(Developpeur developpeur) {
        this.developpeur = developpeur;
    }

    public StatusTask getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(StatusTask statusTask) {
        this.statusTask = statusTask;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", estimationJour=" + estimationJour +
                ", estimationHeure=" + estimationHeure +
                ", developpeur=" + developpeur +
                ", statusTask=" + statusTask +
                ", project=" + project +
                '}';
    }
}
