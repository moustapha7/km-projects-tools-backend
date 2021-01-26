package com.km.projects.tools.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "commentaire")
public class Commentaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String content;

    @Column
    private Instant createdOn;

    @Column
    private String username;


    @ManyToOne
    @JoinColumn(name = "PROJECT_ID", nullable = true)
    private  Project project;


    public Commentaire() {
    }

    public Commentaire(long id, String content, Instant createdOn, String username, Project project) {
        this.id = id;
        this.content = content;
        this.createdOn = createdOn;
        this.username = username;
        this.project = project;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Commentaire{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createdOn=" + createdOn +
                ", username='" + username + '\'' +

                '}';
    }
}
