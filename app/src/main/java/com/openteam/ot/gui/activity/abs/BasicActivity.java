package com.openteam.ot.gui.activity.abs;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.openteam.ot.R;


/**
 * Created by zoz on 05/10/2016.
 */

public abstract class BasicActivity extends AbstractActivity {

    private static final String TAG = "BasicActivity";
    protected Toolbar toolbar;
    protected TextView toobarTitle;
    protected ImageButton arrow;

    public BasicActivity(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        overridePendingTransition(0, R.anim.splash_fade_out);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar.setPadding(0,0,0,0);//for tab otherwise give space in tab

        toobarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toobarTitle.setText(getResources().getString(R.string.title_campaigns_list).toUpperCase());
        arrow = (ImageButton) toolbar.findViewById(R.id.arrow);

    }

    @Override
    protected void initListeners() {
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getSupportFragmentManager().getBackStackEntryCount() > 1){
                    getSupportFragmentManager().popBackStack();
                }else{
                    finish();
                }
                BasicActivity.this.overridePendingTransition(0, R.anim.splash_fade_out);
            }
        });
    }


    @Override
    protected int getReplaceableFragmentContainerId() {
        return R.id.content_main;
    }

    @Override
    protected int getContentView() {
        return R.layout.basic_main;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BasicActivity.this.overridePendingTransition(0, R.anim.splash_fade_out);
    }

    public TextView getToolbarTitle() {
        return toobarTitle;
    }
}
