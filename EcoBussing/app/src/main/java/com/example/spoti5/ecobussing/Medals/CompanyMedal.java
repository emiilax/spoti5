package com.example.spoti5.ecobussing.Medals;

import com.example.spoti5.ecobussing.Database.DatabaseHolder;
import com.example.spoti5.ecobussing.Database.IDatabase;
import com.example.spoti5.ecobussing.Profiles.Company;
import com.example.spoti5.ecobussing.Profiles.IProfile;

/**
 * Created by Erik on 2015-10-18.
 */
public class CompanyMedal {

    private double maxPointTotal;
    private double currentPointTotal;

    private int employeesMax;
    private int employeesMin;

    private Company company;
    private IDatabase database;

    public CompanyMedal(String company){
        database = DatabaseHolder.getDatabase();
        this.company = (Company)database.getCompany(company);

        pointMedal();
    }

    private void pointMedal() {
        maxPointTotal = 1000;
        currentPointTotal = company.getpointTot();
    }

    public int getPointPercentage(){
        return (int)(currentPointTotal/maxPointTotal)*100;
    }

    public double getCurrentPointTotal() {
        return currentPointTotal;
    }

    public double getMaxPointTotal() {
        return maxPointTotal;
    }

    public void setMaxPointTotal(double maxPointTotal) {
        this.maxPointTotal = maxPointTotal;
    }

    public int getEmployeesMax() {
        return employeesMax;
    }

    public int getEmployeesMin() {
        return employeesMin;
    }

    public void setEmployeesMax(int employeesMax) {
        this.employeesMax = employeesMax;
    }
}
