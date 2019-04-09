package com.alejandra.icesiinteractiva.DB;

import android.os.AsyncTask;

import com.alejandra.icesiinteractiva.model.Invitado;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHandler extends AsyncTask<String, Void, Void> {

    public static final String LOGIN = "P09728_1_1";
    public static final String PASS = "JuElt3Ae";
    public static final String URL = "jdbc:mysql://200.3.193.22:3306/" + LOGIN;
    private Connection conn;

    @Override
    protected Void doInBackground(String... strings) {

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(URL, LOGIN, PASS);
        } catch (Exception e) {}
        return null;
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

    private String traerDatos() {
        String datos = "";
        Statement state = null;
        try {
            state = conn.createStatement();

            ResultSet rs = state.executeQuery("Select * from invitado");

            while (rs.next()) {
                String nickname = rs.getString("nickname");
                String email = rs.getString("email");
                String puntaje = rs.getString("puntaje");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return datos;
    }

}
