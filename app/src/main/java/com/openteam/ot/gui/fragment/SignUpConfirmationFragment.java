package com.openteam.ot.gui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.openteam.ot.R;

/**
 * Created by zoz on 12/10/2016.
 */

public class SignUpConfirmationFragment extends AbstractFragment{

    private TextView confirmBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.sign_up_confirmation,container,false);
        getBasicActivity().getToolbarTitle().setText(getResources().getString(R.string.confirmation).toUpperCase());
        confirmBtn = (TextView) view.findViewById(R.id.sign_up_confirm_confirmation);
        return view;
    }

    @Override
    public void initListeners() {
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAbsActivity().replaceFragment(new SignUpWelcomeFragment());
            }
        });
    }
}
