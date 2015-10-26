package com.example.spoti5.ecobussing.controller;

import com.example.spoti5.ecobussing.controller.database.DatabaseHolder;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabase;
import com.example.spoti5.ecobussing.model.profile.interfaces.IProfile;
import com.example.spoti5.ecobussing.model.profile.interfaces.IUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erik on 2015-10-13.
 * Searches the database for specfic name and returns list with user that has that mail
 */
public class Tools {

    private static Tools simpelSearch;
    private List<IProfile> results = new ArrayList<>();

    private IDatabase database;

    public static Tools getInstance(){
        if(simpelSearch == null){
            simpelSearch = new Tools();
        }
        return simpelSearch;
    }

    private Tools(){
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

    public List<IProfile> companySearch(String name){
        results.clear();

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
