package com.example.spoti5.ecobussing.controller.medals;

import com.example.spoti5.ecobussing.controller.database.DatabaseHolder;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabase;
import com.example.spoti5.ecobussing.model.profile.Company;

/**
 * Created by Erik on 2015-10-18.
 */
public class CompanyMedal {

    private double maxPointTotal;
    private double currentPointTotal;

    private int employeesMax;
    private int employeesCurrent;

    private Company company;


    public CompanyMedal(String company){
        IDatabase database = DatabaseHolder.getDatabase();
        this.company = (Company)database.getCompany(company);

        pointMedal();
        employeesMedal();
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

    private void employeesMedal(){
        employeesMax = company.getNbrEmployees();
        employeesCurrent = company.getMembers().size();
    }

    public int getEmployeesPercantage(){
        return (employeesCurrent/employeesMax)*100;
    }

    public int getEmployeesMax() {
        return employeesMax;
    }

    public int getEmployeesCurrent() {
        return employeesCurrent;
    }

    public void setEmployeesMax(int employeesMax) {
        this.employeesMax = employeesMax;
    }
}
