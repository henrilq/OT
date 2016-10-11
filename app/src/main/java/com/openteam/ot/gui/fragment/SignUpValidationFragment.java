package com.openteam.ot.gui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.openteam.ot.R;
import com.openteam.ot.gui.activity.DrawerActivity;

/**
 * Created by zoz on 07/10/2016.
 */

public class SignUpValidationFragment extends AbstractFragment {


    private TextView confirm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.sign_up_validation,container,false);
        confirm = (TextView) view.findViewById(R.id.sign_up_confirm_validation);
        return view;
    }

    @Override
    public void initListeners() {
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DrawerActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}
