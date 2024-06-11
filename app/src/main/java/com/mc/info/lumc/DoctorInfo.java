package com.mc.info.lumc;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;


public class DoctorInfo extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Doctor doctor;
    private TextView txt,call;
    private Button btn;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private Button btnMyPatients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_info);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            doctor = (Doctor) extras.getSerializable("take");
            txt = (TextView) findViewById(R.id.activity_doctor_info_Name);
            txt.setText(doctor.getFirstName() + " " + doctor.getLastName());
            txt = (TextView) findViewById(R.id.activity_doctor_info_specialty);
            txt.setText(doctor.getSpecialty());
            txt = (TextView) findViewById(R.id.activity_doctor_info_expeienceYears);
            txt.setText(doctor.getExperienceYears() + " years");
            call = (TextView) findViewById(R.id.activity_doctor_info_phone);
            call.setText(doctor.getPhone());
            btn = (Button)findViewById(R.id.activity_doctor_info_consult);

            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+call.getText().toString()));
                    startActivity(callIntent);
                }
            });

            txt = (TextView) findViewById(R.id.activity_doctor_info_address);
            txt.setText(doctor.getAddress().toString());
            txt = (TextView) findViewById(R.id.activity_doctor_info_email);
            txt.setText(doctor.getEmail());

            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                        emailIntent.setType("message/rfc822");
                        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{txt.getText().toString()});
                        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
                        startActivity(Intent.createChooser(emailIntent,"Sending Email"));
                    } catch (ActivityNotFoundException ex){
                        Toast toast=   Toast.makeText(DoctorInfo.this,"Cannot Connect ",Toast.LENGTH_LONG);
                        toast.show();
                    }
                }

            });

            btnMyPatients = (Button)findViewById(R.id.activity_doctor_info_ViewMyPatients);
            btnMyPatients.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent( DoctorInfo.this, MyPatients.class);
                    i.putExtra("take", DoctorInfo.this.doctor);
                    startActivity(i);
                }
            });

            Person person = DBHandler.getInstance().getActiveUser();
            if(person instanceof Patient){
                btnMyPatients.setVisibility(View.INVISIBLE);
            }
        }


        drawerLayout = (DrawerLayout) findViewById(R.id.doctor_info_drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open,R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.main_nav) ;
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item){
                unCheckAllMenuItems(navigationView);
                item.setChecked(true);
                if(item.getItemId()==R.id.drwrViewDoctors)
                    startActivity(new Intent(DoctorInfo.this, ListDoctors.class));
                else if(item.getItemId()==R.id.drwrViewPatients)
                    startActivity(new Intent(DoctorInfo.this, ListPatients.class));
                return true;
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void unCheckAllMenuItems(NavigationView navigationView) {
        final Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item.hasSubMenu()) {
                SubMenu subMenu = item.getSubMenu();
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    subMenuItem.setChecked(false);
                }
            }
            else {
                item.setChecked(false);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    public void consultDoctor(View view) {
        Consults consults = new Consults(null,doctor.getId(),DBHandler.getInstance().getUser().getUid(),new Date());
        DBHandler.getInstance().addConsult(consults);
        Toast.makeText(this,"You are now this doctor's patient",Toast.LENGTH_SHORT).show();
        btn.setVisibility(view.INVISIBLE);
    }
}
