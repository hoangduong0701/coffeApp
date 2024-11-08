package com.example.menunav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.example.menunav.fragment.AboutFragment;
import com.example.menunav.fragment.MainFragment;
import com.example.menunav.fragment.MessFragment;
import com.example.menunav.fragment.SettingsFragment;
import com.example.menunav.fragment.callFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    ImageButton  menuBtn;
    MessFragment messFragment;
    SettingsFragment settingsFragment;
    AboutFragment aboutFragment;
    callFragment callFragment;
    MainFragment mainFragment;
    BookActivity bookActivity;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout =findViewById(R.id.drawrlayout);
        context = this;
        messFragment =new MessFragment();
        callFragment = new callFragment();
        mainFragment = new MainFragment();
        aboutFragment = new AboutFragment();
        settingsFragment = new SettingsFragment();
        bookActivity = new BookActivity();
        menuBtn = findViewById(R.id.main_menu_btn);
        listenNer();
        navigationView();
        bottomNavigationView();
    }



    void listenNer(){
        menuBtn.setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));
    }
    void navigationView(){
        navigationView = findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (drawerLayout != null){
                    drawerLayout.closeDrawers();
                }
                if (item.getItemId() == R.id.navChat){
                    displayFragment(messFragment);

                }
                if (item.getItemId() == R.id.navHome){
                   displayFragment(mainFragment);

                }
                if (item.getItemId() == R.id.navAbout){

                    displayFragment(aboutFragment);
                }
                if (item.getItemId() == R.id.navCall){

                    displayFragment(callFragment);
                }
                if (item.getItemId() == R.id.setting){

                    displayFragment(settingsFragment);
                }
                if (item.getItemId() == R.id.book){
                    startActivity(new Intent(context, BookActivity.class));
                }
                return true;

            }

        });


    }
    private void displayFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, fragment);
        transaction.hide(messFragment);
        transaction.hide(settingsFragment);
        transaction.hide(callFragment);
        transaction.hide(mainFragment);
        transaction.hide(aboutFragment);
        transaction.show(fragment);
        transaction.commit();
    }
    void bottomNavigationView() {

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_chat) {

                    displayFragment(messFragment);
                }
                if (item.getItemId() == R.id.menu_phonebook) {
                    displayFragment(callFragment);

                }
                if (item.getItemId() == R.id.menu_story) {

                    displayFragment(aboutFragment);

                }

                if (item.getItemId() == R.id.menu_profile) {

                    displayFragment(settingsFragment);

                }

                return true;

            }
        });
        bottomNavigationView.setSelectedItemId(R.id.menu_chat);
    }
}