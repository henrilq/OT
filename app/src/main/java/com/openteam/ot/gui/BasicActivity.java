package com.openteam.ot.gui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.openteam.ot.R;
import com.openteam.ot.gui.fragment.CampaignDetailsFragment;
import com.openteam.ot.model.Campaign;


/**
 * Created by zoz on 05/10/2016.
 */

public class BasicActivity extends AbstractActivity {

    private static final String TAG = "BasicActivity";
    private Toolbar toolbar;
    private TextView title;
    private ImageButton arrow;

    public BasicActivity(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_main);
        overridePendingTransition(0, R.anim.splash_fade_out);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        //toolbar.setPadding(50,0,50,0);//for tab otherwise give space in tab

        title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        title.setText(getResources().getString(R.string.title_campaigns_list).toUpperCase());
        arrow = (ImageButton) toolbar.findViewById(R.id.arrow);



        Object obj = getIntent().getSerializableExtra("obj");
        CampaignDetailsFragment fragment = new CampaignDetailsFragment();
        if(obj != null){
            Bundle args = new Bundle();
            args.putSerializable("obj", (Campaign)obj);
            fragment.setArguments(args);
        }
        replaceFragment(fragment);

    }

    @Override
    protected void initListeners() {
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                BasicActivity.this.overridePendingTransition(0, R.anim.splash_fade_out);
            }
        });
    }


    @Override
    protected int getReplaceableFragmentContainerId() {
        return R.id.content_main;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BasicActivity.this.overridePendingTransition(0, R.anim.splash_fade_out);
    }
}
