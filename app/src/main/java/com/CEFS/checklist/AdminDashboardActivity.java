package com.CEFS.checklist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;


public class AdminDashboardActivity extends AppCompatActivity {

    String userID;
    String TAG = "";
    String email = "";
    String employeeID = "";
    String userdesignation = "";
    BottomNavigationView bottomtabmenu;
    DatabaseReference ref, checkData, testOil, testdistance;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        userID = fAuth.getCurrentUser().getUid();
        Log.d(TAG, "query:"+ userID);

        email = getIntent().getStringExtra("Email");
        employeeID = getIntent().getStringExtra("Employee_ID");
        userdesignation = getIntent().getStringExtra("Designation");

        ref = FirebaseDatabase.getInstance().getReference("Employee").child(userID);

        bottomtabmenu = findViewById(R.id.bottom_navigation);
        bottomtabmenu.setSelectedItemId(R.id.to_approve);

        bottomtabmenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.completed:
                        startActivity(new Intent(getApplicationContext(), CompleteActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.to_approve:
                        startActivity(new Intent(getApplicationContext(), AdminApprovalActivity.class));
                        return true;
                }

                return false;
            }
        });

//        bottomtabmenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.completed:
//                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                    case R.id.to_approve:
//                        startActivity(new Intent(getApplicationContext(), AdminApprovalActivity.class));
//                        return true;
//                }
//                return false;
//            }
//        });
    }
}