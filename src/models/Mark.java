package models;

import java.io.Serializable;

public class Mark implements Serializable {

    private double firstAttestation; 
    private double secondAttestation;
    private double finalExam; 

    public Mark(double firstAttestation, double secondAttestation, double finalExam) {
        setFirstAttestation(firstAttestation);
        setSecondAttestation(secondAttestation);
        setFinalExam(finalExam);
    }

    public double getFirstAttestation() { 
        return firstAttestation; 
    }

    public double getSecondAttestation() { 
        return secondAttestation; 
    }

    public double getFinalExam() { 
        return finalExam; 
    }

    public void setFirstAttestation(double firstAttestation) {
        if (firstAttestation < 0 || firstAttestation > 30) {
            throw new IllegalArgumentException("First attestation must be between 0 and 30");
        }
        this.firstAttestation = firstAttestation;
    }

    public void setSecondAttestation(double secondAttestation) {
        if (secondAttestation < 0 || secondAttestation > 30) {
            throw new IllegalArgumentException("Second attestation must be between 0 and 30");
        }
        this.secondAttestation = secondAttestation;
    }

    public void setFinalExam(double finalExam) {
        if (finalExam < 0 || finalExam > 40) {
            throw new IllegalArgumentException("Final exam must be between 0 and 40");
        }
        this.finalExam = finalExam;
    }

    public double getTotal() {
        return firstAttestation + secondAttestation + finalExam;
    }

    public String getGrade() {
        double total = getTotal();

        if (total >= 90) return "A";
        else if (total >= 80) return "B";
        else if (total >= 70) return "C";
        else if (total >= 60) return "D";
        else return "F";
    }

    @Override
    public String toString() {
        return "Mark: " + getTotal() + " (" + getGrade() + ")";
    }
}