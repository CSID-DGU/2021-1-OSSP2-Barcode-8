package com.barcode.cvs_review;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class MainFragment extends Fragment {
    ViewGroup rootView;
    ImageButton button1;
    ImageButton button2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, true);

        button1 = (ImageButton)rootView.findViewById(R.id.Button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.onFragmentChanged(1);
            }
        });

        button2 = (ImageButton)rootView.findViewById(R.id.Button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.onFragmentChanged(2);
            }
        });
        return rootView;
    }
}