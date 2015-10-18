package com.example.spoti5.ecobussing.CompanySwipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.spoti5.ecobussing.Profiles.Company;
import com.example.spoti5.ecobussing.Profiles.IProfile;
import com.example.spoti5.ecobussing.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by matildahorppu on 18/10/15.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<IProfile> listDataHeader;
    private HashMap<IProfile, String> listDataChild;

    public ExpandableListAdapter(Context context, List<IProfile> users, HashMap<IProfile, String> map){
        this.context = context;
        this.listDataHeader = users;
        this.listDataChild = map;
    }


   // @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition));
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String headerTitle = getGroup(groupPosition).getClass().getName();

        if(convertView == null){
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =infalInflater.inflate(R.layout.toplist_item, null);
        }

        TextView ett = (TextView)convertView.findViewById(R.id.toplistItem_name);
        TextView tva = (TextView)convertView.findViewById(R.id.toplistItem_subtitle);

        ett.setText(headerTitle);
        tva.setText("");

        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String)getChild(groupPosition, childPosition);

        if(convertView == null){
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_child, null);
        }

        TextView textListChild = (TextView)convertView.findViewById(R.id.textView15);

        textListChild.setText(childText);
        textListChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ta bort användaren från företaget
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
