package com.example.ph26503_and_net_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ph26503_and_net_assignment.Fragment.HomeFragment;
import com.example.ph26503_and_net_assignment.Service.UserResponse;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private ApiSerivce apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.24.30.145:3000/api/") // Replace with your API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of the ApiService
        apiService = retrofit.create(ApiSerivce.class);

        // Get references to the EditText fields and the login button
        TextInputLayout usernameed = findViewById(R.id.et_username);
        TextInputLayout passworded = findViewById(R.id.et_password);
        Button loginButton = findViewById(R.id.btn_login);
        TextView createaccount = findViewById(R.id.createaccount);
        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Set an OnClickListener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered email and password
                String username = usernameed.getEditText().getText().toString();
                String password = passworded.getEditText().getText().toString();

                // Call the login function with the entered email and password
                login(username, password);
            }
        });
    }

    private void login(String username, String password) {
        Call<UserResponse> call = apiService.login(username,password);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    if (userResponse != null && userResponse.getStatus() == 1) {
                        // Login successful
                        List<User> userList = userResponse.getData();
                        if (userList != null && userList.size() > 0) {
                            boolean isMatched = false;
                            User loggedInUser = null;
                            for (User user : userList) {
                                String apiUsername = user.getUsername();
                                String apiPassword = user.getPassword();

                                // Compare the entered email and password with the API response
                                if (username.equals(apiUsername) && password.equals(apiPassword)) {
                                    // Username and password match
                                    isMatched = true;
                                    loggedInUser = user;
                                    break;
                                }
                            }

                            if (isMatched) {

                                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("currentUserId", loggedInUser.get_id()); // Replace "get_id()" with the actual ID field name
                                editor.apply();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("username", loggedInUser.getUsername());
                                intent.putExtra("email", loggedInUser.getEmail());
                                intent.putExtra("idUser",loggedInUser.get_id());
                                intent.putExtra("image",loggedInUser.getImage());

                                // Start the MainActivity
                                startActivity(intent);
                            } else {
                                // Incorrect username or password
                                Toast.makeText(LoginActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        // Login failed
                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // API request failed
                    Toast.makeText(LoginActivity.this, "API request failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                // Handle network or API call failure
                Toast.makeText(LoginActivity.this, "Login failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

