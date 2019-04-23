package com.alejandra.icesiinteractiva;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private static final int requestCamaraPermissionID = 1001;

    private EditText et_signup_name;
    private EditText et_signup_email;
    private Button btn_signup_ingresar;
    private RelativeLayout relative;
    private RelativeLayout relative_imagen;
    private TextView registrarme;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(Login.this,
                    new String[]{Manifest.permission.CAMERA}, requestCamaraPermissionID);
        }

        auth = FirebaseAuth.getInstance();

        relative = findViewById(R.id.relative);
        relative.getBackground().setAlpha(40);

        relative_imagen = findViewById(R.id.relative_imagen);
        relative_imagen.getBackground().setAlpha(95);

        et_signup_name = findViewById(R.id.et_signup_nombre);
        et_signup_email = findViewById(R.id.et_signup_correo);
        btn_signup_ingresar = findViewById(R.id.btn_signup_ingresar);

        registrarme = findViewById(R.id.tv_registrarme);

        if (auth.getCurrentUser() != null) {
            Intent i = new Intent(this, ProjectList.class);
            startActivity(i);
            finish();
        }

        btn_signup_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = et_signup_name.getText().toString();
                String correo = et_signup_email.getText().toString();

                if (nombre.equals("") || correo.equals("")) {

                    AlertDialog.Builder alerta = new AlertDialog.Builder(Login.this);
                    alerta.setTitle("¡Futuro Ingeniero Telemático!");
                    alerta.setMessage("Es necesario que ingreses todos los campos");
                    alerta.setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alerta.show();
                } else {

                    auth.signInWithEmailAndPassword(correo, nombre).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Intent intent = new Intent(Login.this, ProjectList.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            AlertDialog.Builder alerta = new AlertDialog.Builder(Login.this);
                            alerta.setTitle("¡Futuro Ingeniero Telemático!");
                            alerta.setMessage("Estas ingresando mal tus datos");
                            alerta.setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            alerta.show();
                        }
                    });
                }
            }
        });

        registrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
