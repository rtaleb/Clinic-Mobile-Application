package com.mc.info.lumc;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by BurgerMan on 1/11/2017.
 */

public class ListDoctorsFragment extends Fragment {
    private DoctorRecyclerAdapter adapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_list_doctors,container,false);
        recyclerView= (RecyclerView) v.findViewById(R.id.activity_list_doctors_doctorList);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        new AsyncTask<Void,Void,List<Doctor>>(){
            @Override
            protected List<Doctor> doInBackground(Void... params) {
                while (!DBHandler.getInstance().isDataReady())
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                return DBHandler.getInstance().getDoctors();
            }

            @Override
            protected void onPostExecute(List<Doctor> doctors) {
                adapter=new DoctorRecyclerAdapter(doctors);
                recyclerView.setAdapter(adapter);
            }
        }.execute();

    return v;
    }

    public DoctorRecyclerAdapter getAdapter() {
        return adapter;
    }
}
