
package com.mc.info.lumc;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PatientMedicines extends AppCompatActivity {

    private RecyclerView rvMedication;
    private MedicationRecyclerAdapter adapter;
    private List<MedicalReport> mr;
    private List<Medication> m;
    Bundle extras;
    Patient p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_medication);
        extras = getIntent().getExtras();
        p=(Patient) extras.getSerializable("take");
        mr = DBHandler.getInstance().getMyMedicalReport(p);
        if(mr != null) {
            m = new ArrayList<>();
            for (MedicalReport mr1 : mr) {
                List<Medication> m1 = DBHandler.getInstance().getMedicalReportMeds(mr1);
                for (Medication m2 : m1)
                    m.add(m2);
            }

            rvMedication = (RecyclerView) findViewById(R.id.activity_patient_medication_rvmedication);
            rvMedication.setLayoutManager(new LinearLayoutManager(this));
            adapter=new MedicationRecyclerAdapter(m);
            rvMedication.setAdapter(adapter);
        }
    }


}
