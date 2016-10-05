package com.openteam.ot.gui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.openteam.ot.R;
import com.openteam.ot.gui.CampaignDetailsActivity;
import com.openteam.ot.gui.adapter.CampaignListGridViewAdapter;
import com.openteam.ot.model.Campaign;
import com.openteam.ot.service.BackendManager;
import com.openteam.ot.service.BackendService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zoz on 04/10/2016.
 */

public class CampaignListFragment extends AbstractFragment{

    private static final String TAG = "CampaignListActivity";
    private GridView gridView;
    private List<Campaign> campaigns;
    private CampaignListGridViewAdapter adapter;
    private Button openBtn;
    private Button closedBtn;
    private BackendService backendService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.title = getResources().getString(R.string.title_campaigns_list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.campaign_list,container,false);
        backendService = BackendManager.getBackendService();
        campaigns = new ArrayList<>();

        gridView = (GridView) view.findViewById(R.id.grid);
        adapter = new CampaignListGridViewAdapter(getActivity(), campaigns);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Campaign campaign = campaigns.get(position);
                Intent intent = new Intent(getActivity(), CampaignDetailsActivity.class);
                Bundle args = new Bundle();
                args.putString("id",campaign.getId());
                intent.putExtras(args);
                startActivity(intent);
            }
        });

        openBtn = (Button) view.findViewById(R.id.open);
        openBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridView.setAnimation(createAnimation());
                updateButtonsColor(openBtn);
                updateGridView(getOpenCampaigns());
            }
        });

        closedBtn = (Button) view.findViewById(R.id.closed);
        closedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridView.setAnimation(createAnimation());
                updateButtonsColor(closedBtn);
                updateGridView(getClosedCampaigns());
            }
        });
        updateButtonsColor(null);
        return view;
    }

    private void updateGridView(Call<List<Campaign>> call){
        call.enqueue(new Callback<List<Campaign>>() {
            @Override
            public void onResponse(Call<List<Campaign>> call, Response<List<Campaign>> response) {
                List<Campaign> campaigns = response.body();
                if(campaigns == null){
                    Log.d(TAG, "No Campaign found");
                }else{
                    loadData(campaigns);
                }
            }

            @Override
            public void onFailure(Call<List<Campaign>> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    protected Call<List<Campaign>> getOpenCampaigns(){
        return backendService.getOpenCampaigns();
    }

    protected Call<List<Campaign>> getClosedCampaigns(){
        return backendService.getClosedCompaigns();
    }

    protected AnimationSet createAnimation(){
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(500);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        //fadeOut.setStartOffset(1000);
        fadeOut.setDuration(1000);

        AnimationSet animation = new AnimationSet(false);
        //animation.addAnimation(fadeOut);
        animation.addAnimation(fadeIn);
        return animation;
    }

    private void updateButtonsColor(Button selectedBtn){
        openBtn.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.campaign_list_button_unselected));
        closedBtn.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.campaign_list_button_unselected));
        openBtn.setTextColor(ContextCompat.getColor(getActivity(), R.color.btnUnselectedTextColor));
        closedBtn.setTextColor(ContextCompat.getColor(getActivity(), R.color.btnUnselectedTextColor));

        if (selectedBtn != null){
            selectedBtn.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.campaign_list_button_selected));
            selectedBtn.setTextColor(Color.WHITE);
        }
    }

    private void loadData(List<Campaign> campaigns){
        this.campaigns.clear();
        this.campaigns.addAll(campaigns);
        adapter.notifyDataSetChanged();
        gridView.setAdapter(adapter);
    }
}
