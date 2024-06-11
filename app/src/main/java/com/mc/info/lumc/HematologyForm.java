package com.mc.info.lumc;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HematologyForm extends AppCompatActivity {

    private Bundle extras;
    private Patient p;
    private Calendar myCalendar;

    Button save;
    EditText valueRBC,valueHCT,valueMCV,valueMCH,valueRDW,valueWBC,date;
    TextView examName,textNameRBC,textNameHCT,textNameMCV,textNameMCH,textNameRDW,textNameWBC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hematology_form);

        extras = getIntent().getExtras();
        p = (Patient) extras.getSerializable("patient");

        myCalendar = Calendar.getInstance();
        save = (Button) findViewById(R.id.btnInsertHematology);
        examName = (TextView) findViewById(R.id.txtHematologyTitle);

        textNameRBC = (TextView) findViewById(R.id.hematology_form_RBC_name) ;
        textNameHCT = (TextView) findViewById(R.id.hematology_form_HCT_name) ;
        textNameMCV = (TextView) findViewById(R.id.hematology_form_MCV_name) ;
        textNameMCH = (TextView) findViewById(R.id.hematology_form_MCH_name) ;
        textNameRDW = (TextView) findViewById(R.id.hematology_form_RDW_name) ;
        textNameWBC = (TextView) findViewById(R.id.hematology_form_WBC_name) ;

        valueRBC = (EditText) findViewById(R.id.hematology_form_RBC_value) ;
        valueHCT = (EditText) findViewById(R.id.hematology_form_HCT_value) ;
        valueMCV = (EditText) findViewById(R.id.hematology_form_MCV_value) ;
        valueMCH = (EditText) findViewById(R.id.hematology_form_MCH_value) ;
        valueRDW = (EditText) findViewById(R.id.hematology_form_RDW_value) ;
        valueWBC = (EditText) findViewById(R.id.hematology_form_WBC_value) ;
        date = (EditText) findViewById(R.id.hematology_form_date) ;

        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MMM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

                date.setText(sdf.format(myCalendar.getTime()));

            }



        };

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(HematologyForm.this, datePickerListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Examination e;
                MedicalData RBC,HCT,MCV,MCH,RDW,WBC;

                if(valueRBC.equals("") || valueHCT.equals("") || valueMCV.equals("") || valueMCH.equals("") || valueRDW.equals("") || valueWBC.equals("") || date.equals("") )
                    Toast.makeText(getApplicationContext(), "Fill all required fields!", Toast.LENGTH_SHORT).show();
                else {
                    RBC = new MedicalData(textNameRBC.getText().toString(), valueRBC.getText().toString());
                    HCT = new MedicalData(textNameHCT.getText().toString(), valueHCT.getText().toString());
                    MCV = new MedicalData(textNameMCV.getText().toString(), valueMCV.getText().toString());
                    MCH = new MedicalData(textNameMCH.getText().toString(), valueMCH.getText().toString());
                    RDW = new MedicalData(textNameRDW.getText().toString(), valueRDW.getText().toString());
                    WBC = new MedicalData(textNameWBC.getText().toString(), valueWBC.getText().toString());

                    e = new Examination(null, examName.getText().toString(), date.getText().toString(), Examination.examType.HEMATOLOGY);

                    e.getMedicalData().add(RBC);
                    e.getMedicalData().add(HCT);
                    e.getMedicalData().add(MCV);
                    e.getMedicalData().add(MCH);
                    e.getMedicalData().add(RDW);
                    e.getMedicalData().add(WBC);
                    DBHandler.addExamination(p, e);
                    Toast.makeText(getApplicationContext(), "Successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
