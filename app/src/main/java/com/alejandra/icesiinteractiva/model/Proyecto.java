package com.alejandra.icesiinteractiva.model;

import java.io.Serializable;

public class Proyecto implements Serializable {

    private String name;
    private String description;
    private String subject;
    private String exhibitors;
    private int logoProyecto;
    private String palabrasClaves;

    private Pregunta[] preguntas;

    public Proyecto() {
    }

    public Proyecto(String name, String description, String subject, String exhibitors, int logoProyecto, String palabrasClaves) {
        this.name = name;
        this.description = description;
        this.subject = subject;
        this.exhibitors = exhibitors;
        this.logoProyecto = logoProyecto;
        this.palabrasClaves = palabrasClaves;
        preguntas = new Pregunta[2];
    }

    public String getPalabrasClaves () {
        return palabrasClaves;
    }

    public void setPalabrasClaves (String palabrasClaves) {
        this.palabrasClaves = palabrasClaves;
    }

    public int getLogoProyecto () {
        return logoProyecto;
    }

    public void setLogoProyecto(int logoProyecto) {
        this.logoProyecto = logoProyecto;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getExhibitors() {
        return exhibitors;
    }

    public void setExhibitors(String exhibitors) {
        this.exhibitors = exhibitors;
    }
}
