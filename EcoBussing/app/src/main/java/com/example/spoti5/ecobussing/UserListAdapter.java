package com.example.spoti5.ecobussing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.spoti5.ecobussing.Database.DatabaseHolder;
import com.example.spoti5.ecobussing.Database.IDatabase;
import com.example.spoti5.ecobussing.Database.SimpelSearch;
import com.example.spoti5.ecobussing.Profiles.Company;
import com.example.spoti5.ecobussing.Profiles.IProfile;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matildahorppu on 18/10/15.
 */
public class UserListAdapter extends BaseAdapter {

    private List<IProfile> connectedUsers;
    private Context context;
    private Company company;
    private IDatabase database = DatabaseHolder.getDatabase();

    public UserListAdapter(Context context){
        this.context = context;
        connectedUsers = new ArrayList<>();

        company = (Company)database.getCompany(SaveHandler.getCurrentUser().getCompany());
        List<String> tmp;

        tmp = company.getMembers(true);

        for(String str:tmp){
            connectedUsers.add(database.getCompany(str));
        }

    }


    @Override
    public int getCount() {
        return connectedUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return connectedUsers.get(position);
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
            row = inflater.inflate(R.layout.user_list_item, parent, false);
        }else{
            row = convertView;
        }

        TextView nameLabel1 = (TextView) row.findViewById(R.id.userlistItem_name);
        TextView subtitleLabel1 = (TextView) row.findViewById(R.id.userlistItem_subtitle);

        nameLabel1.setText(connectedUsers.get(position).getName());
        subtitleLabel1.setText(Double.toString(connectedUsers.get(position).getCO2Saved(true)));

        return row;
    }
}
