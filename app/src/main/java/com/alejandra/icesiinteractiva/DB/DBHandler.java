package com.alejandra.icesiinteractiva.DB;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.alejandra.icesiinteractiva.model.Invitado;
import com.alejandra.icesiinteractiva.model.Proyecto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBHandler extends AsyncTask<String, Void, Void> {

    private static DBHandler instance;

    public static final String LOGIN = "P09728_1_1";
    public static final String PASS = "JuElt3Ae";
    public static final String URL = "jdbc:mysql://200.3.193.22:3306/" + LOGIN;
    private Connection conn;

    @Override
    protected Void doInBackground(String... strings) {

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(URL, LOGIN, PASS);
        } catch (Exception e) {
            Log.d(">>>> DB", "NO PASO" + e.getMessage());
        }
        finally {
            Log.d(">>>> DB", "PASO");
        }
        return null;
    }

    public static DBHandler getInstance () {
        if (instance == null) {
            instance = new DBHandler();
            instance.execute();
        }
        return instance;
    }

    public void crearInvitadoSQL(final Invitado invitado) {

        class SendData extends AsyncTask<String, Void, Void> {

            @Override
            protected Void doInBackground(String... strings) {
                String info = "'" + invitado.getNickname() + "'" + "," + "'" + invitado.getEmail() + "'";
                Statement state;

                try {
                    state = conn.createStatement();
                    state.execute("insert into invitado VALUES (" + info + ");");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }

        }
        SendData send = new SendData();
        send.execute((Runnable) invitado);
    }

    public ArrayList<Proyecto> traerDatosProyectos() {
        ArrayList<Proyecto> proyectos = new ArrayList<>();
        Statement state = null;
        try {
            state = conn.createStatement();

            ResultSet rs = state.executeQuery("Select * from proyecto");

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                String materia = rs.getString("materia");
                String expositores = rs.getString("expositores");
                int logo = Integer.parseInt(rs.getString("logo"));
                String palabra_clave = rs.getString("palabra_clave");

                Proyecto proyecto = new Proyecto(nombre, descripcion, materia, expositores, logo, palabra_clave);

                proyectos.add(proyecto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return proyectos;
    }

}
