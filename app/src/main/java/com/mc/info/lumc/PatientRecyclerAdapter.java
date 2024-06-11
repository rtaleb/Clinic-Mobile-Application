package com.mc.info.lumc;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;


public class PatientRecyclerAdapter extends RecyclerView.Adapter<PatientRecyclerAdapter.PatientHolder> implements Filterable{
    private PatientFilter patientFilter;
    private List<Patient> filteredPatients;
    private Sort currentSort=Sort.FIRST_NAME;
    public PatientRecyclerAdapter(List<Patient> p)
    {
        filteredPatients =new ArrayList<>(p);
        sortBy(Sort.FIRST_NAME);
        patientFilter=new PatientFilter(filteredPatients,this);
    }
    @Override
    public PatientHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_item,parent,false);
        return new PatientHolder(view);
    }

    @Override
    public void onBindViewHolder(final PatientHolder holder, final int position) {

        holder.textView.setText(filteredPatients.get(position).getFirstName()+" "+ filteredPatients.get(position).getLastName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Patient p = filteredPatients.get(position);
                Intent intent = new Intent(holder.cardView.getContext(), PatientInfo.class);
                intent.putExtra("take",p);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                holder.cardView.getContext().startActivity(intent);
            }
        });
        //holder.imageView.setImageBitmap();
    }

    @Override
    public int getItemCount() {
        return filteredPatients.size();
    }

    @Override
    public Filter getFilter() {

        return patientFilter;
    }
    public void sortBy(Sort type)
    {
        switch (type){
            case FIRST_NAME:
                currentSort=Sort.FIRST_NAME;
                Collections.sort(filteredPatients, new Comparator<Patient>() {
                    @Override
                    public int compare(Patient o1, Patient o2) {
                        return o1.getFirstName().compareTo(o2.getFirstName());
                    }
                });
                break;
            case LAST_NAME:
                currentSort=Sort.LAST_NAME;
                Collections.sort(filteredPatients, new Comparator<Patient>() {
                    @Override
                    public int compare(Patient o1, Patient o2) {
                        return o1.getLastName().compareTo(o2.getLastName());
                    }
                });
                break;
        }
    }
    public enum Sort{
        FIRST_NAME,LAST_NAME
    }
    public class PatientHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView imageView;
        CardView cardView;
        public PatientHolder(View itemView) {
            super(itemView);
            textView= (TextView) itemView.findViewById(R.id.patient_item_name);
            imageView= (ImageView) itemView.findViewById(R.id.patient_item_image);
            cardView= (CardView) itemView.findViewById(R.id.patient_item_card);
        }
    }
    private class PatientFilter extends Filter{
        List<Patient> filtered;
        List<Patient> original;
        PatientRecyclerAdapter adapter;
        public PatientFilter(List<Patient> o,PatientRecyclerAdapter ad)
        {
            original =new ArrayList<>(o);
            adapter=ad;
            filtered=new ArrayList<>();
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filtered.clear();
            FilterResults results=new FilterResults();
            if(constraint.toString().trim().length()==0)
                filtered.addAll(original);
            else {
                Pattern pattern=Pattern.compile(constraint.toString(),Pattern.CASE_INSENSITIVE);
                for (Patient p: original)
                {
                    if(pattern.matcher(p.getFirstName()).find()||pattern.matcher(p.getLastName()).find())
                        filtered.add(p);
                }
            }
            results.count=filtered.size();
            results.values=filtered;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredPatients=new ArrayList<>((List<Patient>)results.values);
            sortBy(currentSort);
            adapter.notifyDataSetChanged();
        }
    }
}
