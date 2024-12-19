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

public class NotifFragment extends Fragment {

    public NotifFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notif, container, false);

        // Hide the Bottom Navigation Bar
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setBottomNavigationVisibility(false);
        }

        // Correctly reference the rootView for finding the button
        Button button = rootView.findViewById(R.id.back);
        button.setOnClickListener(v -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.flFragment, new FirstFragment())
                    .addToBackStack(null) // Optional: Allows back navigation
                    .commit();
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Show the Bottom Navigation Bar when leaving this fragment
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setBottomNavigationVisibility(true);
        }
    }
}
