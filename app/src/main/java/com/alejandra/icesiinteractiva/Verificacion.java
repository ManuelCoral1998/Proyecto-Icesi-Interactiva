package com.alejandra.icesiinteractiva;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Verificacion extends AppCompatActivity {

    private Button btn_continuar_verificacion;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacion);

        auth = FirebaseAuth.getInstance();

        btn_continuar_verificacion = findViewById(R.id.btn_continuar_verificacion);

        btn_continuar_verificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.getCurrentUser().reload();
                if (auth.getCurrentUser().isEmailVerified()) {
                    Intent i = new Intent(Verificacion.this, Welcome.class);
                    startActivity(i);
                    finish();
                } else {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(Verificacion.this);
                    alerta.setTitle("¡Futuro Ingeniero Telematico!");
                    alerta.setMessage("Aún no te hemos podido verificar, por favor espera un momento");
                    alerta.setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alerta.show();
                }
            }
        });


    }
}
