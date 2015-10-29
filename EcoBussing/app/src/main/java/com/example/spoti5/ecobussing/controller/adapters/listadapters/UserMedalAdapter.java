package com.example.spoti5.ecobussing.controller.adapters.listadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.spoti5.ecobussing.controller.medals.UserMedal;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.controller.SaveHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by emilaxelsson on 17/10/15.
 */
public class UserMedalAdapter extends BaseAdapter {
    private List<String> medalNames;
    private UserMedal userMedal;
    private TextView currentText;
    private TextView maxText;
    private ImageView medalImage;
    private ProgressBar progBar;
    private Context context;

    public UserMedalAdapter(Context context) {
        this.context = context;
        medalNames = new ArrayList<>();
        userMedal = new UserMedal(SaveHandler.getCurrentUser());

        for (String item : context.getResources().getStringArray(R.array.user_medals)) {
            medalNames.add(item);
        }
    }

    @Override
    public int getCount() {
        return medalNames.size();
    }

    @Override
    public Object getItem(int position) {
        return medalNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = null;

        if (convertView == null) {

            row = inflater.inflate(R.layout.medal_view, parent, false);

        } else {
            row = convertView;
        }

        TextView title = (TextView) row.findViewById(R.id.medalTitle);
        currentText = (TextView) row.findViewById(R.id.currentText);
        maxText = (TextView) row.findViewById(R.id.maxText);
        medalImage = (ImageView) row.findViewById(R.id.medalImage);
        progBar = (ProgressBar) row.findViewById(R.id.medalProgress);

        title.setText(medalNames.get(position));
        if (position == 0) {
            return saveTogheterMedal(row);
        } else if (position == 1) {
            return timesTraveledMedal(row);
        }

        return row;
    }


    private View saveTogheterMedal(View row) {
        DecimalFormat df = new DecimalFormat("#.00");

        String current = df.format(userMedal.getCurrentCO2Value()) + "kg CO2";
        String full = df.format(userMedal.getFullCO2Value()) + "kg CO2";

        currentText.setText(current);
        maxText.setText(full);

        int perDone = userMedal.getCO2ToPercentage();
        progBar.setProgress(perDone);

        if(perDone > 100){
            medalImage.setImageResource(R.drawable.star);
        }else {
            medalImage.setImageResource(R.drawable.stargrey);
        }
        return row;
    }

    private View timesTraveledMedal(View row) {

        String current = Integer.toString(userMedal.getCurrentTimesTravel()) + " resor";
        String full = Integer.toString(userMedal.getMaxTimesTravel()) + " resor";

        currentText.setText(current);
        maxText.setText(full);

        int perDone = userMedal.getTravelMorePercantage();
        progBar.setProgress(perDone);

        if(perDone > 100) {
            medalImage.setImageResource(R.drawable.bus);
        }else {
            medalImage.setImageResource(R.drawable.colorizedbus);
        }
        return row;

    }

}