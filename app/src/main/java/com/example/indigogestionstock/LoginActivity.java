package com.example.indigogestionstock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.indigogestionstock.Data.ClientDynamicsWebService;
import com.example.indigogestionstock.Models.User;
import com.example.indigogestionstock.UserManager.UserSessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    public EditText usernameEditText;
    public EditText passwordEditText;
    UserSessionManager session;
    Button btnLogin;
    ClientDynamicsWebService client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEditText=findViewById(R.id.user);
        passwordEditText=findViewById(R.id.motDePasse);
        btnLogin=(Button)findViewById(R.id.login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
    }

    public void Login(){
        User user = new User();
        client = new ClientDynamicsWebService();
        String username=usernameEditText.getText().toString().trim();
        String password=passwordEditText.getText().toString().trim();

        user.setUsername(username);
        user.setPassword(password);

        session= new UserSessionManager(getApplicationContext());

        client.login(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body().getId().toString().equals("null")) {
                    Toast.makeText(getApplicationContext(), "Vérifier Vos Informations !?", Toast.LENGTH_LONG).show();
                }
                if (response.body() != null) {
                    String usernameLogin=String.valueOf(response.body().getUsername());
                    String passwordLogin=String.valueOf(response.body().getPassword());
                    if ((usernameLogin.equals(username))&&(passwordLogin.equals(password))){
                         session.createUserLoginSession(String.valueOf(response.body().getId()),String.valueOf(response.body().getUsername()));
                        Toast.makeText(getApplicationContext(),"Bienvenue à notre application "+" " + response.body().getUsername()+" "+response.body().getLastname(), Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        // Add new Flag to start new Activity
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}