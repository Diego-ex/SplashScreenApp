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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    TextView WelcomeTXT, continuarTXT, nuevoUsuario;
    ImageView loginImageView;
    TextInputLayout usuarioTXT, contrasenaTXT;
    MaterialButton btnInicioSesion;
    TextInputEditText emailEditText, passwordEditText;
    private FirebaseAuth mAuth;
    //
    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;
    public static final int RC_SIGN_IN = 0;

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

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        mAuth = FirebaseAuth.getInstance();

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
                    finish();
            }
        });

        btnInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

        //Inicio de sesión por Google Sign In
        signInButton = findViewById(R.id.loginGoogle);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InicioPorGoogle();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private void InicioPorGoogle(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e){
                Toast.makeText(LoginActivity.this, "Falló Google", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(LoginActivity.this, UsuarioActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Falló en iniciar sesión", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //Método de validación
    public void validate() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
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
        iniciarSesion(email, password);
    }

    public void iniciarSesion(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // se comparan las credenciales con el servidor de firebase, es decir, si la comparación esta bien inicia sesión
                        if (task.isSuccessful()){
                            Intent intent = new Intent(LoginActivity.this, UsuarioActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(LoginActivity.this, "Credenciales equivocadas, trata de nuevo", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}