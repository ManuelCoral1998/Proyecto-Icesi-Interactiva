package com.alejandra.icesiinteractiva;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alejandra.icesiinteractiva.model.Pregunta;

import java.util.ArrayList;

public class Question extends AppCompatActivity {

    private LinearLayout linear_preguntas;
    private TextView tv_empecemos;
    private RadioGroup radioGroup1;
    private RadioGroup radioGroup2;
    private ArrayList<Pregunta> preguntas;

    private Button enviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);


        linear_preguntas = findViewById(R.id.linear_preguntas);
        linear_preguntas.getBackground().setAlpha(48);

        tv_empecemos = findViewById(R.id.empecemos);
        tv_empecemos.getBackground().setAlpha(89);

        radioGroup1 = findViewById(R.id.radio_pregunta1);
        radioGroup2 = findViewById(R.id.radio_pregunta2);
        Log.d("TAMAÑO", radioGroup1.getChildCount()+"");


        preguntas = (ArrayList<Pregunta>) getIntent().getSerializableExtra("Preguntas");

        String [] pregunta1Opciones = preguntas.get(0).getOpciones();

        for (int i = 1, k = 0; i < radioGroup1.getChildCount(); i++, k++) {
            RadioButton aux = (RadioButton) radioGroup1.getChildAt(i);
            aux.setText(pregunta1Opciones[k]);
        }

        String [] pregunta2Opciones = preguntas.get(1).getOpciones();

        for (int i = 1, k = 0; i < radioGroup2.getChildCount(); i++, k++) {
            RadioButton aux = (RadioButton) radioGroup2.getChildAt(i);
            aux.setText(pregunta2Opciones[k]);
        }

        enviar = findViewById(R.id.btn_enviar_respuesta);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean p1 = comprobarPregunta1();
                boolean p2 = comprobarPregunta2();



            }
        });

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
