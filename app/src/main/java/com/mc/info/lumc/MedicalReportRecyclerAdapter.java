package com.mc.info.lumc;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by BurgerMan on 1/1/2017.
 */

public class MedicalReportRecyclerAdapter extends AbstractAdapter<MedicalReportRecyclerAdapter.MedicalReportHolder>{
    
    List<MedicalReport> medicalReports;
    
    public  MedicalReportRecyclerAdapter(List<MedicalReport> mr)
    {
        medicalReports =new ArrayList<>(mr);
    }
    @Override
    public MedicalReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_report_item,parent,false);
        return new MedicalReportHolder(view);
    }

    @Override
    public void onBindViewHolder(final MedicalReportHolder holder, final int position) {
        SimpleDateFormat date = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy");
        holder.textDate.setText(date.format(medicalReports.get(position).getReportDate()).toString());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MedicalReport mr = medicalReports.get(position);
                Intent intent = new Intent(holder.cardView.getContext(), ReportDetails.class);
                intent.putExtra("medicalReport",mr);
                holder.cardView.getContext().startActivity(intent);
            }
        });
        holder.textNotes.setText(medicalReports.get(position).getNotes());
    }

    @Override
    public int getItemCount() {
        return medicalReports.size();
    }

    @Override
    void sortBy(Sort sort) {

    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public class MedicalReportHolder extends RecyclerView.ViewHolder{

        TextView textDate;
        TextView textNotes;
        CardView cardView;
        public MedicalReportHolder(View itemView) {
            super(itemView);
            textDate = (TextView) itemView.findViewById(R.id.list_report_item_date);
            textNotes = (TextView) itemView.findViewById(R.id.list_report_item_notes);
            cardView= (CardView) itemView.findViewById(R.id.list_report_item_card);
        }
    }
}
