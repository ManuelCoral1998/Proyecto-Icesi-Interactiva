package com.alejandra.icesiinteractiva.model;

public class Invitado {

    private String id;
    private String email;
    private String nickname;
    private boolean aceptaInfo;
    private int puntaje;

    public Invitado() {
    }

    public Invitado(String id, String nickname, String email, boolean aceptaInfo) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.aceptaInfo = aceptaInfo;
        puntaje = 0;
    }

    public String getId () {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean getAceptaInfo() {
        return aceptaInfo;
    }

    public void setAceptaInfo(boolean aceptaInfo) {
        this.aceptaInfo = aceptaInfo;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
}
