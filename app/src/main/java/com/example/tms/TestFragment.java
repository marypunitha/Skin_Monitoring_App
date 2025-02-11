package com.example.tms;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TestFragment extends Fragment {

    EditText skinTemp, skinMoisture, envTemp, envPressure;
    Button analyzeBtn;
    TextView result;

    DatabaseReference databaseRef;

    public TestFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test, container, false);

        // Firebase reference
        databaseRef = FirebaseDatabase.getInstance().getReference("SkinReadings");

        // Initialize UI components
        skinTemp = view.findViewById(R.id.skinTemp);
        skinMoisture = view.findViewById(R.id.skinMoisture);
        envTemp = view.findViewById(R.id.envTemp);
        envPressure = view.findViewById(R.id.envPressure);
        analyzeBtn = view.findViewById(R.id.analyzeBtn);
        result = view.findViewById(R.id.result);

        // Analyze Button Click Listener
        analyzeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double temp = Double.parseDouble(skinTemp.getText().toString());
                double moisture = Double.parseDouble(skinMoisture.getText().toString());
                double env_temperature = Double.parseDouble(envTemp.getText().toString());
                double pressure = Double.parseDouble(envPressure.getText().toString());

                // Analyze the condition
                String condition = analyzeCondition(temp, moisture, env_temperature, pressure);
                result.setText(condition);

                // Upload data to Firebase
                String id = databaseRef.push().getKey();
                SkinData data = new SkinData(temp, moisture, env_temperature, pressure, condition);
                databaseRef.child(id).setValue(data);
            }
        });

        return view;
    }

    private String analyzeCondition(double temp, double moisture, double envTemp, double pressure) {
        // Threshold Logic for different skin conditions
        if (temp > 35 && moisture > 30) {
            return "Risk: Heat Rash (Prickly Heat). Advice: Cool down, stay hydrated.";
        } else if (temp > 32 && moisture < 20) {
            return "Risk: Dry Skin or Eczema. Advice: Use moisturizer, drink water.";
        } else if (temp > 35) {
            return "Risk: Erythema (Redness) or Dermatitis. Advice: Avoid heat.";
        } else if (moisture < 20) {
            return "Risk: Psoriasis/Dry Skin. Advice: Use intense hydration.";
        } else {
            return "Skin condition: Normal. Keep maintaining good skincare.";
        }
    }

    // Helper Class for Firebase
    public static class SkinData {
        public double temperature, moisture, envTemp, pressure;
        public String condition;

        public SkinData(double temperature, double moisture, double envTemp, double pressure, String condition) {
            this.temperature = temperature;
            this.moisture = moisture;
            this.envTemp = envTemp;
            this.pressure = pressure;
            this.condition = condition;
        }
    }
}
