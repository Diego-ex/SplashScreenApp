package com.poblete.splashscreenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //línea de código para el full screen del splashscreen de la App, ocultando la barra superior. Quita una parte de arriba
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Agregando animaciones
        Animation animacion1 = AnimationUtils.loadAnimation(this,R.anim.desplazamiento_arriba);
        Animation animacion2 = AnimationUtils.loadAnimation(this,R.anim.desplazamiento_abajo);
        //referencias
        TextView ByTxt = findViewById(R.id.ByTxt);
        final TextView DevAndMasterTxt = findViewById(R.id.DevAndMasterTxt);
        final ImageView logoImageView = findViewById(R.id.logoImageView);

        ByTxt.setAnimation(animacion2);
        DevAndMasterTxt.setAnimation(animacion2);
        logoImageView.setAnimation(animacion1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Verificación de usuario al iniciar la aplicación
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(MainActivity.this, UsuarioActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    //caso contrario si la sesión no esta iniciada
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    // arreglo de la cantidad de animaciones que hará .
                    Pair[] pairs = new Pair[2];
                    pairs[0] = new Pair<View, String>(logoImageView, "logoImageTransition");
                    pairs[1]  = new Pair<View, String>(DevAndMasterTxt, "textTransition");

                    //verificar la version. buscada en internet
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                        startActivity(intent, options.toBundle());
                    }else{
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }, 4000);
    }
}