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

public class LoginActivity extends AppCompatActivity {

    TextView WelcomeTXT, continuarTXT, nuevoUsuario;
    ImageView loginImageView;
    TextInputLayout usuarioTXT, contrasenaTXT;
    MaterialButton btnInicioSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //relaciones
        loginImageView = findViewById(R.id.logoImageView);
        WelcomeTXT = findViewById(R.id.WelcomeTXT);
        continuarTXT = findViewById(R.id.continuarTXT);
        usuarioTXT = findViewById(R.id.usuarioTXT);
        contrasenaTXT = findViewById(R.id.contrasenaTXT);
        btnInicioSesion = findViewById(R.id.btnInicioSesion);
        nuevoUsuario = findViewById(R.id.nuevoUsuario);

        nuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                //arreglo de la cantidad de animaciones
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(loginImageView, "logoImageTransition");
                pairs[1] = new Pair<View, String>(WelcomeTXT, "textTrans");
                pairs[2] = new Pair<View, String>(continuarTXT, "iniciaSesionTextTransition");
                pairs[3] = new Pair<View, String>(usuarioTXT, "emailInputTextTrans");
                pairs[4] = new Pair<View, String>(contrasenaTXT, "passwordInputTextTrans");
                pairs[5] = new Pair<View, String>(btnInicioSesion, "btnRegistroTrans");
                pairs[6] = new Pair<View, String>(nuevoUsuario, "newUserTrans");

                //verificar la version. buscada en internet

                    startActivity(intent);
            }
        });
    }
}