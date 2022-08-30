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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.UUID;

import static android.content.ContentValues.TAG;

public class InsertDataActivity extends AppCompatActivity {


    private String foam_form, regNum_form, date_check,foamcapacity, watercapacity, cabinet_form, equipment, quantity, firstShift, secondShift, saveCurrentDate, saveCurrentTime, productRandomKey, downloadImgUrl;
    private Button addNewProdBtn;
    private ImageView backBtn;
    //TextView dhikrID;
    String userID = "";
    private EditText foamtndr, reg_num, date, foam_capacity, water_capacity, cabinet, equipment_list, qty, shiftA, shiftB;
    private static final int GalleryPick = 1;
    private DatabaseReference DoaRef;
    private ProgressDialog loadingBar;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);

        foamtndr       = findViewById(R.id.foam_tender);
        reg_num        = findViewById(R.id.registration_number);
        date           = findViewById(R.id.foamcapacity);
        water_capacity = findViewById(R.id.watercapacity);
        cabinet        = findViewById(R.id.cabinet);
        equipment_list = findViewById(R.id.equipmentList);
        qty            = findViewById(R.id.quantity);
        shiftA         = findViewById(R.id.shiftA);
        shiftB         = findViewById(R.id.shiftB);
        addNewProdBtn  = findViewById(R.id.addNewProduct);

        DoaRef = FirebaseDatabase.getInstance().getReference().child("Doa_List");
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
        cabinet_form = cabinet.getText().toString().trim();
        equipment = equipment_list.getText().toString().trim();
        quantity = qty.getText().toString().trim();
        firstShift = shiftA.getText().toString().trim();
        secondShift = shiftB.getText().toString().trim();

        if (TextUtils.isEmpty(foam_form)) {
            Toast.makeText(this, "Doa name required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(regNum_form)) {
            Toast.makeText(this, "Doa translate description required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(date_check)) {
            Toast.makeText(this, "Translation name required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(foamcapacity)) {
            Toast.makeText(this, "Translation name required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(watercapacity)) {
            Toast.makeText(this, "Translation name required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(cabinet_form)) {
            Toast.makeText(this, "Translation name required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(equipment)) {
            Toast.makeText(this, "Translation name required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(quantity)) {
            Toast.makeText(this, "Translation name required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(firstShift)) {
            Toast.makeText(this, "Translation name required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(secondShift)) {
            Toast.makeText(this, "Translation name required", Toast.LENGTH_SHORT).show();
        }
        else {
            StoreProductInformation();
        }
    }

    private void StoreProductInformation() {
        loadingBar.setTitle("Adding New Product");
        loadingBar.setMessage("Please wait...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        //Calendar calendar = Calendar.getInstance();

        //SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        // saveCurrentDate = currentDate.format(calendar.getTime());

        //SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        //saveCurrentTime = currentTime.format(calendar.getTime());

        //To create a unique product random key, so that it doesn't overwrite other product
        //productRandomKey = saveCurrentDate + saveCurrentTime;

        //set ID
        //doaID = UUID.randomUUID().toString();
        //String zikirID = dhikrID.getText().toString();
        //Log.d(TAG, "ZikirID: "+doaID);

        userID = getIntent().getIntExtra("");

        //put it into database
        HashMap<String, Object> doaMap = new HashMap<>();
        doaMap.put("DoaID", doaID);
        doaMap.put("title", name);
        doaMap.put("titleTranslate", doatranslate);
        doaMap.put("translation", translate);

        DoaRef.child(doaID).updateChildren(doaMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Add Zikr: ");
                    Toast.makeText(InsertDoa.this,"Doa Added", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(InsertDoa.this, AddMenu.class);
                    startActivity(intent);

                }
                else {
                    loadingBar.dismiss();
                    String message = task.getException().toString();
                    Toast.makeText(InsertDoa.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}