package com.CEFS.checklist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.CEFS.checklist.Adapter.AddFormAdapter;
import com.CEFS.checklist.Model.FomData;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;

public class CompleteActivity extends AppCompatActivity {

    RecyclerView checklistRecyclerView;
    FomData formdata;
    DatabaseReference mFormRef;
    AddFormAdapter addFormAdapter;
    String UserID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);

        UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d(TAG, "UserID: "+UserID);

        mFormRef = FirebaseDatabase.getInstance().getReference("Checklist").child(UserID).child("Completed");
        checklistRecyclerView = findViewById(R.id.completetaskFormListRecyclerview);

        formdata = new FomData();

    }

    @Override
    protected void onStart() {
        super.onStart();
        checklistRecyclerView.setHasFixedSize(true);
        checklistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<FomData> options
                = new FirebaseRecyclerOptions.Builder<FomData>()
                .setQuery(mFormRef, FomData.class)
                .build();
        addFormAdapter = new AddFormAdapter(options);
        checklistRecyclerView.setAdapter(addFormAdapter);
        addFormAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        addFormAdapter.stopListening();
    }
}