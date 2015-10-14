package com.example.spoti5.ecobussing.Profiles;

import com.example.spoti5.ecobussing.Database.Database;
import com.example.spoti5.ecobussing.Database.DatabaseHolder;
import com.example.spoti5.ecobussing.Database.IDatabase;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hilden on 2015-09-29.
 */
public class Company implements IProfile {

    private IDatabase database;

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
    private ArrayList<IUser> moderatorMembers;    //The members of the bussiness-profile with modifying rights.
    private ArrayList<IUser> members;             //All members of the bussiness profile.

    private String modMemberJson;
    private String memberJson;

    private String oldMomMemberJson;
    private String oldMemberJson;

    private HashMap userConnectionDates;
    private String usersConnectedJson;
    private String oldUserConnectedJson;

    public Company(){}

    public Company(String businessName, User creatorMember, int nbrEmployees) {
        name = businessName;
        this.creatorMember = creatorMember.getEmail();

        moderatorMembers = new ArrayList<IUser>();
        members = new ArrayList<IUser>();

        moderatorMembers.add(creatorMember);
        members.add(creatorMember);

        pointCurrentMonth = 0;
        pointCurrentYear = 0;
        pointTot = 0;
        companyInfo = "";

        this.nbrEmployees = nbrEmployees;

        userConnectionDates = new HashMap();

        database = DatabaseHolder.getDatabase();
    }

    @Override
    public String getName() {
        return name;
    }

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
        if(oldMemberJson != memberJson) {
            if (!memberJson.equals(null)) {
                Gson gson = new Gson();
                members= gson.fromJson(memberJson, new TypeToken<List<IUser>>(){}.getType());
                oldMemberJson = memberJson;
            }
        }
        updateMembers();
    }

    private void updateModMembersFromJson(){
        if(oldMomMemberJson != modMemberJson) {
            if (!modMemberJson.equals(null)) {
                Gson gson = new Gson();
                moderatorMembers = gson.fromJson(modMemberJson, new TypeToken<List<IUser>>(){}.getType());
                oldMomMemberJson = modMemberJson;
            }
        }
    }

    private void updateUserConnectionDates(){
        if(oldUserConnectedJson != usersConnectedJson){
            if(!usersConnectedJson.equals(null)){
                Gson gson = new Gson();
                userConnectionDates = gson.fromJson(usersConnectedJson, HashMap.class);
                oldUserConnectedJson = usersConnectedJson;
            }
        }
    }

    public List<IUser> getMembers(boolean avoidDatabaseUpload) {
        updateMembersFromJson();
        return members;
    }

    public List<IUser> getModeratorMembers(boolean avoidDatabaseUpload) {
        updateModMembersFromJson();
        return moderatorMembers;
    }


    public boolean userIsCreator(IUser user) {
        if (creatorMember == user.getEmail()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean userIsModerator(IUser user) {
        updateModMembersFromJson();
        for (int i = 0; i < moderatorMembers.size(); i++) {
            if (moderatorMembers.get(i).getEmail() == user.getEmail()) {
                return true;
            }
        }
        return false;
    }

    public boolean userIsMember(IUser user) {
        updateMembersFromJson();
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getEmail() == user.getEmail()) {
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
    public void addModeratorMember(User creator, User user) {
        updateModMembersFromJson();
        if (userIsCreator(creator) && !userIsModerator(user) && userIsMember(user)) {
            moderatorMembers.add(user);
        }
    }

    /**
     * A user adds himself to a business as a member if he is not already a member.
     * @param user
     */
    public void addMember(User user) {
        updateMembersFromJson();
        if (!userIsMember(user)) {
            user.setCompany(name);
            SaveHandler.changeUser(user);
            members.add(user);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date dateTime = new Date();
            String str = dateFormat.format(dateTime);
            String[] date = str.split(" ");
            userConnectionDates.put(user, date[0]);
        }
    }

    /**
     * The business-profile creator removes a user from the moderatorMember list if it is a moderatorMember.
     * (Does not remove the user from the member list)
     * @param creator
     * @param user
     */
    public void removeModeratorMember(User creator, User user) {
        updateModMembersFromJson();
        if (userIsCreator(creator) && userIsModerator(user) && !userIsCreator(user)) {
            moderatorMembers.remove(user);
        }
    }

    /**
     * Removes the user from the member list if the user is a member and not the creator.
     * Also removes the user from the moderatorMember list if the user is a moderatorMember.
     * @param user
     */
    public void removeMember(User user) {
        updateMembersFromJson();
        updateModMembersFromJson();
        if (!userIsCreator(user)) {
            if (userIsModerator(user)) {
                moderatorMembers.remove(user);
                members.remove(user);
            } else {
                if (userIsMember(user)) {
                    members.remove(user);
                }
            }
            user.setCompany("");
            SaveHandler.changeUser(user);
        }
    }

    /**
     * Updates the company's member lists with the current information of each member from the database
     */
    public void updateMembers(){
        updateMembersFromJson();
        updateModMembersFromJson();
        List<IUser> tmpList = database.getUsers();
        for(IUser member:members){
            for (IUser user:tmpList) {
                if(member.getEmail() == user.getEmail()){
                    member = user;
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
    }

    public String getUsersConnectedJson() {
        Gson gson = new Gson();
        usersConnectedJson =  gson.toJson(userConnectionDates);
        return usersConnectedJson;
    }


    @Override
    public Double getDistanceTraveled() {
        //co2Tot/co2 saved per km?
        return null;
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
    public void incCO2Saved(double distance) {

    }

    @Override
    public Double getCO2Saved(boolean avoidDatabaseUpload) {
        return pointTot;
    }

    public int getNbrEmployees() {
        return nbrEmployees;
    }

    public double getpointTot() {
        return pointTot;
    }


    public double getpointCurrentYear() {
        return pointCurrentYear;
    }

    public double getpointCurrentMonth() {
        return  pointCurrentMonth;
    }

    public String getCompanyInfo() {
        return companyInfo;
    }

    public String getModMemberJson() {
        Gson gson = new Gson();
        modMemberJson = gson.toJson(moderatorMembers);
        return modMemberJson;
    }

    public String getMemberJson() {
        Gson gson = new Gson();
        memberJson = gson.toJson(members);
        return memberJson;
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
                ", userConnectedJson=" + usersConnectedJson +
                '}';
    }

    /*
    //NEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEDS CHANGEEEEEEEEEEEEEE
    @Override
    public void updateDistance() {
        distance = 0;
        for (int i = 0; i < members.size(); i++) {
            distance = distance + members.get(i).getDistance();
        }
    }

    public void updateCO2Saved() {
        carbondioxideSaved = 0;
        for (int i = 0; i < members.size(); i++) {
            carbondioxideSaved = carbondioxideSaved + members.get(i).getCO2Saved();
        }
    } */
}
