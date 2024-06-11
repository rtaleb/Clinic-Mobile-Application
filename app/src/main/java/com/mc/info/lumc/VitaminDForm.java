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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class VitaminDForm extends AppCompatActivity {

    private Bundle extras;
    private Patient p;
    private Calendar myCalendar;

    Button save;
    EditText value,date;
    TextView examName,textName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitamin_d_form);

        extras = getIntent().getExtras();
        p = (Patient) extras.getSerializable("patient");

        myCalendar = Calendar.getInstance();
        save = (Button) findViewById(R.id.vitamin_d_form_vitamin_d_btnInsert);
        value = (EditText) findViewById(R.id.vitamin_d_form_vitamin_d_value) ;
        examName = (TextView) findViewById(R.id.txtVitaminDTitle);
        textName = (TextView) findViewById(R.id.vitamin_d_form_vitamin_d_name);
        date = (EditText) findViewById(R.id.vitamin_d_form_vitamin_d_date) ;

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
                new DatePickerDialog(VitaminDForm.this, datePickerListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Examination e;
                if(value.equals("") || date.equals(""))
                    Toast.makeText(getApplicationContext(), "Fill required fields!", Toast.LENGTH_SHORT).show();
                else{
                    MedicalData data = new MedicalData(textName.getText().toString(),value.getText().toString());
                    e = new Examination(null,examName.getText().toString(),date.getText().toString(), Examination.examType.VITAMIN_D);
                    e.getMedicalData().add(data);
                    DBHandler.addExamination(p,e);
                    Toast.makeText(getApplicationContext(), "Successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
