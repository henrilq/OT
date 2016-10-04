package com.openteam.ot.gui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.openteam.ot.R;
import com.openteam.ot.model.Campaign;

import java.util.List;

import retrofit2.Call;

/**
 * Created by zoz on 04/10/2016.
 */

public class MyCampaignListFragment extends CampaignListFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.title = getResources().getString(R.string.title_my_campaigns);
    }

    @Override
    protected Call<List<Campaign>> getOpenCampaigns() {
        return super.getOpenCampaigns();
    }

    @Override
    protected Call<List<Campaign>> getClosedCampaigns() {
        return super.getClosedCampaigns();
    }
}
