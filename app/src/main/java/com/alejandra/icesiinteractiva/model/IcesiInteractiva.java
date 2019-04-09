package com.alejandra.icesiinteractiva.model;

import java.util.ArrayList;

public class IcesiInteractiva {

    private Invitado invitado;
    private ArrayList<Proyecto> proyectos;

    public IcesiInteractiva() {
    }

    public IcesiInteractiva(Invitado invitado, ArrayList<Proyecto> proyectos) {
        this.invitado = invitado;
        this.proyectos = proyectos;
    }

    public Invitado getInvitado() {
        return invitado;
    }

    public void setInvitado(Invitado invitado) {
        this.invitado = invitado;
    }

    public ArrayList<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(ArrayList<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }
}

