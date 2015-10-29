package com.example.spoti5.ecobussing.controller.medals;

import com.example.spoti5.ecobussing.controller.database.DatabaseHolder;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabase;
import com.example.spoti5.ecobussing.controller.profile.Company;

/**
 * Created by Erik on 2015-10-18.
 * Contains all variables and data for company medals
 */
public class CompanyMedal {

    private double maxPointTotal;
    private double currentPointTotal;

    private int employeesMax;
    private int employeesCurrent;

    private Company company;


    public CompanyMedal(String company) {
        IDatabase database = DatabaseHolder.getDatabase();
        this.company = (Company) database.getCompany(company);

        pointMedal();
        employeesMedal();
    }

    //sets all values for point medal
    private void pointMedal() {
        maxPointTotal = 1000;
        currentPointTotal = company.getpointTot();
    }

    public int getPointPercentage() {
        return (int) ((currentPointTotal / maxPointTotal) * 100);
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

    //sets all values for employee medal
    private void employeesMedal() {
        employeesMax = company.getNbrEmployees();
        employeesCurrent = company.getMembers().size();
    }

    public int getEmployeesPercantage() {
        double ec = (double)employeesCurrent;
        double em = (double)employeesMax;
        return (int)((ec / em) * 100);
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
