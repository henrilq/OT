package com.openteam.ot.gui.activity;

import android.os.Bundle;

import com.openteam.ot.gui.activity.abs.BasicActivity;
import com.openteam.ot.gui.fragment.CampaignDetailsFragment;
import com.openteam.ot.model.Campaign;

/**
 * Created by zoz on 07/10/2016.
 */

public class CampaignDetailsActivity extends BasicActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Object obj = getIntent().getSerializableExtra("obj");
        CampaignDetailsFragment fragment = new CampaignDetailsFragment();
        if(obj != null){
            Bundle args = new Bundle();
            args.putSerializable("obj", (Campaign)obj);
            fragment.setArguments(args);
        }
        replaceFragment(fragment);
    }

}
