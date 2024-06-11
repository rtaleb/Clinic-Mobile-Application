package com.mc.info.lumc;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListDoctors extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Menu menu;
    private RecyclerView recyclerView;
    private List<Doctor> doctors;
    private ArrayList<HashMap<String, String>> data = new ArrayList<>();
    private DoctorRecyclerAdapter adapter;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_doctors);
        SearchView sv = (SearchView) findViewById(R.id.activity_list_doctors_search);
        /*
        for (int i = 0; i < doctors.size(); i++) {
            data.add(doctors.get(i).toHashMap());
        }*/

        recyclerView= (RecyclerView) findViewById(R.id.activity_list_doctors_doctorList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new AsyncTask<Void,Void,List<Doctor>>(){
            @Override
            protected List<Doctor> doInBackground(Void... params) {
                while (!DBHandler.getInstance().isDataReady())
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                return DBHandler.getInstance().getDoctors();
            }

            @Override
            protected void onPostExecute(List<Doctor> doctors) {
                ListDoctors.this.doctors=doctors;
                adapter=new DoctorRecyclerAdapter(doctors);
                recyclerView.setAdapter(adapter);
            }
        }.execute();


       /* String[] hash = {DBHandler.COLUMN_FIRST_NAME, DBHandler.COLUMN_LAST_NAME , DBHandler.COLUMN_SPECIALTY};
        int[] toViewIds = {R.id.list_doctor_item_txtFname, R.id.list_doctor_item_txtLname , R.id.list_doctor_item_specialty};
        adapter = new SimpleAdapter(this, data, R.layout.list_doctor_item, hash, toViewIds);
        recyclerView = (ListView) findViewById(R.id.activity_list_doctors_doctorList);
        recyclerView.setAdapter(adapter);
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



//        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String str = adapter.getItem(i).toString();
//                Doctor d;
//                int index=str.lastIndexOf(DBHandler.COLUMN_ID+"=");
//                int last=str.indexOf(",",index);
//                if (last == -1){
//                    last=str.indexOf("}",index);
//                }
//                String idString = str.substring(index+ DBHandler.COLUMN_ID.length()+1,last);
//                d = dbHandler.getDoctorById(Integer.parseInt(idString));
//                if(idString.equals(String.valueOf( d.getId()))){
//                    Intent j = new Intent(ListDoctors.this, DoctorInfo.class);
//                    j.putExtra("take",d.getId());
//                    startActivity(j);
//                }
//            }
//        });

        drawerLayout = (DrawerLayout) findViewById(R.id.list_doctors_drawer);
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
                    startActivity(new Intent(ListDoctors.this, ListDoctors.class));
                }
                else if(item.getItemId()==R.id.drwrViewPatients)
                {
                    drawerLayout.closeDrawers();
                    startActivity(new Intent(ListDoctors.this, ListPatients.class));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_doctors, menu);
        this.menu=menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_list_doctors_ByFirstName)
        {
            unCheckAllMenuItems(menu);
            item.setChecked(true);
            adapter.sortBy(Sort.FIRST_NAME);
            adapter.notifyDataSetChanged();

            return true;
        }
        else if(item.getItemId() == R.id.menu_list_doctors_ByLastName)
        {
            unCheckAllMenuItems(menu);
            item.setChecked(true);
            adapter.sortBy(Sort.LAST_NAME);
            adapter.notifyDataSetChanged();
            return true;
        }
        else if(item.getItemId() == R.id.menu_list_doctors_BySpecialty)
        {
            unCheckAllMenuItems(menu);
            item.setChecked(true);
            adapter.sortBy(Sort.SPECIALTY);
            adapter.notifyDataSetChanged();
            return true;
        }

/*

        String[] hash = {DBHandler.COLUMN_FIRST_NAME, DBHandler.COLUMN_LAST_NAME , DBHandler.COLUMN_SPECIALTY};
        int[] toViewIds = {R.id.list_doctor_item_txtFname, R.id.list_doctor_item_txtLname , R.id.list_doctor_item_specialty};
        adapter = new SimpleAdapter(this, data, R.layout.list_doctor_item, hash, toViewIds);
        recyclerView = (ListView) findViewById(R.id.activity_list_doctors_doctorList);
        recyclerView.setAdapter(adapter);
*/

        if(toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
