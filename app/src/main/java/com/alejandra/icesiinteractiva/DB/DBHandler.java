package com.alejandra.icesiinteractiva.DB;

import android.os.AsyncTask;
import android.util.Log;
import com.alejandra.icesiinteractiva.model.Invitado;
import com.alejandra.icesiinteractiva.model.Pregunta;
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
    private ArrayList<Proyecto> proyectos;

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

                int check = invitado.getAceptaInfo() ? 1: 0;

                String info = "'" + invitado.getId() + "'" + "," + "'" + invitado.getEmail() + "'" + "," + "'" + invitado.getNickname() + "'" + "," + "'" + check + "'" + "," + "'" + invitado.getPuntaje() + "'";
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
        class BringProject extends AsyncTask<String, Void, Void> {
            @Override
            protected Void doInBackground(String... strings) {
                proyectos = new ArrayList<>();
                Statement state;
                try {
                    state = conn.createStatement();
                    ResultSet rs = state.executeQuery("Select * from proyecto");

                    Log.d(">>>RS", rs + "");

                    while (rs.next()) {
                        String nombre = rs.getString("nombre");
                        String descripcion = rs.getString("descripcion");
                        String materia = rs.getString("materia");
                        String expositores = rs.getString("expositores");
                        //int logo = Integer.parseInt(rs.getString("logo"));
                        String palabra_clave = rs.getString("palabra_clave");

                        Log.d(">>>", nombre + " " + descripcion);
                        Proyecto proyecto = new Proyecto(nombre, descripcion, materia, expositores, 0, palabra_clave);

                        proyectos.add(proyecto);
                    }
                } catch(SQLException e)
                {
                    Log.d("ERROR", e.getMessage());
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finishProjects.onFinishProjects();
            }
        }

        BringProject bringProject = new BringProject();
        bringProject.execute();
    }

    public void traerPreguntas (final String nombreProyecto) {
        final ArrayList<Pregunta> preguntas = new ArrayList<>();
        class BringQuestion extends AsyncTask<String, Void, Void> {

            @Override
            protected Void doInBackground(String... strings) {

                Statement state;

                try {
                    state = conn.createStatement();

                    ResultSet rs = state.executeQuery("SELECT * FROM `pregunta` Pe, `proyecto` Pr WHERE Pe.nombreProyecto = '" + nombreProyecto + "' AND Pe.nombreProyecto = Pr.nombre");

                    while (rs.next()){
                        String nombreProyecto = rs.getString("nombreProyecto");
                        String pregunta = rs.getString("pregunta");
                        String opciones = rs.getString("opciones");
                        String opcionCorrecta = rs.getString("opcionCorrecta");

                        Pregunta question = new Pregunta(pregunta, opciones.split(";"), opcionCorrecta);

                        preguntas.add(question);
                    }
                    finishQuestion.onFinishQuestion(preguntas);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        BringQuestion bring = new BringQuestion();
        bring.execute();
    }

    public void actualizarPuntaje (final String id, final int puntaje) {

        class UpdateData extends AsyncTask<String, Void, Void> {

            @Override
            protected Void doInBackground(String... strings) {
                Statement state;
                try {
                    state = conn.createStatement();
                    Log.d("Acutaliar" , id);
                    state.execute("UPDATE `invitado` SET `puntaje`= puntaje+ "+puntaje+" WHERE id = '"+ id + "'");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }

        }
        UpdateData update = new UpdateData();
        update.execute();
    }

    public ArrayList<Proyecto> darProyectos () {
        return proyectos;
    }

    //OBSERVER CODIGO QR
    public interface OnFinishQuestion {
        void onFinishQuestion(ArrayList<Pregunta> preguntas);
    }

    private OnFinishQuestion finishQuestion;

    public void setOnFinishQuestion (OnFinishQuestion finishQuestion) {
        this.finishQuestion = finishQuestion;
    }

    public interface OnFinishProjects {
        void onFinishProjects ();
    }

    private OnFinishProjects finishProjects;

    public void setOnFinishProjects (OnFinishProjects finishProjects) {
        this.finishProjects = finishProjects;
    }

}
