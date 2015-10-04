package com.example.spoti5.ecobussing.Profiles;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hilden on 2015-09-29.
 */
public class BusinessProfile implements IProfile {

    private String companyName;
    private double distance;               //Total distance traveled by bus, in KM.
    private double currentDistance;        //Distance traveled by bus that has not yet been transferred to total, in KM.
    private double carbondioxideSaved;     //Amount of carbondioxide saved.

    /**
     * Different type of members, the "creatorMember" is always a "moderatorMember", and all "moderatorMembers" are always "members".
     */
    private User creatorMember;     //The creator of the bussiness-profile, has deletion right.
    List<User> moderatorMembers;    //The members of the bussiness-profile with modifying rights.
    List<User> members;             //All members of the bussiness profile.


    public BusinessProfile(String businessName, User creatorMember) {
        companyName = businessName;
        this.creatorMember = creatorMember;
        moderatorMembers.add(creatorMember);
        members.add(creatorMember);
        distance = 0;
        currentDistance = 0;
        carbondioxideSaved = 0;
        moderatorMembers = new ArrayList<User>();
        members = new ArrayList<User>();
    }

    public boolean userIsCreator(User user) {
        if (creatorMember.getEmail() == user.getEmail()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean userIsModerator(User user) {
        for (int i = 0; i < moderatorMembers.size(); i++) {
            if (moderatorMembers.get(i).getEmail() == user.getEmail()) {
                return true;
            }
        }
        return false;
    }

    public boolean userIsMember(User user) {
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
        if (userIsCreator(creator) && !userIsModerator(user) && userIsMember(user)) {
            moderatorMembers.add(user);
        }
    }

    /**
     * A user adds himself to a business as a member if he is not already a member.
     * @param user
     */
    public void addMember(User user) {
        if (!userIsMember(user)) {
            members.add(user);
        }
    }

    /**
     * The business-profile creator removes a user from the moderatorMember list if it is a moderatorMember.
     * (Does not remove the user from the member list)
     * @param creator
     * @param user
     */
    public void removeModeratorMember(User creator, User user) {
        if (userIsCreator(creator) && userIsModerator(user)) {
            moderatorMembers.remove(user);
        }
    }

    /**
     * Removes the user from the member list if the user is a member and not the creator.
     * Also removes the user from the moderatorMember list if the user is a moderatorMember.
     * @param user
     */
    public void removeMember(User user) {
        if (!userIsCreator(user)) {
            if (userIsModerator(user)) {
                moderatorMembers.remove(user);
                members.remove(user);
            } else {
                if (userIsMember(user)) {
                    members.remove(user);
                }
            }
        }
    }

    @Override
    public String getName() {
        return companyName;
    }

    @Override
    public double getDistance() {
        return distance;
    }

    @Override
    public double getCO2Saved() {
        return carbondioxideSaved;
    }

    @Override
    public void setName(String name) {
        companyName = name;
    }

    @Override
    public void decDistance(double reducedDistance) {}

    public User getCreatorMember() {
        return creatorMember;
    }

    public List<User> getMembers() {
        return members;
    }

    public List<User> getModeratorMembers() {
        return moderatorMembers;
    }

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
    }
}
