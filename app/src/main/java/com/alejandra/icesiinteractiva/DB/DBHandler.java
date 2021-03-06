package com.alejandra.icesiinteractiva.DB;

import android.os.AsyncTask;
import android.util.Log;

import com.alejandra.icesiinteractiva.R;
import com.alejandra.icesiinteractiva.model.Invitado;
import com.alejandra.icesiinteractiva.model.Pregunta;
import com.alejandra.icesiinteractiva.model.Proyecto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class DBHandler extends AsyncTask<String, Void, Void> {

    private static DBHandler instance;

    public static final String LOGIN = "P09728_1_1";
    public static final String PASS = "JuElt3Ae";
    public static final String URL = "jdbc:mysql://200.3.193.22:3306/" + LOGIN;
    private Connection conn;
    private ArrayList<Proyecto> proyectos;
    private HashMap<String, Integer> iconosProyectos;

    @Override
    protected Void doInBackground(String... strings) {

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(URL, LOGIN, PASS);
            iconosProyectos = new HashMap<>();
            iconosProyectos.put("NerdGo", R.drawable.nerdgo);
            iconosProyectos.put("", R.drawable.nerdgo);
            iconosProyectos.put("Vive Icesi", R.drawable.vive_icesi);
            iconosProyectos.put("Dragon Battle PCS", R.drawable.dragon_battle_pcs);
            iconosProyectos.put("Detección de placas con AWS", R.drawable.deteccion_de_placas_con_aws);
            iconosProyectos.put("VoIP con Zoiper", R.drawable.voip_con_zoiper);
            iconosProyectos.put("IngeniBots", R.drawable.ingenibots);
            iconosProyectos.put("Streaming", R.drawable.streaming);
            iconosProyectos.put("Fix It Felix", R.drawable.fix_it_felix);
            iconosProyectos.put("Administración en Linux", R.drawable.administracion_en_linux);
            iconosProyectos.put("ABACO-BOT", R.drawable.abaco_bot);
            iconosProyectos.put("Icesi Interactiva App", R.drawable.vive_icesi);
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

                String info = "'" + invitado.getId() + "'" + "," + "'" + invitado.getEmail() + "'" + "," + "'" + invitado.getNickname() + "'" + "," + "'" + check + "'" + "," + "'" + invitado.getPuntaje() + "'"+ "," + "'" + 0 + "'";
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
                        int logo = iconosProyectos.get(nombre);
                        String palabra_clave = rs.getString("palabra_clave");

                        Log.d(">>>", nombre + " " + descripcion);
                        Proyecto proyecto = new Proyecto(nombre, descripcion, materia, expositores, logo, palabra_clave);

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
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finishQuestion.onFinishQuestion(preguntas);
            }
        }
        BringQuestion bring = new BringQuestion();
        bring.execute();
    }

    public void actualizarPuntaje (final String id, final int puntaje, final int tiempo) {

        class UpdateData extends AsyncTask<String, Void, Void> {

            @Override
            protected Void doInBackground(String... strings) {
                Statement state;
                try {
                    state = conn.createStatement();
                    state.execute("UPDATE `invitado` SET `puntaje`= puntaje+ " +puntaje+", " + "`tiempo` = tiempo + " + tiempo+" WHERE id = '" + id + "'");
                    //state.execute("UPDATE `invitado` SET `puntaje`= puntaje+ "+puntaje+" WHERE id = '"+ id + "'");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }

        }
        UpdateData update = new UpdateData();
        update.execute();
    }

    public void generarRankingYTraerPuntaje (final String id) {
        final String[] invitadosRanking = new String[4];
        class GenerateRanking extends AsyncTask<String, Void, Void> {

            @Override
            protected Void doInBackground(String... strings) {
                Statement state;
                try {
                    state = conn.createStatement();
                    ResultSet rs = state.executeQuery("SELECT nombre, puntaje FROM `invitado` ORDER BY puntaje DESC, tiempo DESC LIMIT 3");

                    int i = 0;
                    while(rs.next()) {
                        String nombre = rs.getString("nombre");
                        String puntaje = rs.getString("puntaje");
                        invitadosRanking[i] = nombre + "\n" + puntaje;
                        i++;
                    }

                    rs = state.executeQuery("SELECT puntaje from `invitado` WHERE id = '" + id + "'");

                    while (rs.next()) {
                        String puntaje = rs.getString("puntaje");
                        invitadosRanking[3] = puntaje;
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finishRanking.onFinishRanking(invitadosRanking);
            }
        }
        GenerateRanking generateRanking = new GenerateRanking();
        generateRanking.execute();
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

    //OBSERVER TRAER PROYECTOS
    public interface OnFinishProjects {
        void onFinishProjects ();
    }

    private OnFinishProjects finishProjects;

    public void setOnFinishProjects (OnFinishProjects finishProjects) {
        this.finishProjects = finishProjects;
    }

    //OBSERVER RANKING
    public interface OnFinishRanking {
        void onFinishRanking(String[] ranking);
    }

    private OnFinishRanking finishRanking;

    public void setOnFinishRanking(OnFinishRanking finishRanking) {
        this.finishRanking = finishRanking;
    }


}
