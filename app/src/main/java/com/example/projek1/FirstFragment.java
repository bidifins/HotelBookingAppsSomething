package com.example.projek1;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FirstFragment extends Fragment {

    private ImageView ProfilePNG;
    private TextView tvDisplayUsername;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_first, container, false);

        ProfilePNG = rootView.findViewById(R.id.ProfilePNG);
        tvDisplayUsername = rootView.findViewById(R.id.tvDisplayUsername);

        sharedPreferences = requireContext().getSharedPreferences("UserProfile", Context.MODE_PRIVATE);

        // Correctly reference the rootView for finding the button
        ImageButton button = rootView.findViewById(R.id.notifbutton);
        button.setOnClickListener(v -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.flFragment, new NotifFragment())
                    .addToBackStack(null) // Optional: Allows back navigation
                    .commit();
        });

        // Automatically find all CardView elements in the layout
        ViewGroup rootGroup = (ViewGroup) rootView;
        findAndSetupCardViews(rootGroup);

        loadProfile();

        return rootView;
    }

    private void findAndSetupCardViews(ViewGroup rootGroup) {
        for (int i = 0; i < rootGroup.getChildCount(); i++) {
            View child = rootGroup.getChildAt(i);

            if (child instanceof CardView) {
                // Set up click listener for the CardView
                CardView cardView = (CardView) child;
                cardView.setOnClickListener(v -> {
                    String cardIdName = getResources().getResourceEntryName(cardView.getId());
                    String fragmentName = cardIdName + "Fragment";

                    try {
                        // Dynamically instantiate the fragment
                        Class<?> fragmentClass = Class.forName("com.example.projek1." + fragmentName);
                        Fragment fragment = (Fragment) fragmentClass.newInstance();

                        // Begin fragment transaction
                        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.flFragment, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } else if (child instanceof ViewGroup) {
                // Recursively find CardViews in child ViewGroups
                findAndSetupCardViews((ViewGroup) child);
            }
        }
    }

    private void loadProfile() {
        String defaultUsername = "User";
        String username = sharedPreferences.getString("username", defaultUsername);

        String defaultImageUri = "android.resource://" + requireContext().getPackageName() + "/" + R.drawable.profile;
        String imageUriString = sharedPreferences.getString("profileImageUri", defaultImageUri);

        tvDisplayUsername.setText(username);

        Uri imageUri = Uri.parse(imageUriString);
        ProfilePNG.setImageURI(imageUri);
    }

}
