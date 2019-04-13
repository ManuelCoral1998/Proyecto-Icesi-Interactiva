package com.alejandra.icesiinteractiva.model;

import java.io.Serializable;

public class Pregunta implements Serializable {

    private String pregunta;
    private String[] opciones;
    private String opcionCorrecta;

    public Pregunta() {
    }

    public Pregunta(String pregunta, String[] opciones, String opcionCorrecta) {
        this.pregunta = pregunta;
        this.opciones = opciones;
        this.opcionCorrecta = opcionCorrecta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String[] getOpciones() {
        return opciones;
    }

    public void setOpciones(String[] opciones) {
        this.opciones = opciones;
    }

    public String getOpcionCorrecta() {
        return opcionCorrecta;
    }

    public void setOpcionCorrecta(String opcionCorrecta) {
        this.opcionCorrecta = opcionCorrecta;
    }
}
