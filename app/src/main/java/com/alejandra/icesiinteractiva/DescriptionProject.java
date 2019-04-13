package com.alejandra.icesiinteractiva;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alejandra.icesiinteractiva.DB.DBHandler;
import com.alejandra.icesiinteractiva.model.Proyecto;

public class DescriptionProject extends AppCompatActivity {

    private LinearLayout linear_proyecto;
    private TextView nombre_proyecto;
    private TextView tv_descripcion_proyecto;
    private TextView tv_nombre_expositores;
    private Proyecto visitando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_project);

        nombre_proyecto = findViewById(R.id.nombre_proyecto_actividad_descripcion);
        nombre_proyecto.getBackground().setAlpha(89);
        linear_proyecto = findViewById(R.id.linear_proyecto_activity_descripcion);
        linear_proyecto.getBackground().setAlpha(48);
        tv_descripcion_proyecto = findViewById(R.id.tv_descripcion_proyecto);

        tv_nombre_expositores = findViewById(R.id.nombre_expositores_proyecto);

        visitando = (Proyecto) getIntent().getSerializableExtra("Proyecto");

        tv_nombre_expositores.setText("Expositor\n" + visitando.getExhibitors());
        tv_descripcion_proyecto.setText(visitando.getDescription());
        nombre_proyecto.setText(visitando.getName());

    }
}
