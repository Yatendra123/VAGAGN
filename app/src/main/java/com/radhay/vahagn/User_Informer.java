package com.radhay.vahagn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import static com.radhay.vahagn.Registration.pos;

public class User_Informer extends AppCompatActivity {
Button updateData;
Button back_button;
EditText name;
EditText address;
EditText phone;
Spinner blood_type_spinner;
DatabaseDemo myd;
String[] strings = {"A-","A","A+","B-","B","B+","O-","O","O+","AB-","AB","AB+"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__informer);
        updateData = findViewById(R.id.updateData);
        back_button = findViewById(R.id.back_button);
        myd = new DatabaseDemo(this);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        blood_type_spinner = findViewById(R.id.blood_type_spinner);
        name.setText(Setting.Name);
        address.setText(Setting.Address);
        phone.setText(Setting.Phone);
        blood_type_spinner.setAdapter(new ArrayAdapter<>(User_Informer.this,
                android.R.layout.simple_spinner_dropdown_item,strings));

        blood_type_spinner.setSelection(pos-1);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User_Informer.this,Setting.class);
                startActivity(intent);
                finish();
            }
        });
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate=myd.userUpdate(name.getText().toString(),address.getText().toString(),phone.getText().toString(),blood_type_spinner.getSelectedItem().toString());
                pos=blood_type_spinner.getSelectedItemPosition();
                pos+=1;
                if (isUpdate)
                    Toast.makeText(User_Informer.this,"Updated Succcessful!",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(User_Informer.this,"Updated Failed!",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(User_Informer.this,Setting.class);
        startActivity(intent);
        finish();
    }
}