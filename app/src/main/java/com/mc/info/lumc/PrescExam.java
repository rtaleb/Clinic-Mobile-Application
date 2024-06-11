package com.mc.info.lumc;

import java.io.Serializable;

/**
 * Created by hphh4 on 2/8/2017.
 */
public class PrescExam implements Serializable{

    private String id;
    private String prescExamName;

    public PrescExam() {
        this.id = "";
        this.prescExamName = "";
    }

    public PrescExam(String id, String prescExamName) {
        this.id = id;
        this.prescExamName = prescExamName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrescExamName() {
        return prescExamName;
    }

    public void setPrescExamName(String prescExamName) {
        this.prescExamName = prescExamName;
    }
}
