package com.mc.info.lumc;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by toma on 1/6/2017.
 */

public class MedicalResultRecyclerAdapter extends RecyclerView.Adapter<MedicalResultRecyclerAdapter.ResultHolder>{
    List<Examination> exams;
    private Bundle extras;
    private Patient p;

    public MedicalResultRecyclerAdapter(List<Examination> e) {
        exams =new ArrayList<>(e);
    }

    public MedicalResultRecyclerAdapter.ResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.medical_result_item,parent,false);

        return new ResultHolder(view);
    }

    public void onBindViewHolder(final ResultHolder holder, final int position) {

        holder.textViewName.setText(exams.get(position).getExamName());
        holder.textViewDate.setText(exams.get(position).getDate().toString());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Examination e = exams.get(position);
                DBHandler.getInstance().setQuickFixExamination(e);
                Intent intent = new Intent(holder.cardView.getContext(), MedicalResultInfo.class);
                intent.putExtra("take",e);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                holder.cardView.getContext().startActivity(intent);
            }
        });
    }


    public int getItemCount() {
        return exams.size();
    }

    public class ResultHolder extends RecyclerView.ViewHolder{

        TextView textViewName;
        TextView textViewDate;
        CardView cardView;
        public ResultHolder(View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.medical_result_name);
            textViewDate = (TextView) itemView.findViewById(R.id.medical_result_date);
            cardView= (CardView) itemView.findViewById(R.id.medical_card);
        }
    }
}
