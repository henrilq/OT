package com.openteam.ot.gui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openteam.ot.R;

/**
 * Created by zoz on 07/10/2016.
 */

public class SignInFragment extends AbstractFragment{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.sign_in,container,false);
        return view;
    }

    @Override
    public void initListeners() {

    }
}
