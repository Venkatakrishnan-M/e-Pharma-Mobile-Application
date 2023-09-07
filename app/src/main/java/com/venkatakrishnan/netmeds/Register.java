package com.venkatakrishnan.netmeds;

//import androidx.annotation.NonNull;
import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity implements View.OnClickListener {
    EditText user,pass,passCon;
    TextView login;
    Button button;
    ProgressBar loader;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        //Button Instance
        button = findViewById(R.id.createAcc);
        button.setOnClickListener(this);

        //Firebase Instances
        mAuth=FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        //ProgressBar Instance
        loader = findViewById(R.id.progressReg);

        //GoTo Login page Code
        login = findViewById(R.id.goToLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        loader.setVisibility(View.VISIBLE);
        user = findViewById(R.id.emailInput);
        pass = findViewById(R.id.passInput);
        passCon = findViewById(R.id.passInputCon);

        String email = user.getText().toString();
        String dpass = pass.getText().toString();
        String password = passCon.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "User Name needed", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(dpass)){
            Toast.makeText(this, "Please give a Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please Confirm Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(dpass.equals(password)){

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                // Sign Up success, update UI with registration Activity

                                FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(Register.this, "User Registered Successfully, Please Verify Your email ID", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(Register.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                                userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                DocumentReference documentReference = fstore.collection("user").document(userId);
                                Map<String,Object> user = new HashMap<>();
                                user.put("user_id",userId);
                                user.put("email",email);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d(TAG,"user profile Created for: "+userId);
                                        loader.setVisibility(View.GONE);
                                        Intent registration = new Intent(getApplicationContext(), User_info1.class);
                                        startActivity(registration);
                                        finish();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        String message = e.toString();
                                        Log.d(TAG,"On Failure"+message);
                                        Toast.makeText(Register.this,message,Toast.LENGTH_LONG).show();
                                        loader.setVisibility(View.GONE);
                                    }
                                });

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                loader.setVisibility(View.GONE);
                                Toast.makeText(Register.this, "Account Creating failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
        else{
            Toast.makeText(this,"Passwords must match each other",Toast.LENGTH_LONG).show();
            return;
        }


    }
}