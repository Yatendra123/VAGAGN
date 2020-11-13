package com.radhay.vahagn;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class Registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
Button cancel_button;
EditText name1;
EditText number;
EditText address;
EditText name;
EditText phone;
Spinner blood_type_spinner;
Button save_button;
Button get_Name;
Button get_Number;
DatabaseDemo myd;
protected static int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        myd = new DatabaseDemo(this);
        String[] blood_name={"Blood Type","A-","A","A+","B-","B","B+","O-","O","O+","AB-","AB","AB+"};
         if(ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_CONTACTS)==PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS)==PackageManager.PERMISSION_GRANTED &&
         ActivityCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)==PackageManager.PERMISSION_GRANTED &&
         ActivityCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED &&
         ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED){
             Cursor cursor=myd.getAllData();
     if (cursor.moveToNext()){
            Intent intent = new Intent(Registration.this,MainActivity.class);
            startActivity(intent);
            finish();
     }
        }else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS, Manifest.permission.SEND_SMS, Manifest.permission.CALL_PHONE,Manifest.permission.ACCESS_COARSE_LOCATION
            ,Manifest.permission.ACCESS_FINE_LOCATION}, 1);
}
        cancel_button = findViewById(R.id.cancel_button);
        save_button  = findViewById(R.id.save_button);
        name1 = findViewById(R.id.name1);
        number = findViewById(R.id.number);
        name  = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        blood_type_spinner = findViewById(R.id.blood_type_spinner);
                get_Name = findViewById(R.id.get_Name);
        get_Number = findViewById(R.id.get_Number);
        myd = new DatabaseDemo(this);
        blood_type_spinner.setAdapter(new ArrayAdapter<>(Registration.this,
                android.R.layout.simple_spinner_dropdown_item,blood_name));
              get_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,Uri.encode(number.getText().toString()));

                    @SuppressLint("Recycle") Cursor cursor = getContentResolver().query(uri,new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME},null,null
                    ,null);
                    String stringContactName="Invalid";
                    if(cursor !=null){
                        if (cursor.moveToFirst()){
                            stringContactName = cursor.getString(0);
                        }
                    }
                    name1.setText(stringContactName);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        get_Number.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                try {
                    @SuppressLint("Recycle") Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.TYPE},
                            "DISPLAY_NAME ='" + name1.getText().toString() + "'", null, null);
                    cursor.moveToFirst();
                    number.setText(cursor.getString(0));
                    System.out.println("number "+cursor.getString(0));
                }catch (Exception e){
                    e.printStackTrace();
                    number.setText(R.string.NA);
                }
            }
        });
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();
            }
        });
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                              boolean isInserted = myd.insertData(name.getText().toString(), address.getText().toString(), phone.getText().toString(),
                            blood_type_spinner.getSelectedItem().toString(), name1.getText().toString(),
                            number.getText().toString());
                    boolean isADD = myd.addData(name1.getText().toString(), number.getText().toString());
                    pos=blood_type_spinner.getSelectedItemPosition();
                    if (isInserted && isADD)
                        Toast.makeText(Registration.this, "Data is inserted", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(Registration.this, "Data is not inserted", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Registration.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}