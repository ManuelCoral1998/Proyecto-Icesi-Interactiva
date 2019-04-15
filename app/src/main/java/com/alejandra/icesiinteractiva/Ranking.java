package com.alejandra.icesiinteractiva;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class Ranking extends AppCompatActivity {

    private LinearLayout linear_ranking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        linear_ranking = findViewById(R.id.linear_ranking);
        linear_ranking.getBackground().setAlpha(49);
    }
}
