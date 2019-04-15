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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alejandra.icesiinteractiva.DB.DBHandler;
import com.alejandra.icesiinteractiva.model.Invitado;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    DBHandler conn;
    private static final int requestCamaraPermissionID = 1001;

    private EditText et_signup_name;
    private EditText et_signup_email;
    private CheckBox cb_acept_info;
    private Button btn_signup_ingresar;
    private RelativeLayout relative;
    private RelativeLayout relative_imagen;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

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
        cb_acept_info = findViewById(R.id.cb_acept_info);
        btn_signup_ingresar = findViewById(R.id.btn_signup_ingresar);

        conn = DBHandler.getInstance();

        if (auth.getCurrentUser() != null) {
            Intent i = new Intent(this, ProjectList.class);
            startActivity(i);
            finish();
        }

        final ActionCodeSettings actionCodeSettings =
                ActionCodeSettings.newBuilder()
                        // URL you want to redirect back to. The domain (www.example.com) for this
                        // URL must be whitelisted in the Firebase Console.
                        .setUrl("icesi-interactiva.firebaseapp.com")
                        // This must be true
                        .setHandleCodeInApp(true)
                        .setAndroidPackageName(
                                "com.alejandra.icesiinteractiva",
                                true, /* installIfNotAvailable */
                                "12"    /* minimumVersion */)
                        .build();

        btn_signup_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = et_signup_name.getText().toString();
                String correo = et_signup_email.getText().toString();

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
                    auth.createUserWithEmailAndPassword(correo, nombre).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            //VERIFICAR EL CORREO
                            //auth.sendSignInLinkToEmail(et_signup_email.getText().toString(), actionCodeSettings);

                            String id = auth.getCurrentUser().getUid();
                            Invitado invitado = new Invitado(id, et_signup_name.getText().toString(), et_signup_email.getText().toString(), cb_acept_info.isChecked());
                            conn.crearInvitadoSQL(invitado);

                            if (cb_acept_info.isChecked()) {
                                FirebaseUser user = auth.getCurrentUser();

                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Intent i = new Intent(Login.this, Verificacion.class);
                                        startActivity(i);
                                        finish();
                                    }
                                });
                            } else {
                                if (invitado != null) {
                                    Intent intent = new Intent(Login.this, Welcome.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                        }
                    });

                }

            }
        });

    }
}
