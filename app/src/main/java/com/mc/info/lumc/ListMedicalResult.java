package com.mc.info.lumc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import static com.mc.info.lumc.DBHandler.getInstance;

public class ListMedicalResult extends AppCompatActivity {
    private DBHandler dbHandler = getInstance();
    private RecyclerView recyclerView;
    private List exams;
    private MedicalResultRecyclerAdapter adapter;
    private Bundle extras;
    private Patient p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_medical_result);

        extras = getIntent().getExtras();
        p = (Patient) extras.getSerializable("patient");

        recyclerView= (RecyclerView) findViewById(R.id.activity_list_medical_result_medicalResultList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        exams=dbHandler.getInstance().getExaminations(p);
        dbHandler.setQuickFixPatent(p);
        adapter=new MedicalResultRecyclerAdapter(exams);
        recyclerView.setAdapter(adapter);
    }
}

