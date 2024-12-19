package com.example.projek1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SuccessFragment extends Fragment {

    public SuccessFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_success, container, false);

        TextView text = view.findViewById(R.id.uh);

        // Hide the Bottom Navigation Bar
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setBottomNavigationVisibility(false);
        }

        // Correctly reference the rootView for finding the button
        Button button = view.findViewById(R.id.back);
        button.setOnClickListener(v -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.flFragment, new FirstFragment())
                    .addToBackStack(null) // Optional: Allows back navigation
                    .commit();

            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).setBottomNavigationVisibility(true);
            }
        });

        Bundle args = getArguments();
        // DOESNT WORK AHHHHHHHHHH
        if (args != null) {
            double totalPrice1 = args.getDouble("basePrice");
            text.setText("Congratulations! Your total price is: " + totalPrice1);
        } else {
            text.setText("Booking was successful!");
        }

        return view;
    }
}