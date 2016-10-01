package com.example.didoy.flippingcardview.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.didoy.flippingcardview.R;

/**
 * Created by Didoy on 9/27/2016.
 */
public class FragmentBackCard extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_back_card, container, false);
        return v;
    }
}
