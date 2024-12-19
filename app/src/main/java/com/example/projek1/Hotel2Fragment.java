package com.example.projek1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class Hotel2Fragment extends Fragment {

    public Hotel2Fragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hotel2, container, false);

        // Hide the Bottom Navigation Bar
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setBottomNavigationVisibility(false);
        }

        Button button = rootView.findViewById(R.id.back);
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

        Button btnHotel1 = rootView.findViewById(R.id.button3);
        btnHotel1.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("hotelName", "The Oberoi Lombok");
            bundle.putDouble("basePrice", 100.0);

            BookingFragment fragment = new BookingFragment();
            fragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.flFragment, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        return rootView;
    }

}
