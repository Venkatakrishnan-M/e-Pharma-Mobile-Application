package com.venkatakrishnan.netmeds;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    private List<String> productList;
    private ArrayAdapter<String> adapter;
    private ListView listView;

    private ListView myListView;
    private ArrayAdapter<String> myAdapter;
    private List<String> originalData;  // The original data list
    private List<String> filteredData;  // The filtered data list


    public Home() {
        // Required empty public constructor
    }

    @SuppressLint("CutPasteId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Log.d("Home","Entered OnCreate method");


        listView = view.findViewById(R.id.productListView);
        productList = getSampleData(); // Retrieve sample data

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, productList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedProduct = productList.get(position);
                Log.d("DashboardFragment", "Selected product: " + selectedProduct);
                ((MainActivity) requireActivity()).addToCart(selectedProduct); // Call addToCart method in MainActivity
            }
        });

        myListView = view.findViewById(R.id.productListView);
        androidx.appcompat.widget.SearchView searchView = view.findViewById(R.id.searchBar);

        Log.d("Home","Reached OriginalData intialiazation");

        originalData= getSampleData();
        Log.d("Home","Original data in onCreate"+originalData);

        myAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, originalData);
        myListView.setAdapter(myAdapter);

        filteredData = new ArrayList<>(originalData);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Not used in this example
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("Home","Original data in onQueryTextChange"+originalData);

                Log.d("Home", "onQueryTextChange: " + newText);
                // Filter the data based on the search query
                filterData(newText);
                return true;
            }
        });

        return view;

    }

    private void filterData(String query) {
        Log.d("Home","The query String is :"+query);
        filteredData.clear();
        Log.d("Home","filtered data after clear :"+filteredData.toString());

        if (query.isEmpty()) {
            Log.d("Home","Inside the isEmpty");
            originalData= getSampleData();
            // If the query is empty, show all the original data
            Log.d("Home","Original data in filterData"+originalData.toString());
            filteredData.addAll(originalData);
            Log.d("Home","updated filterdata"+filteredData.toString());
        } else {
            // Filter the original data based on the query
            for (String item : originalData) {
                if (item.toLowerCase().contains(query.toLowerCase())) {
                    filteredData.add(item);
                }
            }
        }
        // Update the adapter with the filtered data
        myAdapter.clear();
        myAdapter.addAll(filteredData);
        myAdapter.notifyDataSetChanged();
    }



    @Override
    public void onResume() {
        super.onResume();
        setupListView();
        originalData.clear();
        originalData.addAll(getSampleData());
        filteredData.clear();
        filteredData.addAll(originalData);
        myAdapter.notifyDataSetChanged();
    }

    private void setupListView() {
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedProduct = productList.get(position);
                Log.d("DashboardFragment", "Selected product: " + selectedProduct);
                ((MainActivity) requireActivity()).addToCart(selectedProduct); // Call addToCart method in MainActivity
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(getActivity(), Dolo.class);
                    startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(getActivity(), Amoxicillin.class);
                    startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(getActivity(), Amlong.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }

    private List<String> getSampleData() {

        List<String> data = new ArrayList<>();
        data.add("Dolo-650mg - Price : 10");
        data.add("Amoxicillin - Price : 99");
        data.add("Amlong - Price : 25");
        data.add("Aspirin - Price : 65");
        data.add("Cetirizine - Price: 15");
        data.add("Metformin - Price: 94");
        data.add("Ibuprofen - Price: 56");
        data.add("Omeprazole - Price: 77");

        // Add more sample products as needed
        return data;
    }
}







