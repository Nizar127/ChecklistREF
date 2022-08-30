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

        shift_A1 = findViewById(R.id.shiftA1);
        shift_A2 = findViewById(R.id.shiftA2);
        shift_A3 = findViewById(R.id.shiftA3);
        shift_A4 = findViewById(R.id.shiftA4);
        shift_A5 = findViewById(R.id.shiftA5);
        shift_A6 = findViewById(R.id.shiftA6);

        shift_B1 = findViewById(R.id.shiftB1);
        shift_B2 = findViewById(R.id.shiftB2);
        shift_B3 = findViewById(R.id.shiftB3);
        shift_B4 = findViewById(R.id.shiftB4);
        shift_B5 = findViewById(R.id.shiftB5);
        shift_B6 = findViewById(R.id.shiftB6);

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
        equipRef = FirebaseDatabase.getInstance().getReference("Equipment_List").child("equipmentList");

        equipRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                if(task.getResult().exists()){
                    equipRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot datasnapshot) {

                        Object quantity_1 = datasnapshot.child(equipID).child("Quantity1").getValue();
                        Object quantity_2 = datasnapshot.child(equipID).child("Quantity2").getValue();
                        Object quantity_3 = datasnapshot.child(equipID).child("Quantity3").getValue();
                        Object quantity_4 = datasnapshot.child(equipID).child("Quantity4").getValue();
                        Object quantity_5 = datasnapshot.child(equipID).child("Quantity5").getValue();
                        Object quantity_6 = datasnapshot.child(equipID).child("Quantity6").getValue();

                        Object newshiftA1 = datasnapshot.child(equipID).child("shift1A").getValue();
                        Object newshiftA2 = datasnapshot.child(equipID).child("shift2A").getValue();
                        Object newshiftA3 = datasnapshot.child(equipID).child("shift3A").getValue();
                        Object newshiftA4 = datasnapshot.child(equipID).child("shift4A").getValue();
                        Object newshiftA5 = datasnapshot.child(equipID).child("shift5A").getValue();
                        Object newshiftA6 = datasnapshot.child(equipID).child("shift6A").getValue();

                        Object newshiftB1 = datasnapshot.child(equipID).child("shift1B").getValue();
                        Object newshiftB2 = datasnapshot.child(equipID).child("shift2B").getValue();
                        Object newshiftB3 = datasnapshot.child(equipID).child("shift3B").getValue();
                        Object newshiftB4 = datasnapshot.child(equipID).child("shift4B").getValue();
                        Object newshiftB5 = datasnapshot.child(equipID).child("shift5B").getValue();
                        Object newshiftB6 = datasnapshot.child(equipID).child("shift6B").getValue();


                        qty1.setText(quantity_1.toString());
                        qty2.setText(quantity_2.toString());
                        qty3.setText(quantity_3.toString());
                        qty4.setText(quantity_4.toString());
                        qty5.setText(quantity_5.toString());
                        qty6.setText(quantity_6.toString());

                        shift_A1.setText(newshiftA1.toString());
                        shift_A2.setText(newshiftA2.toString());
                        shift_A3.setText(newshiftA3.toString());
                        shift_A4.setText(newshiftA4.toString());
                        shift_A5.setText(newshiftA5.toString());
                        shift_A6.setText(newshiftA6.toString());

                        shift_B1.setText(newshiftB1.toString());
                        shift_B2.setText(newshiftB2.toString());
                        shift_B3.setText(newshiftB3.toString());
                        shift_B4.setText(newshiftB4.toString());
                        shift_B5.setText(newshiftB5.toString());
                        shift_B6.setText(newshiftB6.toString());
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