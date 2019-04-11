package com.alejandra.icesiinteractiva;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.alejandra.icesiinteractiva.DB.DBHandler;
import com.alejandra.icesiinteractiva.model.Invitado;

public class Login extends AppCompatActivity {

    private DBHandler conn;

    private EditText et_signup_name;
    private EditText et_signup_email;
    private CheckBox cb_acept_info;
    private Button btn_signup_ingresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_signup_name = findViewById(R.id.et_signup_nombre);
        et_signup_email = findViewById(R.id.et_signup_correo);
        cb_acept_info = findViewById(R.id.cb_acept_info);
        btn_signup_ingresar = findViewById(R.id.btn_signup_ingresar);

        btn_signup_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = et_signup_name.getText().toString();
                String correo = et_signup_email.getText().toString();
                boolean check = cb_acept_info.isChecked();

                Invitado invitado = null;

                if (nombre.equals("") || correo.equals("")) {

                    AlertDialog.Builder alerta = new AlertDialog.Builder(Login.this);
                    alerta.setTitle("Alerta");
                    alerta.setMessage("Por favor ingresa todos los campos");
                    alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alerta.show();
                } else {
                    invitado = new Invitado(nombre, correo, check);
                }

                if (invitado != null) {
                    Intent intent = new Intent(Login.this, Welcome.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

        conn = DBHandler.getInstance();



    }
}
