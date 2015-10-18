package com.example.spoti5.ecobussing;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spoti5.ecobussing.Activites.ToplistFragment;
import com.example.spoti5.ecobussing.Database.Database;
import com.example.spoti5.ecobussing.Database.DatabaseHolder;
import com.example.spoti5.ecobussing.Database.IDatabase;
import com.example.spoti5.ecobussing.Profiles.IProfile;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.Profiles.User;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by emilaxelsson on 04/10/15.
 */
public class ToplistAdapter extends BaseAdapter {

    private List<IProfile> companyList;
    private List<IUser> personList;
    private Context context;
    private IDatabase database;
    private boolean company;

    private String range;

    public ToplistAdapter(Context context, String range, boolean company){

        this.context = context;
        database = DatabaseHolder.getDatabase();
        int size = 0;
        this.range = range;

        if(company){
            this.company = true;
            companyList = new ArrayList<>();
            //Used because the list is back-to-front
            List<IProfile> tempList = new ArrayList<IProfile>();
            if(range.equals("month") || range == null){
                size = database.getCompaniesToplistMonth().size();
                tempList = database.getCompaniesToplistMonth();
            }else if(range.equals("year")){
                size = database.getCompaniesToplistYear().size();
                tempList = database.getCompaniesToplistYear();
            }else if(range.equals("total")){
                size = database.getCompaniesToplistAll().size();
                tempList = database.getCompaniesToplistAll();
            }

            for(int i = size-1; i>=0; i--){

                companyList.add(tempList.get(i));
            };
        }else{
            this.company = false;
            personList = new ArrayList<>();
            //Used because the list is back-to-front
            List<IUser> tempList = new ArrayList<>();
            if(range.equals("month") || range == null){
                size = database.getUserToplistMonth().size();
                tempList = database.getUserToplistMonth();
            }else if(range.equals("year")){
                size = database.getUserToplistYear().size();
                tempList = database.getUserToplistYear();
            }else if(range.equals("total")){
                size = database.getUserToplistAll().size();
                tempList = database.getUserToplistYear();
            }

            for(int i = size-1; i>=0; i--){

                personList.add(tempList.get(i));

            };
        }

    }

    @Override
    public int getCount() {
        if(company){
            return companyList.size();
        }
        return personList.size();
    }

    @Override
    public Object getItem(int position) {
        if(company){
            return companyList.get(position);
        }
        return personList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = null;

        if(convertView == null){

            row = inflater.inflate(R.layout.toplist_item, parent, false);

        }else{
            row = convertView;
        }

        TextView name = (TextView) row.findViewById(R.id.toplistItem_name);
        TextView co2 = (TextView) row.findViewById(R.id.toplistItem_subtitle);

        //ImageView rowIcon = (ImageView) row.findViewById(R.id.listItemIcon);
        //ImageView icon = (ImageView) row.findViewById(R.id.listItemIcon);
        if(company){
            name.setText((position + 1) + ". " + companyList.get(position).getName());
        }else{
            name.setText((position + 1) + ". " + personList.get(position).getName());
            DecimalFormat df = new DecimalFormat("####0.00");


            String value = "";
            if(range.equals("month")){
                value = df.format(personList.get(position).getCo2CurrentMonth());

            }else if(range.equals("year")){
                value = df.format(personList.get(position).getCo2CurrentYear());

            }else if(range.equals("total")){
                value = df.format(personList.get(position).getCo2Tot());

            }

            co2.setText(value + " kgCO2");
        }
        //System.out.println(listItems.size());




        return row;
    }
}
