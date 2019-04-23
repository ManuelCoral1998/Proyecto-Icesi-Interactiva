package com.alejandra.icesiinteractiva;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alejandra.icesiinteractiva.model.Proyecto;

public class DescriptionProject extends AppCompatActivity {

    private LinearLayout linear_proyecto;
    private TextView nombre_proyecto;
    private TextView tv_descripcion_proyecto;
    private TextView tv_nombre_expositores;
    private Proyecto visitando;

    private Button btn_escanear_qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_project);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

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

        btn_escanear_qr = findViewById(R.id.btn_escanear_qr);

        btn_escanear_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DescriptionProject.this, ScanQR.class);
                startActivityForResult(i, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_CANCELED) {
            Log.d("CERRAR", "CERRAR");
            setResult(RESULT_CANCELED);
            this.finish();
        }
    }
}
