package com.example.adminappstudentregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class ViewStudentsActivity extends AppCompatActivity {




    RecyclerView recyclerView;
    FirebaseFirestore db;
    FirestoreRecyclerOptions<DataModel> options;
    Query query;


    FirestoreRecyclerAdapter<DataModel, StudentViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);

        recyclerView = findViewById(R.id.recyclerview);

        String myear=getIntent().getStringExtra("year");
        String mbranch=getIntent().getStringExtra("branch");

        db = FirebaseFirestore.getInstance();

        Log.d("SAD", "onCreate: "+mbranch+" "+myear);
        if (mbranch.equals("All")&& myear.equals("0")){
            query = db.collection("students");
            Log.d("SAD", "onCreate:true ");
        }else if (mbranch.equals("All")){
            query = db.collection("students").whereEqualTo("year",myear);
        }else if (myear.equals("0")){
            query = db.collection("students").whereEqualTo("branch",mbranch);
        }else{
            query = db.collection("students").whereEqualTo("branch",mbranch).whereEqualTo("year",myear);
        }
        options = new FirestoreRecyclerOptions.Builder<DataModel>().setQuery(query, DataModel.class).build();

        adapter = new FirestoreRecyclerAdapter<DataModel, StudentViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull StudentViewHolder holder, final int position, @NonNull final DataModel model) {
                holder.name.setText(model.getName());
                holder.branch.setText(model.getBranch());

                if (model.isApproval()){
                    holder.imageView.setVisibility(View.VISIBLE);
                    holder.approve.setVisibility(View.GONE);
                }else{
                    holder.imageView.setVisibility(View.GONE);
                    holder.approve.setVisibility(View.VISIBLE);
                }

                holder.approve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map=new HashMap<>();
                        map.put("approval",true);
                        db.collection("students").document(getSnapshots().getSnapshot(position).getId()).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ViewStudentsActivity.this, "Approved !", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ViewStudentsActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ViewStudentsActivity.this, DetailsActivity.class);
                        intent.putExtra("name", model.getName());
                        intent.putExtra("branch", model.getBranch());
                        intent.putExtra("email", model.getEmail());
                        intent.putExtra("year", model.getyear());
                        intent.putExtra("rollno", model.getRollno());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.studentitem, parent, false);
                return new StudentViewHolder(view);
            }

        };

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);





    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView name, branch;
        ImageView imageView;
        Button approve;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (itemView).findViewById(R.id.studentname);
            branch = (itemView).findViewById(R.id.branch);
            imageView = (itemView).findViewById(R.id.imageview);
            approve = (itemView).findViewById(R.id.approvestudent);


        }
    }


}