package com.venkatakrishnan.netmeds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PaymentFailure extends AppCompatActivity {
    Intent intent = getIntent();
    TextView textView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_failure);
        String Error = intent.getStringExtra("error");
        textView = findViewById(R.id.failure_out);
        textView.setText(Error);
        button = findViewById(R.id.failure_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentFailure.this,Billing.class);
                startActivity(intent);
            }
        });
    }
}