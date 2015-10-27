package com.example.spoti5.ecobussing.model.profile;

import com.example.spoti5.ecobussing.controller.SaveHandler;
import com.example.spoti5.ecobussing.controller.database.DatabaseHolder;
import com.example.spoti5.ecobussing.model.profile.interfaces.IUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by Erik on 2015-10-27.
 * If this class is edited it may cause the database to function
 * inproperly. It's a class storing data with getters and setters on the database. Contains json converters
 */
public class DatabaseCompany {

    /**
     * Following variables are stored in the database
     */
    private String name;
    private double pointCurrentMonth;
    private double pointCurrentYear;
    private double pointTot;
    private String companyInfo;
    private int nbrEmployees;
    private String modMemberJson;
    private String memberJson;
    private String co2Json;
    private String pointJson;
    private String creatorMember; //The creator of the bussiness-profile, has deletion right.

    private DeepMap<Integer, Integer, Integer, Double> co2SavedMap; //= new DeepMap<>();
    private DeepMap<Integer, Integer, Integer, Double> pointSavedMap;// = new DeepMap<>();

    /**
     * Different type of members, moderatorMembers are admins, and all "moderatorMembers" are always "members".
     */
    private List<String> moderatorMembers;    //The members of the bussiness-profile with modifying rights.
    private List<String> members;             //All members of the bussiness profile.

    private String oldMomMemberJson;
    private String oldMemberJson;

    private String oldCo2Json   = "";
    private String oldPointJson = "";


    public DatabaseCompany(){} // required empty constructor

    public DatabaseCompany(String businessName, IUser creatorMember, int nbrEmployees) {
        name = businessName;
        this.creatorMember = creatorMember.getEmail();

        moderatorMembers = new ArrayList<String>();
        members = new ArrayList<String>();

        moderatorMembers.add(creatorMember.getEmail());
        members.add(creatorMember.getEmail());

        pointCurrentMonth = 0;
        pointCurrentYear = 0;
        pointTot = 0;
        companyInfo = "";

        oldMemberJson="";
        oldMomMemberJson="";

        modMemberJson = "";
        memberJson = "";

        this.nbrEmployees = nbrEmployees;

        updateMemberJson();
        updateModMemberJson();

        co2SavedMap = new DeepMap<>();
        pointSavedMap = new DeepMap<>();

        updatePointJson();
        updateCo2Json();
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompanyInfo(String info){
        companyInfo = info;
    }

    public String getCreatorMember() {
        return creatorMember;
    }

    public int getNbrEmployees() {
        return nbrEmployees;
    }

    public double getpointTot() {
        return pointTot;
    }

    public double getPointCurrentYear() {
        return pointCurrentYear;
    }

    public double getPointCurrentMonth() {
        return  pointCurrentMonth;
    }

    public String getCompanyInfo() {
        return companyInfo;
    }

    public void setPointCurrentMonth(double pointCurrentMonth) {
        this.pointCurrentMonth = pointCurrentMonth;
    }

    public void setNbrEmployees(int nbrEmployees){
        this.nbrEmployees = nbrEmployees;
    }

    public void setPointCurrentYear(double pointCurrentYear) {
        this.pointCurrentYear = pointCurrentYear;
    }

    public void setPointTot(double pointTot) {
        this.pointTot = pointTot;
    }

    public void setMembers(List<String> members){
        this.members = members;
        updateMemberJson();
    }

    public void setModMembers(List<String> modMembers){
        this.moderatorMembers = modMembers;
        updateModMemberJson();
    }

    /**
     *
     * @param avoidDatabaseUpload, a parameter that can be both true or false, avoids member array to
     *                             be stored at the database
     * @return list of members in the company
     */
    public List<String> getMembers(boolean avoidDatabaseUpload) {
        updateMembersFromJson();
        return members;
    }

    /**
     *
     * @param avoidDatabaseUpload, a parameter that can be both true or false, avoids member array to
     *                             be stored at the database
     * @return list of moderator members in the company
     */
    public List<String> getModeratorMembers(boolean avoidDatabaseUpload) {
        updateModMembersFromJson();
        return moderatorMembers;
    }

    /**
     *
     * @param avoidDatabaseUpload a parameter that can be both true or false, avoids member array to
     *                             be stored at the database
     * @return DeepMap of the co2 the company have saved
     */
    public DeepMap<Integer, Integer, Integer, Double> getCo2SavedMap(boolean avoidDatabaseUpload) {
        updateCo2Map();
        return co2SavedMap;
    }

    public void setCo2SavedMap(DeepMap<Integer, Integer, Integer, Double> co2SavedMap) {
        this.co2SavedMap = co2SavedMap;
        updateCo2Json();
    }

    /**
     *
     * @param avoidDatabaseUpload a parameter that can be both true or false, avoids member array to
     *                             be stored at the database
     * @return DeepMap of the points the company have saved
     */
    public DeepMap<Integer, Integer, Integer, Double> getPointSavedMap(boolean avoidDatabaseUpload) {
        updatePointMap();
        return pointSavedMap;
    }

    public void setMoneySavedMap(DeepMap<Integer, Integer, Integer, Double> pointSavedMap) {
        this.pointSavedMap = pointSavedMap;
        updatePointJson();
    }

    // Everything below has to do with json

    //Only for database
    public String getModMemberJson() {
        return modMemberJson;
    }

    //Only for database
    public String getMemberJson() {
        return memberJson;
    }

    //Only for database
    public String getCo2Json(){
        return co2Json;
    }

    //Only for database
    public String getPointJson(){
        return pointJson;
    }

    // following methods parse maps and list to json

    private void updateModMemberJson(){
        modMemberJson = new Gson().toJson(moderatorMembers);
    }

    private void updateMemberJson(){
        memberJson = new Gson().toJson(members);
    }

    private void updateCo2Json(){
        Gson gson = new Gson();
        co2Json =  gson.toJson(co2SavedMap);
    }

    private void updatePointJson(){
        Gson gson = new Gson();
        pointJson =  gson.toJson(pointSavedMap);
    }

    //following method parses maps and list from json

    private void updatePointMap(){
        if(pointSavedMap == null || !oldPointJson.equals(pointJson)){
            Gson gson = new Gson();
            pointSavedMap = gson.fromJson(this.getPointJson(), DeepMap.class);
            oldPointJson = this.getPointJson();
        }
    }

    private void updateCo2Map(){
        if(co2SavedMap == null || !oldCo2Json.equals(co2Json)){
            Gson gson = new Gson();
            co2SavedMap = gson.fromJson(this.getCo2Json(), DeepMap.class);
            oldCo2Json = this.getCo2Json();
        }
    }


    private void updateMembersFromJson(){
        if(oldMemberJson == null || members == null) {
            if (!(memberJson == null)) {
                Gson gson = new Gson();
                members= gson.fromJson(memberJson, new TypeToken<List<String>>(){}.getType());
                oldMemberJson = memberJson;
            }
        }
    }

    private void updateModMembersFromJson(){
        if(oldMomMemberJson==null || moderatorMembers == null) {
            if (!(modMemberJson== null)) {
                Gson gson = new Gson();
                moderatorMembers = gson.fromJson(modMemberJson, new TypeToken<List<String>>(){}.getType());
                oldMomMemberJson = modMemberJson;
            }
        }
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", pointCurrentMonth=" +  pointCurrentMonth +
                ", pointCurrentYear=" + pointCurrentYear +
                ", pointTot=" + pointTot + ", " +
                "nbrEmployees="+ nbrEmployees +
                ", companyInfo='" + companyInfo + '\'' +
                ", creatorMember='" + creatorMember + '\'' +
                ", modMemberJson='" + modMemberJson + '\'' +
                ", memberJson='" + memberJson + '\'' +
                ", pointJson='" + pointJson + '\'' +", " +
                "co2Json='" + co2Json + '\'' +
                '}';
    }
}
