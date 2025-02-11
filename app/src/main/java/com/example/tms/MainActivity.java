package com.example.tms;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;


import com.example.tms.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Load HomeFragment by default
        replaceFragment(new HomeFragment());

        // Handle BottomNavigationView item clicks
        binding.bottomNavView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home_m) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.profile_m) {
                replaceFragment(new ProfileFragment());
            } else if (itemId == R.id.test_m) {
                replaceFragment(new TestFragment());
            } else if (itemId == R.id.log_m) {
                replaceFragment(new LogoutFragment());
            }
           
       

            return true;
        });
    }

    // Method to replace fragments
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }


}
