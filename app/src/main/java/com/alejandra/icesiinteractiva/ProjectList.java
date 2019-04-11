package com.alejandra.icesiinteractiva;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.PointerIcon;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alejandra.icesiinteractiva.DB.DBHandler;
import com.alejandra.icesiinteractiva.model.Invitado;
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

        db = DBHandler.getInstance();

        listaProyectos = (ListView) findViewById(R.id.listView);

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

        Invitado temp = new Invitado("a", "b", true);

        db.crearInvitadoSQL(temp);

        while (!db.isBanderaSQL()) {
            db.traerDatosProyectos();
        }

        ArrayList<Proyecto> proyectos = db.darProyectos();

        return proyectos;
    }

}
