package com.venkatakrishnan.netmeds;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import android.os.Bundle;
import android.widget.TextView;

public class Amlong extends AppCompatActivity {

    TextView name,desc,quantity,price;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amlong);
        name=findViewById(R.id.drug_name3);
        desc=findViewById(R.id.drug_desc3);
        quantity=findViewById(R.id.drug_quan3);
        price=findViewById(R.id.drug_price3);

        db = FirebaseFirestore.getInstance();
        db.collection("medicine").document("taTo0wUz8K0rKoaNfrSk")
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