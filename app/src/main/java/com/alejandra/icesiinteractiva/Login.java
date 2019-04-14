package com.alejandra.icesiinteractiva;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.alejandra.icesiinteractiva.DB.DBHandler;
import com.alejandra.icesiinteractiva.model.Invitado;

public class Login extends AppCompatActivity {

    DBHandler conn;
    private static final int requestCamaraPermissionID = 1001;

    private EditText et_signup_name;
    private EditText et_signup_email;
    private CheckBox cb_acept_info;
    private Button btn_signup_ingresar;
    private RelativeLayout relative;
    private RelativeLayout relative_imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Login.this,
                    new String[]{Manifest.permission.CAMERA}, requestCamaraPermissionID);
        }


        relative = findViewById(R.id.relative);
        relative.getBackground().setAlpha(40);

        relative_imagen = findViewById(R.id.relative_imagen);
        relative_imagen.getBackground().setAlpha(95);

        et_signup_name = findViewById(R.id.et_signup_nombre);
        et_signup_email = findViewById(R.id.et_signup_correo);
        cb_acept_info = findViewById(R.id.cb_acept_info);
        btn_signup_ingresar = findViewById(R.id.btn_signup_ingresar);

        conn = DBHandler.getInstance();

        btn_signup_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = et_signup_name.getText().toString();
                String correo = et_signup_email.getText().toString();
                boolean check = cb_acept_info.isChecked();

                Invitado invitado = null;

                if (nombre.equals("") || correo.equals("")) {

                    AlertDialog.Builder alerta = new AlertDialog.Builder(Login.this);
                    alerta.setTitle("¡Futuro Ingeniero Telematico!");
                    alerta.setMessage("Es necesario que ingreses todos los campos");
                    alerta.setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alerta.show();
                } else {
                    invitado = new Invitado(nombre, correo, check);
                    conn.crearInvitadoSQL(invitado);
                }

                if (invitado != null) {
                    Intent intent = new Intent(Login.this, Welcome.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

    }
}
