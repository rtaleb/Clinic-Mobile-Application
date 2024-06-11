package com.mc.info.lumc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Examination implements Serializable{
    private String id,examName;
    private String date;
    private examType type;
    private List<MedicalData> medicalData;

    public List<MedicalData> getMedicalData() {
        return medicalData;
    }

    public void setMedicalData(List<MedicalData> medicalData) {
        this.medicalData = medicalData;
    }

    public examType getType() {
        return type;
    }

    public void setType(examType type) {
        this.type = type;
    }

    public Examination() {
        this.id = "";
        this.examName = "";
        this.date ="";
        this.type = examType.CHOLESTEROL;
        medicalData=new ArrayList<>();
    }

    public Examination(String id, String examName, String date, examType type) {
        this.id = id;
        this.examName = examName;
        this.date = date;
        this.type = type;
        medicalData=new ArrayList<>();
    }


    public enum examType{
        HEMATOLOGY,VITAMIN_A,VITAMIN_B,VITAMIN_C,VITAMIN_D,CHOLESTEROL;

    }

    public String getExamName(){
        switch (type){
            case HEMATOLOGY: return "hematology";
            case VITAMIN_A: return "vitamin A";
            case VITAMIN_B: return "vitamin B";
            case VITAMIN_C: return "vitamin  C";
            case VITAMIN_D: return "vitamin D";
            case CHOLESTEROL: return "cholesterol";
        }
        return "";
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}