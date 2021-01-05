package com.km.projects.tools.model;

import javax.persistence.*;

@Entity
@Table(name = "status_project")
public class StatusProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="STATUS_PROJECT_ID")
    private long id;

    private String name;

    public StatusProject() {
    }

    public StatusProject(long id, String name) {
        this.id = id;
        this.name = name;
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
}
