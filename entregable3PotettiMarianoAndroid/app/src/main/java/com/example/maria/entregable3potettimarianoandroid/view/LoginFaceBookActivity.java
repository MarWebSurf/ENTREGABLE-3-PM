package com.example.maria.entregable3potettimarianoandroid.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maria.entregable3potettimarianoandroid.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFaceBookActivity extends AppCompatActivity {
    TextView textView;
    //facebook
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    //firebase
    private FirebaseAuth mAuth;
    private Button ingresarSinSesionButton;
    //login entrar firebase nativo
    private EditText editTextMail;
    private EditText editTextPass;
    private Button botonCrear;
    private Button botonLogin;

private Button botonCerrarFBactivity;
    private TextView textViewMailLogueado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_face_book);

        textViewMailLogueado = findViewById(R.id.textview_logueado);
        botonCerrarFBactivity = findViewById(R.id.cerrar_Sesion_facebookActivity);
        textView = findViewById(R.id.irAMainTemporario);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginFaceBookActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // printHash();

        //firebase:
        mAuth = FirebaseAuth.getInstance();
        // if(Profile.getCurrentProfile() != null)  {


        ingresarSinSesionButton = findViewById(R.id.ingresar_sin_iniciar_Sesion_button_id);

        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(LoginFaceBookActivity.this, MainActivity.class);
            startActivity(intent);
            ingresarSinSesionButton.setVisibility(ingresarSinSesionButton.GONE);
        }
        if (mAuth.getCurrentUser() == null) {
            ingresarSinSesionButton.setVisibility(ingresarSinSesionButton.VISIBLE);
        }
//facebook login:
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");

        // If using in a fragment
        // loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
                textViewMailLogueado.setVisibility(View.GONE);

                Intent intent = new Intent(LoginFaceBookActivity.this, MainActivity.class);
                startActivity(intent);
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

//cerrar sesion firebase + cerrar sesion facebook

        ingresarSinSesionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginFaceBookActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        botonCerrarFBactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAuth.getCurrentUser() != null){

                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        if (AccessToken.getCurrentAccessToken() != null) {
                            //si es que esta logueado con facebook, tengo que desloguearlo
                            LoginManager.getInstance().logOut();
                        }
                        //esto es para desloguearlo de firebase, ya se  que entro con facebok o nativo
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(LoginFaceBookActivity.this, "sesion cerrada", Toast.LENGTH_SHORT).show();
                        textViewMailLogueado.setVisibility(View.GONE);
                        ingresarSinSesionButton.setVisibility(View.VISIBLE);
                    }
                }else {
                    Toast.makeText(LoginFaceBookActivity.this, "no hay sesion activa", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //crear usuario y login nativo:
        editTextMail = findViewById(R.id.editTextEmail);
        editTextPass = findViewById(R.id.editTextPass);

        botonCrear = findViewById(R.id.BotonCrearUsuario);

        botonLogin = findViewById(R.id.BotonLogUsuario);

        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAuth.getCurrentUser() != null){
                    Intent intent = new Intent(LoginFaceBookActivity.this, MainActivity.class);
                    startActivity(intent);
                }else if (validarContraseña(editTextPass.getText().toString()) && validarEmail(editTextMail.getText().toString())) {
                    loginUsuario(editTextMail.getText().toString(), editTextPass.getText().toString());
                    if(mAuth.getCurrentUser() != null){
                        Intent intent = new Intent(LoginFaceBookActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        botonCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarContraseña(editTextPass.getText().toString()) && validarEmail(editTextMail.getText().toString())) {
                    crearUsuario(editTextMail.getText().toString(), editTextPass.getText().toString());
                }
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {


        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        //AuthCredential credential = GoogleAuthProvider.getCredential(googleIdToken, null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();


                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginFaceBookActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }


    private void crearUsuario(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // si entra aca es porque se creo la cuenta exitosamente
                            Log.d("firebase", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(LoginFaceBookActivity.this, MainActivity.class);
                            startActivity(intent);


                        } else {
                            // si entra aca es porque NO se creo la cuenta exitosamente
                            Log.w("firebase", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginFaceBookActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    private boolean validarContraseña(String string) {
        //TODO HARE LAS VALIDACIONES
        if (string.length() > 5) {
            return true;
        } else {
            Toast.makeText(LoginFaceBookActivity.this, "Ingrese contraseña con al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean validarEmail(String s) {
        if (!editTextMail.getText().toString().isEmpty()) {
            return true;
        } else {
            Toast.makeText(LoginFaceBookActivity.this, "Ingrese su email!!!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void loginUsuario(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            textViewMailLogueado.setVisibility(View.VISIBLE);
                            textViewMailLogueado.setText("Usuario : " + mAuth.getCurrentUser().getEmail());
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(LoginFaceBookActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginFaceBookActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            textViewMailLogueado.setVisibility(View.VISIBLE);
            ingresarSinSesionButton.setVisibility(View.GONE);
            textViewMailLogueado.setText("Usuario : " + mAuth.getCurrentUser().getEmail());
        }

        if (FirebaseAuth.getInstance().getCurrentUser() == null && (Profile.getCurrentProfile() == null)) {
            ingresarSinSesionButton.setVisibility(View.VISIBLE);

        }
    }

    /*  private void printHash() {
        try {

            PackageInfo info =
                    getPackageManager().getPackageInfo(getApplicationContext().getPackageName(),
                            PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.v("MY KEY HASH:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }*/


}
