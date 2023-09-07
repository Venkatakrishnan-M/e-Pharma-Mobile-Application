package com.venkatakrishnan.netmeds;

import static android.content.ContentValues.TAG;
import static android.webkit.ConsoleMessage.MessageLevel.LOG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class Profile extends Fragment {
    private static final String TAG = "ProfileFragment"; // Log tag

    private TextView nameText,genderText,phoneText;
    private TextView addressText;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        nameText = view.findViewById(R.id.profile_name);
        genderText = view.findViewById(R.id.profile_gender);
        phoneText = view.findViewById(R.id.profile_phone);
        addressText = view.findViewById(R.id.profile_address);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadUserProfile();
    }

    private void loadUserProfile() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            String userUID = user.getUid();
            Log.d(TAG,"user: "+userUID);
            firestore.collection("user").document(userUID).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                String name = document.getString("name");
                                String address = document.getString("address");
                                String gender = document.getString("gender");
                                String phone = document.getString("phone");

                                // Update the UI elements with user data
                                nameText.append(name);
                                genderText.append(gender);
                                addressText.append(address);
                                phoneText.append(phone);
                            } else {
                                if (document != null) {
                                    Log.d(TAG, "User document does not exist. Metadata: " + document.getMetadata());
                                } else {
                                    Log.d(TAG, "User document not found.");
                                }
                            }
                        } else {
                            // Handle the error
                            Exception exception = task.getException();
                            if (exception != null) {
                                Log.e(TAG, "Error fetching user profile: " + exception.getMessage());
                                exception.printStackTrace();
                            } else {
                                Log.e(TAG, "Error fetching user profile: Unknown exception occurred.");
                            }
                        }
                    });
        } else {
            // User not logged in
            Log.d(TAG, "User not logged in.");
        }
    }
}


