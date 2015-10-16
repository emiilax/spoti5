package com.example.spoti5.ecobussing.Activites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spoti5.ecobussing.Database.SimpelSearch;
import com.example.spoti5.ecobussing.Profiles.Company;
import com.example.spoti5.ecobussing.Profiles.IProfile;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.Profiles.User;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erik on 2015-10-13.
 */
public class SearchAdapter extends BaseAdapter {
    private List<IProfile> searchResults;
    private Context context;
    private SimpelSearch simpelSearch = SimpelSearch.getInstance();

    public SearchAdapter(Context context, String searchWord){
        this.context = context;
        if(!searchWord.equals("")) {
            searchResults = simpelSearch.search(searchWord);
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
