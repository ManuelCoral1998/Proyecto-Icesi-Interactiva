package com.alejandra.icesiinteractiva;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DescriptionProject extends AppCompatActivity {

    private LinearLayout linear_proyecto;
    private TextView tv_proyecto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_project);

        linear_proyecto = findViewById(R.id.linear_preguntas);
        linear_proyecto.getBackground().setAlpha(48);

        tv_proyecto = findViewById(R.id.empecemos);
        tv_proyecto.getBackground().setAlpha(89);

    }
}
