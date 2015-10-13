package com.example.spoti5.ecobussing.Database;

import com.example.spoti5.ecobussing.Profiles.IProfile;
import com.example.spoti5.ecobussing.Profiles.IUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erik on 2015-10-13.
 * Searches the database for specfic name and returns list with user that has that mail
 */
public class SimpelSearch {

    private static SimpelSearch simpelSearch;
    private List<IProfile> results = new ArrayList<>();

    private IDatabase database;

    public static SimpelSearch getInstance(){
        if(simpelSearch == null){
            simpelSearch = new SimpelSearch();
        }
        return simpelSearch;
    }

    private SimpelSearch(){
        database = DatabaseHolder.getDatabase();
    }

    public List<IProfile> search(String name){
        results.clear();
        for(IUser user: database.getUsers()){
            if((user.getName().toLowerCase()).contains(name.toLowerCase())){
                results.add(user);
            }
        }

        for (IProfile company: database.getCompanies()){
            if((company.getName().toLowerCase()).contains(name.toLowerCase())){
                results.add(company);
            }
        }

        return results;
    }

    public List<IProfile> oldResults(){
        return results;
    }
}
