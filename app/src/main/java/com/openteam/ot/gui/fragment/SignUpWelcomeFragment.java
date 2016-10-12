package com.openteam.ot.gui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openteam.ot.R;
import com.openteam.ot.gui.activity.DrawerActivity;

/**
 * Created by zoz on 12/10/2016.
 */

public class SignUpWelcomeFragment extends AbstractFragment{

    private View enableNotification;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.sign_up_welcome,container,false);
        getBasicActivity().getToolbarTitle().setText(getResources().getString(R.string.welcome).toUpperCase());
        enableNotification = view.findViewById(R.id.enable_notification);
        getBasicActivity().getArrow().setVisibility(View.INVISIBLE);
        getBasicActivity().setLockBackPressed(true);
        return view;
    }

    @Override
    public void initListeners() {
        enableNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DrawerActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}
