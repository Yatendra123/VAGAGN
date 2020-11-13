package com.radhay.vahagn;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Setting extends AppCompatActivity {
Button contact;
Button information;
Button home1;
DatabaseDemo myd;
public static String contact_name="NA";
public static String contact_number="####";
    public static String Name="NA";
    public static String Address="####";
    public static String Phone="NA";
    public static String blood_type="####";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        contact = findViewById(R.id.contact);
        information= findViewById(R.id.information);
        home1 = findViewById(R.id.home1);
        myd = new DatabaseDemo(this);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myd.getAllData();
                if(res.getCount()==0) {
                    return;
                }
                while (res.moveToNext()){
                 contact_number=res.getString(5);
                    contact_name=res.getString(4);
                }
                Intent intent  = new Intent(Setting.this,UpdateDemo.class);
                startActivity(intent);
                finish();
            }
        });
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myd.getAllData();
                if(res.getCount()==0) {
                    return;
                }
                while (res.moveToNext()){
                    Name=res.getString(0);
                    Address =res.getString(1);
                    Phone  = res.getString(2);
                    blood_type = res.getString(3);
                }

                Intent intent = new Intent(Setting.this,User_Informer.class);
                    startActivity(intent);
                    finish();
            }
        });
        home1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Setting.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}