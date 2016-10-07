package com.openteam.ot.gui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by zoz on 04/10/2016.
 */

public abstract class AbstractFragment extends Fragment{

    protected String title;

    public String getTitle() {
        return title;
    }

    public abstract void initListeners();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        initListeners();
    }
}
