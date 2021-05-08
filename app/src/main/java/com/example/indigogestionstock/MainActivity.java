package com.example.indigogestionstock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.indigogestionstock.Fragments.MenuGeneralFragment;
import com.example.indigogestionstock.Fragments.PurchaseFragment;
import com.example.indigogestionstock.Fragments.SalesOrderFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DrawerLayout dr = findViewById(R.id.drawerlayout);
        MenuGeneralFragment menuGeneralFragment = new MenuGeneralFragment();
        NavigationView nv = findViewById(R.id.sideBar);
        nv.setNavigationItemSelectedListener(sidnavListener);

        findViewById(R.id.btnMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dr.openDrawer(GravityCompat.START);
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, menuGeneralFragment).commit();

    }  public NavigationView.OnNavigationItemSelectedListener sidnavListener = new NavigationView.OnNavigationItemSelectedListener(){

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            DrawerLayout dr = findViewById(R.id.drawerlayout);
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.MenuGéneral:
                    selectedFragment = new MenuGeneralFragment();
                    dr.closeDrawers();
                    break;
                case R.id.commandevente:
                    selectedFragment = new SalesOrderFragment();
                    dr.closeDrawers();

                break;
                case R.id.Commandeachat:
                    selectedFragment = new PurchaseFragment();
                    dr.closeDrawers();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };

}