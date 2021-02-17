package com.km.projects.tools.model;

import javax.persistence.*;

@Entity
@Table(name = "status_task")
public class StatusTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="STATUS_TASK_ID")
    private long id;

    private String name;

    public StatusTask() {
    }

    public StatusTask(String name) {
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

    @Override
    public String toString() {
        return "StatusTask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
