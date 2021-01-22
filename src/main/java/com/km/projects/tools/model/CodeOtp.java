package com.km.projects.tools.model;


import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity
public class CodeOtp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String status;

     private ZonedDateTime dateGene;

     private  int code;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    public CodeOtp() {

    }

    public CodeOtp(long id, String status, ZonedDateTime dateGene, int code, User user) {
        this.id = id;
        this.status = status;
        this.dateGene = dateGene;
        this.code = code;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZonedDateTime getDateGene() {
        return dateGene;
    }

    public void setDateGene(ZonedDateTime dateGene) {
        this.dateGene = dateGene;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
