package com.mc.info.lumc;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MedicalResultInfoRecyclerAdapter extends RecyclerView.Adapter<MedicalResultInfoRecyclerAdapter.ResultHolder> {


    List<MedicalData> data;

    public MedicalResultInfoRecyclerAdapter(List<MedicalData> m) {
        data =new ArrayList<>(m);
    }

    public MedicalResultInfoRecyclerAdapter.ResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.medical_result_info_item,parent,false);
        return new MedicalResultInfoRecyclerAdapter.ResultHolder(view);
    }

    public void onBindViewHolder(final MedicalResultInfoRecyclerAdapter.ResultHolder holder, final int position) {
        holder.textViewName.setText(data.get(position).getName());
        holder.textViewValue.setText(data.get(position).getValue());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    public int getItemCount() {
        return data.size();
    }
    public class ResultHolder extends RecyclerView.ViewHolder{

        TextView textViewName;
        TextView textViewValue;
        CardView cardView;
        public ResultHolder(View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.medical_result_info_item_name);
            textViewValue = (TextView) itemView.findViewById(R.id.medical_result_info_item_value);
            cardView= (CardView) itemView.findViewById(R.id.medical_result_info_item_card);
        }
    }
}