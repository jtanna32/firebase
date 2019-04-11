package com.example.firebase;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class homepage extends AppCompatActivity implements View.OnClickListener{
    Button button;
    EditText number;
    EditText messge;
    Button send1;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        number = findViewById(R.id.number);
        messge = findViewById(R.id.mess);
        send1 = findViewById(R.id.send);
        firebaseAuth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logout);

        send1.setOnClickListener(this);
        button.setOnClickListener(this);
        if (ContextCompat.checkSelfPermission(homepage.this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(homepage.this,
                    Manifest.permission.SEND_SMS)) {
                ActivityCompat.requestPermissions(homepage.this,
                        new String[]{Manifest.permission.SEND_SMS}, 1);
            } else {
                ActivityCompat.requestPermissions(homepage.this,
                        new String[]{Manifest.permission.SEND_SMS}, 1);
            }
        }else
        {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 1: {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(homepage.this,
                            Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "permission not granted",Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v == send1){
            String num = number.getText().toString();
            String mss = messge.getText().toString();

            try{
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(num,null,mss,null,null);
                Toast.makeText(homepage.this,"Sent", Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Toast.makeText(homepage.this,"failed", Toast.LENGTH_SHORT).show();
            }
        }
        if (v == button){
            firebaseAuth.signOut();
            startActivity(new Intent(homepage.this,MainActivity.class));
        }
    }
}
