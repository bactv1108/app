package com.example.ph26503_and_net_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.ph26503_and_net_assignment.Fragment.HomeFragment;
import com.example.ph26503_and_net_assignment.Fragment.InfoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private String username;
    private String email;
    private String idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        if (intent != null) {
            username = intent.getStringExtra("username");
            email = intent.getStringExtra("email");
            idUser=intent.getStringExtra("idUser");
        }

        // Create a bundle to pass the user information to the HomeFragment
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("email", email);
        bundle.putString("idUser",idUser);
        // Create an instance of the HomeFragment and set the bundle
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        InfoFragment infoFragment = new InfoFragment();
        infoFragment.setArguments(bundle);

        // Replace the fragmentContainer with the HomeFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, homeFragment)
                .commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, homeFragment).commit();
                        break;
                    case R.id.nav_info:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, infoFragment)
                                .addToBackStack(null) // Add info fragment to the back stack
                                .commit();
                        break;
                }
                return true;
            }
        });
    }
}