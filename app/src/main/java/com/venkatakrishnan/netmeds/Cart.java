package com.venkatakrishnan.netmeds;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class Cart extends Fragment {

    private List<String> cartItems;
    private ArrayAdapter<String> adapter;

    Button button;

    public Cart() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ListView listView = view.findViewById(R.id.cartListView);


        cartItems = new ArrayList<>();
        Log.d("CartFragment","You have Initialized cartItems"+cartItems);


        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, cartItems);
        listView.setAdapter(adapter);

        Button sendButton = view.findViewById(R.id.proceedToPayment);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the new activity
                Intent intent = new Intent(getActivity(), Billing.class);
                // Pass the cartItems as an extra in the Intent
                intent.putStringArrayListExtra("cartItems", new ArrayList<>(cartItems));
                // Start the new activity
                startActivity(intent);
            }
        });


        return view;
    }

    public void updateCart(String product) {
        Log.d("CartFragment", "updateCart() method called");
        Log.d("CartFragment", "CartItems inside the Cart Fragment: " + cartItems);
        Log.d("CartFragment","Product received into Cart Fragment is: "+product);
        if (cartItems != null) {
            cartItems.add(product);
            Log.d("Cart", "Cart items in Cart Fragment: " + cartItems.toString());
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void updateUI() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI(); // Call updateUI() when the fragment becomes visible
    }
}



