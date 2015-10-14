package com.example.spoti5.ecobussing;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.spoti5.ecobussing.Database.DatabaseHolder;
import com.example.spoti5.ecobussing.Profiles.IUser;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.SavedData.SaveHandler;

/**
 * Created by Erik on 2015-10-04.
 */
public class EditInfoFragment extends Fragment {

    private IUser currentUser;
    private EditText nameField;
    private EditText password1;
    private EditText password2;
    private Button saveChanges;
    private Context context;
    private ImageView profilePic;

    public EditInfoFragment(){

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = super.getContext();
        currentUser = SaveHandler.getCurrentUser();
        View view = inflater.inflate(R.layout.edit_info_fragment, container, false);
        nameField = (EditText) view.findViewById(R.id.nameField);
        password1 = (EditText) view.findViewById(R.id.password1);
        password2 = (EditText) view.findViewById(R.id.password2);
        saveChanges = (Button) view.findViewById(R.id.save_button);
        profilePic = (ImageView) view.findViewById(R.id.edit_profile_pic);

        profilePic.setOnClickListener(changeImage);
        saveChanges.setOnClickListener(save);
        initFields();
        return view;
    }

    View.OnClickListener changeImage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Context context = getActivity().getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            CharSequence text = "Ej implementerat";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    };

    private void initFields(){
        nameField.setText(currentUser.getName());
    }


    private View.OnClickListener save = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            changeUser();
        }
    };

    private void changeUser(){
        currentUser.setName(nameField.getText().toString());
        SaveHandler.changeUser(currentUser);
        DatabaseHolder.getDatabase().updateUser(currentUser);
    }

}
