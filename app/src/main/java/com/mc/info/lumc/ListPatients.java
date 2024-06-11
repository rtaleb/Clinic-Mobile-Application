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

public class ListPatients extends AppCompatActivity {
    private Menu menu;
    private RecyclerView recyclerView;
    private List<Patient> patients;

    private SearchView sv;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private PatientRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_patients);
        sv= (SearchView) findViewById(R.id.activity_list_patients_search);


//        for (int i = 0; i < filteredPatients.size(); i++) {
//            data.add(filteredPatients.get(i).toHashMap());
//        }

        recyclerView= (RecyclerView) findViewById(R.id.activity_list_patients_patientList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new AsyncTask<Void,Void,List<Patient>>(){
            @Override
            protected List<Patient> doInBackground(Void... params) {
                while (!DBHandler.getInstance().isDataReady())
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                return DBHandler.getInstance().getPatients();
            }

            @Override
            protected void onPostExecute(List<Patient> patients) {
                ListPatients.this.patients=patients;
                adapter=new PatientRecyclerAdapter(patients);
                recyclerView.setAdapter(adapter);
            }
        }.execute();


/*


        String[] hash = {DBHandler.COLUMN_FIRST_NAME, DBHandler.COLUMN_LAST_NAME};
        int[] toViewIDs = {R.id.list_item_patient_firstName, R.id.list_item_patient_lastName};
        adapter = new SimpleAdapter(this, data, R.layout.list_patients_item, hash, toViewIDs);
        lv = (ListView) findViewById(R.id.activity_list_patients_patientList);
        lv.setAdapter(adapter);
*/


        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(adapter!=null)
                adapter.getFilter().filter(newText);
                return false;
            }
        });


//
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Patient p = adapter1.getItem(i);
//
//                Intent j = new Intent(ListPatients.this, PatientInfo.class);
//                j.putExtra("take",p.getId());
//                startActivity(j);
//
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
                    startActivity(new Intent(ListPatients.this, ListDoctors.class));
                }
                else if(item.getItemId()==R.id.drwrViewPatients)
                {
                    drawerLayout.closeDrawers();
                    startActivity(new Intent(ListPatients.this, ListPatients.class));
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
            adapter.sortBy(PatientRecyclerAdapter.Sort.FIRST_NAME);
            adapter.notifyDataSetChanged();
            return true;
        }
        else if(item.getItemId() == R.id.menu_list_patients_ByLastName)
        {
            unCheckAllMenuItems(menu);
            item.setChecked(true);
            adapter.sortBy(PatientRecyclerAdapter.Sort.LAST_NAME);
            adapter.notifyDataSetChanged();
            return true;
        }
/*

        String[] hash = {DBHandler.COLUMN_FIRST_NAME, DBHandler.COLUMN_LAST_NAME};
        int[] toViewIds = {R.id.list_item_patient_firstName, R.id.list_item_patient_lastName};
        adapter = new SimpleAdapter(this, data, R.layout.list_patients_item, hash, toViewIds);
        lv = (ListView) findViewById(R.id.activity_list_patients_patientList);
        lv.setAdapter(adapter);
*/

        if(toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

}
