package com.example.projek1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class BookingFragment extends Fragment {

    public BookingFragment() {
        // Required empty public constructor
    }

    double totalPrice;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

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

        TextView tvHotelName = view.findViewById(R.id.tvHotelName);
        Spinner spinnerRoomType = view.findViewById(R.id.spinnerRoomType);
        DatePicker datePickerStart = view.findViewById(R.id.datePickerStart);
        DatePicker datePickerEnd = view.findViewById(R.id.datePickerEnd);
        TextView tvTotalPrice = view.findViewById(R.id.tvTotalPrice);
        Button btnCalculate = view.findViewById(R.id.btnCalculate);

        // Retrieve passed data
        Bundle args = getArguments();
        String hotelName = args.getString("hotelName");
        double basePrice = args.getDouble("basePrice");

        tvHotelName.setText(hotelName);

        // Function to calculate total price
        Runnable calculatePrice = () -> {
            // Calculate days
            int startYear = datePickerStart.getYear();
            int startMonth = datePickerStart.getMonth();
            int startDay = datePickerStart.getDayOfMonth();

            int endYear = datePickerEnd.getYear();
            int endMonth = datePickerEnd.getMonth();
            int endDay = datePickerEnd.getDayOfMonth();

            Calendar startDate = Calendar.getInstance();
            startDate.set(startYear, startMonth, startDay);

            Calendar endDate = Calendar.getInstance();
            endDate.set(endYear, endMonth, endDay);

            long diff = endDate.getTimeInMillis() - startDate.getTimeInMillis();
            int days = (int) (diff / (1000 * 60 * 60 * 24));

            if (days < 1) {
                tvTotalPrice.setText("Invalid dates");
                return;
            }

            // Calculate price
            double roomMultiplier = spinnerRoomType.getSelectedItemPosition() + 1; // Example multiplier
            totalPrice = (basePrice * roomMultiplier) * days;

            String formattedPrice = "Total Price: $" + totalPrice;
            tvTotalPrice.setText(formattedPrice);
        };

        // Add listeners
        datePickerStart.init(datePickerStart.getYear(), datePickerStart.getMonth(), datePickerStart.getDayOfMonth(),
                (view1, year, monthOfYear, dayOfMonth) -> calculatePrice.run());

        datePickerEnd.init(datePickerEnd.getYear(), datePickerEnd.getMonth(), datePickerEnd.getDayOfMonth(),
                (view1, year, monthOfYear, dayOfMonth) -> calculatePrice.run());

        spinnerRoomType.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                calculatePrice.run();
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                // Do nothing
            }
        });

        calculatePrice.run();

        btnCalculate.setOnClickListener(v -> {

            // DOESNT WORK AHHHHHHHHHH

            Bundle bundle1 = new Bundle();
            bundle1.putDouble("basePrice", totalPrice);

            // Create the SuccessFragment and set the bundle
            SuccessFragment successFragment = new SuccessFragment();
            successFragment.setArguments(bundle1);

            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.flFragment, new SuccessFragment())
                    .addToBackStack(null) // Optional: Allows back navigation
                    .commit();

            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).setBottomNavigationVisibility(true);
            }
        });

        return view;
    }
}