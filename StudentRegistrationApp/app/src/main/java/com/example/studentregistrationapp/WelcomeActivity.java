package com.example.studentregistrationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class WelcomeActivity extends AppCompatActivity {

    TextView tv3;

    String name,email,branch,rollno;
    int year;
    boolean status=false;

    Button checkStatus,checkDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        tv3=findViewById(R.id.textView3);
        checkDetails=findViewById(R.id.checkdetails);
        checkStatus=findViewById(R.id.checkstatus);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        db.collection("students").document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                tv3.setText("Welcome "+documentSnapshot.getString("name"));
                name=documentSnapshot.getString("name");
                email=documentSnapshot.getString("email");
                branch=documentSnapshot.getString("branch");
                rollno=documentSnapshot.getString("rollno");
                try {
                    status=documentSnapshot.getBoolean("approval");
                }catch (Exception e){

                }
                year= Integer.parseInt(documentSnapshot.getString("year"));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(WelcomeActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        checkDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WelcomeActivity.this,DetailsActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("branch",branch);
                intent.putExtra("year",year);
                intent.putExtra("email",email);
                intent.putExtra("rollno",rollno);
                startActivity(intent);
            }
        });

        checkStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               isApproved();
            }
        });


    }

    public void isApproved(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        db.collection("students").document(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                try {
                    status=documentSnapshot.getBoolean("approval");
                }catch (Exception e){

                }
                if (status){
                    Toast.makeText(WelcomeActivity.this, "Your Registration is Approved !", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(WelcomeActivity.this, "Sorry your registration is yet to be approved", Toast.LENGTH_LONG).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(WelcomeActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}