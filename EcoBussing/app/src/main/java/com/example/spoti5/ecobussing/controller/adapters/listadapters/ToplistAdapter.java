package com.example.spoti5.ecobussing.controller.adapters.listadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.spoti5.ecobussing.controller.database.DatabaseHolder;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabase;
import com.example.spoti5.ecobussing.model.profile.Company;
import com.example.spoti5.ecobussing.model.profile.interfaces.IProfile;
import com.example.spoti5.ecobussing.model.profile.interfaces.IUser;
import com.example.spoti5.ecobussing.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emil Axelsson on 04/10/15.
 *
 * Used to put the toplist from the Database in to listitems.
 *
 */
public class ToplistAdapter extends BaseAdapter {

    private List<IProfile> companyList;
    private List<IUser> personList;
    private Context context;
    private boolean company;
    private String range;


    public ToplistAdapter(Context context, String range, boolean company){

        this.context = context;
        IDatabase database = DatabaseHolder.getDatabase();
        int size = 0;
        this.range = range;

        // Different list depending on whether its a company or not
        if(company){
            this.company = true;
            companyList = new ArrayList<>();

            // Different list depending on range
            List<IProfile> tempList = new ArrayList<>();
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

            // Load items backwards because the items in the list are back-to-front
            for(int i = size-1; i>=0; i--){

                companyList.add(tempList.get(i));
            }

        }else{
            this.company = false;
            personList = new ArrayList<>();

            // Different list depending on range
            List<IUser> tempList = new ArrayList<>();
            if(range.equals("month") || range == null){
                size = database.getUserToplistMonth().size();
                tempList = database.getUserToplistMonth();
            }else if(range.equals("year")){
                size = database.getUserToplistYear().size();
                tempList = database.getUserToplistYear();
            }else if(range.equals("total")){
                size = database.getUserToplistAll().size();
                tempList = database.getUserToplistAll();
            }

            // Load items backwards because the items in the list are back-to-front
            for(int i = size-1; i>=0; i--){
                personList.add(tempList.get(i));

            }
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
        View row;

        if(convertView == null){
            row = inflater.inflate(R.layout.toplist_item, parent, false);
        }else{
            row = convertView;
        }

        TextView name = (TextView) row.findViewById(R.id.toplistItem_name);
        TextView co2 = (TextView) row.findViewById(R.id.toplistItem_subtitle);

        // This icon will be used when pictures for users are implemented
        //ImageView icon = (ImageView) row.findViewById(R.id.listItemIcon);

        // Put value and name in diffrently depending on if its a company or person
        String value = "";
        if(company){
            name.setText((position + 1) + ". " + companyList.get(position).getName());
            DecimalFormat df = new DecimalFormat("####0");

            if(range.equals("month")){

                value = Double.toString(((Company) companyList.get(position)).getPointCurrentMonth());

            }else if(range.equals("year")){
                value = Double.toString(((Company) companyList.get(position)).getPointCurrentYear());

            }else if(range.equals("total")) {
                value = Double.toString(((Company) companyList.get(position)).getpointTot());

            }
            value = df.format(value);

        }else{
            name.setText((position + 1) + ". " + personList.get(position).getName());
            DecimalFormat df = new DecimalFormat("####0.00");

            if(range.equals("month")){
                value = df.format(personList.get(position).getCo2CurrentMonth());

            }else if(range.equals("year")){
                value = df.format(personList.get(position).getCo2CurrentYear());

            }else if(range.equals("total")){
                value = df.format(personList.get(position).getCo2Tot());

            }


        }
        if(company){
            co2.setText(value + " poäng");
        }else{
            co2.setText(value + " kgCO2");
        }


        return row;
    }
}
