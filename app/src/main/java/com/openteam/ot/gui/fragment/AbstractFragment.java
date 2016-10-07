package com.openteam.ot.gui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.openteam.ot.gui.activity.abs.AbstractActivity;

/**
 * Created by zoz on 04/10/2016.
 */

public abstract class AbstractFragment extends Fragment{

    protected String title;
    protected Handler handler;

    public String getTitle() {
        return title;
    }

    public abstract void initListeners();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        initListeners();
    }

    protected AbstractActivity getAbsActivity(){
        return (AbstractActivity) getActivity();
    }
}
