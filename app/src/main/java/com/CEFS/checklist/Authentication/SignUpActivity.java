package com.CEFS.checklist.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.CEFS.checklist.AuthenticationActivity;
import com.CEFS.checklist.Model.TheUserData;
import com.CEFS.checklist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class SignUpActivity extends AppCompatActivity {

    Button signUpButton;
    EditText username, pass, employee_ID, thedesignation, phoneNum, birth;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    ProgressDialog loadingBar;
    TheUserData userData;
    String email, password, userID, designation, empID,the_employee, watch_commander, inspector_commander;
    //RadioButton other_empId, watch_com_id, inspect_com_id;
    DatabaseReference reference, spinnerRef;
    int i = 0;
    ImageView back_btn;
    CheckBox remember, showpass;
    Spinner spinner;
    List<String> names;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        back_btn = findViewById(R.id.back_btn_add_signupmain);
        reference = FirebaseDatabase.getInstance().getReference("Employee");
        loadingBar = new ProgressDialog(this);
        showpass = findViewById(R.id.login_checkbox);
        spinner = findViewById(R.id.spinner);
        //thedesignation = findViewById(R.id.signup_designation_input);
        names = new ArrayList<>();


        signUpButton = findViewById(R.id.Register_btn);
        username     =(EditText) findViewById(R.id.signup_username_input);
        pass         =(EditText)  findViewById(R.id.signup_password_input);
        employee_ID  = (EditText) findViewById(R.id.signup_id_input);

        //watch_com_id   = findViewById(R.id.watchcommandersignup);
       // other_empId    = findViewById(R.id.otheremployeesignup);
        //inspect_com_id = findViewById(R.id.inspectorsignup);

        progressBar = findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, AuthenticationActivity.class);
                startActivity(intent);
            }
        });

        spinnerRef = FirebaseDatabase.getInstance().getReference("Designation");
        spinnerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
               for(DataSnapshot childSnapshot:snapshot.getChildren()){
                   String spinnerName = childSnapshot.child("name").getValue(String.class);
                   names.add(spinnerName);
               }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SignUpActivity.this, android.R.layout.simple_spinner_dropdown_item, names);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                designation = spinner.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //show password
        showpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        userData = new TheUserData();

//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    i = (int) snapshot.getChildrenCount();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterNewUser();
            }
        });
    }

    private void RegisterNewUser() {

        // show the visibility of progress bar to show loading
        progressBar.setVisibility(View.VISIBLE);

        // Take the value of two edit texts in Strings
        email = username.getText().toString();
        password = pass.getText().toString();
        empID = employee_ID.getText().toString();

        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email",
                    Toast.LENGTH_LONG)
                    .show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password",
                    Toast.LENGTH_LONG)
                    .show();
        } else if (TextUtils.isEmpty(empID)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter EMployee ID",
                    Toast.LENGTH_LONG)
                    .show();
        } else {
            StoreData();

        }
    }

    private void StoreData(){
        designation = spinner.getSelectedItem().toString();
        Log.d(TAG, "designation: "+designation);

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("Email", email);
        userMap.put("Employee_ID", empID);
        userMap.put("Designation",designation);

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    userID = mAuth.getCurrentUser().getUid();
                    Log.d(TAG, "Get Data: "+userID);

                    Toast.makeText(SignUpActivity.this,"User Created", Toast.LENGTH_SHORT).show();

                    reference.child(empID).updateChildren(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);
                                Toast.makeText(SignUpActivity.this,"User is added",Toast.LENGTH_SHORT).show();
                            }else{
                                String message = task.getException().toString();
                                Toast.makeText(SignUpActivity.this,"Error: "+message,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}