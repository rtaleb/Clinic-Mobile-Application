package com.mc.info.lumc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MedicalExaminationForm extends AppCompatActivity {

    private Bundle extras;
    private Patient p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_examination_form);

        extras = getIntent().getExtras();
        p = (Patient) extras.getSerializable("patient");

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Exams, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            boolean isFirstSelection = true;
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
               if(isFirstSelection == true)
                    isFirstSelection = false;
                else{
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    Intent intent;
                    switch(selectedItem){
                        case "Cholesterol":
                            intent = new Intent(getApplicationContext(), CholesterolForm.class);
                            intent.putExtra("patient",p);
                            startActivity(intent);
                            break;
                        case "Hematology":
                            intent = new Intent(getApplicationContext(), HematologyForm.class);
                            intent.putExtra("patient",p);
                            startActivity(intent);
                            break;
                        case "Vitamin A":
                            intent = new Intent(getApplicationContext(), VitaminAForm.class);
                            intent.putExtra("patient",p);
                            startActivity(intent);
                            break;
                        case "Vitamin B":
                            intent = new Intent(getApplicationContext(), VitaminBForm.class);
                            intent.putExtra("patient",p);
                            startActivity(intent);
                            break;
                        case "Vitamin C":
                            intent = new Intent(getApplicationContext(), VitaminCForm.class);
                            intent.putExtra("patient",p);
                            startActivity(intent);
                            break;
                        case "Vitamin D":
                            intent = new Intent(getApplicationContext(), VitaminDForm.class);
                            intent.putExtra("patient",p);
                            startActivity(intent);
                            break;
                    }

                }
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

    }
}
