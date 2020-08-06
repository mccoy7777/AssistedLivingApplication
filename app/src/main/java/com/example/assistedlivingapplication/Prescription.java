package com.example.assistedlivingapplication;

public class Prescription {

    private String prescriptionName;
    private int dosage;

    public Prescription(){

    }//default constructor to store to firebase

    public Prescription(String prescriptionName, int dosage) {
        this.prescriptionName = prescriptionName;
        this.dosage = dosage;
    }//end of constructor

    public String getPrescriptionName() {
        return prescriptionName;
    }//end of getter

    public int getDosage() {
        return dosage;
    }//end of getter

}//end of Prescription class
