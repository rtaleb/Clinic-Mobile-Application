package com.mc.info.lumc;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MedicalResultInfo extends AppCompatActivity {

    private DBHandler dbHandler = DBHandler.getInstance();
    private RecyclerView recyclerView;
    private ArrayList<MedicalData> data = new ArrayList<>();
    private MedicalResultInfoRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_medical_result_info);
        recyclerView= (RecyclerView) findViewById(R.id.activity_medical_result_info_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<MedicalData> exams=dbHandler.getMedicalResult(dbHandler.getQuickFixPatent().getId(),dbHandler.getQuickFixExamination().getId());
        adapter = new MedicalResultInfoRecyclerAdapter(exams);
        recyclerView.setAdapter(adapter);
    }
}
