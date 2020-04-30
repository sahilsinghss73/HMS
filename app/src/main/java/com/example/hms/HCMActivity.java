package com.example.hms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HCMActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_c_m);

//        System.out.println();
        Log.d("type","inside");
        navigationView = findViewById(R.id.hcm_nav_view);
        Toolbar toolbar=findViewById(R.id.hcm_toolbar);
        setSupportActionBar(toolbar);

        drawer=findViewById(R.id.hcm_drawer_layout);

        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().replace(R.id.hcm_frame_container, new MessageFragment()).commit();
        navigationView.setCheckedItem(R.id.hcm_nav_home);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.hcm_nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new MessageFragment()).commit();
                break;
            case R.id.hcm_nav_lodge_new_notice:
                startActivity(new Intent(HCMActivity.this,LaunchNoticeActivity.class));
                break;
            case R.id.hcm_nav_lodge_new_complaint:
                startActivity(new Intent(HCMActivity.this,LaunchComplaintActivity.class));
                break;
            case R.id.hcm_nav_view_all_complaints:
                startActivity(new Intent(HCMActivity.this,ViewComplaintActivity.class));
                break;
            case R.id.hcm_nav_notice:
                startActivity(new Intent(HCMActivity.this,NoticeActivity.class));
                break;
            case R.id.hcm_nav_contact:
                startActivity(new Intent(HCMActivity.this,ListofstudentsActivity.class));
                break;


            case R.id.hcm_nav_signout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(HCMActivity.this, "Logout Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HCMActivity.this,LoginActivity.class));
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