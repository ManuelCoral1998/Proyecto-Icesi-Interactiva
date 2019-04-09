package com.alejandra.icesiinteractiva.model;

public class Proyecto {

    private String name;
    private String description;
    private String subject;
    private String exhibitors;

    public Proyecto() {
    }

    public Proyecto(String name, String description, String subject, String exhibitors) {
        this.name = name;
        this.description = description;
        this.subject = subject;
        this.exhibitors = exhibitors;
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
