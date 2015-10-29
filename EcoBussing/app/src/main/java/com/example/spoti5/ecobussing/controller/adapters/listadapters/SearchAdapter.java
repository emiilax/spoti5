package com.example.spoti5.ecobussing.controller.adapters.listadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.spoti5.ecobussing.controller.Tools;
import com.example.spoti5.ecobussing.controller.profile.interfaces.IProfile;
import com.example.spoti5.ecobussing.controller.profile.interfaces.IUser;
import com.example.spoti5.ecobussing.R;

import java.util.List;

/**
 * Created by Erik on 2015-10-13.
 */
public class SearchAdapter extends BaseAdapter {
    private List<IProfile> searchResults;
    private Context context;
    private Tools simpelSearch = Tools.getInstance();

    public SearchAdapter(Context context, String searchWord){
        this.context = context;
        if(searchWord.equals("")) {
            searchWord = "---";
        }
        searchResults = simpelSearch.search(searchWord);
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
        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = null;

        if(convertView == null){

            row = inflater.inflate(R.layout.toplist_item, parent, false);

        }else{
            row = convertView;
        }

        TextView nameLabel = (TextView) row.findViewById(R.id.toplistItem_name);
        TextView subtitleLabel = (TextView) row.findViewById(R.id.toplistItem_subtitle);
        //ImageView rowIcon = (ImageView) row.findViewById(R.id.listItemIcon);
        //ImageView icon = (ImageView) row.findViewById(R.id.listItemIcon);

        IProfile profile = searchResults.get(position);
        nameLabel.setText(profile.getName());
        if(profile instanceof IUser){
            IUser user = (IUser) profile;
            subtitleLabel.setText("Användare");
        } else {
            subtitleLabel.setText("Företag");
        }

        return row;
    }
}
