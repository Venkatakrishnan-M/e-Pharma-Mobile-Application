package com.venkatakrishnan.netmeds;

import static android.content.ContentValues.TAG;
import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;

public class Billing extends AppCompatActivity implements PaymentResultListener {
    TextView cartItemsTextView,totalPrice;
    Button button;
    int total=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_billing);
        button = findViewById(R.id.button);

        Checkout.preload(getApplicationContext());


        Intent intent = getIntent();
        ArrayList<String> cartItems = intent.getStringArrayListExtra("cartItems");
        cartItemsTextView= findViewById(R.id.cartItemsTextView);
        totalPrice=findViewById(R.id.total);
        StringBuilder cartItemsText = new StringBuilder();
        for (String item : cartItems) {
            Log.d("Billing",item);
            int sliceStartIndex = item.length() - 2;
            String sliced = item.substring(sliceStartIndex);
            total=total+parseInt(sliced);
            cartItemsText.append(item).append("\n\n");
        }
        totalPrice.append(" "+total);
        String  totalInRazpayFormat =  ""+total*100;
        cartItemsTextView.setText(cartItemsText.toString());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startPayment(totalInRazpayFormat);
            }
        });

    }
    public void startPayment(String totalInRazpayFormat) {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_GVXBQTIAcErszX");

        checkout.setImage(R.drawable.logo);

        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();

            options.put("name", "e-Pharma Pvt. Ltd");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
//            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#39fad3");
            options.put("currency", "INR");
            options.put("amount", totalInRazpayFormat);//pass amount in currency subunits
            options.put("prefill.email", "gaurav.kumar@example.com");
            options.put("prefill.contact","7550180419");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
            Intent intent = new Intent(Billing.this,PaymentSuccess.class);
            startActivity(intent);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Intent intent = new Intent(Billing.this,PaymentSuccess.class);
        intent.putExtra("error",s);
        startActivity(intent);
    }

    @Override
    public void onResume(){
        super.onResume();
    }
}