package com.example.tms;



import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private EditText etFirstName, etLastName, etEmail, etPhone, etDOB, etSkinType, etSkinAllergies;
    private RadioGroup rgGender;
    private Button btnSave;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference("Users");

        // Initialize UI elements
        etFirstName = view.findViewById(R.id.etFirstName);
        etLastName = view.findViewById(R.id.etLastName);
        etEmail = view.findViewById(R.id.etEmail);
        etPhone = view.findViewById(R.id.etPhone);
        etDOB = view.findViewById(R.id.etDOB);
        etSkinType = view.findViewById(R.id.etSkinType);
        etSkinAllergies = view.findViewById(R.id.etSkinAllergies);
        rgGender = view.findViewById(R.id.rgGender);
        btnSave = view.findViewById(R.id.btnSave);

        // Set email to read-only
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            etEmail.setText(currentUser.getEmail());
        }

        // Load existing data
        loadProfileData();

        // Save profile on button click
        btnSave.setOnClickListener(v -> saveProfileData());

        return view;
    }

    private void loadProfileData() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) return;

        databaseRef.child(user.getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                DataSnapshot snapshot = task.getResult();
                etFirstName.setText(snapshot.child("firstName").getValue(String.class));
                etLastName.setText(snapshot.child("lastName").getValue(String.class));
                etPhone.setText(snapshot.child("phone").getValue(String.class));
                etDOB.setText(snapshot.child("dob").getValue(String.class));
                etSkinType.setText(snapshot.child("skinType").getValue(String.class));
                etSkinAllergies.setText(snapshot.child("skinAllergies").getValue(String.class));

                String gender = snapshot.child("gender").getValue(String.class);
                if ("Male".equals(gender)) {
                    rgGender.check(R.id.rbMale);
                } else if ("Female".equals(gender)) {
                    rgGender.check(R.id.rbFemale);
                }
            }
        });
    }

    private void saveProfileData() {
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String phone = etPhone.getText().toString();
        String dob = etDOB.getText().toString();
        String skinType = etSkinType.getText().toString();
        String skinAllergies = etSkinAllergies.getText().toString();

        int selectedGenderId = rgGender.getCheckedRadioButtonId();
        RadioButton selectedGender = getView().findViewById(selectedGenderId);
        String gender = selectedGender != null ? selectedGender.getText().toString() : "";

        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName)) {
            Toast.makeText(getContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) return;

        Map<String, Object> userProfile = new HashMap<>();
        userProfile.put("firstName", firstName);
        userProfile.put("lastName", lastName);
        userProfile.put("phone", phone);
        userProfile.put("dob", dob);
        userProfile.put("gender", gender);
        userProfile.put("skinType", skinType);
        userProfile.put("skinAllergies", skinAllergies);

        databaseRef.child(user.getUid()).updateChildren(userProfile).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Profile saved successfully.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed to save profile.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
