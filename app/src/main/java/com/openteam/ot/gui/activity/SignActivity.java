package com.openteam.ot.gui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.RelativeLayout;

import com.openteam.ot.R;
import com.openteam.ot.gui.activity.abs.BasicActivity;
import com.openteam.ot.gui.fragment.AbstractFragment;
import com.openteam.ot.gui.fragment.SignUpFragment;

/**
 * Created by zoz on 07/10/2016.
 */

public class SignActivity extends BasicActivity {

    AbstractFragment activeFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout layout = (RelativeLayout) toolbar.findViewById(R.id.basic_toolbar);
        layout.setBackground(ContextCompat.getDrawable(this,R.drawable.sign_bar));
        title.setText(getResources().getString(R.string.sign_up).toUpperCase());
        activeFragment = new SignUpFragment();
        replaceFragment(activeFragment);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Horrible but it works !
        if(activeFragment != null && activeFragment.isVisible()){
            activeFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

}
