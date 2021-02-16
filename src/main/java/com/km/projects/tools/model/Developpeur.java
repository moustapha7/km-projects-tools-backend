package com.km.projects.tools.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(name = "developpeur")
public class Developpeur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String firstname;


    private String name;

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(unique = true)
    private String username;


    @NaturalId
    @NotBlank
    @Size(max = 50)
    @Email
    @Column(unique = true)
    private String email;


    private  String photoName;


    public Developpeur() {
    }

    public Developpeur(Long id, String firstname, String name, String username, String email, String photoName) {
        this.id = id;
        this.firstname = firstname;
        this.name = name;
        this.username = username;
        this.email = email;
        this.photoName = photoName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    @Override
    public String toString() {
        return "Developpeur{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", photoName='" + photoName + '\'' +
                '}';
    }
}
