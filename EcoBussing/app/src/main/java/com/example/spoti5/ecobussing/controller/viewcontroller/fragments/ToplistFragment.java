package com.example.spoti5.ecobussing.controller.viewcontroller.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.spoti5.ecobussing.controller.viewcontroller.activities.ActivityController;
import com.example.spoti5.ecobussing.controller.viewcontroller.activities.MainActivity;
import com.example.spoti5.ecobussing.controller.profile.interfaces.IProfile;
import com.example.spoti5.ecobussing.R;
import com.example.spoti5.ecobussing.controller.adapters.listadapters.ToplistAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ToplistFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ToplistFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ToplistAdapter listAdapter;

    public ToplistFragment() {
        // Required empty public constructor

    }

    public static ToplistFragment newInstance(boolean isCompany){
        ToplistFragment tf = new ToplistFragment();

        tf.isCompany = isCompany;
        tf.range = "month";

        return tf;
    }

    public static ToplistFragment newInstance(boolean isCompany, String range){
        ToplistFragment tf = new ToplistFragment();

        tf.isCompany = isCompany;
        tf.range = range;

        return tf;
    }

    private String range;
    private boolean isCompany;


    public void setisCompany(boolean truFal){
        isCompany = truFal;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_toplist, container, false);

        ListView drawerList= (ListView) view.findViewById(R.id.toplistListView);

        listAdapter = new ToplistAdapter(view.getContext(), range, isCompany);
        drawerList.setAdapter(listAdapter);
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = listAdapter.getItem(position);
                IProfile profile = (IProfile) item;
                ((MainActivity)getActivity()).changeToProfileFragment(profile, profile.getName());
            }
        });



        return view;

    }

    public void setRange(String range){
        this.range = range;
        listAdapter = new ToplistAdapter(ActivityController.getContext(), range, isCompany);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
