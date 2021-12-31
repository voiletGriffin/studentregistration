package com.example.studentregistrationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SigninActivity extends AppCompatActivity {

    EditText fullName,email,password,rollno;
    Button register;
    Spinner yearsp,branchsp;

    String mEmail,mPassword,mName,mRoll,mBranch;
    int year;
    ArrayAdapter<CharSequence> adapter1 ,adapter2;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mAuth = FirebaseAuth.getInstance();

        fullName=findViewById(R.id.nameet);
        email=findViewById(R.id.emailet);
        password=findViewById(R.id.passwordet);
        rollno=findViewById(R.id.idet);
        yearsp=findViewById(R.id.spinneryear);
        branchsp=findViewById(R.id.spinnerbranch);

        register=findViewById(R.id.registerbtn);
        adapter1=ArrayAdapter.createFromResource(this,R.array.yeararry,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2=ArrayAdapter.createFromResource(this,R.array.brancharray,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        yearsp.setAdapter(adapter1);
        branchsp.setAdapter(adapter2);


        yearsp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year=position+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        branchsp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mBranch=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fullName.getText().toString().isEmpty()||fullName.getText().toString().equals("")){
                    fullName.setError("Enter Name");
                    return;
                }
                if (email.getText().toString().isEmpty()||email.getText().toString().equals("")){
                    email.setError("Enter email");
                    return;
                }
                if (password.getText().toString().isEmpty()||password.getText().toString().equals("")){
                    password.setError("Enter password");
                    return;
                }
                if (rollno.getText().toString().isEmpty()||rollno.getText().toString().equals("")){
                    rollno.setError("Enter Reg.ID");
                    return;
                }
                mEmail=email.getText().toString();
                mName=fullName.getText().toString();
                mRoll=rollno.getText().toString();

                registerUser(email.getText().toString(),password.getText().toString());
                Toast.makeText(SigninActivity.this, "Please wait.....", Toast.LENGTH_SHORT).show();

            }
        });





    }

    public void registerUser(String memail,String mpassword){
        mAuth.createUserWithEmailAndPassword(memail, mpassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                String uid = mAuth.getCurrentUser().getUid();
                Toast.makeText(SigninActivity.this, "You have Successfully Registered!", Toast.LENGTH_SHORT).show();
                uploadData(uid);
               // Toast.makeText(SigninActivity.this, "You have Successfully Registered!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SigninActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void uploadData(String uid){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> user = new HashMap<>();
        user.put("name", mName);
        user.put("branch", mBranch);
        user.put("email", mEmail);
        user.put("year",""+year);
        user.put("rollno",mRoll);
        user.put("approval",false);
        db.collection("students").document(uid).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(SigninActivity.this, "Data Uploaded!", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(SigninActivity.this,WelcomeActivity.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SigninActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}