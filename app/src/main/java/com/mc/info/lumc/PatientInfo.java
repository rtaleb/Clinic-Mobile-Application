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
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PatientInfo extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private TextView txt,call;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    Patient p;
    private Button btnMyDoctors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            p = (Patient) extras.getSerializable("take");
            txt = (TextView) findViewById(R.id.activity_patient_info_Name);
            txt.setText(p.getFirstName() + " " + p.getLastName());
            call = (TextView) findViewById(R.id.activity_patient_info_Phone);
            call.setText(p.getPhone());

            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + call.getText().toString()));
                    startActivity(callIntent);

                }
            });


            txt = (TextView) findViewById(R.id.activity_patient_info_Address);
            txt.setText(p.getAddress().toString());
            txt = (TextView) findViewById(R.id.activity_patient_info_Email);
            txt.setText(p.getEmail());
            txt = (TextView) findViewById(R.id.activity_patient_info_BloodGroup);
            txt.setText(p.getBloodGroup());
            txt = (TextView) findViewById(R.id.activity_patient_info_Height);
            txt.setText(p.getHeight());
            txt = (TextView) findViewById(R.id.activity_patient_info_Weight);
            txt.setText(p.getWeight());

            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                        emailIntent.setType("message/rfc822");
                        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{txt.getText().toString()});
                        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
                        startActivity(Intent.createChooser(emailIntent, "Sending Email"));
                    } catch (ActivityNotFoundException ex) {
                        Toast toast = Toast.makeText(PatientInfo.this, "Cannot Connect ", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            });

            btnMyDoctors = (Button)findViewById(R.id.activity_patient_info_ViewMyDoctors);
            btnMyDoctors.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent( PatientInfo.this, MyDoctors.class);
                    i.putExtra("take", PatientInfo.this.p);
                    startActivity(i);
                }
            });

            Person person = DBHandler.getInstance().getActiveUser();
            if(person instanceof Doctor){
                btnMyDoctors.setVisibility(View.INVISIBLE);
            }
        }
        drawerLayout = (DrawerLayout) findViewById(R.id.patient_info_drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open,R.string.close);

            drawerLayout = (DrawerLayout) findViewById(R.id.patient_info_drawer);
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
                        startActivity(new Intent(PatientInfo.this, ListDoctors.class));
                    else if(item.getItemId()==R.id.drwrViewPatients)
                        startActivity(new Intent(PatientInfo.this, ListPatients.class));
                    return true;
                }
            });
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(DBHandler.getInstance().getLoginType().equals(DBHandler.LoginType.PATIENT)){

        }
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
            } else {
                item.setChecked(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_patient_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_patient_info_editProfile)
            Toast.makeText(this,"editProfile",Toast.LENGTH_SHORT).show();
        if(toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }


    public void goToExam(View view) {
        Intent intent = new Intent(this, MedicalExaminationForm.class);
        intent.putExtra("patient", p);
        startActivity(intent);
    }

    public void goToListReports(View view) {
        Intent intent = new Intent(this,ListReports.class);
        intent.putExtra("take",p);
        startActivity(intent);
    }
    public void goToMedicalResult(View view){
        Intent intent = new Intent(this,ListMedicalResult.class);
        intent.putExtra("patient",p);
        startActivity(intent);
    }

    public void goToHealthState(View view){
        Intent intent = new Intent(this,HealthStateForm.class);
        intent.putExtra("patient",p);
        startActivity(intent);
    }

    public void goToMyMedicines(View view){
        Intent i = new Intent(PatientInfo.this,PatientMedicines.class);
        i.putExtra("take",p);
        startActivity(i);
    }
}

