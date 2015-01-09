/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashtable;

/**
 *
 * @author Joon
 */
public class Record {

    private String name;
    private String idNumber;
    private int rank;
    private double gpa;
    private String major;

    public Record(String name, String idNumber, int rank, double gpa, String major) {
        this.name = name;
        this.idNumber = idNumber;
        this.rank = rank;
        this.gpa = gpa;
        this.major = major;
    }
    
    public String toString() {
        String recordString = "Name:    " + name + "\n";
        recordString += "ID:        " + idNumber + "\n";
        recordString += "Rank:      " + rank + "\n";
        recordString += "GPA:       " + gpa + "\n";
        recordString += "Major:     " + major + "\n";
        return recordString;
    }

}
