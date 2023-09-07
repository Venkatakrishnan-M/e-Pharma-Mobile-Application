//package com.venkatakrishnan.netmeds;
//
//
//import static android.content.ContentValues.TAG;
//
//import android.content.pm.PackageManager;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.Manifest;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.content.ContextCompat;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.tasks.OnSuccessListener;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Locale;
//
//public class User_info extends AppCompatActivity {
//
//    FusedLocationProviderClient fusedLocationProviderClient;
//    EditText address;
//    Button button;
//    private final static int REQUEST_CODE =100;
//    @Override
//    protected void onCreate(Bundle savedInstanceState)  {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.user_info_activity);
//        address=findViewById(R.id.user_address);
//        button=findViewById(R.id.user_fetch_location);
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        button.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                getLastLocation();
//            }
//        });
//    }
//
//    private void getLastLocation(){
//        if(ContextCompat.checkSelfPermission(User_info.this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
//                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        if(location!=null){
//                            Geocoder geocoder = new Geocoder(User_info.this, Locale.getDefault());
//                            List<Address> add = null;
//                            try {
//                                add = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
//                                Log.d(TAG,"Address is: "+add.get(0).getAddressLine(0));
//                                address.setText(add.get(0).getAddressLine(0));
//
//                            } catch (IOException e) {
//                                throw new RuntimeException(e);
//                            }
//                        }
//                    }
//                });
//        }else{
//            askPermission();
//        }
//    }
//    private void askPermission(){
//        ActivityCompat.requestPermissions(User_info.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode == REQUEST_CODE){
//            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                getLastLocation();
//            }else{
//                Toast.makeText(User_info.this,"Please Provide the Permissions",Toast.LENGTH_SHORT).show();
//
//            }
//        }
//    }
//}
