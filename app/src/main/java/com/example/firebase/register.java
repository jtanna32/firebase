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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class register extends AppCompatActivity implements View.OnClickListener {

    private Button btn;
    private EditText email;
    private EditText password;
    private TextView signin;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    firebaseFirestore=FirebaseFirestore.getInstance();
        btn = findViewById(R.id.register);
        email = findViewById(R.id.ema);
        password = findViewById(R.id.pass);
        signin = findViewById(R.id.signin);

        btn.setOnClickListener(this);
        signin.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            Intent intent = new Intent(register.this,MainActivity.class);
            startActivity(intent);
        }
    }

    private void DB(String email1, String password1, String Doc_id){
        CollectionReference dbusers = firebaseFirestore.collection("Users");
        Users user = new Users(email1,password1);
        dbusers.document(Doc_id).set(user).addOnSuccessListener(new OnSuccessListener<Void>()
        {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(register.this, "Database Created", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(register.this, "Database not created", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == btn){
            userregister();
        }
        if(v == signin){
            Intent intent = new Intent(register.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void userregister() {
        final String password1 = password.getText().toString().trim();
        final String email1 = email.getText().toString().trim();
        if(TextUtils.isEmpty(email1)){
            Toast.makeText(this,"Enter the email",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(password1)){
            Toast.makeText(this,"Enter the password",Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            progressDialog.setMessage("Registering User...");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email1,password1)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(register.this,"USer registered succesfully", Toast.LENGTH_SHORT).show();
                                DB(email1,password1,firebaseAuth.getUid());
                                Intent intent =  new Intent(register.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(register.this,"could not register", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
