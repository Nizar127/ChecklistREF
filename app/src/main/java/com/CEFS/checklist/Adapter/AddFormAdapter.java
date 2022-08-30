package com.CEFS.checklist.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CEFS.checklist.EquipmentDetailChecklistActivity;
import com.CEFS.checklist.Model.FomData;
import com.CEFS.checklist.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.jetbrains.annotations.NotNull;

public class AddFormAdapter extends FirebaseRecyclerAdapter<FomData, AddFormAdapter.AddFormViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AddFormAdapter(@NonNull @NotNull FirebaseRecyclerOptions<FomData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull AddFormViewHolder holder, int position, @NonNull @NotNull FomData model) {
        holder.foam_tndr.setText(model.getFoam_tender());
        holder.reg_name.setText(model.getRegistration_num());
        holder.thedate.setText(model.getDate());
        holder.shiftAView.setText(model.getShift1());
        holder.shiftBView.setText(model.getShift2());
        holder.foam_capacityview.setText(model.getFoam_capacity());
        holder.water_capacityview.setText(model.getWater_capacity());
        holder.view_equipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EquipmentDetailChecklistActivity.class);
                intent.putExtra("EquipmentID", model.getID());
                v.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @NotNull
    @Override
    public AddFormViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewformlayout, parent, false);
        return new AddFormViewHolder(view);
    }

    public class AddFormViewHolder extends RecyclerView.ViewHolder{

        TextView foam_tndr, reg_name,thedate,shiftAView,shiftBView,foam_capacityview, water_capacityview;
        Button view_equipment;
        public AddFormViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            foam_tndr      = itemView.findViewById(R.id.foamtenderNameView);
            reg_name       = itemView.findViewById(R.id.regisNameView);
            thedate        = itemView.findViewById(R.id.dateView);
            shiftAView     = itemView.findViewById(R.id.shiftAView);
            shiftBView     = itemView.findViewById(R.id.shiftBView);
            view_equipment = itemView.findViewById(R.id.view_equipment_btn);
        }
    }
}
