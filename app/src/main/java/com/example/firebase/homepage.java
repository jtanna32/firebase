package com.example.firebase;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Message;
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

public class homepage extends AppCompatActivity {
    Button button;
    EditText number;
    EditText message;
    Button send;
    FirebaseAuth firebaseAuth;
    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        number = findViewById(R.id.number);
        message = findViewById(R.id.mess);
        send = findViewById(R.id.send);
        setContentView(R.layout.activity_homepage);
        firebaseAuth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logout);

        send.setEnabled(false);
        if(CheckPermission(Manifest.permission.SEND_SMS)){
            send.setEnabled(true);
        }
            else
          {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(homepage.this,MainActivity.class));
            }
        });
    }

    public void onSend(View v){
        String phonenumber = number.getText().toString();
        String smsMessage = message.getText().toString();

        if(phonenumber == null || phonenumber.length() == 0 || smsMessage == null || smsMessage.length() == 0){
            return;
        }

        if (CheckPermission(Manifest.permission.SEND_SMS)) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phonenumber, null, smsMessage , null, null);
            Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"message not sent",Toast.LENGTH_SHORT).show();
        }
    }

    public boolean CheckPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }
}
