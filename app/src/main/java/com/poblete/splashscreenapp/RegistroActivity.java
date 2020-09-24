package com.poblete.splashscreenapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class RegistroActivity extends AppCompatActivity {


    TextView nuevoUsuario, WelcomeTXT, continuarTXT;
    ImageView RegistroImageView;
    TextInputLayout usuarioTextField, contrasenaTextField;
    MaterialButton btnInicioSesion;
    TextInputEditText emailEditText, passwordEditText, confirmPasswordEditText;
    //objeto de autenticación
    private FirebaseAuth mAuth;

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
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);

        nuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transitionBack();
            }
        });

        btnInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //método de validación
                validate();
            }
        });
        //con el objeto se puede llamar a los métodos, que son verificar y mandar las credenciales para crear un nuevo usuario.
        mAuth = FirebaseAuth.getInstance();
    }
    //Método de validación
    public void validate() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        // si el email es vacío o si es o no una dirección de email (dentro del método entra si no es una dirección)
        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Correo invalido");
            return;
        }else{
            emailEditText.setError(null);
        }
        // si la contraseña está vacía o tiene menos de 8 caracteres
        if (password.isEmpty() || password.length() < 8){
            passwordEditText.setError("Se necesitan más de 8 caracteres");
            return;
        }else if (!Pattern.compile("[0-9]").matcher(password).find()) {
            //quiero que contenga un número esta contraseña
            passwordEditText.setError("Al menos un número");
            return;
        }else {
            //si no hay error se continua con la lógica
            passwordEditText.setError(null);
        }
        // determino si las contraseñas ingresadas son iguales
        if (!confirmPassword.equals(password)){
            confirmPasswordEditText.setError("Deben ser iguales");
            return;
        }else {
            //método de registrar en firebase
            registrar(email, password);
        }
    }
    //Método de registro
    public void registrar(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //si el usuario se registro con exito será enviado a la actividad de usuario
                        if(task.isSuccessful()){
                            Intent intent = new Intent(RegistroActivity.this, UsuarioActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(RegistroActivity.this, "Falló en registrarse", Toast.LENGTH_LONG).show();
                        }
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
            finish();

    }
}