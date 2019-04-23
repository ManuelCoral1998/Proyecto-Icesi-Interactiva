package com.alejandra.icesiinteractiva;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {

    private EditText et_signup_name;
    private EditText et_signup_email;
    private Button btn_signup_ingresar;
    private RelativeLayout relative;
    private RelativeLayout relative_imagen;
    private CheckBox cb_acept_info;

    FirebaseAuth auth;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        dbHandler = DBHandler.getInstance();

        relative = findViewById(R.id.relative_signup);
        relative.getBackground().setAlpha(40);

        relative_imagen = findViewById(R.id.relative_imagen_signup);
        relative_imagen.getBackground().setAlpha(95);

        et_signup_name = findViewById(R.id.et_signup_nombre_signup);
        et_signup_email = findViewById(R.id.et_signup_correo_signup);
        btn_signup_ingresar = findViewById(R.id.btn_signup_ingresar_signup);

        cb_acept_info = findViewById(R.id.cb_acept_info);

        btn_signup_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = et_signup_name.getText().toString();
                String correo = et_signup_email.getText().toString();

                if (nombre.equals("") || correo.equals("")) {

                    AlertDialog.Builder alerta = new AlertDialog.Builder(SignUp.this);
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

                    auth.createUserWithEmailAndPassword(correo, nombre).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            //VERIFICAR EL CORREO
                            //auth.sendSignInLinkToEmail(et_signup_email.getText().toString(), actionCodeSettings);

                            String id = auth.getCurrentUser().getUid();
                            Invitado invitado = new Invitado(id, et_signup_name.getText().toString(), et_signup_email.getText().toString(), cb_acept_info.isChecked());
                            dbHandler.crearInvitadoSQL(invitado);

                            if (cb_acept_info.isChecked()) {
                                FirebaseUser user = auth.getCurrentUser();

                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Intent i = new Intent(SignUp.this, Verificacion.class);
                                        startActivity(i);
                                        finish();
                                    }
                                });
                            } else {
                                if (invitado != null) {
                                    Intent intent = new Intent(SignUp.this, Welcome.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            AlertDialog.Builder alerta = new AlertDialog.Builder(SignUp.this);
                            alerta.setTitle("¡Futuro Ingeniero Telemático!");
                            alerta.setMessage("La dirección de correo que ingresaste ya se encuentra registrada");
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




    }
}
