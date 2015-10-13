package com.example.spoti5.ecobussing;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spoti5.ecobussing.Database.DatabaseHolder;
import com.example.spoti5.ecobussing.Database.IDatabase;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;

/**
 * Created by matildahorppu on 13/10/15.
 */
public class ConnectedCompanyFragment extends Fragment {

    private IDatabase database;
    private IUser currentUser;

    private TextView infoText;
    private ImageView companyImage;
    private TextView companyName;
    private Button viewCompanyButton;
    private Button disconnectCompany;

    public ConnectedCompanyFragment(){
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        database = DatabaseHolder.getDatabase();
        currentUser = SaveHandler.getCurrentUser();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_connected_company,container,false);

        infoText = (TextView)view.findViewById(R.id.textViewInfoText);
        companyImage = (ImageView)view.findViewById(R.id.imageViewComp);
        companyName = (TextView)view.findViewById(R.id.textViewCompanyName);
        viewCompanyButton = (Button)view.findViewById(R.id.buttonShowProfile);
        disconnectCompany = (Button)view.findViewById(R.id.buttonDisconnect);

        viewCompanyButton.setOnClickListener(viewCompany);
        disconnectCompany.setOnClickListener(disconnectFromComp);

        companyName.setText(database.getCompany(currentUser.getCompany()).toString());

        return view;
    }

    private View.OnClickListener viewCompany = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener disconnectFromComp = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

}
