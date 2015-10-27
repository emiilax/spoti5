package com.example.spoti5.ecobussing.model.profile;

import com.example.spoti5.ecobussing.controller.database.DatabaseHolder;
import com.example.spoti5.ecobussing.model.profile.interfaces.IProfile;
import com.example.spoti5.ecobussing.model.profile.interfaces.IUser;
import com.example.spoti5.ecobussing.controller.SaveHandler;
import java.util.Calendar;
import java.util.List;

/**
 * Created by hilden on 2015-09-29.
 * This users can be edited etc without errors on the database. No variables are stored here
 * because everything is stored in the databaseCompany
 */
public class Company implements IProfile {

    private final DatabaseCompany dbCompany;

    public Company(DatabaseCompany dbCompany){
        this.dbCompany = dbCompany;
    }

    public Company(String businessName, IUser creatorMember, int nbrEmployees) {
        dbCompany = new DatabaseCompany(businessName, creatorMember, nbrEmployees);
    }

    @Override
    public String getName() { return dbCompany.getName(); }

    @Override
    public void setName(String name) {
        dbCompany.setName(name);
    }

    public void setCompanyInfo(String info){
        dbCompany.setCompanyInfo(info);
    }

    public String getCreatorMember() {
        return dbCompany.getCreatorMember();
    }


    public List<String> getMembers() {
        return dbCompany.getMembers(true);
    }

    public List<String> getModeratorMembers() {
        return dbCompany.getModeratorMembers(true);
    }


    public boolean userIsCreator(IUser user) {

        return dbCompany.getCreatorMember() == user.getEmail();
    }

    public void setNbrEmployees(int nbrEmployees){
        dbCompany.setNbrEmployees(nbrEmployees);
    }

    public boolean userIsModerator(IUser user) {
        List<String> users = dbCompany.getModeratorMembers(true);
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).equals(user.getEmail())) {
                return true;
            }
        }
        return false;
    }

    public boolean userIsMember(IUser user) {
        List<String> users = dbCompany.getMembers(true);
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).equals(user.getEmail())) {
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
        List<String> users = dbCompany.getModeratorMembers(true);
        if (userIsCreator(creator) && !userIsModerator(user) && userIsMember(user)) {
            users.add(user.getEmail());
        }
        dbCompany.setModMembers(users);
    }

    /**
     * A user adds himself to a business as a member if he is not already a member.
     * @param user
     */
    public void addMember(IUser user) {
        List<String> users = dbCompany.getMembers(true);
        if (!userIsMember(user)) {
            user.setCompany(dbCompany.getName());
            SaveHandler.changeUser(user);

            users.add(user.getEmail());
            dbCompany.setMembers(users);
        }
    }

    /**
     * The business-profile creator removes a user from the moderatorMember list if it is a moderatorMember.
     * (Does not remove the user from the member list)
     * @param creator
     * @param user
     */
    public void removeModeratorMember(IUser creator, IUser user) {
        List<String> users = dbCompany.getModeratorMembers(true);
        if (userIsCreator(creator) && userIsModerator(user) && !userIsCreator(user)) {
            users.remove(user.getEmail());
            dbCompany.setModMembers(users);
        }
    }

    /**
     * Removes the user from the member list if the user is a member and not the creator.
     * Also removes the user from the moderatorMember list if the user is a moderatorMember.
     * @param user
     */
    public void removeMember(IUser user) {
        List<String> users = dbCompany.getMembers(true);
        List<String> modUsers = dbCompany.getModeratorMembers(true);
        if (!userIsCreator(user)) {
            if (userIsModerator(user)) {
                modUsers.remove(user.getEmail());
                users.remove(user.getEmail());
            } else {
                if (userIsMember(user)) {
                    users.remove(user.getEmail());
                }
            }
            dbCompany.setModMembers(modUsers);
            dbCompany.setMembers(users);
            user.setCompany("");
            SaveHandler.changeUser(user);
        }
    }

    public void incPoints(double co2Saved){
        checkDate();
        double points = calculatePoints(co2Saved);
        dbCompany.setPointCurrentMonth(dbCompany.getPointCurrentMonth() + points);
        dbCompany.setPointCurrentYear(dbCompany.getPointCurrentYear() + points);
        dbCompany.setPointTot(dbCompany.getpointTot() +points);
    }

    public double calculatePoints(double co2Saved){
        if(co2Saved == 0) return 0;

        return 100 * (2+(10*co2Saved)) / (100+dbCompany.getNbrEmployees());
    }

    public void checkDate(){
        Calendar cal = Calendar.getInstance();
        int month1 = cal.get(Calendar.MONTH);
        int year1 = cal.get(Calendar.YEAR);

        cal.add(Calendar.DAY_OF_MONTH, -1);
        int month2 = cal.get(Calendar.MONTH);
        int year2 = cal.get(Calendar.YEAR);

        if(month1 != month2) dbCompany.setPointCurrentMonth(0);
        if(year1 != year2) dbCompany.setPointCurrentYear(0);
    }

    public void newJourney(double co2Saved){
        incCO2Saved(co2Saved);
        incPoints(co2Saved);
        DatabaseHolder.getDatabase().updateCompany(this);
    }




    // Getters
    public double getPointsSavedDate(int year, int month, int day){

    double value = 0;
    for(String s: dbCompany.getMembers(true)){
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
        for(String s: dbCompany.getMembers(true)){
            try{
                IUser usr = DatabaseHolder.getDatabase().getUser(s);

                value += calculatePoints(usr.getCO2SavedMonth(year, month));
            }catch (NullPointerException e){
                value += 0;
            }
        }

        return value;
    }

    public int getNbrEmployees() {
        return dbCompany.getNbrEmployees();
    }

    public double getpointTot() {
        checkDate();
        return dbCompany.getpointTot();
    }

    public double getPointCurrentYear() {
        checkDate();
        return dbCompany.getPointCurrentYear();
    }

    public double getPointCurrentMonth() {
        checkDate();
        return  dbCompany.getPointCurrentMonth();
    }

    public String getCompanyInfo() {
        return dbCompany.getCompanyInfo();
    }

    public DatabaseCompany getDatabaseCompany(){
        return dbCompany;
    }

    //TODO WITH DEEPMAPS-------------------------------------------------------------
    @Override
    public Double getDistanceTraveled() {
        //co2Tot/co2 saved per km?
        return null;
    }

    @Override
    public void incCO2Saved(double distance) { checkDate(); }

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
    public Double getCO2Saved() {
        return 0.0;
    }

    @Override
    public Double getCO2SavedPast7Days(){
        return null;
    }

}
