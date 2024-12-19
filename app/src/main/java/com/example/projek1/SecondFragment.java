package com.example.projek1;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class SecondFragment extends Fragment {

    private EditText searchEditText;
    private List<CardView> cardViews;
    private List<String> cardIds;

    public SecondFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_second, container, false);

        ViewGroup rootGroup = (ViewGroup) rootView;
        findAndSetupCardViews(rootGroup);

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the search input field
        searchEditText = view.findViewById(R.id.searchEditText);

        // Initialize the lists
        cardViews = new ArrayList<>();
        cardIds = new ArrayList<>();

        // Get the parent layout containing all CardViews
        ViewGroup parentLayout = view.findViewById(R.id.parentLayout);

        // Check if the parentLayout is not null
        if (parentLayout == null) {
            throw new IllegalStateException("Parent layout not found. Make sure R.id.parentLayout exists in your XML layout.");
        }

        // Dynamically populate cardViews and cardIds
        for (int i = 0; i < parentLayout.getChildCount(); i++) {
            View child = parentLayout.getChildAt(i);

            if (child instanceof CardView) {
                CardView cardView = (CardView) child;
                cardViews.add(cardView);

                // Get the unique identifier for this CardView (you can set it in XML or dynamically)
                TextView cardText = cardView.findViewById(R.id.cardText);
                if (cardText != null) {
                    cardIds.add(cardText.getText().toString()); // Use the TextView content as ID
                } else {
                    cardIds.add("Unknown"); // Fallback ID
                }

                // Initially set all CardViews to GONE
                cardView.setVisibility(View.GONE);
            }
        }

        // Log the result to verify initialization
        Log.d("Debug", "Number of CardViews: " + cardViews.size());
        Log.d("Debug", "Card IDs: " + cardIds);

        // Add a TextWatcher to listen for changes in the search input
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim();

                // If the query is empty, hide all CardViews
                if (query.isEmpty()) {
                    for (CardView cardView : cardViews) {
                        cardView.setVisibility(View.GONE);
                    }
                } else {
                    // Loop through CardViews and toggle visibility based on the query
                    for (int i = 0; i < cardViews.size(); i++) {
                        CardView cardView = cardViews.get(i);

                        // Check the CardView ID
                        boolean matchesId = cardIds.get(i).toLowerCase().contains(query.toLowerCase());

                        // Check the TextView content inside the CardView
                        TextView cardText = cardView.findViewById(R.id.cardText);

                        boolean matchesText = cardText.getText().toString().toLowerCase().contains(query.toLowerCase());

                        // Show the CardView if either the ID or TextView content matches
                        if (matchesId || matchesText) {
                            cardView.setVisibility(View.VISIBLE);
                        } else {
                            cardView.setVisibility(View.GONE);
                        }
                    }
                }
            }


            @Override
            public void afterTextChanged(Editable s) {
                // No action needed here
            }
        });
    }
}