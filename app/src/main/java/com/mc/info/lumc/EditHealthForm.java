package com.mc.info.lumc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditHealthForm extends AppCompatActivity {

    private Patient p;
    private TextView txt;
    private Spinner s;
    private String state,weight,height,valueBG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_health_form);

        
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            p = (Patient) extras.getSerializable("patient");

            txt = (TextView) findViewById(R.id.activity_health_state_form_height_value);
            if (!p.getHeight().equals("Not provided"))
                txt.setText((String) DBHandler.getInstance().getHeight(p));

            txt = (TextView) findViewById(R.id.activity_health_state_form_weight_value);
            if (!p.getHeight().equals("Not provided"))
                txt.setText((String) DBHandler.getInstance().getWeight(p));

            txt = (TextView) findViewById(R.id.activity_health_state_form_health_state_value);
            if(!p.getHeight().equals("No state"))
                txt.setText((String) DBHandler.getInstance().getHealthState(p));


            Spinner spinner = (Spinner) findViewById(R.id.spinner2);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.BloodGroups, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem = parent.getItemAtPosition(position).toString();
                    switch (selectedItem) {
                        case "BloodGroup":
                            valueBG = "Not provided";
                            break;
                        case "A+":
                            valueBG = "A+";
                            break;
                        case "A-":
                            valueBG = "A-";
                            break;
                        case "B+":
                            valueBG = "B+";
                            break;
                        case "B-":
                            valueBG = "A-";
                            break;
                        case "AB+":
                            valueBG = "AB+";
                            break;
                        case "AB-":
                            valueBG = "AB-";
                            break;
                        case "O+":
                            valueBG = "O+";
                            break;
                        case "O-":
                            valueBG = "O-";
                            break;
                    }
                }

                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            Button save = (Button) findViewById(R.id.btnEditHealthStateSave);


            save.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    txt = (TextView) findViewById(R.id.activity_health_state_form_health_state_value);
                    state = txt.getText().toString();
                    txt = (TextView) findViewById(R.id.activity_health_state_form_weight_value);
                    weight = txt.getText().toString();
                    txt = (TextView) findViewById(R.id.activity_health_state_form_height_value);
                    height = txt.getText().toString();

                    DBHandler.addHealthState(p, state);
                    if(!valueBG.equals("Not provided"))
                        DBHandler.addBloodGroup(p,valueBG);
                    DBHandler.addHeight(p,height);
                    DBHandler.addWeight(p,weight);
                    Toast.makeText(getApplicationContext(), "Successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent i = new Intent(EditHealthForm.this, HealthStateForm.class);
                    i.putExtra("patient", p);
                    startActivity(i);

                }
            });
        }
    }
}
