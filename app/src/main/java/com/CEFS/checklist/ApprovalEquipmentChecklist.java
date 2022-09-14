package com.CEFS.checklist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class ApprovalEquipmentChecklist extends AppCompatActivity {

    TextView qty1,qty2, qty3, qty4, qty5, qty6;
    TextView shift_A1,shift_A2,shift_A3,shift_A4, shift_A5, shift_A6;
    TextView shift_B1, shift_B2, shift_B3,shift_B4, shift_B5, shift_B6;
    String equipID = "";
    ImageView backbtn;
    DatabaseReference equipRef;
    Button approveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_equipment_checklist);

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

        approveBtn = findViewById(R.id.approve_view_equipment_btn);
        equipID = getIntent().getStringExtra("EquipmentID");

        getequipment(equipID);

        backbtn = findViewById(R.id.back_btn_equipment_theold);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ApprovalEquipmentChecklist.this, AdminApprovalActivity.class);
                startActivity(intent);
            }
        });

        approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ApprovalEquipmentChecklist.this, ApprovalName.class);
                intent.putExtra("ApprovalID",equipID);
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

                            Object quantity_1 = datasnapshot.child(equipID).child("Quantity_1").getValue();
                            Object quantity_2 = datasnapshot.child(equipID).child("Quantity_2").getValue();
                            Object quantity_3 = datasnapshot.child(equipID).child("Quantity_3").getValue();
                            Object quantity_4 = datasnapshot.child(equipID).child("Quantity_4").getValue();
                            Object quantity_5 = datasnapshot.child(equipID).child("Quantity_5").getValue();
                            Object quantity_6 = datasnapshot.child(equipID).child("Quantity_6").getValue();

                            Object newshiftA1 = datasnapshot.child(equipID).child("ShiftA1").getValue();
                            Object newshiftA2 = datasnapshot.child(equipID).child("ShiftA2").getValue();
                            Object newshiftA3 = datasnapshot.child(equipID).child("ShiftA3").getValue();
                            Object newshiftA4 = datasnapshot.child(equipID).child("ShiftA4").getValue();
                            Object newshiftA5 = datasnapshot.child(equipID).child("ShiftA5").getValue();
                            Object newshiftA6 = datasnapshot.child(equipID).child("ShiftA6").getValue();

                            Object newshiftB1 = datasnapshot.child(equipID).child("ShiftB1").getValue();
                            Object newshiftB2 = datasnapshot.child(equipID).child("ShiftB2").getValue();
                            Object newshiftB3 = datasnapshot.child(equipID).child("ShiftB3").getValue();
                            Object newshiftB4 = datasnapshot.child(equipID).child("ShiftB4").getValue();
                            Object newshiftB5 = datasnapshot.child(equipID).child("ShiftB5").getValue();
                            Object newshiftB6 = datasnapshot.child(equipID).child("ShiftB6").getValue();


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