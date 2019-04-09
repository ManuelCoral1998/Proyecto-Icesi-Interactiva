package com.alejandra.icesiinteractiva;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class Login extends AppCompatActivity {


    private EditText et_signup_name;
    private EditText et_signup_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_signup_name = findViewById(R.id.et_signup_nombre);
        et_signup_email = findViewById(R.id.et_signup_correo);


    }
}
