package com.example.user.sharingcaring.FragmentActivities;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.sharingcaring.PostActivities.TutorPostActivity;
import com.example.user.sharingcaring.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TutorFragment extends Fragment {

    View view;
    FloatingActionButton floatingActionButton;

    public TutorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_tutor, container, false);
        floatingActionButton=view.findViewById(R.id.float_button_tutor);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tutorIntent=new Intent(getContext(),TutorPostActivity.class);
                startActivity(tutorIntent);
            }
        });

        return view;
    }

}
