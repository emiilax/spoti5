package com.example.spoti5.ecobussing.controller.adapters.listadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.spoti5.ecobussing.controller.medals.CompanyMedal;
import com.example.spoti5.ecobussing.controller.profile.interfaces.IUser;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.controller.SaveHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erik on 17/10/15.
 * Sets the view elements for company medal
 */
public class CompanyMedalAdapter extends BaseAdapter {
    private List<String> medalNames;
    private CompanyMedal companyMedal;
    private TextView currentText;
    private TextView maxText;
    private ImageView medalImage;
    private ProgressBar progBar;
    private Context context;
    private IUser currentUser;

    public CompanyMedalAdapter(Context context) {
        this.context = context;
        medalNames = new ArrayList<>();
        currentUser = SaveHandler.getCurrentUser();
        if(!currentUser.getCompany().equals("")) {
            companyMedal = new CompanyMedal(currentUser.getCompany());
        }

        for (String item : context.getResources().getStringArray(R.array.company_medals)) {
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

        if(!currentUser.getCompany().equals("")) {
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


            if (!currentUser.getCompany().equals("")) {
                title.setText(medalNames.get(position));
                if (position == 0) {
                    return saveTogheterMedal(row);
                } else if (position == 1) {
                    return peopleMedal(row);
                }
            }
        } else {
            if (convertView == null) {
                row = inflater.inflate(R.layout.no_company, parent, false);
            } else {
                row = convertView;
            }

            if(position == 0){
                ((TextView)row.findViewById(R.id.no_company_text)).setText(R.string.no_company_text);
            } else {
                ((TextView)row.findViewById(R.id.no_company_text)).setText("");
            }
        }
        return row;
    }

    /*
    Sets all values to the medal view, such as progress, pictures etc.
     */
    private View saveTogheterMedal(View row) {
        DecimalFormat df = new DecimalFormat("#.00");

        String current = df.format(companyMedal.getCurrentPointTotal()) + " poäng";
        String full = df.format(companyMedal.getMaxPointTotal())  + " poäng";

        currentText.setText(current);
        maxText.setText(full);

        int progPer = companyMedal.getPointPercentage();
        progBar.setProgress(progPer);

        if(progPer >= 100){
            medalImage.setImageResource(R.drawable.star);
        }else {
            medalImage.setImageResource(R.drawable.stargrey);
        }
        return row;
    }

    private View peopleMedal(View row) {

        String current = Integer.toString(companyMedal.getEmployeesCurrent()) + " anställda åker kollektivt av " + Integer.toString(companyMedal.getEmployeesMax()) + "st";
        String max ="";

        currentText.setText(current);
        maxText.setText(max);

        int progPer = companyMedal.getEmployeesPercantage();
        progBar.setProgress(progPer);

        if(progPer >= 100){
            medalImage.setImageResource(R.drawable.peoplemedal);
        }else {
            medalImage.setImageResource(R.drawable.peoplemedalgreysmall);
        }

        return row;
    }

}