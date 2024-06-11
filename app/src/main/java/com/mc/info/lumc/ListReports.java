package com.mc.info.lumc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.List;

public class ListReports extends AppCompatActivity implements Serializable {

    private List<MedicalReport> mr;
    private RecyclerView rvReports;
    private MedicalReportRecyclerAdapter adapter;
    Bundle extras;
    Patient p;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reports);
        extras = getIntent().getExtras();
        p=(Patient) extras.getSerializable("take");
        mr = DBHandler.getInstance().getMyMedicalReport(p);
        rvReports = (RecyclerView) findViewById(R.id.activity_list_reports);
        rvReports.setLayoutManager(new LinearLayoutManager(this));
        adapter=new MedicalReportRecyclerAdapter(mr);
        rvReports.setAdapter(adapter);
    }

    public void newReport(View view) {
        Intent i = new Intent(this,CreateMedicalReport.class);
        MedicalReport mr = new MedicalReport();
        i.putExtra("take",mr);
        i.putExtra("patient",(Patient)extras.getSerializable("take"));
        startActivity(i);
        finish();
    }
}
