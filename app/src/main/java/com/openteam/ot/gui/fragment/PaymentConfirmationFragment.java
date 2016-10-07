package com.openteam.ot.gui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openteam.ot.R;

/**
 * Created by zoz on 06/10/2016.
 */

public class PaymentConfirmationFragment extends AbstractFragment{

    private static final String TAG = "PayConfirmation";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.title = getResources().getString(R.string.title_campaigns_list);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.payment_confirmation,container,false);

        return view;
    }

    @Override
    public void initListeners() {

    }
}
