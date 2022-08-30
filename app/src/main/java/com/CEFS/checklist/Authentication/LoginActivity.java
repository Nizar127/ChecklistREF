package com.CEFS.checklist.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.CEFS.checklist.AdminDashboardActivity;
import com.CEFS.checklist.MainActivity;
import com.CEFS.checklist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    EditText username, pass, empId;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    CheckBox showpass;
    DatabaseReference mBase;
    TextView forgotPass, adminLogin;
    FirebaseAuth.AuthStateListener mAuthstate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mBase = FirebaseDatabase.getInstance().getReference("Employee");

        loginButton = findViewById(R.id.login_btn);
        username = findViewById(R.id.login_username_input);
        pass = findViewById(R.id.login_password_input);
        empId = findViewById(R.id.login_employeeid_input);
        progressBar = findViewById(R.id.progressbar);
//        remember = findViewById(R.id.remember_me);
        showpass = findViewById(R.id.login_checkbox);
        forgotPass = findViewById(R.id.forget_password_link);
        adminLogin = findViewById(R.id.admin_login_btn);

        //admin login
        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                startActivity(intent);
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

        //forgot password
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password");
                passwordResetDialog.setMessage("Enter Your Email To Received Reset Link");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String mail = resetMail.getText().toString();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(LoginActivity.this, "Reset Link Sent To Your Email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Error! Reset Link Is No Sent", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                //initiate the builder dialog

                passwordResetDialog.create().show();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {

        // Take the value of two edit texts in Strings
        String email, password, employeeID;
        email = username.getText().toString();
        password = pass.getText().toString();
        employeeID = empId.getText().toString();

        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(employeeID)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter Employee ID!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }



        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // show the visibility of progress bar to show loading
                    progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),
                            "Registration successful!",
                            Toast.LENGTH_LONG)
                            .show();
                    String ResultID = task.getResult().getUser().getUid();

                    //DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Employee").child(ResultID).child("EmployeeID");

                    //check if the employee ID is match
                    mBase.child(ResultID).child("EmployeeID").equalTo(employeeID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                // hide the progress bar
                                progressBar.setVisibility(View.GONE);
                                Intent intent
                                        = new Intent(LoginActivity.this,
                                        MainActivity.class);
                                startActivity(intent);
                            }else{

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });


                    //if(mBase.child(ResultID).child("isActive").equals(false)){
                       // Toast.makeText(getApplicationContext(), "Login failed!!" + " Please try again later", Toast.LENGTH_LONG).show();
                   // }



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
}