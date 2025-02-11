package com.example.tms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Find the TextView for displaying the detailed date
        TextView tvDetailedDate = view.findViewById(R.id.tvDetailedDate);

        // Set the current detailed date
        String currentDate = getCurrentDetailedDate();
        tvDetailedDate.setText(currentDate);

        return view;
    }

    // Method to get the current detailed date
    private String getCurrentDetailedDate() {
        // Format: "EEEE, dd MMMM yyyy" -> e.g., "Monday, 22nd September 2024"
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }
}
