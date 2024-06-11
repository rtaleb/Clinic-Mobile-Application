package com.mc.info.lumc;

import android.app.Application;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by BurgerMan on 12/9/2016.
 */

public class DBHandler extends Application{

    private static final String DATABASE_NAME="MedicalCenter.db";
    public static final String TABLE_PATIENT ="patient";
    public static final String TABLE_DOCTOR ="doctor";
    public static  final String TABLE_DOCTOR_CONSULTS = "doctorConsults";
    public static  final String TABLE_PATIENT_CONSULTS = "patientConsults";
    public static  final String TABLE_CONSULTS = "consults";
    public static final String TABLE_MEDICAL_EXAMINATION ="medicalExamination";
    public static final String TABLE_EXAMINATION ="examination";
    public static final String TABLE_MEDICAL_REPORT ="medicalReport";
    public static final String TABLE_MEDICATION ="medication";
    public static final String TABLE_PRECAUTION ="precaution";
    private static final String TABLE_PRESCEXAM = "prescExam" ;
    public static final String TABLE_MEDICAL_DATA ="medicalData";
    public static final String TABLE_HEALTH_STATE ="healthState";
    public static final String COULMN_DATE ="date";
    public static final String COLUMN_ID="id";
    public static final String COLUMN_FIRST_NAME ="firstname";
    public static final String COLUMN_LAST_NAME ="lastname";
    public static final String COLUMN_CITY ="city";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_STREET ="street";
    public static final String COLUMN_BUILDING ="building";
    public static final String COLUMN_PHONE ="phone";
    public static final String COLUMN_EMAIL ="email";
    public static final String COLUMN_SPECIALTY ="specialty";
    public static final String COLUMN_EXPERIENCE_YEARS ="experienceYears";
    public static final String COLUMN_MEDICATION ="medication";
    public static final String COLUMN_MEDICAL_REPORTS ="medicalReports";
    public static final String COLUMN_PID_FK = "pid";
    public static final String COLUMN_DID_FK ="did";
    public static final String COLUMN_DATEOFCONSULTATION ="dateOfConsultation";
    public static final String COLUMN_MEDICINE_NAME = "name";


    public FirebaseDatabase database;

    private boolean dataReady =false;
    private boolean loggedIn=false;
    private static DBHandler singleton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private Patient patientMe;
    private Doctor doctorMe;
    private LoginType loginType=LoginType.PATIENT;
    private DataSnapshot dataSnapshot;
    private Patient quickFixPatent;
    private Examination quickFixExamination;

    public Patient getQuickFixPatent() {
        return quickFixPatent;
    }

    public void setQuickFixPatent(Patient quickFixPatent) {
        this.quickFixPatent = quickFixPatent;
    }

    public Examination getQuickFixExamination() {
        return quickFixExamination;
    }

    public void setQuickFixExamination(Examination quickFixExamination) {
        this.quickFixExamination = quickFixExamination;
    }

    public List<Examination> getMedicalExaminations(String pid) {
        List<Examination> exams = new ArrayList<>();
        for(DataSnapshot d: dataSnapshot.child(TABLE_EXAMINATION+"/"+pid).getChildren())
            exams.add(d.getValue(Examination.class));
        return exams;
    }


    public enum LoginType {
        DOCTOR,PATIENT
    }

    public Person getActiveUser(){
        switch (loginType){
            case DOCTOR:
                return doctorMe;
            case PATIENT:
                return patientMe;
        }
        return null;
    }

    public void setActiveUser(Person p){
        switch (loginType){
            case DOCTOR:
                doctorMe = (Doctor) p;
            case PATIENT:
                patientMe = (Patient) p;
        }
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public FirebaseUser getUser() {
        return mAuth.getCurrentUser();
    }

    public void setUser(FirebaseUser user) {
        /*database.getReference().child(TABLE_PATIENT).child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Patient person= dataSnapshot.getValue(Patient.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        this.user=user;
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public void setmAuth(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    public FirebaseAuth.AuthStateListener getmAuthListener() {
        return mAuthListener;
    }

    public void setmAuthListener(FirebaseAuth.AuthStateListener mAuthListener) {
        this.mAuthListener = mAuthListener;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton =this;
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        checkLogin();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                checkLogin();
                // ...
            }
        };
        mAuth.addAuthStateListener(mAuthListener);
        DatabaseReference reference=database.getReference();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DBHandler.this.dataSnapshot=dataSnapshot;
                /*patients=new ArrayList<Patient>();
                for (DataSnapshot patient : dataSnapshot.child(TABLE_PATIENT).getChildren()) {
                    Patient p= patient.getValue(Patient.class);
                    p.setId(patient.getKey());
                    patients.add(p);
                }

                doctors=new ArrayList<Doctor>();
                for (DataSnapshot doctor : dataSnapshot.child(TABLE_DOCTOR).getChildren()) {
                    Doctor d= doctor.getValue(Doctor.class);
                    d.setId(doctor.getKey());
                    doctors.add(d);
                }*/
                dataReady =true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void checkLogin() {
        loggedIn=false;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            DBHandler db = DBHandler.getInstance();
            db.setUser(user);
            db.database.getReference(TABLE_PATIENT).child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    DBHandler.getInstance().setActiveUser(dataSnapshot.getValue(Patient.class));
                    loggedIn=true;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    loggedIn=true;
                }
            });

            //
            setLoginType(LoginType.PATIENT);
        } else {
            // User is signed out
            loggedIn=true;
        }
    }

    public static DBHandler getInstance()
    {
        return singleton;
    }
    public boolean isDataReady()
    {
        return dataReady;
    }

    public List<Doctor> getDoctors() {
        List<Doctor> doctors=new ArrayList<Doctor>();
        for (DataSnapshot doctor : dataSnapshot.child(TABLE_DOCTOR).getChildren()) {
            Doctor d= doctor.getValue(Doctor.class);
            d.setId(doctor.getKey());
            doctors.add(d);}
            return doctors;
    }

    public List<MedicalData> getMedicalResult(final String pid,final String eid) {
        List<MedicalData> medicalData=new ArrayList<>();
        for(DataSnapshot d: dataSnapshot.child(TABLE_EXAMINATION+"/"+pid+"/"+eid+"/medicalData").getChildren())
            medicalData.add(d.getValue(MedicalData.class));
        return medicalData;
    }

    public void addPatient(Patient p){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(TABLE_PATIENT);
        String key = myRef.push().getKey();
        p.setId(key);
        myRef.child(key).setValue(p);

    }


    public void addDoctor(Doctor d){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(TABLE_DOCTOR);
        String key = myRef.push().getKey();
        d.setId(key);
        myRef.child(key).setValue(d);

    }

    public void addConsult (Consults c){

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(TABLE_CONSULTS);
        String key = myRef.push().getKey();
        c.setCid(key);
        myRef.child(key).setValue(c);
        FirebaseDatabase.getInstance().getReference(TABLE_DOCTOR_CONSULTS+"/"+c.getDid()+"/"+c.getCid()).setValue(c);
        FirebaseDatabase.getInstance().getReference(TABLE_PATIENT_CONSULTS+"/"+c.getPid()+"/"+c.getCid()).setValue(c);

    }

    public void signUp(Person person){

    }

    public List<Patient> getPatients() {
        List<Patient> patients=new ArrayList<Patient>();
        for (DataSnapshot patient : dataSnapshot.child(TABLE_PATIENT).getChildren()) {
            Patient p= patient.getValue(Patient.class);
            p.setId(patient.getKey());
            patients.add(p);
        }
        return patients;
    }

    public List<Patient> getMyPatients(Doctor d) {
        List<Patient> myPatients = new ArrayList<>();
        final String did = d.getId();

                    for (DataSnapshot consult :  dataSnapshot.child(TABLE_CONSULTS).getChildren()) {
                        Consults mConsult = consult.getValue(Consults.class);
                        if ((mConsult).getDid().equals(did)) {
                            Patient p = getPatientById(mConsult.getPid());
                            if (myPatients.contains(p))
                                continue;
                            else
                                myPatients.add(p);
                        }
                    }
        return myPatients;
        }

    public List<Doctor> getMyDoctors( Patient p) {
        List<Doctor> myDoctors = new ArrayList<Doctor>();
        final String pid = p.getId();

        for (DataSnapshot consult :  dataSnapshot.child(TABLE_CONSULTS).getChildren()) {
            Consults mConsult = consult.getValue(Consults.class);
            if ((mConsult).getPid().equals(pid)) {
                Doctor d = getDoctorById(mConsult.getDid());
                if (myDoctors.contains(d))
                    continue;
                else
                    myDoctors.add(d);
            }
        }
        return myDoctors;
    }

//hayde m3sh ra7 n3uza sta3malna badela get my reports + get report medication btseer ashal
   /* public List<Medication> getPatientMedicines(Patient p){
        // return p.getPatientMedicines();
        final String pid = p.getId();
        final List<Medication> patientMedicines = new ArrayList<>();
        final DatabaseReference patientRef = FirebaseDatabase.getInstance().getReference(TABLE_PATIENT);
        //patientRef.orderByChild(COLUMN_ID).equalTo(pid).
        final Semaphore semaphore = new Semaphore(0);
        patientRef.orderByChild(COLUMN_ID).equalTo(pid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot medication : dataSnapshot.child(TABLE_MEDICATION).getChildren()){
                    Medication m = medication.getValue(Medication.class);
                    patientMedicines.add(m);
                }
                semaphore.release();
            }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  patientMedicines;


}*/

    public Patient getPatientById(final String id){
       return dataSnapshot.child(TABLE_PATIENT+"/"+id).getValue(Patient.class);
    }

    public Doctor getDoctorById(String id) {
        return dataSnapshot.child(TABLE_DOCTOR+"/"+id).getValue(Doctor.class);
    }


    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public static void addMedicalReport(MedicalReport mr , Patient p){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(TABLE_MEDICAL_REPORT).child(p.getId());
        String key = myRef.push().getKey();
        mr.setId(key);
        myRef.child(key).setValue(mr);
    }

    public static void addMedication(Medication m , MedicalReport mr){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(TABLE_MEDICATION).child(mr.getId());
        String key = myRef.push().getKey();
        m.setId(key);
        myRef.child(key).setValue(m);
    }

    public static void addPrecaution(Precaution p , MedicalReport mr){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(TABLE_PRECAUTION).child(mr.getId());
        String key = myRef.push().getKey();
        p.setId(key);
        myRef.child(key).setValue(p);
    }

    public static void addPrescExam(PrescExam pe, MedicalReport mr) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(TABLE_PRESCEXAM).child(mr.getId());
        String key = myRef.push().getKey();
        pe.setId(key);
        myRef.child(key).setValue(pe);
    }

    public List<MedicalReport> getMyMedicalReport(Patient p) {
        List<MedicalReport> myMedicalReports = new ArrayList<>();
        for (DataSnapshot mr : dataSnapshot.child(TABLE_MEDICAL_REPORT + "/" + p.getId()).getChildren()) {
            MedicalReport mMr = mr.getValue(MedicalReport.class);
            myMedicalReports.add(mMr);
        }
        return myMedicalReports;
    }


    public List<PrescExam> getMedicalReportExams(MedicalReport mr) {
        List<PrescExam> pe = new ArrayList<>();
        for (DataSnapshot pre1 : dataSnapshot.child(TABLE_PRESCEXAM + "/" + mr.getId()).getChildren()) {
            PrescExam pre = pre1.getValue(PrescExam.class);
            pe.add(pre);
        }
        return pe;
    }

    public List<Medication> getMedicalReportMeds(MedicalReport mr) {
        List<Medication> m = new ArrayList<>();
        for (DataSnapshot m1 : dataSnapshot.child(TABLE_MEDICATION + "/" + mr.getId()).getChildren()) {
            Medication m2 = m1.getValue(Medication.class);
            m.add(m2);
        }
        return m;
    }

    public List<Precaution> getMedicalReportPrecs(MedicalReport mr) {
        List<Precaution> p = new ArrayList<>();
        for (DataSnapshot p1 : dataSnapshot.child(TABLE_PRECAUTION + "/" + mr.getId()).getChildren()) {
            Precaution p2 = p1.getValue(Precaution.class);
            p.add(p2);
        }
        return p;
    }

    public static void addExamination(Patient p, Examination e){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(TABLE_EXAMINATION).child(p.getId());
        String key = myRef.push().getKey();
        e.setId(key);
        myRef.child(key).setValue(e);
    }

    public List<Examination> getExaminations(Patient p) {
        List<Examination> Examination=new ArrayList<Examination>();
        for (DataSnapshot exam : dataSnapshot.child(TABLE_EXAMINATION+"/"+p.getId()).getChildren()) {
            Examination e= exam.getValue(Examination.class);
            e.setId(exam.getKey());
            Examination.add(e);
        }
        return Examination;
    }

    public static void addMedicalData(Examination e, MedicalData m){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(TABLE_EXAMINATION).child(e.getId());

        String key = myRef.push().getKey();
        myRef.child(key).setValue(m);
    }

    public static void addHealthState(Patient p, String s){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(TABLE_HEALTH_STATE).child(p.getId());
        myRef.removeValue();
        myRef.setValue(s);
    }

    public Object getHealthState (Patient p){
        return dataSnapshot.child(TABLE_HEALTH_STATE + "/" + p.getId()).getValue();
    }

    public static void addBloodGroup(Patient p,String bg){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(TABLE_PATIENT).child(p.getId()).child("bloodGroup");
        myRef.removeValue();
        myRef.setValue(bg);
    }

    public static void addWeight(Patient p,String w){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(TABLE_PATIENT).child(p.getId()).child("weight");
        myRef.removeValue();
        myRef.setValue(w);
    }

    public static void addHeight(Patient p,String h){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(TABLE_PATIENT).child(p.getId()).child("height");
        myRef.removeValue();
        myRef.setValue(h);
    }

    public Object getHeight(Patient p){
        return dataSnapshot.child(TABLE_PATIENT + "/" + p.getId()).child("height").getValue();
    }

    public Object getWeight(Patient p){
        return dataSnapshot.child(TABLE_PATIENT + "/" + p.getId()).child("weight").getValue();
    }

    public Object getBloodGroup(Patient p){
        return dataSnapshot.child(TABLE_PATIENT + "/" + p.getId()).child("bloodGroup").getValue();
    }
}