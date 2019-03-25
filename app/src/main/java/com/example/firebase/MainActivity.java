package com.example.firebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button sign;
    private EditText email2;
    private EditText password2;
    private TextView signup;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sign = findViewById(R.id.sign);
        email2 = findViewById(R.id.email);
        password2 = findViewById(R.id.password);
        signup = findViewById(R.id.signup);


        sign.setOnClickListener(this);
        signup.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            Intent intent = new Intent(MainActivity.this,homepage.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        if ( v == sign){
            signin();
        }
        if( v == signup){
            Intent intent = new Intent(MainActivity.this,register.class);
            startActivity(intent);
        }
    }

    private void signin() {
        String password3 = password2.getText().toString().trim();
        String email3 = email2.getText().toString().trim();
        if(TextUtils.isEmpty(email3)){
            Toast.makeText(this,"Enter the email",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(password3)){
            Toast.makeText(this,"Enter the password",Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            progressDialog.setMessage("signining in...");
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(email3,password3)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(MainActivity.this,"USer registered succesfully", Toast.LENGTH_SHORT).show();
                                Intent intent =  new Intent(MainActivity.this, homepage.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(MainActivity.this,"could not register", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
