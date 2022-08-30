package com.CEFS.checklist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class AdminApprovalActivity extends AppCompatActivity {

    String UserID = "";

    DatabaseReference mFormRef;
    Query watchRef;
    DatabaseReference InspectRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_approval);

        UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        watchRef = FirebaseDatabase.getInstance().getReference("Employee").child(UserID).orderByChild("Designation").equalTo("Watch_Commander");


        //if user is watch commander, then access only at certain approval level
        //if user is inspect commander, then access only at inspect commander
        //ger user ID, n if user ID have designation watch commander, do the assignment
        watchRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        getWatchCommander(UserID);

        getInspectCommander(UserID);


    }

    private void getInspectCommander(String userID) {
    }

    private void getWatchCommander(String userID) {
    }
}