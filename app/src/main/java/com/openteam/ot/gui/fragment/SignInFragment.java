package com.openteam.ot.gui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.openteam.ot.R;

/**
 * Created by zoz on 07/10/2016.
 */

public class SignInFragment extends AbstractFragment{

    private TextView confirm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.sign_in,container,false);
        confirm = (TextView) view.findViewById(R.id.sign_up_confirm);
        return view;
    }

    @Override
    public void initListeners() {
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAbsActivity().replaceFragment(new SmsValidation(), R.anim.trans_left_in, R.anim.trans_left_out);
            }
        });
    }
}
