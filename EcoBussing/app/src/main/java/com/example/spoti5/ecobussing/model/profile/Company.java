package com.example.spoti5.ecobussing.model.profile;

import com.example.spoti5.ecobussing.controller.database.DatabaseHolder;
import com.example.spoti5.ecobussing.model.profile.interfaces.IProfile;
import com.example.spoti5.ecobussing.model.profile.interfaces.IUser;
import com.example.spoti5.ecobussing.controller.SaveHandler;
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
 * Created by hilden on 2015-09-29.
 */
public class Company implements IProfile {

    private String name;
    private double pointCurrentMonth;
    private double pointCurrentYear;
    private double pointTot;
    private String companyInfo;
    private int nbrEmployees;

    /**
     * Different type of members, the "creatorMember" is always a "moderatorMember", and all "moderatorMembers" are always "members".
     */
    private String creatorMember;                 //The creator of the bussiness-profile, has deletion right.
    private ArrayList<String> moderatorMembers;    //The members of the bussiness-profile with modifying rights.
    private ArrayList<String> members;             //All members of the bussiness profile.

    private String modMemberJson;
    private String memberJson;

    private String oldMomMemberJson;
    private String oldMemberJson;

    private HashMap userConnectionDates;

    private String usersConnectedJson;
    private String oldUserConnectedJson;

    public Company(){}

    public Company(String businessName, IUser creatorMember, int nbrEmployees) {
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

        userConnectionDates = new HashMap();

        updateMemberJson();
        updateModMemberJson();
        updateUserConnectedJson();
    }

    @Override
    public String getName() { return name; }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setCompanyInfo(String info){
        companyInfo = info;
    }

    public String getCreatorMember() {
        return creatorMember;
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

    private void updateUserConnectionDates(){
        if(oldUserConnectedJson == null || userConnectionDates == null){
            if(!(usersConnectedJson==null)){
                Gson gson = new Gson();
                userConnectionDates = gson.fromJson(usersConnectedJson, HashMap.class);
                oldUserConnectedJson = usersConnectedJson;
            }
        }
    }

    public List<String> getMembers(boolean avoidDatabaseUpload) {
        updateMembersFromJson();
        return members;
    }

    public List<String> getModeratorMembers(boolean avoidDatabaseUpload) {
        updateModMembersFromJson();
        return moderatorMembers;
    }


    public boolean userIsCreator(IUser user) {

        return creatorMember == user.getEmail();
    }

    public boolean userIsModerator(IUser user) {
        updateModMembersFromJson();
        for (int i = 0; i < moderatorMembers.size(); i++) {
            if (moderatorMembers.get(i).equals(user.getEmail())) {
                return true;
            }
        }
        return false;
    }

    public boolean userIsMember(IUser user) {
        updateMembersFromJson();
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).equals(user.getEmail())) {
                return true;
            }
        }
        return false;
    }

    /**
     * The business-profile creator adds a user to the moderatorMember list.
     * The parameters are validated as they are entered.
     * @param creator
     * @param user
     */
    public void addModeratorMember(IUser creator, IUser user) {
        if (userIsCreator(creator) && !userIsModerator(user) && userIsMember(user)) {
            moderatorMembers.add(user.getEmail());
            updateModMemberJson();
        }
    }

    /**
     * A user adds himself to a business as a member if he is not already a member.
     * @param user
     */
    public void addMember(IUser user) {
        updateUserConnectionDates();
        if (!userIsMember(user)) {
            user.setCompany(name);
            SaveHandler.changeUser(user);

            members.add(user.getEmail());
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.GERMAN);
            Date dateTime = new Date();
            String str = dateFormat.format(dateTime);
            //String[] date = str.split(" ");

            userConnectionDates.put(user, str);

            updateMemberJson();
        }
    }

    /**
     * The business-profile creator removes a user from the moderatorMember list if it is a moderatorMember.
     * (Does not remove the user from the member list)
     * @param creator
     * @param user
     */
    public void removeModeratorMember(IUser creator, IUser user) {
        if (userIsCreator(creator) && userIsModerator(user) && !userIsCreator(user)) {
            moderatorMembers.remove(user.getEmail());
            updateModMemberJson();
        }
    }

    /**
     * Removes the user from the member list if the user is a member and not the creator.
     * Also removes the user from the moderatorMember list if the user is a moderatorMember.
     * @param user
     */
    public void removeMember(IUser user) {
        if (!userIsCreator(user)) {
            if (userIsModerator(user)) {
                moderatorMembers.remove(user.getEmail());
                members.remove(user.getEmail());
                updateModMemberJson();
            } else {
                if (userIsMember(user)) {
                    members.remove(user.getEmail());
                }
            }
            updateMemberJson();
            user.setCompany("");
            SaveHandler.changeUser(user);
        }
    }

    public void incPoints(double co2Saved){
        checkDate();

        pointCurrentMonth  += calculatePoints(co2Saved);
        pointCurrentYear   += calculatePoints(co2Saved);
        pointTot           += calculatePoints(co2Saved);


    }

    public double calculatePoints(double co2Saved){
        if(co2Saved == 0) return 0;

        return 100 * (2+(10*co2Saved)) / (100+nbrEmployees);
    }

    public void checkDate(){
        Calendar cal = Calendar.getInstance();
        int month1 = cal.get(Calendar.MONTH);
        int year1 = cal.get(Calendar.YEAR);

        cal.add(Calendar.DAY_OF_MONTH, -1);
        int month2 = cal.get(Calendar.MONTH);
        int year2 = cal.get(Calendar.YEAR);

        if(month1 != month2) pointCurrentMonth = 0;
        if(year1 != year2) pointCurrentYear = 0;
    }

    public void newJourney(double co2Saved){
        incCO2Saved(co2Saved);
        incPoints(co2Saved);
        DatabaseHolder.getDatabase().updateCompany(this);
    }

    /**
     * Updates the company's member lists with the current information of each member from the database, not needed?
     */
 /*   public void updateMembers(){
        updateMembersFromJson();
        updateModMembersFromJson();
        List<IUser> tmpList = database.getUsers();
        for(String memberMail:members){
            for (IUser user:tmpList) {
                if(memberMail.equals(user.getEmail())){
                    memberMail = user.getEmail();
                }
            }
        }

        for(IUser modMember:moderatorMembers){
            for (IUser member:members) {
                if(modMember.getEmail() == member.getEmail()){
                    modMember = member;
                }
            }
        }
        updateModMemberJson();
        updateMemberJson();
    }*/



    private void updateUserConnectedJson(){
        usersConnectedJson =  new Gson().toJson(userConnectionDates);
    }


    @Override
    public Double getDistanceTraveled() {
        //co2Tot/co2 saved per km?
        return null;
    }


    @Override
    public void incCO2Saved(double distance) { checkDate(); }


    private void updateModMemberJson(){
        modMemberJson = new Gson().toJson(moderatorMembers);
    }


    private void updateMemberJson(){
        memberJson = new Gson().toJson(members);
    }



    // Getters
    public double getPointsSavedDate(int year, int month, int day){

    double value = 0;
    for(String s: getMembers(true)){
        try{
            IUser usr = DatabaseHolder.getDatabase().getUser(s);
            value += calculatePoints(usr.getCO2SavedDate(year, month, day));
        }catch (NullPointerException e){
            value += 0;
        }
    }

    return value;
}

    public double getPointsSavedMonth(int year, int month){

        double value = 0;
        for(String s: getMembers(true)){
            try{
                IUser usr = DatabaseHolder.getDatabase().getUser(s);

                value += calculatePoints(usr.getCO2SavedMonth(year, month));
            }catch (NullPointerException e){
                value += 0;
            }
        }

        return value;
    }

    @Override
    public Double getCO2SavedYear(Integer year) {
        return null;
    }

    @Override
    public Double getCO2SavedMonth(Integer year, Integer month) {
        return null;
    }

    @Override
    public Double getCO2SavedDate(Integer year, Integer month, Integer day) {
        return null;
    }

    @Override
    public Double getCO2SavedPast7Days(boolean avoidDatabaseUpload) {
        return null;
    }

    @Override
    public Double getCO2Saved(boolean avoidDatabaseUpload) {
        return pointTot;
    }

    public int getNbrEmployees() {
        return nbrEmployees;
    }

    public double getpointTot() {
        //checkDate();
        return pointTot;
    }


    public double getPointCurrentYear() {
        //checkDate();
        return pointCurrentYear;
    }

    public double getPointCurrentMonth() {
        //checkDate();
        return  pointCurrentMonth;
    }

    public String getCompanyInfo() {
        return companyInfo;
    }

    public String getModMemberJson() {
        return modMemberJson;
    }

    public String getUsersConnectedJson() {
        return usersConnectedJson;
    }

    public String getMemberJson() { return memberJson; }




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
                ", userConnectedJson=" + usersConnectedJson +
                '}';
    }
}
