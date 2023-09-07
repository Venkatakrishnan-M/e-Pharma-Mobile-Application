package com.venkatakrishnan.netmeds;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import android.os.Bundle;
import android.widget.TextView;

public class Amoxicillin extends AppCompatActivity {

    TextView name,desc,quantity,price;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amoxicillin);
        name=findViewById(R.id.drug_name2);
        desc=findViewById(R.id.drug_desc2);
        quantity=findViewById(R.id.drug_quan2);
        price=findViewById(R.id.drug_price2);

        db = FirebaseFirestore.getInstance();
        db.collection("medicine").document("f1U1RF5s0YmW6rVis8ZY")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Data retrieval is successful
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            // Iterate over the documents in the collection
                            // Access the data in each document
                            String drug_name = documentSnapshot.getString("name");
                            String drug_desc = documentSnapshot.getString("desc");
                            String drug_quan = documentSnapshot.getString("quantity");
                            String drug_price = documentSnapshot.getString("price");

                            name.setText(drug_name);
                            desc.setText(drug_desc);
                            quantity.setText("Number of Tablets: "+drug_quan);
                            price.setText("Price in Rs: "+drug_price);

                        }
                    } else {
                        // Handle the error
                        Exception exception = task.getException();
                        if (exception != null) {
                            // Handle the exception
                        }
                    }
                });
    }
}