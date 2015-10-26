package com.example.spoti5.ecobussing.controller.adapters.listadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spoti5.ecobussing.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created b emilaxelsson on 22/09/15.
 */
public class DrawerListAdapter extends BaseAdapter {

    private List<String> listItems;
    private Context context;
    private boolean connected;

    public DrawerListAdapter(Context context, boolean connected){
        listItems = new ArrayList<>();
        this.context = context;
        this.connected = connected;

        if(!connected) {
            for (String item : context.getResources().getStringArray(R.array.drawer_array)) {
                listItems.add(item);
            }
        }else{
            for(String item : context.getResources().getStringArray(R.array.drawer_array2)){
                listItems.add(item);
            }
        }

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

            row = inflater.inflate(R.layout.drawer_list_item, parent, false);

        }else{
            row = convertView;
        }

        TextView rowLabel = (TextView) row.findViewById(R.id.listItemLabel);
        //ImageView rowIcon = (ImageView) row.findViewById(R.id.listItemIcon);
        ImageView icon = (ImageView) row.findViewById(R.id.listItemIcon);

        if(!connected) {
            switch (position) {
                case 0:
                    icon.setImageResource(R.drawable.user);
                    break;
                case 1:
                    icon.setImageResource(R.drawable.logo_compact);
                    break;
                case 2:
                    icon.setImageResource(R.drawable.toplisticon);
                    break;
                case 3:
                    icon.setImageResource(R.drawable.medalicon);
                    break;
                case 4:
                    icon.setImageResource(R.drawable.logo_compact_setting);
                    break;
                case 5:
                    icon.setImageResource(R.drawable.editicon);
                    break;
                case 6:
                    icon.setImageResource(R.drawable.logout);

            }
            rowLabel.setText(listItems.get(position));
            return row;
        }else{
            switch (position) {
                case 0:
                    icon.setImageResource(R.drawable.user);
                    break;
                case 1:
                    icon.setImageResource(R.drawable.logo_compact);
                    break;
                case 2:
                    icon.setImageResource(R.drawable.toplisticon);
                    break;
                case 3:
                    icon.setImageResource(R.drawable.medalicon);
                    break;
                case 4:
                    icon.setImageResource(R.drawable.editicon);
                    break;
                case 5:
                    icon.setImageResource(R.drawable.logout);

            }
            rowLabel.setText(listItems.get(position));
            return row;
        }
    }
}
