package com.mc.info.lumc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

public class ReportDetails extends AppCompatActivity implements Serializable{
    Bundle extras;
    TextView txtNotes,
             txtDate,
             txtExams,
             txtMeds,
             txtPrecs;
    MedicalReport mr;
    List<Medication> meds;
    List<PrescExam> exams;
    List<Precaution> precs;
    String ex="" , prc="" , md="";
    SimpleDateFormat date = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_details);
        extras = getIntent().getExtras();
        mr = (MedicalReport) extras.getSerializable("medicalReport");
        exams = DBHandler.getInstance().getMedicalReportExams(mr);
        precs = DBHandler.getInstance().getMedicalReportPrecs(mr);
        meds = DBHandler.getInstance().getMedicalReportMeds(mr);
        txtNotes = (TextView) findViewById(R.id.activity_report_details_notes);
        txtDate = (TextView) findViewById(R.id.activity_report_details_date);
        txtMeds = (TextView) findViewById(R.id.activity_report_details_meds);
        txtExams = (TextView) findViewById(R.id.activity_report_details_exams);
        txtPrecs = (TextView) findViewById(R.id.activity_report_details_precs);
        txtNotes.setText(mr.getNotes());
        txtDate.setText(date.format(mr.getReportDate()).toString());
        for(PrescExam pe : exams)
            ex += pe.getPrescExamName() + "\n";
        for(Medication m : meds)
            md += m.getMedname() + "\n";
        for(Precaution p : precs)
            prc += p.getPrecName() + "\n";
        if(ex == "")
            ex = "no examinations";
        if(prc == "")
            prc = "no precautions";
        if(md == "")
            md = "no medications";
        txtExams.setText(ex);
        txtMeds.setText(md);
        txtPrecs.setText(prc);
    }

}
