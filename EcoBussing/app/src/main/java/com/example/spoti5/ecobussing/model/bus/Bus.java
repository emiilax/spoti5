package com.example.spoti5.ecobussing.model.bus;

/**
 * Created by emilaxelsson on 28/09/15.
 *
 * Class used to create busses.
 */
public class Bus {

    /** The bus dwg-number */
    private String dwg;

    /** The bus vin-number */
    private String VIN;

    /** The bus registration-number */
    private String regNr;

    /** The bus mac-adress */
    private String macAdress;

    public Bus(String dwg, String VIN, String regNr, String macAdress){

        this.dwg = dwg;
        this.VIN = VIN;
        this.regNr = regNr;
        this.macAdress = macAdress;

    }

    // Getters
    public String getDwg() { return dwg; }
    public String getVIN() { return VIN; }
    public String getRegNr() { return regNr; }
    public String getMacAdress() { return macAdress; }

    // Setters
    public void setDwg(String dwg) { this.dwg = dwg; }
    public void setVIN(String VIN) { this.VIN = VIN; }
    public void setRegNr(String regNr) { this.regNr = regNr; }
    public void setMacAdress(String macAdress) { this.macAdress = macAdress; }
}
