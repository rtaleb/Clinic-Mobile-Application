package com.mc.info.lumc;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class RegisterActivity extends AppCompatActivity {

    FloatingActionButton fab;
    CardView cvAdd;
    EditText etEmail;
    EditText etPassword;
    EditText etPasswordRepeat;
    EditText etFirstName;
    EditText etLastName;
    EditText etPhoneNumber;
    EditText etCity;
    EditText etStreet;
    EditText etBuilding;
    EditText etSpecialty;
    EditText etExperienceYears;
    Spinner spUserType;
    LinearLayout spc , expyr;
    Button btGo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fab= (FloatingActionButton) findViewById(R.id.fab);
        cvAdd= (CardView) findViewById(R.id.cv_add);
        etEmail = (EditText) findViewById(R.id.activity_register_et_email);
        etPassword = (EditText) findViewById(R.id.activity_register_et_password);
        etPasswordRepeat = (EditText) findViewById(R.id.activity_register_et_repeatpassword);
        etFirstName = (EditText) findViewById(R.id.activity_register_et_fname);
        etLastName = (EditText) findViewById(R.id.activity_register_et_lname);
        etPhoneNumber = (EditText) findViewById(R.id.activity_register_et_phone);
        etCity = (EditText) findViewById(R.id.activity_register_et_city);
        etStreet = (EditText) findViewById(R.id.activity_register_et_street);
        etBuilding = (EditText) findViewById(R.id.activity_register_et_building);
        etExperienceYears = (EditText) findViewById(R.id.activity_register_et_expYears);
        etSpecialty = (EditText) findViewById(R.id.activity_register_et_specialty);
        spUserType = (Spinner) findViewById(R.id.activity_register_sp_userType);
        spc = (LinearLayout) findViewById(R.id.spc);
        expyr = (LinearLayout) findViewById(R.id.expyr);
        btGo= (Button) findViewById(R.id.activity_register_bt_go);

        spUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spUserType.getSelectedItem().toString().equals("Doctor")){
                    spc.setVisibility(View.VISIBLE);
                    expyr.setVisibility(View.VISIBLE);}
                else{
                    spc.setVisibility(View.INVISIBLE);
                    expyr.setVisibility(View.INVISIBLE);}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (spUserType.getSelectedItem().toString().equals("Doctor")){
            spc.setVisibility(View.VISIBLE);
            expyr.setVisibility(View.VISIBLE);}
        else{
            spc.setVisibility(View.INVISIBLE);
            expyr.setVisibility(View.INVISIBLE);}

        btGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String passwordRepeat = etPasswordRepeat.getText().toString().trim();
                final String firstName= etFirstName.getText().toString().trim();
                final String lastName= etLastName.getText().toString().trim();
                final String phoneNumber = etPhoneNumber.getText().toString().trim();
                final String city = etCity.getText().toString().trim();
                final String street = etStreet.getText().toString().trim();
                final String building = etBuilding.getText().toString().trim();
                final int experienceYears = Integer.parseInt(etExperienceYears.getText().toString().trim());
                final String specialty = etSpecialty.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 8) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 8 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!TextUtils.equals(password,passwordRepeat)){
                    Toast.makeText(getApplicationContext(), "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(firstName)) {
                    Toast.makeText(getApplicationContext(), "Enter first name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(lastName)) {
                    Toast.makeText(getApplicationContext(), "Enter last name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(getApplicationContext(), "Enter phone!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(city)) {
                    Toast.makeText(getApplicationContext(), "Enter city!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(street)) {
                    Toast.makeText(getApplicationContext(), "Enter street!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(building)) {
                    Toast.makeText(getApplicationContext(), "Enter building!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //create user
                DBHandler.getInstance().getmAuth().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(RegisterActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    DBHandler db =DBHandler.getInstance();
                                    if(spUserType.getSelectedItem().toString().equals("Doctor")){
                                        Doctor my = new Doctor(DBHandler.getInstance().getUser().getUid(),firstName,lastName,phoneNumber, email,new Address(city,street,building),specialty,experienceYears);
                                        db.database.getReference().child(DBHandler.TABLE_DOCTOR).child(my.getId()).setValue(my);
                                        db.setLoginType(DBHandler.LoginType.DOCTOR);
                                        db.setLoggedIn(true);
                                        db.setActiveUser(my);
                                        Toast.makeText(RegisterActivity.this, db.getLoginType().toString(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Patient me = new Patient(DBHandler.getInstance().getUser().getUid(),firstName,lastName,phoneNumber, email,new Address(city,street,building));
                                        db.database.getReference().child(DBHandler.TABLE_PATIENT).child(me.getId()).setValue(me);
                                        db.setLoginType(DBHandler.LoginType.PATIENT);
                                        db.setLoggedIn(true);
                                        db.setActiveUser(me);
                                    }
                                    startActivity(new Intent(RegisterActivity.this, Main.class));
                                    finish();
                                }
                            }
                        });
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShowEnterAnimation();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth()/2,0, fab.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd,cvAdd.getWidth()/2,0, cvAdd.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.plus);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }
    @Override
    public void onBackPressed() {

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP)animateRevealClose();
        else super.onBackPressed();
    }
}
