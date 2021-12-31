package com.example.adminappstudentregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddCourseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private ChangeNavigationActivities mChangeActivityFromNavigation;
    private Context mContext;

    Spinner year, branch;
    ArrayAdapter<CharSequence> adapter1, adapter2;
    Button move;
    EditText course;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        mContext =getApplicationContext();
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToolbar = findViewById(R.id.toolbar);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        mChangeActivityFromNavigation = new ChangeNavigationActivities();

        year = (Spinner) findViewById(R.id.filteryear);
        branch = (Spinner) findViewById(R.id.filterbranch);
        move = (Button) findViewById(R.id.move);
        course = (EditText) findViewById(R.id.coursename);


        adapter1 = ArrayAdapter.createFromResource(this, R.array.yeararry, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2 = ArrayAdapter.createFromResource(this, R.array.brancharray, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        year.setAdapter(adapter1);
        branch.setAdapter(adapter2);

        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myear=""+year.getSelectedItemPosition();
                String mbranch=branch.getSelectedItem().toString();
                String mcourse=course.getText().toString();
                FirebaseFirestore db=FirebaseFirestore.getInstance();
                Map<String,Object> map=new HashMap<>();
                map.put("year",myear);
                map.put("mbranch",mbranch);
                map.put("course",mcourse);
                db.collection("courses").document().set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(mContext, "Course Added", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, "error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

              //  Intent intent=new Intent(FilterStudents.this,ViewStudentsActivity.class);
             //   intent.putExtra("year",myear);
             //   intent.putExtra("branch",mbranch);
              //  startActivity(intent);
            }
        });

    }

    private void setNavigation() {
        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.getMenu().getItem(1).setChecked(true);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setNavigation();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            mChangeActivityFromNavigation.startHomeActivity(mContext);
        } else if (id==R.id.nav_courses){
        //    mDrawerLayout.closeDrawer(GravityCompat.START);
         //   mChangeActivityFromNavigation.startAvailableCoursesActivity(mContext);
        }else if (id==R.id.logout){

            mDrawerLayout.closeDrawer(GravityCompat.START);
            mChangeActivityFromNavigation.startFilerActivity(mContext);
        }


        return false;
    }
}