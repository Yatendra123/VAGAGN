package com.radhay.vahagn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class Preview_Demo extends AppCompatActivity {
DatabaseDemo myd;
TextView textView;
String msg="";
Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview__demo);
        myd  = new DatabaseDemo(this);
        textView = findViewById(R.id.textView);
        msg="Hi,this is ";
        send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textView.getText().equals("")){
                    textView.setBackgroundColor(200);
                }
                else{
                    Cursor res = myd.setData();
                    msg = (String) textView.getText();
                        if (ContextCompat.checkSelfPermission(Preview_Demo.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                            SmsManager sms = SmsManager.getDefault();
                            while(res.moveToNext()) {
                                String number= res.getString(1);
                                sms.sendTextMessage(number,null,msg,null,null);
                        }
                    }
                }
            }
        });
    }
}