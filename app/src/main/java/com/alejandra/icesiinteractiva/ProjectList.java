package com.alejandra.icesiinteractiva;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alejandra.icesiinteractiva.DB.DBHandler;
import com.alejandra.icesiinteractiva.model.Proyecto;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ProjectList extends AppCompatActivity implements DBHandler.OnFinishProjects, DBHandler.OnFinishRanking{

    private ListView listaProyectos;
    private Adapter adaptador;
    private LinearLayout linear_proyectos;
    private TextView tv_proyectos;
    private BottomNavigationView navigationView;

    DBHandler db;
    FirebaseAuth auth;

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
        db.setOnFinishRanking(this);
        db.traerDatosProyectos();

        auth = FirebaseAuth.getInstance();

        navigationView = findViewById(R.id.navigation_project);
        Menu menu = navigationView.getMenu();

        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.salir:
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ProjectList.this);
                        alerta.setTitle("¡Futuro Ingeniero Telematico!");
                        alerta.setMessage("¿Estas seguro de que deseas cerrar sesión?");
                        alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                auth.signOut();
                                dialog.dismiss();
                                Intent i = new Intent(ProjectList.this, Login.class);
                                startActivity(i);
                                finish();
                            }
                        });
                        alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alerta.show();
                        break;

                    case R.id.menubar_project:
                        break;

                    case R.id.menubar_ranking:
                        db.generarRanking();
                        break;

                }
                return false;
            }
        });

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

    @Override
    public void onFinishRanking(String[] ranking) {
        Intent intent = new Intent(ProjectList.this, Ranking.class);
        intent.putExtra("Ranking", ranking);
        startActivity(intent);
        finish();
    }
}
