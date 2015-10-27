package com.example.spoti5.ecobussing.controller.adapters.listadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spoti5.ecobussing.controller.database.DatabaseHolder;
import com.example.spoti5.ecobussing.controller.database.interfaces.IDatabase;
import com.example.spoti5.ecobussing.model.profile.Company;
import com.example.spoti5.ecobussing.model.profile.interfaces.IProfile;
import com.example.spoti5.ecobussing.model.profile.interfaces.IUser;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.controller.SaveHandler;

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

        tmp = company.getMembers();

        for(String str:tmp){
            connectedUsers.add(database.getUser(str));
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

        final TextView nameLabel1 = (TextView) row.findViewById(R.id.userlistItem_name);
        TextView subtitleLabel1 = (TextView) row.findViewById(R.id.userlistItem_subtitle);

        nameLabel1.setText(connectedUsers.get(position).getName());
        subtitleLabel1.setText(Double.toString(connectedUsers.get(position).getCO2Saved()));

        ImageView removeButton = (ImageView)row.findViewById(R.id.removeButton);

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IUser user = database.getUser(nameLabel1.getText().toString());
                company.removeMember(user);
                user.setCompany("");
                database.updateCompany(company);
                database.updateUser(user);
            }
        });

        return row;
    }
}
