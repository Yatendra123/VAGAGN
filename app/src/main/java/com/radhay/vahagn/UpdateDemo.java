package com.radhay.vahagn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateDemo extends AppCompatActivity {
EditText name;
EditText Phone;
Button home;
Button update;
Button add_button;
DatabaseDemo myd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_demo);
        myd = new DatabaseDemo(this);
        home = findViewById(R.id.home);
        name = findViewById(R.id.name);
        Phone = findViewById(R.id.Phone);
        name.setText(Setting.contact_name);
        Phone.setText(Setting.contact_number);
        add_button = findViewById(R.id.add_button);
        update = findViewById(R.id.update);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAdd = myd.addData(name.getText().toString(),Phone.getText().toString());
                if (isAdd)
                    Toast.makeText( UpdateDemo.this, "Add Successfully!!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(UpdateDemo.this,"Add Failed!!",Toast.LENGTH_LONG).show();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = myd.updateData(name.getText().toString(),Phone.getText().toString());
                if(isUpdated)
                    Toast.makeText(UpdateDemo.this,"Updated Successfully",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(UpdateDemo.this,"Updated Filed!!",Toast.LENGTH_LONG).show();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(UpdateDemo.this,Setting.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UpdateDemo.this,Setting.class);
        startActivity(intent);
        finish();
    }
}