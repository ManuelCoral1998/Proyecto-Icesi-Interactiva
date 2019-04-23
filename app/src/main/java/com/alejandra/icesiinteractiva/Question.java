package com.alejandra.icesiinteractiva;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alejandra.icesiinteractiva.DB.DBHandler;
import com.alejandra.icesiinteractiva.model.Pregunta;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Console;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Question extends AppCompatActivity {

    private LinearLayout linear_preguntas;
    private TextView tv_empecemos;
    private RadioGroup radioGroup1;
    private RadioGroup radioGroup2;
    private ArrayList<Pregunta> preguntas;

    private TextView tv_pregunta1;
    private TextView tv_pregunta2;

    private Button enviar;

    DBHandler dbHandler;
    FirebaseAuth auth;
    private int segundos;
    private Timer timer;

    class Contador extends TimerTask {

        @Override
        public void run() {
            segundos++;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        this.segundos = 0;

        timer = new Timer();
        timer.schedule(new Contador(), 0, 1000);

        linear_preguntas = findViewById(R.id.linear_preguntas);
        linear_preguntas.getBackground().setAlpha(48);

        tv_empecemos = findViewById(R.id.empecemos);
        tv_empecemos.getBackground().setAlpha(89);

        tv_pregunta1 = findViewById(R.id.pregunta1);
        tv_pregunta2 = findViewById(R.id.pregunta2);

        radioGroup1 = findViewById(R.id.radio_pregunta1);
        radioGroup2 = findViewById(R.id.radio_pregunta2);

        dbHandler = DBHandler.getInstance();
        auth = FirebaseAuth.getInstance();

        preguntas = (ArrayList<Pregunta>) getIntent().getSerializableExtra("Preguntas");

        String [] pregunta1Opciones = preguntas.get(0).getOpciones();

        tv_pregunta1.setText(preguntas.get(0).getPregunta());

        for (int i = 1, k = 0; i < radioGroup1.getChildCount(); i++, k++) {
            RadioButton aux = (RadioButton) radioGroup1.getChildAt(i);
            aux.setText(pregunta1Opciones[k]);
        }

        String [] pregunta2Opciones = preguntas.get(1).getOpciones();

        tv_pregunta2.setText(preguntas.get(1).getPregunta());

        for (int i = 1, k = 0; i < radioGroup2.getChildCount(); i++, k++) {
            RadioButton aux = (RadioButton) radioGroup2.getChildAt(i);
            aux.setText(pregunta2Opciones[k]);
        }

        enviar = findViewById(R.id.btn_enviar_respuesta);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();

                boolean p1 = comprobarPregunta1();
                boolean p2 = comprobarPregunta2();

                String id = auth.getCurrentUser().getUid();

                int puntos = 0;
                int tiempo = segundos;

                Log.d("Tiempo: " ,tiempo + " segundos");

                if (p1 && p2) {
                    puntos = 10;
                    dbHandler.actualizarPuntaje(id, puntos, tiempo);
                } else if (p1 || p2) {
                    puntos = 5;
                    dbHandler.actualizarPuntaje(id, puntos, tiempo);
                }
                mostrarPuntaje(puntos);
            }
        });
    }

    public void mostrarPuntaje (int puntos) {

        final AlertDialog.Builder dialogoPuntos = new AlertDialog.Builder(Question.this);
        dialogoPuntos.setTitle("¡Futuro Ingeniero Telemático!");
        dialogoPuntos.setMessage("Gracias por participar, los puntos que ganaste por responder son: " + puntos);
        dialogoPuntos.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Question.this, ProjectList.class);
                startActivity(intent);
                finish();
            }
        });

        dialogoPuntos.show();
    }


    public boolean comprobarPregunta1 () {
        boolean correcto = false;

        int radioButtonID = radioGroup1.getCheckedRadioButtonId();
        View radioButton = radioGroup1.findViewById(radioButtonID);
        int indice = radioGroup1.indexOfChild(radioButton);
        RadioButton aux = (RadioButton) radioGroup1.getChildAt(indice);

        String respuesta = aux.getText().toString();

        if (respuesta.equals(preguntas.get(0).getOpcionCorrecta())) {
            correcto = true;
        }

        return correcto;
    }

    public boolean comprobarPregunta2 () {
        boolean correcto = false;

        int radioButtonID = radioGroup2.getCheckedRadioButtonId();
        View radioButton = radioGroup2.findViewById(radioButtonID);

        int indice = radioGroup2.indexOfChild(radioButton);

        RadioButton aux = (RadioButton) radioGroup2.getChildAt(indice);

        String respuesta = aux.getText().toString();

        if (respuesta.equals(preguntas.get(1).getOpcionCorrecta())) {
            correcto = true;
        }

        return correcto;
    }

}
