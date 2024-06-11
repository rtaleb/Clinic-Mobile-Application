package com.mc.info.lumc;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.SearchView;

import java.util.List;

public class MyPatients extends AppCompatActivity {
    private Menu menu;
    private RecyclerView recyclerView;
    private List<Patient> myPatientsList;
    private PatientRecyclerAdapter myPatientsAdapter;

    private SearchView sv;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_patients);
        sv = (SearchView) findViewById(R.id.activity_my_patients_search);
        recyclerView = (RecyclerView) findViewById(R.id.activity_my_patients_myPatientList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new AsyncTask<Void, Void, List<Patient>>() {
            @Override
            protected List<Patient> doInBackground(Void... params) {
                /*while (!dbHandler.isDataReady())
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                Bundle extras = getIntent().getExtras();
                Doctor d = (Doctor) extras.getSerializable("take");
                return DBHandler.getInstance().getMyPatients(d);
            }

            @Override
            protected void onPostExecute(List<Patient> myPatientsList) {
                MyPatients.this.myPatientsList = myPatientsList;
                myPatientsAdapter = new PatientRecyclerAdapter(myPatientsList);
                recyclerView.setAdapter(myPatientsAdapter);
            }
        }.execute();


        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(myPatientsAdapter!=null)
                    myPatientsAdapter.getFilter().filter(newText);
                return false;
            }
        });

 //       recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
 //          @Override
//           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String str = myPatientsAdapter.getItem(i).toString();
//               Doctor d;
//               int index=str.lastIndexOf(DBHandler.COLUMN_ID+"=");
//                int last=str.indexOf(",",index);
//                if (last == -1){
//                    last=str.indexOf("}",index);
//                }
//                String idString = str.substring(index+ DBHandler.COLUMN_ID.length()+1,last);
//                d = dbHandler.getPatientById(Integer.parseInt(idString));
//                if(idString.equals(String.valueOf( d.getId()))){
//                    Intent j = new Intent(myPatient.this, PatientInfo.class);
//                    j.putExtra("take",p.getId());
//                    startActivity(j);
//                }
//            }
//        });



        drawerLayout = (DrawerLayout) findViewById(R.id.list_patients_drawer);
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
                {
                    drawerLayout.closeDrawers();
                    startActivity(new Intent(MyPatients.this, ListDoctors.class));
                }
                else if(item.getItemId()==R.id.drwrViewPatients)
                {
                    drawerLayout.closeDrawers();
                    startActivity(new Intent(MyPatients.this, ListPatients.class));
                }
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
            } else {
                item.setChecked(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_patients, menu);
        this.menu=menu;
        return true;
    }

    private void unCheckAllMenuItems(Menu menu) {
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
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_list_patients_ByFirstName)
        {
            unCheckAllMenuItems(menu);
            item.setChecked(true);
            myPatientsAdapter.sortBy(PatientRecyclerAdapter.Sort.FIRST_NAME);
            myPatientsAdapter.notifyDataSetChanged();
            return true;
        }
        else if(item.getItemId() == R.id.menu_list_patients_ByLastName)
        {
            unCheckAllMenuItems(menu);
            item.setChecked(true);
            myPatientsAdapter.sortBy(PatientRecyclerAdapter.Sort.LAST_NAME);
            myPatientsAdapter.notifyDataSetChanged();
            return true;
        }
        if(toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

}



