package com.venkatakrishnan.netmeds;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class User_info1 extends AppCompatActivity implements View.OnClickListener, LocationListener {

    EditText _name, _phone, _gender, _address;
    Button sub_button, gps_button;
    FirebaseFirestore db;
    ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_activity);

        //Progressbar Instance
        loader = findViewById(R.id.user_prog);

        //Edit Text Instances
        _name = findViewById(R.id.user_name);
        _phone = findViewById(R.id.user_phone);
        _gender = findViewById(R.id.user_gender);
        _address = findViewById(R.id.user_address);

        //Firebase Instance
        db = FirebaseFirestore.getInstance();

        //Submit Button Instances
        sub_button = findViewById(R.id.go_to_dashboard);
        sub_button.setOnClickListener(this);

        gps_button = findViewById(R.id.user_fetch_location);
        gps_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(User_info1.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    try {
                        getLocation();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    ActivityCompat.requestPermissions(User_info1.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);

                }
            }
        });


    }

    @SuppressLint("MissingPermission")
    public void getLocation() throws IOException {

        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, User_info1.this);
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            double lat = location.getLatitude();
            double longitude = location.getLongitude();
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());

            try {
                List<Address> addressList = geocoder.getFromLocation(lat, longitude, 1);
                _address.setText(addressList.get(0).getAddressLine(0));
            }
            catch (IOException e){

                Log.d(TAG,"error in getLocation function"+e.getMessage());
                e.printStackTrace();
            }
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==200 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            try {
                getLocation();
            } catch (IOException e) {
                Log.d(TAG,"error in onRequestPermissionsResult function"+e.getMessage());
                throw new RuntimeException(e);
            }
        }
        else{
            _address = findViewById(R.id.user_address);
            _address.setText(R.string.permission_denied);
        }
    }

    @Override
    public void onClick(View v) {
        String name = _name.getText().toString();
        String phone = _phone.getText().toString();
        String gender = _gender.getText().toString();
        String address = _address.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Name field can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Phone number field can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(gender)) {
            Toast.makeText(this, "Gender field can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(this, "Address Field can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        DocumentReference docRef = db.collection("user").document(userId);
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("phone", phone);
        user.put("gender", gender);
        user.put("address", address);
        docRef.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(User_info1.this, "User_info updated. Validate your email and Login Again", Toast.LENGTH_LONG).show();
                Log.d(TAG, "user profile updated for: " + userId);
                loader.setVisibility(View.GONE);
                Intent registration = new Intent(getApplicationContext(), Login.class);
                startActivity(registration);
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Log.d(TAG, "On Failure in user_login" + message);
                Toast.makeText(User_info1.this, message, Toast.LENGTH_LONG).show();
                loader.setVisibility(View.GONE);
            }
        });


    }


    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }
}