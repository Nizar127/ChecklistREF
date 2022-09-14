package com.CEFS.checklist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.CEFS.checklist.Adapter.AddFormAdapter;
import com.CEFS.checklist.Adapter.ApproveFormAdapter;
import com.CEFS.checklist.Model.FomData;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import static android.content.ContentValues.TAG;

public class AdminApprovalActivity extends AppCompatActivity {

    RecyclerView approvalchecklistRecyclerView;
    String UserID = "";
    String equipID = "";
    DatabaseReference mFormRef, mUserRef;
    Query watchRef, InspectRef;
    ApproveFormAdapter adapter;
    FomData formdata;
    String key;
    String email = "";
    String employeeID = "";
    //biz logic
    //after login go to dashboard
    //in dashboard there are 2 tab, approved and complete
    //in approved tab only list out the form list
    //For the approved tab, this activity is the one used
    //We will identify user ID first, n send them to respective page
    //if user are watch commander, then page will appear in watch commander
    //if user are inspect commander, then page will appear in inspect commander
    //DatabaseReference InspectRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_approval);


        UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d(TAG, "UserID: "+UserID);

        key = FirebaseDatabase.getInstance().getReference("Checklist").getKey();
        Log.d(TAG, "Form Key: "+key);

        email = getIntent().getStringExtra("Email");
        employeeID = getIntent().getStringExtra("Employee_ID");

        formdata = new FomData();

        //approvalchecklistRecyclerView = findViewById(R.id.approvalFormListRecyclerview);
        //mFormRef   = FirebaseDatabase.getInstance().getReference("Checklist");
        //mUserRef   = FirebaseDatabase.getInstance().getReference("Employee");
        //watchRef   = FirebaseDatabase.getInstance().getReference("Employee").child(UserID).orderByChild("Designation").equalTo("Watch_Commander");
        //InspectRef = FirebaseDatabase.getInstance().getReference("Employee").child(UserID).orderByChild("Designation").equalTo("Inspect_Commander");

        mFormRef = FirebaseDatabase.getInstance().getReference("Checklist").child("For Review");
        Log.d(TAG, "FormRef: "+mFormRef);
        approvalchecklistRecyclerView = findViewById(R.id.approvalFormListRecyclerview);
        approvalchecklistRecyclerView.setHasFixedSize(true);
        approvalchecklistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<FomData> options
                = new FirebaseRecyclerOptions.Builder<FomData>()
                .setQuery(mFormRef, FomData.class)
                .build();
        Log.d(TAG, "recyclerview: ");

        adapter = new ApproveFormAdapter(options);
        approvalchecklistRecyclerView.setAdapter(adapter);
        //getInspectCommander(UserID);
        //getWatchCommander(UserID);

//        mUserRef.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            //if user exists then check if this people is watch commander or inspect commander
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    String watchCom = snapshot.child("Designation").getValue(String.class);
//                    String WC = "Watch Commander";
//                    String inspectCom = snapshot.child("Designation").getValue(String.class);
//                    String InspectBy ="Inspect By";
//
//                    if(watchCom.equals(WC)){
//                        getWatchCommander(UserID);
//                    }else if(inspectCom.equals(InspectBy)){
//                        getInspectCommander(UserID);
//                    }
//                    watchRef.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                            if(snapshot.exists()){
//                                getWatchCommander(UserID);
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//                        }
//                    });
//                } else{
//                    InspectRef.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                            if(snapshot.exists()){
//                                getInspectCommander(UserID);
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });


    }

//    private void getInspectCommander(String userID) {
//        DatabaseReference theinspectRef = (DatabaseReference) FirebaseDatabase.getInstance().getReference("Checklist").child(userID).orderByChild("Designation").equalTo("Inspect_Commander");
//
//        approvalchecklistRecyclerView.setHasFixedSize(true);
//        approvalchecklistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        FirebaseRecyclerOptions<FomData> options
//                = new FirebaseRecyclerOptions.Builder<FomData>()
//                .setQuery(theinspectRef, FomData.class)
//                .build();
//        adapter = new AddFormAdapter(options);
//        approvalchecklistRecyclerView.setAdapter(adapter);
//
//    }

//    private void getWatchCommander(String userID) {
//        DatabaseReference thewatchRef = (DatabaseReference) FirebaseDatabase.getInstance().getReference("Checklist").child("For Review");
//
//        approvalchecklistRecyclerView.setHasFixedSize(true);
//        approvalchecklistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        FirebaseRecyclerOptions<FomData> options
//                = new FirebaseRecyclerOptions.Builder<FomData>()
//                .setQuery(thewatchRef, FomData.class)
//                .build();
//        adapter = new AddFormAdapter(options);
//        approvalchecklistRecyclerView.setAdapter(adapter);
//    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}