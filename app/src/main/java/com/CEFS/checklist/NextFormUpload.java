package com.CEFS.checklist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
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

public class NextFormUpload extends AppCompatActivity {

    MaterialButton addnew;
    ImageView backbtn;
    EditText qty1,qty2,qty3,qty4,qty5,qty6;
    TextView equip_1, equip_2, equip_3, equip_4, equip_5, equip_6;
    String theFormID = "";
    String form_tender = "";
    String regNum = "";
    String date_checklist_start = "";
    String wCapacity = "";
    String fCapacity = "";
    String cabinet_type = "";
    String theqty1,theqty2, theqty3, theqty4, theqty5, theqty6;
    String theshiftA1,theshiftA2,theshiftA3,theshiftA4,theshiftA5,theshiftA6;
    String theshiftB1,theshiftB2,theshiftB3,theshiftB4,theshiftB5,theshiftB6;
    String equipment1,equipment2,equipment3,equipment4,equipment5,equipment6;
    ProgressDialog loadingBar;
    DatabaseReference dbRef, spinnerRef, cabinetRef;
    Spinner shiftA1spinner,shiftA2spinner,shiftA3spinner,shiftA4spinner,shiftA5spinner,shiftA6spinner;
    Spinner shiftB1spinner,shiftB2spinner,shiftB3spinner,shiftB4spinner,shiftB5spinner,shiftB6spinner;
    ArrayAdapter<String> arrayAdapter;
    List<String> shiftClass;
    String shift1_A1,shift1_A2,shift1_A3,shift1_A4,shift1_A5,shift1_A6;
    String shift2_A1,shift2_A2,shift2_A3,shift2_A4,shift2_A5,shift2_A6;
    String shift1_B1,shift1_B2,shift1_B3,shift1_B4,shift1_B5,shift1_B6;
    String shift2_B1,shift2_B2,shift2_B3,shift2_B4,shift2_B5,shift2_B6;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_form_upload);

        //get intent from insert data activity
        theFormID = getIntent().getStringExtra("form_id");
        form_tender = getIntent().getStringExtra("Form_tender");
        regNum = getIntent().getStringExtra("Registration_Number");
        date_checklist_start = getIntent().getStringExtra("Date");
        wCapacity = getIntent().getStringExtra("Water_Capacity");
        fCapacity = getIntent().getStringExtra("Foam_Capacity");

        //initialize the array
        shiftClass = new ArrayList<>();

        //initialize ref
        spinnerRef = FirebaseDatabase.getInstance().getReference("Shift");

        shiftA1spinner = findViewById(R.id.spinnerfirstequipmentshift1);
        shiftA2spinner = findViewById(R.id.spinnersecondequipmentshift1);
        shiftA3spinner = findViewById(R.id.spinnerthirdequipmentshift1);
        shiftA4spinner = findViewById(R.id.spinnerfourthequipmentshift1);
        shiftA5spinner = findViewById(R.id.spinnerfifthequipmentshift1);
        shiftA6spinner = findViewById(R.id.spinnersixthequipmentshift1);


        shiftB1spinner = findViewById(R.id.spinnerfirstequipmentshift2);
        shiftB2spinner = findViewById(R.id.spinnersecondequipmentshift2);
        shiftB3spinner = findViewById(R.id.spinnerthirdequipmentshift2);
        shiftB4spinner = findViewById(R.id.spinnerfourthequipmentshift2);
        shiftB5spinner = findViewById(R.id.spinnerfifthequipmentshift2);
        shiftB6spinner = findViewById(R.id.spinnersixthequipmentshift2);



        //the database reference
        dbRef = FirebaseDatabase.getInstance().getReference("Checklist").child("For Review");

        spinnerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot childSnapshot:snapshot.getChildren()){
                    String spinnerName = childSnapshot.child("name").getValue(String.class);
                    shiftClass.add(spinnerName);
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(NextFormUpload.this, android.R.layout.simple_spinner_dropdown_item, shiftClass);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                shiftA1spinner.setAdapter(arrayAdapter);
                shiftA2spinner.setAdapter(arrayAdapter);
                shiftA3spinner.setAdapter(arrayAdapter);
                shiftA4spinner.setAdapter(arrayAdapter);
                shiftA5spinner.setAdapter(arrayAdapter);
                shiftA6spinner.setAdapter(arrayAdapter);

                shiftB1spinner.setAdapter(arrayAdapter);
                shiftB2spinner.setAdapter(arrayAdapter);
                shiftB3spinner.setAdapter(arrayAdapter);
                shiftB4spinner.setAdapter(arrayAdapter);
                shiftB5spinner.setAdapter(arrayAdapter);
                shiftB6spinner.setAdapter(arrayAdapter);


                }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        //HUGE LIST
        shiftA1spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                shift1_A1 = shiftA1spinner.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        shiftA2spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                shift1_A2 = shiftA2spinner.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        shiftA3spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                shift1_A3 = shiftA3spinner.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        shiftA4spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                shift1_A4 = shiftA4spinner.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        shiftA5spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                shift1_A5 = shiftA5spinner.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        shiftA6spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                shift1_A6 = shiftA6spinner.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        shiftB1spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                shift1_B1 = shiftB1spinner.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        shiftB2spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                shift1_B2 = shiftB2spinner.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        shiftB3spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                shift1_B3 = shiftB3spinner.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        shiftB4spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                shift1_B4 = shiftB4spinner.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        shiftB5spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                shift1_B5 = shiftB5spinner.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        shiftB6spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                shift1_B6 = shiftB6spinner.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        addnew = findViewById(R.id.addNewProduct);
        backbtn = findViewById(R.id.back_btn_add_to_form);

        equip_1 = findViewById(R.id.equip1);
        equip_2 = findViewById(R.id.equip2);
        equip_3 = findViewById(R.id.equip3);
        equip_4 = findViewById(R.id.equip4);
        equip_5 = findViewById(R.id.equip5);
        equip_6 = findViewById(R.id.equip6);

        //addnew
        qty1    = findViewById(R.id.quantityfirstequipment);
        qty2    = findViewById(R.id.quantitysecondequipment);
        qty3    = findViewById(R.id.quantitythirdequipment);
        qty4    = findViewById(R.id.quantityfourthequipment);
        qty5    = findViewById(R.id.quantityfifthequipment);
        qty6    = findViewById(R.id.quantitysixthequipment);

        equipment1 = equip_1.getText().toString();
        equipment2 = equip_2.getText().toString();
        equipment3 = equip_3.getText().toString();
        equipment4 = equip_4.getText().toString();
        equipment5 = equip_5.getText().toString();
        equipment6 = equip_6.getText().toString();

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NextFormUpload.this, InsertDataActivity.class);
                startActivity(intent);
            }
        });

        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddRemaining();
            }
        });
        loadingBar = new ProgressDialog(this);
    }

    private void AddRemaining() {

        theqty1 = qty1.getText().toString().trim();
        theqty2 = qty2.getText().toString().trim();
        theqty3 = qty3.getText().toString().trim();
        theqty4 = qty4.getText().toString().trim();
        theqty5 = qty5.getText().toString().trim();
        theqty6 = qty6.getText().toString().trim();

        shift1_A1 = shiftA1spinner.getSelectedItem().toString();
        shift1_A2 = shiftA2spinner.getSelectedItem().toString();
        shift1_A3 = shiftA3spinner.getSelectedItem().toString();
        shift1_A4 = shiftA4spinner.getSelectedItem().toString();
        shift1_A5 = shiftA5spinner.getSelectedItem().toString();
        shift1_A6 = shiftA6spinner.getSelectedItem().toString();

        shift1_B1 = shiftB1spinner.getSelectedItem().toString();
        shift1_B2 = shiftB2spinner.getSelectedItem().toString();
        shift1_B3 = shiftB3spinner.getSelectedItem().toString();
        shift1_B4 = shiftB4spinner.getSelectedItem().toString();
        shift1_B5 = shiftB5spinner.getSelectedItem().toString();
        shift1_B6 = shiftB6spinner.getSelectedItem().toString();


        if (TextUtils.isEmpty(theqty1)) {
            Toast.makeText(this, "Quantity for first equipment is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(theqty2)) {
            Toast.makeText(this, "Quantity for second equipment is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(theqty3)) {
            Toast.makeText(this, "Quantity for third equipment is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(theqty4)) {
            Toast.makeText(this, "Quantity for fourth equipment is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(theqty5)) {
            Toast.makeText(this, "Quantity for fifth equipment is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(theqty6)) {
            Toast.makeText(this, "Quantity for sixth equipment is required", Toast.LENGTH_SHORT).show();
        }        
        
        else if (TextUtils.isEmpty(shift1_A1)) {
            Toast.makeText(this, "Shift A for first equipment for first equipment is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(shift1_A2)){
            Toast.makeText(this, "Shift A for second equipment is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(shift1_A3)) {
            Toast.makeText(this, "Shift A for third equipment is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(shift1_A4)) {
            Toast.makeText(this, "Shift A  for fourth equipment is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(shift1_A5)) {
            Toast.makeText(this, "Shift A  for fifth equipment is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(shift1_A6)) {
            Toast.makeText(this, "Shift A  for sixth equipment is required", Toast.LENGTH_SHORT).show();
        }
        
        else if (TextUtils.isEmpty(shift1_B1)) {
            Toast.makeText(this, "Shift B for first equipment is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(shift1_B2)){
            Toast.makeText(this, "Shift B  for second equipment is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(shift1_B3)) {
            Toast.makeText(this, "Shift B for third equipment is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(shift1_B4)) {
            Toast.makeText(this, "Shift B for fourth equipment is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(shift1_B5)) {
            Toast.makeText(this, "Shift B for fifth equipment is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(shift1_B6)) {
            Toast.makeText(this, "Shift B for sixth equipment is required", Toast.LENGTH_SHORT).show();
        }
        else {
            StoreProductInformation();
        }


    }

    private void StoreProductInformation() {

        loadingBar.setTitle("Adding New Checklist");
        loadingBar.setMessage("Please wait...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        //put it into database
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("InsertID", theFormID);
        dataMap.put("Form_tender", form_tender);
        dataMap.put("Registration_Number", regNum);
        dataMap.put("Date", date_checklist_start);
        dataMap.put("Water_Capacity", wCapacity);
        dataMap.put("Foam_Capacity",fCapacity);
        dataMap.put("Equipment_1", equipment1);
        dataMap.put("Quantity_1", theqty1);
        dataMap.put("ShiftA1",shift1_A1);
        dataMap.put("ShiftB1", shift1_B1);

        dataMap.put("Equipment_2", equipment2);
        dataMap.put("Quantity_2", theqty2);
        dataMap.put("ShiftA2",shift1_A2);
        dataMap.put("ShiftB2", shift1_B2);

        dataMap.put("Equipment_3", equipment3);
        dataMap.put("Quantity_3", theqty3);
        dataMap.put("ShiftA3",shift1_A3);
        dataMap.put("ShiftB3", shift1_B3);

        dataMap.put("Equipment_4", equipment4);
        dataMap.put("Quantity_4", theqty4);
        dataMap.put("ShiftA4",shift1_A4);
        dataMap.put("ShiftB4", shift1_B4);

        dataMap.put("Equipment_5", equipment5);
        dataMap.put("Quantity_5", theqty5);
        dataMap.put("ShiftA5",shift1_A5);
        dataMap.put("ShiftB5", shift1_B5);

        dataMap.put("Equipment_6", equipment6);
        dataMap.put("Quantity_6", theqty6);
        dataMap.put("ShiftA6",shift1_A6);
        dataMap.put("ShiftB6", shift1_B6);

        dbRef.child(theFormID).updateChildren(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "next form: ");
                    Toast.makeText(NextFormUpload.this,"Data Added", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(NextFormUpload.this, MainActivity.class);
                    startActivity(intent);
                    //Intent intent = new In
                    //startActivity(intent);


                }
                else {
                    loadingBar.dismiss();
                    String message = task.getException().toString();
                    Toast.makeText(NextFormUpload.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}