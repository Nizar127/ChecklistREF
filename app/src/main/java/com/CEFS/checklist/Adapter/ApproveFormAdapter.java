package com.CEFS.checklist.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CEFS.checklist.EquipmentDetailApproval;
import com.CEFS.checklist.EquipmentDetailChecklistActivity;
import com.CEFS.checklist.Model.FomData;
import com.CEFS.checklist.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.jetbrains.annotations.NotNull;

public class ApproveFormAdapter extends FirebaseRecyclerAdapter<FomData, ApproveFormAdapter.ApproveFormViewHolder>  {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ApproveFormAdapter(@NonNull @NotNull FirebaseRecyclerOptions<FomData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull ApproveFormViewHolder holder, int position, @NonNull @NotNull FomData model) {
        holder.foam_tndr.setText(model.getForm_tender());
        holder.reg_name.setText(model.getRegistration_Number());
        holder.thedate.setText(model.getDate());
        holder.foam_capacityview.setText(model.getFoam_Capacity());
        holder.water_capacityview.setText(model.getWater_Capacity());
        holder.view_equipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EquipmentDetailChecklistActivity.class);
                intent.putExtra("EquipmentID", model.getInsertID());
                v.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @NotNull
    @Override
    public ApproveFormViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.approveviewform, parent, false);
        return new ApproveFormViewHolder(view);
    }

    public class ApproveFormViewHolder extends RecyclerView.ViewHolder{

        TextView foam_tndr, reg_name,thedate,shiftAView,shiftBView,foam_capacityview, water_capacityview;
        Button view_equipment;

        public ApproveFormViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            foam_tndr          = itemView.findViewById(R.id.foamtenderNameViewapproval);
            reg_name           = itemView.findViewById(R.id.regisNameViewapproval);
            thedate            = itemView.findViewById(R.id.dateViewapproval);
            foam_capacityview  = itemView.findViewById(R.id.foamcapacityViewapproval);
            water_capacityview = itemView.findViewById(R.id.watercapacityViewapproval);
            view_equipment     = itemView.findViewById(R.id.view_equipment_btnapprove);
        }
    }
}
