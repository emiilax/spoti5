package com.example.spoti5.ecobussing.Activites;

import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
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
 * Created by emilaxelsson on 22/09/15.
 */
public class DrawerListAdapter extends BaseAdapter {

    private List<String> listItems;
    private Context context;
    DrawerListAdapter(Context context){
        listItems = new ArrayList<>();
        this.context = context;


        for(String item: context.getResources().getStringArray(R.array.drawer_array)){
            listItems.add(item);
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

        rowLabel.setText(listItems.get(position));

        return row;
    }
}
