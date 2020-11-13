package com.radhay.vahagn;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class sos extends AppCompatActivity {
Button home;
Button ambulance_button;
Button fire_button;
Button police_button;
Button us_button;
private  static  int a=0,f=0,p=0,u=0;
private static final int REQUEST_CALL=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);
        home = findViewById(R.id.home);
        ambulance_button = findViewById(R.id.ambulance_button);
        fire_button = findViewById(R.id.fire_button);
        police_button = findViewById(R.id.police_button);
        us_button = findViewById(R.id.us_button);
        ambulance_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f=0;p=0;u=0;a=1;
                makePhoneCall();
            }
        });
        fire_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f=1;p=0;u=0;a=0;
                makePhoneCall();
            }
        });
        police_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f=0;p=1;u=0;a=0;
                makePhoneCall();
            }
        });
        us_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f=0;p=0;u=1;a=0;
                makePhoneCall();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sos.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }
        }
    }

    private void makePhoneCall() {
        String number="";
        if(a==1) {
            number = ambulance_button.getText().toString();
        }else if(p==1)
        {
            number = police_button.getText().toString();
        }else if(u==1){
            number = us_button.getText().toString();
        }else if(f==1){
            number = fire_button.getText().toString();
        }
            if(number.trim().length()>0){
                if(ContextCompat.checkSelfPermission(sos.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(sos.this,new String[] {Manifest.permission.CALL_PHONE},1);
                }else{
                    String dial = "tel:" + number;
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                }
            }

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(sos.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}