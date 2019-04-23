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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Ranking extends AppCompatActivity {

    private LinearLayout linear_ranking;
    private TextView participante1;
    private TextView participante2;
    private TextView participante3;
    private BottomNavigationView navigationView;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

        auth = FirebaseAuth.getInstance();

        linear_ranking = findViewById(R.id.linear_ranking);
        linear_ranking.getBackground().setAlpha(49);

        String [] ranking = getIntent().getStringArrayExtra("Ranking");

        participante1 = findViewById(R.id.participante1);
        participante1.setText(ranking[0]);
        participante2 = findViewById(R.id.participante2);
        participante2.setText(ranking[1]);
        participante3 = findViewById(R.id.participante3);
        participante3.setText(ranking[2]);

        navigationView = findViewById(R.id.navigation_ranking);
        Menu menu = navigationView.getMenu();

        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.salir:
                        AlertDialog.Builder alerta = new AlertDialog.Builder(Ranking.this);
                        alerta.setTitle("¡Futuro Ingeniero Telemático!");
                        alerta.setMessage("¿Estás seguro de que deseas cerrar sesión?");
                        alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                auth.signOut();
                                dialog.dismiss();
                                Intent i = new Intent(Ranking.this, Login.class);
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
                        Intent intent = new Intent(Ranking.this, ProjectList.class);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.menubar_ranking:
                        break;

                }

                return false;
            }
        });

    }
}
