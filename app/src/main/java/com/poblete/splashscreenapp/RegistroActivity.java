package com.poblete.splashscreenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class RegistroActivity extends AppCompatActivity {


    TextView nuevoUsuario, WelcomeTXT, continuarTXT;
    ImageView RegistroImageView;
    TextInputLayout usuarioTextField, contrasenaTextField;
    MaterialButton btnInicioSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //relaciones
        RegistroImageView = findViewById(R.id.logoImageView);
        WelcomeTXT = findViewById(R.id.WelcomeTXT);
        continuarTXT = findViewById(R.id.continuarTXT);
        usuarioTextField = findViewById(R.id.usuarioTextField);
        contrasenaTextField = findViewById(R.id.contrasenaTextField);
        btnInicioSesion = findViewById(R.id.btnInicioSesion);
        nuevoUsuario = findViewById(R.id.nuevoUsuario);

        nuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transitionBack();
            }
        });

    }

    //lo que va a pasar cuando presionen la flecha atras
    @Override
    public void onBackPressed(){
        transitionBack();
    }

    public void transitionBack() {
        Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
        //arreglo de la cantidad de animaciones
        Pair[] pairs = new Pair[7];
        pairs[0] = new Pair<View, String>(RegistroImageView, "logoImageTransition");
        pairs[1] = new Pair<View, String>(WelcomeTXT, "textTrans");
        pairs[2] = new Pair<View, String>(continuarTXT, "iniciaSesionTextTransition");
        pairs[3] = new Pair<View, String>(usuarioTextField, "emailInputTextTrans");
        pairs[4] = new Pair<View, String>(contrasenaTextField, "passwordInputTextTrans");
        pairs[5] = new Pair<View, String>(btnInicioSesion, "btnRegistroTrans");
        pairs[6] = new Pair<View, String>(nuevoUsuario, "newUserTrans");

        //verificar la version. buscada en internet
            startActivity(intent);

    }
}