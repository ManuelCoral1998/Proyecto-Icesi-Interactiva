package com.alejandra.icesiinteractiva;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Question extends AppCompatActivity {

    private LinearLayout linear_preguntas;
    private TextView tv_empecemos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        linear_preguntas = findViewById(R.id.linear_preguntas);
        linear_preguntas.getBackground().setAlpha(48);

        tv_empecemos = findViewById(R.id.empecemos);
        tv_empecemos.getBackground().setAlpha(89);




    }
}
