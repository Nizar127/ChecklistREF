package com.CEFS.checklist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.CEFS.checklist.Authentication.SignUpActivity;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ApprovalName extends AppCompatActivity {

    ImageView backBTN, img1,img2;
    EditText thename,thename2;
    SignaturePad signed,signed2;
    Spinner spinner1, spinner2;
    DatabaseReference mRef, mBase,spinnerRef;
    String Designation1, Designation2, UserID, key, formReference;
    List<String> names;
    Button approveBtn;
    Uri imgURL;
    StorageReference approvImgRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_name);

        UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        formReference = getIntent().getStringExtra("ApprovalID");

        img1 = findViewById(R.id.image1);
        img2 = findViewById(R.id.image2);
        thename = findViewById(R.id.nameapproval);
        thename2 = findViewById(R.id.nameapproval2);
        signed = findViewById(R.id.signature1);
        signed2 = findViewById(R.id.signature2);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        approveBtn = findViewById(R.id.approveForm);
        names = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        key = database.getReference("Checklist").push().getKey();

        approvImgRef =  FirebaseStorage.getInstance().getReference("Signature");
        mBase = FirebaseDatabase.getInstance().getReference("Checklist");
        mRef = FirebaseDatabase.getInstance().getReference("Checklist").child(UserID).child("Approve");

        spinnerRef = FirebaseDatabase.getInstance().getReference("Designation");
        spinnerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot childSnapshot:snapshot.getChildren()){
                    String spinnerName = childSnapshot.child("name").getValue(String.class);
                    names.add(spinnerName);
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ApprovalName.this, android.R.layout.simple_spinner_dropdown_item, names);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(arrayAdapter);
                spinner2.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                Designation1 = spinner1.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                Designation2 = spinner2.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ApprovalName.this, AdminDashboardActivity.class);
                startActivity(intent);
            }
        });

        approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ApproveUser();

            }
        });

    }

    private void ApproveUser() {
        Bitmap bitmap = signed.getSignatureBitmap();
        img1.setImageBitmap(bitmap);
        TextView signedText1 = findViewById(R.id.signText);
        signedText1.setBackground(new BitmapDrawable(getResources(),bitmap));

        Bitmap bitmap1 = signed2.getSignatureBitmap();
        img2.setImageBitmap(bitmap1);
        TextView signedText2 = findViewById(R.id.signText2);
        signedText2.setBackground(new BitmapDrawable(getResources(),bitmap));

        HashMap<String, Object> signMap = new HashMap<>();
        signMap.put("Name_shift1", thename);
        signMap.put("Designation_shift1",Designation1);
        signMap.put("Signature_Shift1", signedText1);
        signMap.put("Name_shift2", thename2);
        signMap.put("Designation_shift2",Designation2);
        signMap.put("Signature_Shift2", signedText2);

        mBase.child(key).child("Approved").updateChildren(signMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Form Have Been Approved",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ApprovalName.this, AdminDashboardActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}