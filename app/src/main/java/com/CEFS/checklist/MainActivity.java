package com.CEFS.checklist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.CEFS.checklist.Adapter.AddFormAdapter;
import com.CEFS.checklist.Model.FomData;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    RecyclerView checklistRecyclerView;
    FloatingActionButton btnForm;
    FomData formdata;
    DatabaseReference mFormRef;
    AddFormAdapter addFormAdapter;
    String key;
    String UserID = "";
    String email = "";
    String employeeID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d(TAG, "UserID: "+UserID);

        key = FirebaseDatabase.getInstance().getReference("Checklist").getKey();
        Log.d(TAG, "Form Key: "+key);

        email = getIntent().getStringExtra("Email");
        employeeID = getIntent().getStringExtra("Employee_ID");

        mFormRef = FirebaseDatabase.getInstance().getReference("Checklist").child("For Review");
        Log.d(TAG, "FormRef: "+mFormRef);
        checklistRecyclerView = findViewById(R.id.taskFormListRecyclerview);
        checklistRecyclerView.setHasFixedSize(true);
        checklistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnForm = findViewById(R.id.floatingBtn);
        FirebaseRecyclerOptions<FomData> options
                = new FirebaseRecyclerOptions.Builder<FomData>()
                .setQuery(mFormRef, FomData.class)
                .build();
        Log.d(TAG, "recyclerview: ");

        addFormAdapter = new AddFormAdapter(options);
        checklistRecyclerView.setAdapter(addFormAdapter);

        btnForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InsertDataActivity.class);
                startActivity(intent);
            }
        });

        formdata = new FomData();

    }

    @Override
    protected void onStart() {
        super.onStart();


        addFormAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        addFormAdapter.stopListening();
    }
}