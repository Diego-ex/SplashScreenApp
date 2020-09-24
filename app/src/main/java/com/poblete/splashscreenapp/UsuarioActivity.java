package com.poblete.splashscreenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UsuarioActivity extends AppCompatActivity {


    TextView emailTextView;
    MaterialButton logoutBoton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        emailTextView = findViewById(R.id.emailTextView);
        logoutBoton = findViewById(R.id.logoutBoton);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //si un usuario no es nulo, es decir, si ya inicio sesión se usará el email en esa label
        if (user != null) {
            emailTextView.setText(user.getEmail());
        }

        // cuando se pique el cerrar sesión
        logoutBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(UsuarioActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}