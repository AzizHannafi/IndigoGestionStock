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
import android.widget.TextView;
import android.widget.Toast;

import com.example.indigogestionstock.Data.ClientDynamicsWebService;
import com.example.indigogestionstock.Fragments.MenuGeneralFragment;
import com.example.indigogestionstock.Fragments.PurchaseFragment;
import com.example.indigogestionstock.Fragments.SalesOrderFragment;
import com.example.indigogestionstock.Models.User;
import com.example.indigogestionstock.UserManager.UserSessionManager;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ClientDynamicsWebService client;
    UserSessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DrawerLayout dr = findViewById(R.id.drawerlayout);
        MenuGeneralFragment menuGeneralFragment = new MenuGeneralFragment();
        NavigationView nv = findViewById(R.id.sideBar);
        nv.setNavigationItemSelectedListener(sidnavListener);
        session = new UserSessionManager(getApplicationContext());


        // Check user login (this is the important point)
        // If User is not logged in , This will redirect user to LoginActivity
        // and finish current activity from activity stack.
        if (session.checkLogin())
            finish();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        //get element of sidebare header
        String id = user.get(UserSessionManager.KEY_ID);
        String username = user.get(UserSessionManager.KEY_USERNAME);
        View header = nv.getHeaderView(0);
        TextView userNameLastname = (TextView) header.findViewById(R.id.usernameLastname);
        TextView codemagasin = (TextView) header.findViewById(R.id.magasin);

        client= new ClientDynamicsWebService();
        client.getUserByID(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body().getId().toString().equals("null")){
                    Toast.makeText(getApplicationContext(), "ID invalid", Toast.LENGTH_LONG).show();
                 }
                if(response.body().getId().toString().equals(id)){
                    userNameLastname.setText(response.body().getUsername().toString()+" "+response.body().getLastname().toString());
                    codemagasin.setText(response.body().getLocationCode().toString());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                //Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


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
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    break;
                case R.id.commandevente:
                    selectedFragment = new SalesOrderFragment();
                    dr.closeDrawers();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                break;
                case R.id.Commandeachat:
                    selectedFragment = new PurchaseFragment();
                    dr.closeDrawers();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    break;
                case R.id.logout:
                    session.logoutUser();
                    break;
            }

            return true;
        }
    };

}