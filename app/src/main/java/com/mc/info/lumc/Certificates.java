package com.mc.info.lumc;

/**
 * Created by TOSHIBA on 07-Jan-17.
 */

public class Certificates {


    private String certName;
    private int certYear;

    public Certificates() {
    }

    public Certificates(String certName, int certYear) {
        this.certName = certName;
        this.certYear = certYear;
    }

    public String getCertName() {
        return certName;
    }

    public void setCertName(String certName) {
        this.certName = certName;
    }

    public int getCertYear() {
        return certYear;
    }

    public void setCertYear(int certYear) {
        this.certYear = certYear;
    }

}
