package com.mc.info.lumc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Consults implements Serializable {
    private String cid,
                   pid,
                   did;
    private Date dateOfConsultation;
    private List<MedicalReport> medicalReports;

    public Consults() {
        this.cid = "";
        this.did = "";
        this.pid = "";
        this.dateOfConsultation = new Date();
        this.medicalReports = new ArrayList<>();
    }

    public Consults(String cid, String did, String pid, Date dateOfConsultation) {
        this.cid = cid;
        this.did = did;
        this.pid = pid;
        this.dateOfConsultation = (Date) dateOfConsultation.clone();
        this.medicalReports = new ArrayList<>();
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String id) {
        this.cid = cid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getPid() {
        return pid;
    }

    public String getDid() {
        return did;
    }

    public List<MedicalReport> getMedicalReports() {
        return new ArrayList<>(medicalReports);
    }

    public void setMedicalReports(List<MedicalReport> medicalReports) {
        this.medicalReports = new ArrayList<>(medicalReports);
    }

    public Date getDateOfConsultation() {
        return (Date) dateOfConsultation.clone();
    }

    public void setDateOfConsultation(Date dateOfConsultation) {
        this.dateOfConsultation = (Date) dateOfConsultation.clone();
    }
}
