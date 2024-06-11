package com.mc.info.lumc;
//
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
public class Main extends AppCompatActivity{

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    CircleImageView profile;
    TextView username;
    TextView email;
    Button loginButton;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = (NavigationView) findViewById(R.id.main_nav) ;
        View header = navigationView.getHeaderView(0);
        profile = (CircleImageView) header.findViewById(R.id.login_header_profile);
        username = (TextView) header.findViewById(R.id.login_header_username);
        email = (TextView) header.findViewById(R.id.login_header_email);
        loginButton= (Button) header.findViewById(R.id.login_header_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main.this,LoginActivity.class));
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame,new LandingFragment()).commit();
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open,R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item){
                unCheckAllMenuItems(navigationView);
                item.setChecked(true);
                if(item.getItemId()== R.id.drwrViewDoctors)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame,new ListDoctorsFragment()).commit();
                    drawerLayout.closeDrawers();
                    fixMenuDoctors();
                }

                else if(item.getItemId()==R.id.drwrViewPatients)
                {
                    startActivity(new Intent(Main.this, ListPatients.class));
                }
                return true;
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new AsyncTask<Void,Void,Person>(){
            @Override
            protected Person doInBackground(Void... params) {
                while(!DBHandler.getInstance().isLoggedIn()){

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return DBHandler.getInstance().getActiveUser();
            }

            @Override
            protected void onPostExecute(final Person p) {
                if(p!=null){
                    loginButton.setVisibility(View.INVISIBLE);
                    username.setText(p.getFirstName()+ " " + p.getLastName() );
                    email.setText(p.getEmail());
                    navigationView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(DBHandler.getInstance().getLoginType().equals(DBHandler.LoginType.PATIENT))
                            {
                                Intent intent=new Intent(Main.this,PatientInfo.class);
                                intent.putExtra("take",p);
                                startActivity(intent);
                            }
                            else
                            {
                                Intent intent=new Intent(Main.this,DoctorInfo.class);
                                intent.putExtra("take",p);
                                startActivity(intent);
                            }
                        }
                    });
                    if(p instanceof Patient){
                        navigationView.getMenu().removeItem(R.id.drwrViewPatients);
                    }
                }
                //else startActivity(new Intent(Main.this,LoginActivity.class));
            }
        }.execute();

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
    private void fixMenuLanding(){
            menu.findItem(R.id.menu_list_doctors_ByFirstName).setVisible(false);
            menu.findItem(R.id.menu_list_doctors_ByLastName).setVisible(false);
            menu.findItem(R.id.menu_list_doctors_BySpecialty).setVisible(false);
        }

    private void fixMenuDoctors(){
        menu.findItem(R.id.menu_list_doctors_ByFirstName).setVisible(true);
        menu.findItem(R.id.menu_list_doctors_ByLastName).setVisible(true);
        menu.findItem(R.id.menu_list_doctors_BySpecialty).setVisible(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_doctors,menu);
        this.menu=menu;
        fixMenuLanding();
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
    public boolean onOptionsItemSelected(MenuItem item){
        Fragment fragment=getSupportFragmentManager().findFragmentById(R.id.activity_main_frame);
        AbstractAdapter adapter=null;
        if(fragment instanceof ListDoctorsFragment)
            adapter=((ListDoctorsFragment) fragment).getAdapter();
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

        if(toggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }


}
