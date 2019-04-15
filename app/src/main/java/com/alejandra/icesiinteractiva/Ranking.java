package com.alejandra.icesiinteractiva;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Ranking extends AppCompatActivity {

    private LinearLayout linear_ranking;
    private TextView participante1;
    private TextView participante2;
    private TextView participante3;
    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

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
