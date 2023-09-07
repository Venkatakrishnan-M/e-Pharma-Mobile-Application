package com.venkatakrishnan.netmeds;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends AppCompatActivity implements View.OnClickListener {
    Button login;
    EditText user,pass;
    TextView register,forget;
    FirebaseAuth mAuth;
    private ImageView togglePasswordIcon;
    private boolean passwordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        login=findViewById(R.id.button);
        login.setOnClickListener(this);
        mAuth =FirebaseAuth.getInstance();
        pass=findViewById(R.id.password);
        user=findViewById(R.id.username);

        forget= findViewById(R.id.login_forget);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Forgot.class);
                intent.putExtra("user",user.getText().toString());
                startActivity(intent);
            }
        });


        togglePasswordIcon = findViewById(R.id.pass_hide);
        togglePasswordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility();
            }
        });


        register = findViewById(R.id.goToReg);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        user=findViewById(R.id.username);
        pass=findViewById(R.id.password);
        String email = user.getText().toString();
        String password = pass.getText().toString();
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(Login.this, "User Name Required", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(Login.this, "Password Required", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(Login.this, "Authentication Success.",
                                    Toast.LENGTH_SHORT).show();
                            Intent home = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(home);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }
    private void togglePasswordVisibility() {
        if (passwordVisible) {
            // Hide the password
            pass.setTransformationMethod(new PasswordTransformationMethod());
        } else {
            // Show the password
            pass.setTransformationMethod(null);
        }

        // Toggle the flag
        passwordVisible = !passwordVisible;

        // Move the cursor to the end of the text
        pass.setSelection(pass.getText().length());
    }

}
