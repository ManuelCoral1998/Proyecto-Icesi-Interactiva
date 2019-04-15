package com.alejandra.icesiinteractiva;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alejandra.icesiinteractiva.DB.DBHandler;
import com.alejandra.icesiinteractiva.model.Pregunta;
import com.alejandra.icesiinteractiva.model.Proyecto;

import java.util.ArrayList;

public class ProjectList extends AppCompatActivity implements DBHandler.OnFinishProjects{

    private ListView listaProyectos;
    private Adapter adaptador;
    private LinearLayout linear_proyectos;
    private TextView tv_proyectos;

    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);

        listaProyectos = findViewById(R.id.listView);

        linear_proyectos= findViewById(R.id.linear_proyectos);

        linear_proyectos.getBackground().setAlpha(48);

        tv_proyectos = findViewById(R.id.tv_proyectos);

        tv_proyectos.getBackground().setAlpha(89);

        db = DBHandler.getInstance();
        db.setOnFinishProjects(this);
        db.traerDatosProyectos();

    }

    private ArrayList<Proyecto> generarArrayList () {
        ArrayList<Proyecto> proyectos = db.darProyectos();
        return proyectos;
    }

    @Override
    public void onFinishProjects() {
        adaptador = new Adapter(this, generarArrayList());



        listaProyectos.setAdapter(adaptador);

        listaProyectos.setTextFilterEnabled(true);

        listaProyectos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Proyecto aVisitar = (Proyecto) parent.getItemAtPosition(position);

                Intent i = new Intent(ProjectList.this, DescriptionProject.class);
                i.putExtra("Proyecto", aVisitar);
                startActivity(i);
            }
        });
    }
}
