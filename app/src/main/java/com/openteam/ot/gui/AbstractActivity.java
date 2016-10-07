package com.openteam.ot.gui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.AnimRes;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.openteam.ot.R;
import com.openteam.ot.gui.fragment.AbstractFragment;

/**
 * Created by zoz on 06/10/2016.
 */

public abstract class AbstractActivity extends AppCompatActivity {


    protected Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
    }

    public void replaceFragment(final AbstractFragment fragment){
        replaceFragment(fragment, getReplaceableFragmentContainerId(), R.anim.fade_in, R.anim.fade_out);
    }

    public void replaceFragment(final AbstractFragment fragment, @IdRes int containerViewId, @AnimRes int enter,
                                @AnimRes int exit){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(enter, exit);
        transaction.replace(containerViewId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        initListeners();
    }

    protected abstract void initListeners();


    /**
     * @return replaceable fragment container id
     */
    protected abstract int getReplaceableFragmentContainerId();


}
