package com.app.yuru.ui.transition;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.yuru.R;


public class SleepEnhancer2New extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sleep_enhancer2_new, container, false);

        ImageView sleep2_right = view.findViewById(R.id.sleep2_right);

        sleep2_right.setOnClickListener(v-> getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framwQts, new WakeUpProgram()).commit());

        return view;
    }
}