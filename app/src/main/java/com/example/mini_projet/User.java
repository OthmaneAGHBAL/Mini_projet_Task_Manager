package com.example.mini_projet;

public class User {
    private String nom_user;
    private String mp_user;
    private String email_user;

    public User(String nom_user, String mp_user, String email_user) {
        this.nom_user = nom_user;
        this.mp_user = mp_user;
        this.email_user = email_user;
    }

    public String getEmail_user() {
        return email_user;
    }

    public void setEmail_user(String email_user) {
        this.email_user = email_user;
    }

    public String getMp_user() {
        return mp_user;
    }

    public void setMp_user(String mp_user) {
        this.mp_user = mp_user;
    }

    public String getNom_user() {
        return nom_user;
    }

    public void setNom_user(String nom_user) {
        this.nom_user = nom_user;
    }
}
