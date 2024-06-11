package com.mc.info.lumc;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BurgerMan on 1/1/2017.
 */

public class MedicationRecyclerAdapter extends AbstractAdapter<MedicationRecyclerAdapter.MedicationHolder>{

    List<Medication> medications;

    public MedicationRecyclerAdapter(List<Medication> m)
    {
        medications =new ArrayList<>(m);
    }
    @Override
    public MedicationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.medication_item,parent,false);
        return new MedicationHolder(view);
    }

    @Override
    public void onBindViewHolder(final MedicationHolder holder, final int position) {
        holder.txtName.setText(medications.get(position).getMedname());
        holder.txtFrom.setText("From: " + medications.get(position).getFrom());
        holder.txtTo.setText("To: " + medications.get(position).getTo());
    }

    @Override
    public int getItemCount() {
        return medications.size();
    }

    @Override
    void sortBy(Sort sort) {

    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public class MedicationHolder extends RecyclerView.ViewHolder{

        TextView txtName;
        TextView txtFrom;
        TextView txtTo;
        CardView cardView;
        public MedicationHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.medication_item_name);
            txtFrom = (TextView) itemView.findViewById(R.id.medication_item_from);
            txtTo = (TextView) itemView.findViewById(R.id.medication_item_to);
            cardView= (CardView) itemView.findViewById(R.id.medication_item_card);
        }
    }
}
