package com.openteam.ot.gui.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.RelativeLayout;

import com.openteam.ot.R;
import com.openteam.ot.gui.activity.abs.BasicActivity;
import com.openteam.ot.gui.fragment.SignInFragment;

/**
 * Created by zoz on 07/10/2016.
 */

public class SignActivity extends BasicActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout layout = (RelativeLayout) toolbar.findViewById(R.id.basic_toolbar);
        layout.setBackground(ContextCompat.getDrawable(this,R.drawable.sign_bar));
        title.setText(getResources().getString(R.string.sign_up).toUpperCase());
        replaceFragment(new SignInFragment());
    }

}
