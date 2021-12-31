package com.example.studentregistrationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    TextView name,email,branch,year,roll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        name=findViewById(R.id.name);
        email=findViewById(R.id.youremail);
        branch=findViewById(R.id.yourbranch);
        year=findViewById(R.id.youryear);
        roll=findViewById(R.id.yourRoll);

        name.setText(""+getIntent().getStringExtra("name"));
        email.setText(""+getIntent().getStringExtra("email"));
        branch.setText(""+getIntent().getStringExtra("branch"));
        year.setText(""+getIntent().getIntExtra("year",1));
        roll.setText(""+getIntent().getStringExtra("rollno"));
    }
}