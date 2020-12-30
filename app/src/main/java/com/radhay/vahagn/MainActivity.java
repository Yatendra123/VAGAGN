package com.radhay.vahagn;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    ImageButton sos_button;
    ImageButton help;
    ImageButton hospital_button;
    ImageButton home_button;
    ImageButton blood_bank;
    MainActivity receiver;
    ImageButton setting_button;
    DatabaseDemo myd;
    TextView textView3;
    private boolean p=false;
    private String loc="NA";
    double latitude=0,longitude=0;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static String addressDemo="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myd = new DatabaseDemo(this);
        setting_button = findViewById(R.id.settings_button);
        textView3 = findViewById(R.id.textView3);
        home_button = findViewById(R.id.home_button);
        hospital_button  = findViewById(R.id.hospital_button);
        blood_bank =findViewById(R.id.bloodbank_button);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLocationDemo();
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });
        setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Setting.class);
                startActivity(intent);
                finish();
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocationDemo();
                if (p){
                    myd.updateloc(addressDemo.toString());
                    Toast.makeText(MainActivity.this,"Location already fatched",Toast.LENGTH_SHORT).show();
                }

            }
        });
        receiver = new MainActivity();
        sos_button = findViewById(R.id.sos_button);
        help = findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this
                        , Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED) {

                    if(isLocationEnabled()) {
                        SmsManager sms = SmsManager.getDefault();
                        Cursor res = myd.setData();
                        while (res.moveToNext()) {
                            String number = res.getString(1);
                            //          String messagebody = "ok "+addressDemo;
                            if(addressDemo.equals("")){
                                textView3.setText("Please Ensure Your Location: "+addressDemo+" \nSomething Wrong Click me to Correct This");
                                textView3.setBackgroundColor(0xFFB30000);
                            }else {
                                String messagebody = "Hi everyone i need suddenly help at " + addressDemo;
                                String sh1 = " (" + "https://www.google.com/maps/search/?api=1&query=" + latitude + "," + longitude + ")\nPlease help me";
                                if(loc.equals("False")) {
                                    textView3.setText("Please Ensure Your Location: " + addressDemo + " \nSomething Wrong Click me to Correct This");
                                    textView3.setBackgroundColor(0xFFB30000);
                                }
                                System.out.println(messagebody);
                                try {
                                    sms.sendTextMessage(number, null, messagebody, null, null);
                                    sms.sendTextMessage(number, null, sh1, null, null);
                                    Toast.makeText(getApplicationContext(), "Message Sent Succusfully !!", Toast.LENGTH_LONG).show();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "Message Failed", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }else{
                        getLocationDemo();
                    }
                }else
                {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS}, 1);
                }
            }
        });
        sos_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, sos.class);
                startActivity(intent);
                finish();
            }
        });
        hospital_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MapDemo.class);
                startActivity(intent);
                finish();
            }
        });
        blood_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,BloodBank.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void makePhoneCall() {
        String number =  "Na";
        Cursor cursor = myd.getAllData();
        while (cursor.moveToNext()) {
            System.out.print("");
        }if(cursor.moveToPrevious()) {
            number = cursor.getString(5);
        }
        try {
            String dial = "tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }catch (Exception e){
            e.printStackTrace();
        }
            }

    private void getLocationDemo() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location != null) {
                            try {
                                System.out.println("enter into end");
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                addressDemo = addresses.get(0).getAddressLine(0);
                                textView3.setText("Your Current Location: "+addressDemo+"\nplease sure your location otherwise click me!");
                                p=true;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(MainActivity.this, "location not fatech", Toast.LENGTH_LONG).show();
                            Cursor cursor=myd.getloc();
                            if(cursor.moveToFirst()){
                                addressDemo=cursor.getString(1);
                            }
                            textView3.setText("This can be Last Location: "+addressDemo);
                           loc = "False";
                           p=false;
                        }
                    }
                });

            } else {
                Toast.makeText(MainActivity.this, "Please turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);

            }
        }else{
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocationDemo();
            }
        }
    }
    protected  boolean isLocationEnabled(){
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED
         && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            getLocationDemo();
        }
    }
}

