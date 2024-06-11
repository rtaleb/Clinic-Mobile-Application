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

/**
 * Created by BurgerMan on 1/1/2017.
 */

public class DoctorRecyclerAdapter extends AbstractAdapter<DoctorRecyclerAdapter.DoctorHolder>{
    DoctorFilter doctorFilter;
    List<Doctor> filteredDoctors;
    Sort currentSort= Sort.FIRST_NAME;
    public DoctorRecyclerAdapter(List<Doctor> d)
    {
        filteredDoctors =new ArrayList<>(d);
        sortBy(Sort.FIRST_NAME);
        doctorFilter=new DoctorFilter(filteredDoctors,this);
    }
    @Override
    public DoctorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_item,parent,false);
        return new DoctorHolder(view);
    }

    @Override
    public void onBindViewHolder(final DoctorHolder holder, final int position) {

        holder.textViewName.setText(filteredDoctors.get(position).getFirstName()+" "+ filteredDoctors.get(position).getLastName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Doctor d = filteredDoctors.get(position);

                Intent intent = new Intent(holder.cardView.getContext(), DoctorInfo.class);
                intent.putExtra("take",d);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                holder.cardView.getContext().startActivity(intent);
            }
        });
        holder.textViewSpecialty.setText(filteredDoctors.get(position).getSpecialty());
        //holder.imageView.setImageBitmap();
    }

    @Override
    public int getItemCount() {
        return filteredDoctors.size();
    }

    @Override
    public Filter getFilter() {

        return doctorFilter;
    }
    public void sortBy(Sort type)
    {
        switch (type){
            case FIRST_NAME:
                currentSort= Sort.FIRST_NAME;
                Collections.sort(filteredDoctors, new Comparator<Doctor>() {
                    @Override
                    public int compare(Doctor o1, Doctor o2) {
                        return o1.getFirstName().compareTo(o2.getFirstName());
                    }
                });
                break;
            case LAST_NAME:
                currentSort= Sort.LAST_NAME;
                Collections.sort(filteredDoctors, new Comparator<Doctor>() {
                    @Override
                    public int compare(Doctor o1, Doctor o2) {
                        return o1.getLastName().compareTo(o2.getLastName());
                    }
                });
                break;
            case SPECIALTY:
                currentSort= Sort.SPECIALTY;
                Collections.sort(filteredDoctors, new Comparator<Doctor>() {
                    @Override
                    public int compare(Doctor o1, Doctor o2) {
                        return o1.getSpecialty().compareTo(o2.getSpecialty());
                    }
                });
                break;

        }
    }

    public class DoctorHolder extends RecyclerView.ViewHolder{

        TextView textViewName;
        TextView textViewSpecialty;
        ImageView imageView;
        CardView cardView;
        public DoctorHolder(View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.doctor_item_name);
            textViewSpecialty = (TextView) itemView.findViewById(R.id.doctor_item_specialty);
            imageView= (ImageView) itemView.findViewById(R.id.doctor_item_image);
            cardView= (CardView) itemView.findViewById(R.id.doctor_item_card);
        }
    }
    private class DoctorFilter extends Filter{
        List<Doctor> filtered;
        List<Doctor> original;
        DoctorRecyclerAdapter adapter;
        public DoctorFilter(List<Doctor> o,DoctorRecyclerAdapter ad)
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
                for (Doctor d: original)
                {
                    if(pattern.matcher(d.getFirstName()).find()||pattern.matcher(d.getLastName()).find()||pattern.matcher(d.getSpecialty()).find())
                        filtered.add(d);
                }
            }
            results.count=filtered.size();
            results.values=filtered;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredDoctors=new ArrayList<>((List<Doctor>)results.values);
            sortBy(currentSort);
            adapter.notifyDataSetChanged();
        }
    }
}