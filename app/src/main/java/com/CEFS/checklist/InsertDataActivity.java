package com.CEFS.checklist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.CEFS.checklist.Model.FomData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.UUID;

import static android.content.ContentValues.TAG;

public class InsertDataActivity extends AppCompatActivity {


    private String foam_form, regNum_form, date_check,foamcapacity, watercapacity, cabinet_form, equipment, quantity, firstShift, secondShift, saveCurrentDate, saveCurrentTime, productRandomKey, downloadImgUrl;
    String mainFormID = "";
    MaterialButton addNewProdBtn, equipment_list;
    private ImageView backBtn;
    //TextView dhikrID;
    String userID = "";
    String key;
    private EditText foamtndr, reg_num, date, foam_capacity, water_capacity, cabinet,  qty, shiftA, shiftB;
    private static final int GalleryPick = 1;
    private DatabaseReference EquipRef;
    private ProgressDialog loadingBar;
    FomData formdata;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);

        foamtndr       = findViewById(R.id.foam_tender);
        reg_num        = findViewById(R.id.registration_number);
        date           = findViewById(R.id.dateItem);
        foam_capacity  = findViewById(R.id.foamcapacity);
        water_capacity = findViewById(R.id.watercapacity);
        formdata = new FomData();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        key = database.getReference("Checklist").push().getKey();

        addNewProdBtn  = findViewById(R.id.nextBtnUpload);

        EquipRef = FirebaseDatabase.getInstance().getReference().child("Checklist").child("For_Review");
        backBtn        = findViewById(R.id.back_btn_add_new);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InsertDataActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        addNewProdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProductData();
            }
        });
        loadingBar = new ProgressDialog(this);


    }

    private void ValidateProductData() {
        foam_form = foamtndr.getText().toString().trim();
        regNum_form = reg_num.getText().toString().trim();
        date_check = date.getText().toString().trim();
        foamcapacity = foam_capacity.getText().toString().trim();
        watercapacity = water_capacity.getText().toString().trim();


        //mainFormID = FirebaseDatabase.getInstance().getReference("Equipment_List").getKey();
        //Log.d(TAG, "Key: "+mainFormID);

        if (TextUtils.isEmpty(foam_form)) {
            Toast.makeText(this, "Form Tender required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(regNum_form)) {
            Toast.makeText(this, "Registration Number required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(date_check)) {
            Toast.makeText(this, "Date is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(foamcapacity)) {
            Toast.makeText(this, "Foam Capacity required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(watercapacity)) {
            Toast.makeText(this, "Water Capacity required", Toast.LENGTH_SHORT).show();
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

        Intent intent = new Intent(InsertDataActivity.this, NextFormUpload.class);
       // intent.putExtra("form_id", formdata.getID());
        intent.putExtra("form_id", key);
//        intent.putExtra("Form_tender", formdata.getFoam_tender());
//        intent.putExtra("Registration_Number", formdata.getRegistration_num());
//        intent.putExtra("Date", formdata.getDate());
//        intent.putExtra("Water_Capacity", formdata.getWater_capacity());
//        intent.putExtra("Foam_Capacity", formdata.getFoam_capacity());
//        intent.putExtra("Cabinet", formdata.getCabinet_type());
        intent.putExtra("Form_tender", foam_form);
        intent.putExtra("Registration_Number", regNum_form);
        intent.putExtra("Date", date_check);
        intent.putExtra("Water_Capacity", watercapacity);
        intent.putExtra("Foam_Capacity", foamcapacity);
        startActivity(intent);
    }
}