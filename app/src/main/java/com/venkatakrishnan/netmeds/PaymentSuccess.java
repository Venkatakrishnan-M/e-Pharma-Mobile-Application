package com.venkatakrishnan.netmeds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class PaymentSuccess extends AppCompatActivity {
    VideoView videoView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);
        videoView = findViewById(R.id.paymentSuccess_video);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.success);
        videoView.setVideoURI(uri);
        videoView.start();
        button = findViewById(R.id.paymentSuccess_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentSuccess.this,Billing.class);
                startActivity(intent);
            }
        });

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

    }

    @Override
    protected void onResume() {
        videoView.resume();
        super.onResume();
    }

    @Override
    protected  void onPause(){
        videoView.suspend();
        super.onPause();
    }

}