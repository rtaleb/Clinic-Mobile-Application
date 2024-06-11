package com.mc.info.lumc;

import android.widget.SimpleAdapter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MedicalReport implements Serializable {

    private String id,
                   notes;
    private Date reportDate;

    public MedicalReport() {
        this.id = "";
        this.notes = "";
        this.reportDate = new Date();
    }

    public MedicalReport(String id,String notes,Date reportDate) {
        this.id = id;
        this.notes = notes;
        this.reportDate = (Date) reportDate.clone();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getReportDate() {
        return (Date) reportDate.clone();
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = (Date) reportDate.clone();
    }

}
