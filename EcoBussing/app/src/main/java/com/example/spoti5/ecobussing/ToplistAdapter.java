package com.example.spoti5.ecobussing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spoti5.ecobussing.Database.Database;
import com.example.spoti5.ecobussing.Database.DatabaseHolder;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emilaxelsson on 04/10/15.
 */
public class ToplistAdapter extends BaseAdapter {

    private List<IUser> listItems;
    private Context context;
    public ToplistAdapter(Context context){
        listItems = new ArrayList<IUser>();
        this.context = context;

        listItems = DatabaseHolder.getDatabase().getUsers();
        System.out.println(listItems.size());

    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
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

        name.setText((position + 1) + ". " + listItems.get(position).getName());
        //System.out.println(listItems.size());
        co2.setText(Double.toString(listItems.get(position).getMoneySavedPast7Days(true)));

        return row;
    }
}
