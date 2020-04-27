package com.example.hms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class WardenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warden);

//        System.out.println();
        navigationView = findViewById(R.id.warden_nav_view);
        Toolbar toolbar=findViewById(R.id.warden_toolbar);
        setSupportActionBar(toolbar);

        drawer=findViewById(R.id.warden_drawer_layout);

        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().replace(R.id.warden_frame_container, new MessageFragment()).commit();
        navigationView.setCheckedItem(R.id.warden_nav_home);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.warden_nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new MessageFragment()).commit();
                break;
            case R.id.nav_lodge_new_complaint:
                startActivity(new Intent(WardenActivity.this,LaunchComplaintActivity.class));
                break;
            case R.id.warden_nav_view_all_complaints:
                startActivity(new Intent(WardenActivity.this,ViewComplaintActivity.class));
                break;
            case R.id.warden_nav_notice:

                break;

            case R.id.warden_nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new AboutFragment()).commit();
                break;

            case R.id.warden_nav_signout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(WardenActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(WardenActivity.this,LoginActivity.class));
                break;
        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            FirebaseAuth.getInstance().signOut();
            super.onBackPressed();
            return;
        }
        else { Toast.makeText(getBaseContext(), "Tap back button in order to logout", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();

    }
}