package com.CEFS.checklist.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.CEFS.checklist.AdminDashboardActivity;
import com.CEFS.checklist.AuthenticationActivity;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class AdminLoginActivity extends AppCompatActivity {

    Query WatchRef,InspectRef;
    Button adminloginButton;
    EditText username, pass;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    CheckBox showpass;
    DatabaseReference mBase, spinnerRef, designRef;
    String userID,empId, designID;
    ImageView backBTN;
    TextView forgotPass, employeeLogin;
    Spinner spinner;
    List<String> names;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);


        mAuth = FirebaseAuth.getInstance();
        mBase = FirebaseDatabase.getInstance().getReference("Employee");

        adminloginButton = findViewById(R.id.login_btn);
        username = findViewById(R.id.adminlogin_username_input);
        pass = findViewById(R.id.adminlogin_password_input);

        spinner = findViewById(R.id.spinneradminlogin);
        names = new ArrayList<>();

        backBTN = findViewById(R.id.back_btn_adminlogin);
        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminLoginActivity.this, AuthenticationActivity.class);
                startActivity(intent);
            }
        });
        progressBar = findViewById(R.id.progressbar);



//        remember = findViewById(R.id.remember_me);
        showpass = findViewById(R.id.login_checkbox);
        forgotPass = findViewById(R.id.forget_password_link);
        employeeLogin = findViewById(R.id.user_login_btn);

        //admin login
        employeeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminLoginActivity.this, LoginActivity.class);
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

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AdminLoginActivity.this, android.R.layout.simple_spinner_dropdown_item, names);
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
                empId = spinner.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //designRef = FirebaseDatabase.getInstance().getReference()

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
                                Toast.makeText(AdminLoginActivity.this, "Reset Link Sent To Your Email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AdminLoginActivity.this, "Error! Reset Link Is No Sent", Toast.LENGTH_SHORT).show();

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

        adminloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        // Take the value of two edit texts in Strings
        String email, password, employeeID, w_commander, inspect_commander;
        email = username.getText().toString();
        password = pass.getText().toString();



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

        Query newRef = mBase.orderByChild("Employee_ID").equalTo(empId);
         WatchRef = mBase.orderByChild("Designation");
         InspectRef = mBase.orderByChild("Designation");

        newRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String ID = snapshot.child(empId).child("Employee_ID").getValue(String.class);
                    String authority = snapshot.child(empId).child("Designation").getValue(String.class);

                    if(ID.equals(empId)){
                        String nameFromDB = snapshot.child(empId).child("Email").getValue(String.class);
                        String designationFromDB = snapshot.child(empId).child("Designation").getValue(String.class);
                        String employeeIDFromDB = snapshot.child(empId).child("Employee_ID").getValue(String.class);

                        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),
                                            "Admin Login successful!",
                                            Toast.LENGTH_LONG)
                                            .show();

                                    assert designationFromDB != null;
                                    if(designationFromDB.equals("Watch Commander") || designationFromDB.equals("Inspect Commander")){
                                        Intent intent = new Intent(AdminLoginActivity.this, AdminDashboardActivity.class);
                                        intent.putExtra("Email", nameFromDB);
                                        intent.putExtra("Designation", designationFromDB);
                                        intent.putExtra("Employee_ID", employeeIDFromDB);
                                        startActivity(intent);
                                    }else{
                                        Intent intent = new Intent(AdminLoginActivity.this, MainActivity.class);
                                        intent.putExtra("Email", nameFromDB);
                                        intent.putExtra("Designation", designationFromDB);
                                        intent.putExtra("Employee_ID", employeeIDFromDB);
                                        startActivity(intent);
                                    }
                                }else{


                                    // Registration failed
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "Registration failed!!"
                                                    + " Please try again later",
                                            Toast.LENGTH_LONG)
                                            .show();


                                }

                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
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
                    String ResultID, watch_comID, inspect_comID;
                    ResultID = task.getResult().getUser().getUid();

                    mBase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            if(snapshot.child("EmployeeID").exists()){

                                //check employee ID if exist
                                //then match them with current logged in ID
                                //then check designation of the ID
                                //if it is watch commander then go to other one fragment
                                //if not going to another fragment/activity
                               Object this_employeeID = snapshot.child("EmployeeID").getValue();

                                Log.d(TAG, "employeeID: "+this_employeeID);
                                if(ResultID == this_employeeID){
                                        
                                }else{

                                }
                                // check the employee exist or not
                                //if exist, is it match,
                                //if it match, then is it correct designation



                                Intent intent = new Intent(AdminLoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                progressBar.setVisibility(View.GONE);
                            }else{
                                Toast.makeText(getApplicationContext(), "Please Try Again", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });

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