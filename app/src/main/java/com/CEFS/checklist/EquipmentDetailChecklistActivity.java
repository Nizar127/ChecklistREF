package com.CEFS.checklist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import static android.content.ContentValues.TAG;

public class EquipmentDetailChecklistActivity extends AppCompatActivity {

    TextView qty1,qty2, qty3, qty4, qty5, qty6;
    TextView shift_A1,shift_A2,shift_A3,shift_A4, shift_A5, shift_A6;
    TextView shift_B1, shift_B2, shift_B3,shift_B4, shift_B5, shift_B6;
    String equipID = "";
    ImageView backbtn;
    DatabaseReference equipRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_detail_checklist);

        qty1 = findViewById(R.id.quantity1);
        qty2 = findViewById(R.id.quantity2);
        qty3 = findViewById(R.id.quantity3);
        qty4 = findViewById(R.id.quantity4);
        qty5 = findViewById(R.id.quantity5);
        qty6 = findViewById(R.id.quantity6);

        shift_A1 = findViewById(R.id.shiftA1data);
        shift_A2 = findViewById(R.id.shiftA2data);
        shift_A3 = findViewById(R.id.shiftA3data);
        shift_A4 = findViewById(R.id.shiftA4data);
        shift_A5 = findViewById(R.id.shiftA5data);
        shift_A6 = findViewById(R.id.shiftA6data);

        shift_B1 = findViewById(R.id.shiftB1data);
        shift_B2 = findViewById(R.id.shiftB2data);
        shift_B3 = findViewById(R.id.shiftB3data);
        shift_B4 = findViewById(R.id.shiftB4data);
        shift_B5 = findViewById(R.id.shiftB5data);
        shift_B6 = findViewById(R.id.shiftB6data);

        //equipRef = FirebaseDatabase.getInstance().getReference("equipment_checklist").child("equipmentList");

        equipID = getIntent().getStringExtra("EquipmentID");

        getequipment(equipID);

        backbtn = findViewById(R.id.back_btn_equipment_theold);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EquipmentDetailChecklistActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

    private void getequipment(String equipID) {
        equipRef = FirebaseDatabase.getInstance().getReference("Checklist").child("For Review");

        equipRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                if(task.getResult().exists()){
                    equipRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot datasnapshot) {

                        String quantity_1 = datasnapshot.child(equipID).child("Quantity_1").getValue(String.class);
                            Log.d(TAG, "qty1: "+quantity_1);
                        String quantity_2 = datasnapshot.child(equipID).child("Quantity_2").getValue(String.class);
                        String quantity_3 = datasnapshot.child(equipID).child("Quantity_3").getValue(String.class);
                        String quantity_4 = datasnapshot.child(equipID).child("Quantity_4").getValue(String.class);
                        String quantity_5 = datasnapshot.child(equipID).child("Quantity_5").getValue(String.class);
                        String quantity_6 = datasnapshot.child(equipID).child("Quantity_6").getValue(String.class);

                        String newshiftA1 = datasnapshot.child(equipID).child("ShiftA1").getValue(String.class);
                        String newshiftA2 = datasnapshot.child(equipID).child("ShiftA2").getValue(String.class);
                        String newshiftA3 = datasnapshot.child(equipID).child("ShiftA3").getValue(String.class);;
                        String newshiftA4 = datasnapshot.child(equipID).child("ShiftA4").getValue(String.class);
                        String newshiftA5 = datasnapshot.child(equipID).child("ShiftA5").getValue(String.class);
                        String newshiftA6 = datasnapshot.child(equipID).child("ShiftA6").getValue(String.class);

                        String newshiftB1 = datasnapshot.child(equipID).child("ShiftB1").getValue(String.class);
                        String newshiftB2 = datasnapshot.child(equipID).child("ShiftB2").getValue(String.class);
                        String newshiftB3 = datasnapshot.child(equipID).child("ShiftB3").getValue(String.class);
                        String newshiftB4 = datasnapshot.child(equipID).child("ShiftB4").getValue(String.class);
                        String newshiftB5 = datasnapshot.child(equipID).child("ShiftB5").getValue(String.class);
                        String newshiftB6 = datasnapshot.child(equipID).child("ShiftB6").getValue(String.class);


                            if (quantity_1 != null) {
                                qty1.setText(quantity_1.toString());
                            }
                            if (quantity_2 != null) {
                                qty2.setText(quantity_2.toString());
                            }
                            if (quantity_3 != null) {
                                qty3.setText(quantity_3.toString());
                            }
                            if (quantity_4 != null) {
                                qty4.setText(quantity_4.toString());
                            }
                            if (quantity_5 != null) {
                                qty5.setText(quantity_5.toString());
                            }
                            if (quantity_6 != null) {
                                qty6.setText(quantity_6.toString());
                            }

                            if (newshiftA1 != null) {
                                shift_A1.setText(newshiftA1.toString());
                            }
                            if (newshiftA2 != null) {
                                shift_A2.setText(newshiftA2.toString());
                            }
                            if (newshiftA3 != null) {
                                shift_A3.setText(newshiftA3.toString());
                            }
                            if (newshiftA4 != null) {
                                shift_A4.setText(newshiftA4.toString());
                            }
                            if (newshiftA5 != null) {
                                shift_A5.setText(newshiftA5.toString());
                            }
                            if (newshiftA6 != null) {
                                shift_A6.setText(newshiftA6.toString());
                            }

                            if (newshiftB1 != null) {
                                shift_B1.setText(newshiftB1.toString());
                            }
                            if (newshiftB2 != null) {
                                shift_B2.setText(newshiftB2.toString());
                            }
                            if (newshiftB3 != null) {
                                shift_B3.setText(newshiftB3.toString());
                            }
                            if (newshiftB4 != null) {
                                shift_B4.setText(newshiftB4.toString());
                            }
                            if (newshiftB5 != null) {
                                shift_B5.setText(newshiftB5.toString());
                            }
                            if (newshiftB6 != null) {
                                shift_B6.setText(newshiftB6.toString());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                }
            }
        });

    }
}