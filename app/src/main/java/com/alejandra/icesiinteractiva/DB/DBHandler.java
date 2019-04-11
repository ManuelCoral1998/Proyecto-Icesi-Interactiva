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

    private boolean banderaSQL;
    public static final String LOGIN = "P09728_1_1";
    public static final String PASS = "JuElt3Ae";
    public static final String URL = "jdbc:mysql://200.3.193.22:3306/" + LOGIN;
    private Connection conn;
    private ArrayList<Proyecto> proyectos;

    @Override
    protected Void doInBackground(String... strings) {

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(URL, LOGIN, PASS);
            banderaSQL = false;
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
                String info = "'" + invitado.getEmail() + "'" + "," + "'" + invitado.getNickname() + "'" + "," + "'" + "1" + "'" + "," + "'" + invitado.getPuntaje() + "'";
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
        send.execute();
    }

    public void traerDatosProyectos() {

        class GetData extends AsyncTask<String, Void, Void> {

            @Override
            protected Void doInBackground(String... strings) {

                proyectos = new ArrayList<>();
                Statement state;
                banderaSQL = false;
                try {
                    state = conn.createStatement();
                    ResultSet rs = state.executeQuery("Select * from proyecto");

                    Log.d(">>>RS", rs+"");

                    while (rs.next()) {
                        String nombre = rs.getString("nombre");
                        String descripcion = rs.getString("descripcion");
                        String materia = rs.getString("materia");
                        String expositores = rs.getString("expositores");
                        int logo = Integer.parseInt(rs.getString("logo"));
                        String palabra_clave = rs.getString("palabra_clave");

                        Log.d(">>>" , nombre + " " + descripcion);
                        Proyecto proyecto = new Proyecto(nombre, descripcion, materia, expositores, logo, palabra_clave);

                        proyectos.add(proyecto);
                    }
                    banderaSQL = true;
                } catch (SQLException e) {
                    Log.d("ERROR", e.getMessage());
                    e.printStackTrace();
                }
                banderaSQL = false;
                return null;
            }
        }
        GetData send = new GetData();
        send.execute();
    }

    public ArrayList<Proyecto> darProyectos () {
        return proyectos;
    }

    public boolean isBanderaSQL() {
        return banderaSQL;
    }
}
