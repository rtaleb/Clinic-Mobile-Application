package com.mc.info.lumc;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by hphh4 on 1/6/2017.
 */

public class CreateMedicalReport extends AppCompatActivity implements Serializable {

    private Button btnAdd,
                   btnSave;
    private Spinner spTypesList,
                    spExamType;
    private LinearLayout llDates;
    private EditText txtPrescribed, txtNotes, txtTo, txtFrom;
    private ListView lvPrescriptions;
    private ArrayAdapter<String> adapter;
    private Bundle extras;
    private MedicalReport mr;
    private Patient p;
    private Calendar myCalendar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_medical_report);
        spTypesList = (Spinner) findViewById(R.id.activity_create_prescription_typeList);
        spExamType = (Spinner) findViewById(R.id.activity_create_prescription_examList);
        llDates = (LinearLayout) findViewById(R.id.activity_create_prescription_dates);
        txtPrescribed = (EditText) findViewById(R.id.activity_create_prescription_prescribed);
        txtNotes = (EditText) findViewById(R.id.activity_create_prescription_notes);

        myCalendar = Calendar.getInstance();
        txtFrom = (EditText) findViewById(R.id.activity_create_prescription_from);

        final DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MMM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

                txtFrom.setText(sdf.format(myCalendar.getTime()));

            }



        };

        txtFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CreateMedicalReport.this, datePickerListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        txtTo = (EditText) findViewById(R.id.activity_create_prescription_to);

        final DatePickerDialog.OnDateSetListener datePickerListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MMM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

                txtTo.setText(sdf.format(myCalendar.getTime()));
            }
        };

        txtTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CreateMedicalReport.this, datePickerListener1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnSave = (Button) findViewById(R.id.activity_create_prescription_saveItem);
        lvPrescriptions = (ListView) findViewById(R.id.activity_create_prescription_listOfPrescriptions);
        adapter = new ArrayAdapter<>(this,R.layout.prescription_item,R.id.prescription_item_prescribe);
        lvPrescriptions.setAdapter(adapter);
        extras = getIntent().getExtras();
        if(extras!=null){
            mr = (MedicalReport) extras.getSerializable("take");
            p = (Patient) extras.getSerializable("patient");
        }
        spTypesList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spTypesList.getSelectedItem().toString().equals("Examination")){
                    spExamType.setVisibility(View.VISIBLE);
                    txtPrescribed.setVisibility(View.INVISIBLE);
                    llDates.setVisibility(View.INVISIBLE);}
                else if(spTypesList.getSelectedItem().toString().equals("Medication")){
                    spExamType.setVisibility(View.INVISIBLE);
                    txtPrescribed.setVisibility(View.VISIBLE);
                    llDates.setVisibility(View.VISIBLE);
                }
                else{
                    spExamType.setVisibility(View.INVISIBLE);
                    txtPrescribed.setVisibility(View.VISIBLE);
                    llDates.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (spTypesList.getSelectedItem().toString().equals("Examination")){
            spExamType.setVisibility(View.VISIBLE);
            txtPrescribed.setVisibility(View.INVISIBLE);
            llDates.setVisibility(View.INVISIBLE);}
        else if(spTypesList.getSelectedItem().toString().equals("Medication")){
            spExamType.setVisibility(View.INVISIBLE);
            txtPrescribed.setVisibility(View.VISIBLE);
            llDates.setVisibility(View.VISIBLE);
        }
        else{
            spExamType.setVisibility(View.INVISIBLE);
            txtPrescribed.setVisibility(View.VISIBLE);
            llDates.setVisibility(View.INVISIBLE);
        }
    }

    public void insertToPrescription(View view) {
        if(spTypesList.getSelectedItem().equals("Examination")){
            adapter.add(spTypesList.getSelectedItem().toString() + ": " + spExamType.getSelectedItem().toString());
            Toast.makeText(this, spExamType.getSelectedItem().toString() + " added",Toast.LENGTH_LONG).show();}
        else if(spTypesList.getSelectedItem().equals("Medication")){
            if (txtPrescribed.getText().toString().equals(""))
                Toast.makeText(this, "Enter a " + spTypesList.getSelectedItem().toString() + " first.",Toast.LENGTH_LONG).show();
            else{
                adapter.add(spTypesList.getSelectedItem().toString() + ": " + txtPrescribed.getText().toString() + "\n"
                        + "From " + txtFrom.getText().toString() + " To " + txtTo.getText().toString());
                txtFrom.setText("");
                txtTo.setText("");
                Toast.makeText(this,txtPrescribed.getText().toString() + " added",Toast.LENGTH_LONG).show();}
        }
        else {
            if (txtPrescribed.getText().toString().equals(""))
                Toast.makeText(this, "Enter a " + spTypesList.getSelectedItem().toString() + " first.", Toast.LENGTH_LONG).show();
            else {
                adapter.add(spTypesList.getSelectedItem().toString() + ": " + txtPrescribed.getText().toString());
                Toast.makeText(this, txtPrescribed.getText().toString() + " added", Toast.LENGTH_LONG).show();}
        }
        txtPrescribed.setText("");
    }


    public void removePrescription(View v)
    {
        final int position = lvPrescriptions.getPositionForView((View) v.getParent());
        adapter.remove(adapter.getItem(position));
        adapter.notifyDataSetChanged();
    }

    public void insertToDatabase(View view) {
        String s;
        if (txtNotes.getText().toString().equals("") && adapter.isEmpty())
            Toast.makeText(this,"Nothing to save",Toast.LENGTH_SHORT).show();
        else {
            mr = new MedicalReport();
            if (txtNotes.getText().toString().equals(""))
                mr.setNotes("No notes given on this report");
            else
                mr.setNotes(txtNotes.getText().toString());
            DBHandler.addMedicalReport(mr, p);
            for (int i = 0; i < adapter.getCount(); i++) {
                s = adapter.getItem(i).substring(adapter.getItem(i).indexOf(":") + 2);
                if (adapter.getItem(i).contains("Medication")) {
                    s = adapter.getItem(i).substring(adapter.getItem(i).indexOf(":") + 2, adapter.getItem(i).indexOf("\n"));
                    String from = adapter.getItem(i).substring(adapter.getItem(i).indexOf("From") + 5, adapter.getItem(i).indexOf("To") - 1);
                    String to = adapter.getItem(i).substring(adapter.getItem(i).indexOf("To") + 3 );
                    Medication m = new Medication("", s, from, to);
                    DBHandler.addMedication(m, mr);
                } else if (adapter.getItem(i).contains("Precaution")) {
                    Precaution pr = new Precaution("", s);
                    DBHandler.addPrecaution(pr, mr);
                } else if (adapter.getItem(i).contains("Examination")) {
                    PrescExam pe = new PrescExam("", s);
                    DBHandler.addPrescExam(pe, mr);
                }
            }
            Toast.makeText(this, "added to Database", Toast.LENGTH_SHORT).show();
            adapter.clear();
            Intent i = new Intent(this, ListReports.class);
            i.putExtra("take", p);
            startActivity(i);
            finish();
        }
    }
}

