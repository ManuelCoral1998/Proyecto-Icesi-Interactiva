package com.alejandra.icesiinteractiva;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.PointerIcon;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alejandra.icesiinteractiva.DB.DBHandler;
import com.alejandra.icesiinteractiva.model.Invitado;
import com.alejandra.icesiinteractiva.model.Proyecto;

import java.util.ArrayList;

public class ProjectList extends AppCompatActivity {

    private ListView listaProyectos;
    private Adapter adaptador;
    private LinearLayout linear_proyectos;
    private TextView tv_proyectos;

    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);

        db = DBHandler.getInstance();

        listaProyectos = (ListView) findViewById(R.id.listView);

        linear_proyectos= findViewById(R.id.linear_proyectos);

        linear_proyectos.getBackground().setAlpha(48);

        tv_proyectos = findViewById(R.id.tv_proyectos);

        tv_proyectos.getBackground().setAlpha(89);

        adaptador = new Adapter(this, generarArrayList());

        listaProyectos.setAdapter(adaptador);

        listaProyectos.setTextFilterEnabled(true);

        listaProyectos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Proyecto aVisitar = (Proyecto) parent.getItemAtPosition(position);




            }
        });

    }


    private ArrayList<Proyecto> generarArrayList () {
        ArrayList<Proyecto> proyectos = db.darProyectos();
        return proyectos;
    }

}
