package com.mc.info.lumc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HealthStateForm extends AppCompatActivity {

    private Patient p;
    private TextView txt;
    private String bg,w,h,hs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_state_form);

        Bundle extras = getIntent().getExtras();
        if (extras != null){

            p = (Patient) extras.getSerializable("patient");

            txt = (TextView) findViewById(R.id.activity_health_state_form_name);
            txt.setText(p.getFirstName()+ " " + p.getLastName());

            txt = (TextView) findViewById(R.id.activity_health_state_form_glood_group);
            bg = (String) DBHandler.getInstance().getBloodGroup(p);
            if(bg == null)
                txt.setText("Not provided");
            else
                txt.setText(bg);

            txt = (TextView) findViewById(R.id.activity_health_state_form_height);
            h = (String) DBHandler.getInstance().getHeight(p);
            if(h == null)
                txt.setText("Not provided");
            else
                txt.setText(h);

            txt = (TextView) findViewById(R.id.activity_health_state_form_weight);
            w = (String) DBHandler.getInstance().getWeight(p);
            if(w == null)
                txt.setText("Not provided");
            else
                txt.setText(w);

            txt = (TextView) findViewById(R.id.activity_health_state_form_state);
            hs = (String) DBHandler.getInstance().getHealthState(p);
            if(hs == null)
                txt.setText("No state");
            else
                txt.setText(hs);

        }
    }

    public void goToEditHealthForm(View view){
        Intent intent = new Intent(this,EditHealthForm.class);
        intent.putExtra("patient",p);
        startActivity(intent);
        finish();
    }

}
