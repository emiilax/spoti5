package com.example.spoti5.ecobussing.Activites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.spoti5.ecobussing.Database.SimpelSearch;
import com.example.spoti5.ecobussing.Profiles.Company;
import com.example.spoti5.ecobussing.Profiles.IProfile;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matildahorppu on 18/10/15.
 */
public class CompanySearchAdapter extends BaseAdapter {

    private List<IProfile> searchResults;
    private List<IProfile> searchedCompanies;
    private Context context;
    private SimpelSearch simpelSearch = SimpelSearch.getInstance();

    public CompanySearchAdapter(Context context, String searchWord){
        this.context = context;
        if(searchWord.equals("")) {
            searchWord = "---";
        }
        searchResults = simpelSearch.search(searchWord);
        searchedCompanies = new ArrayList();

        for(IProfile profile: searchResults){
            if(profile instanceof Company){
                searchedCompanies.add(profile);
            }
        }
    }


    @Override
    public int getCount() {
        return searchResults.size();
    }

    @Override
    public Object getItem(int position) {
        return searchResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row;

        if(convertView == null){
            row = inflater.inflate(R.layout.toplist_item, parent, false);
        }else{
            row = convertView;
        }

        TextView nameLabel1 = (TextView) row.findViewById(R.id.toplistItem_name);
        TextView subtitleLabel1 = (TextView) row.findViewById(R.id.toplistItem_subtitle);

        Company company = (Company)searchedCompanies.get(position);

        nameLabel1.setText(company.getName());
        subtitleLabel1.setText(Integer.toString(company.getMembers(true).size()));


        return row;
    }
}
