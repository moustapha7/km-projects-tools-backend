package com.km.projects.tools.model;

import javax.persistence.*;



@Entity
@Table(name = "project_type")
public class ProjectType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="PROJECT_TYPE_ID")
    private long id;


    private String name;

    public ProjectType() {
    }

    public ProjectType(long id, String name) {
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
