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

public class CholesterolForm extends AppCompatActivity {

    private Bundle extras;
    private Patient p;
    private Calendar myCalendar;

    Button save;
    EditText valueTriglycerides,valueCholesterol,valueLDL,valueHDL,date;
    TextView examName,textNameTriglycerides,textNameCholesterol,textNameLDL,textNameHDL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cholesterol_form);

        extras = getIntent().getExtras();
        p = (Patient) extras.getSerializable("patient");

        myCalendar = Calendar.getInstance();

        save = (Button) findViewById(R.id.btnInsertCholesterol);
        examName = (TextView) findViewById(R.id.txtCholesterolTitle);
        valueCholesterol = (EditText) findViewById(R.id.cholesterol_form_Cholesterol_value) ;
        valueTriglycerides = (EditText) findViewById(R.id.cholesterol_form_Triglycerides_value) ;
        valueLDL = (EditText) findViewById(R.id.cholesterol_form_LDL_value) ;
        valueHDL = (EditText) findViewById(R.id.cholesterol_form_HDl_value) ;
        date = (EditText) findViewById(R.id.cholesterol_form_date) ;
        textNameCholesterol = (TextView) findViewById(R.id.cholesterol_form_Cholesterol_name) ;
        textNameTriglycerides = (TextView) findViewById(R.id.cholesterol_form_Triglycerides_name) ;
        textNameLDL = (TextView) findViewById(R.id.cholesterol_form_LDL_name) ;
        textNameHDL = (TextView) findViewById(R.id.cholesterol_form_HDl_name) ;

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
                new DatePickerDialog(CholesterolForm.this, datePickerListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Examination e;
                MedicalData cholesterol,triglycerides,ldl,hdl;

                cholesterol = new MedicalData(textNameCholesterol.getText().toString(),valueCholesterol.getText().toString());
                triglycerides = new MedicalData(textNameTriglycerides.getText().toString(),valueTriglycerides.getText().toString());
                ldl = new MedicalData(textNameLDL.getText().toString(),valueLDL.getText().toString());
                hdl = new MedicalData(textNameHDL.getText().toString(),valueHDL.getText().toString());
                e = new Examination(null,examName.getText().toString(),date.getText().toString(), Examination.examType.CHOLESTEROL);
                e.setType(Examination.examType.CHOLESTEROL);
                e.getMedicalData().add(cholesterol);
                e.getMedicalData().add(triglycerides);
                e.getMedicalData().add(ldl);
                e.getMedicalData().add(hdl);
                DBHandler.addExamination(p,e);
                Toast.makeText(getApplicationContext(), "Successfully!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
