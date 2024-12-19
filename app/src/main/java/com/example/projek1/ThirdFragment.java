package com.example.projek1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.io.*;

public class ThirdFragment extends Fragment {

    private EditText etUsername;
    private ImageView imgProfile;
    private Button btnChangePhoto, btnSave;
    private SharedPreferences sharedPreferences;
    private Uri profileImageUri;

    public ThirdFragment(){
        // require a empty public constructor
    }

    private static final int PICK_IMAGE = 100;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);

        etUsername = view.findViewById(R.id.etUsername);
        imgProfile = view.findViewById(R.id.imgProfile);
        btnChangePhoto = view.findViewById(R.id.btnChangePhoto);
        btnSave = view.findViewById(R.id.btnSave);

        sharedPreferences = requireContext().getSharedPreferences("UserProfile", Context.MODE_PRIVATE);

        // Load saved data or set default values
        String defaultUsername = "User";
        String username = sharedPreferences.getString("username", defaultUsername);
        etUsername.setText(username);

        String defaultImageUri = "android.resource://" + requireContext().getPackageName() + "/" + R.drawable.profile;
        String imageUriString = sharedPreferences.getString("profileImageUri", defaultImageUri);

        profileImageUri = Uri.parse(imageUriString);
        imgProfile.setImageURI(profileImageUri);

        btnChangePhoto.setOnClickListener(v -> selectImage());
        btnSave.setOnClickListener(v -> saveProfile());

        return view;
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            profileImageUri = data.getData();
            imgProfile.setImageURI(profileImageUri);
        }
    }

    private void saveProfile() {
        String username = etUsername.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        if (profileImageUri != null) {
            editor.putString("profileImageUri", profileImageUri.toString());
        }
        editor.apply();
        Toast.makeText(getContext(), "Profile Saved", Toast.LENGTH_SHORT).show();
    }
}