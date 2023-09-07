package com.venkatakrishnan.netmeds;

import static androidx.constraintlayout.motion.widget.TransitionBuilder.validate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot extends AppCompatActivity {
    EditText editText;
    Button button;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        editText= findViewById(R.id.forgot_edit);
        button= findViewById(R.id.forgot_button);


        auth = FirebaseAuth.getInstance();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editText.getText().toString();
                    validateData(email);
            }
        });
    }

    private void validateData(String email){
            if(email.isEmpty()){
                Toast.makeText(this, "Enter Registered Number", Toast.LENGTH_SHORT).show();
            }
            else{
                forgetPass(email);
            }
    }

    private void forgetPass(String email){
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Forgot.this, "Forget Password - Link Sent", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Forgot.this, Login.class));
                    finish();
                }
                else {
                    Toast.makeText(Forgot.this,"Error: "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}