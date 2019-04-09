package com.alejandra.icesiinteractiva.model;

public class Invitado {

    private String email;
    private String nickname;

    public Invitado() {
    }

    public Invitado(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
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
}
