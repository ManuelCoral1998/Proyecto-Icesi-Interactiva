package com.alejandra.icesiinteractiva;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alejandra.icesiinteractiva.DB.DBHandler;
import com.alejandra.icesiinteractiva.model.Proyecto;

import java.util.ArrayList;

public class ProjectList extends AppCompatActivity {

    private ListView listaProyectos;
    private Adapter adaptador;

    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);

        listaProyectos = (ListView) findViewById(R.id.listView);

        listaProyectos.setAdapter(adaptador);

        listaProyectos.setTextFilterEnabled(true);

        db = DBHandler.getInstance();

        listaProyectos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Proyecto aVisitar = (Proyecto) parent.getItemAtPosition(position);

            }
        });

    }

    private ArrayList<Proyecto> generarArrayList () {
        ArrayList<Proyecto> lista = new ArrayList<>();


        return lista;
    }

}
