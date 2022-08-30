package com.CEFS.checklist.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.CEFS.checklist.Model.TheUserData;
import com.CEFS.checklist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    Button signUpButton;
    EditText username, pass, phoneNum, birth;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    ProgressDialog loadingBar;
    TheUserData userData;
    String email, password, designation, employee, watch_commander, inspector_commander;
    RadioButton empId, watch_com_id, inspect_com_id;
    DatabaseReference reference;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        reference = FirebaseDatabase.getInstance().getReference("Employee");
        loadingBar = new ProgressDialog(this);

        signUpButton = findViewById(R.id.Register_btn);
        username     = findViewById(R.id.signup_username_input);
        pass         = findViewById(R.id.signup_password_input);

        watch_com_id   = findViewById(R.id.watchcommandersignup);
        empId          = findViewById(R.id.employeesignup);
        inspect_com_id = findViewById(R.id.inspectorsignup);

        progressBar = findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();

        userData = new TheUserData();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    i = (int) snapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

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
        employee = empId.getText().toString();
        watch_commander = watch_com_id.getText().toString();
        inspector_commander = inspect_com_id.getText().toString();

        userData.setName(email);
        //check value radio button
        if(empId.isChecked()){
            userData.setDesignation(employee);
            reference.child(String.valueOf(i+1)).setValue(userData);
            //reference.child(String.valueOf(i+1).setValue(userData));

        }
        else if(watch_com_id.isChecked()){
            userData.setDesignation(watch_commander);
            reference.child(String.valueOf(i+1)).setValue(userData);
            designation = "Watch_Commander";
            //reference.child(String.valueOf(i+1).setValue(userData));
        }else if(inspect_com_id.isChecked()) {
            userData.setDesignation(inspector_commander);
            reference.child(String.valueOf(i + 1)).setValue(userData);
            designation = "Inspect_Commander";
            //reference.child(String.valueOf(i+1).setValue(userData));
        }else if(empId.isChecked()){
            userData.setDesignation(employee);
            reference.child(String.valueOf(i + 1)).setValue(userData);
            designation = "Employee";
        } else{
            Toast.makeText(SignUpActivity.this, "You must choose one of the designation", Toast.LENGTH_SHORT).show();
        }

        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }if (TextUtils.isEmpty(employee)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter Employee ID",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        else{
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Checking the credentials...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            // ValidatePhoneNumber(name, phone, birthdate, password);
        }


        mAuth
                .createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                    "Registration successful!",
                                    Toast.LENGTH_LONG)
                                    .show();
                            CreateAccount();

                            // hide the progress bar
                            progressBar.setVisibility(View.GONE);

                            // if the user created intent to login activity
                        }
                        else {

                            // Registration failed
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Registration failed!!"
                                            + " Please try again later",
                                    Toast.LENGTH_LONG)
                                    .show();

                            // hide the progress bar
                            progressBar.setVisibility(View.GONE);
                        }


                    }
                });
    }

    private void CreateAccount() {
        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(!snapshot.child("Employee").child(email).exists()){
                    HashMap<String, Object> userdataMap = new HashMap<>();

                    userdataMap.put("email", email);
                    userdataMap.put("designation",designation);
                    userdataMap.put("EmployeeID",employee);
                    userdataMap.put("password", password);

                    rootRef.child("Employee").child(email).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(SignUpActivity.this, "Account Created!" + email, Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }

                                    else {
                                        loadingBar.dismiss();
                                        Toast.makeText(SignUpActivity.this, "We're sorry, Something happen, please try again.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(SignUpActivity.this, "This username already exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(SignUpActivity.this, "Please use other username.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}