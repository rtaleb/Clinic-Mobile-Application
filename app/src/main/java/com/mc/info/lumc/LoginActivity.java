package com.mc.info.lumc;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etEmail;
    EditText etPassword;
    Button btGo;
    CardView cv;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);
        btGo= (Button) findViewById(R.id.bt_go);
        cv = (CardView) findViewById(R.id.cv);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        btGo.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setExitTransition(null);
                    getWindow().setEnterTransition(null);
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
                    startActivity(new Intent(this, RegisterActivity.class), options.toBundle());
                } else {
                    startActivity(new Intent(this, RegisterActivity.class));
                }
                break;
            case R.id.bt_go:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Explode explode = new Explode();
                    explode.setDuration(500);

                    getWindow().setExitTransition(explode);
                    getWindow().setEnterTransition(explode);
                }
                final ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
                btGo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email = etEmail.getText().toString();
                        final String password = etPassword.getText().toString();

                        if (email.equals("") || password.equals("")) {
                            Toast.makeText(getApplicationContext(), "Enter email address and password!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        /*if (password.equals("")) {
                            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                            return;
                        }*/
                        else {
                            //authenticate user
                            FirebaseAuth auth = FirebaseAuth.getInstance();
                            auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        // If sign in fails, display a message to the user. If sign in succeeds
                                        // the auth state listener will be notified and logic to handle the
                                        // signed in user can be handled in the listener.
                                        if (!task.isSuccessful()) {
                                            // there was an error
                                            if (password.length() < 8) {
                                                Toast.makeText(LoginActivity.this, "Error, password cannot be less than 8 characters!", Toast.LENGTH_LONG).show();}
                                            else {
                                                Toast.makeText(LoginActivity.this, "Error, bad email/password combination!", Toast.LENGTH_LONG).show();}
                                        }
                                        else {
                                            Intent intent = new Intent(LoginActivity.this, Main.class);
                                            startActivity(intent, oc2.toBundle());
                                            finish();
                                        }
                                    }
                                });
                        }
                    }
                });
                /*Intent i2 = new Intent(this, Main.class);
                startActivity(i2, oc2.toBundle());
                break;*/
        }
    }
}
