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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.openteam.ot.R;
import com.openteam.ot.gui.activity.CampaignDetailsActivity;
import com.openteam.ot.gui.adapter.CampaignListGridViewAdapter;
import com.openteam.ot.model.Campaign;
import com.openteam.ot.service.BackendManager;
import com.openteam.ot.service.BackendService;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.services.concurrency.AsyncTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zoz on 04/10/2016.
 */

public class CampaignListFragment extends AbstractFragment{

    private static final String TAG = "CampaignListActivity";
    private GridView gridView;
    private List<Campaign> campaigns;
    private CampaignListGridViewAdapter adapter;
    private TextView openBtn;
    private TextView closedBtn;
    private BackendService backendService;
    private Animation animation;
    private ImageView loader;

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

        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
        gridView.setAnimation(animation);
        openBtn = (TextView) view.findViewById(R.id.open);
        closedBtn = (TextView) view.findViewById(R.id.closed);

        loader = (ImageView)view.findViewById(R.id.loader);
        displayLoader(false);
        updateButtonsColor(null);
        return view;
    }

    @Override
    public void initListeners() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Campaign campaign = campaigns.get(position);
                Intent intent = new Intent(getActivity(), CampaignDetailsActivity.class);
                intent.putExtra("obj",campaign);
                startActivity(intent);
            }
        });

        openBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridView.startAnimation(animation);
                updateButtonsColor(openBtn);
                updateGridView(getOpenCampaigns());
            }
        });

        closedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridView.startAnimation(animation);
                updateButtonsColor(closedBtn);
                updateGridView(getClosedCampaigns());
            }
        });
    }

    private void updateGridView(Call<List<Campaign>> call){
        final long start = System.currentTimeMillis();
        clearData();
        displayLoader(true);
        call.enqueue(new Callback<List<Campaign>>() {
            @Override
            public void onResponse(Call<List<Campaign>> call, Response<List<Campaign>> response) {
                final List<Campaign> campaigns = response.body();
                if(campaigns == null){
                    Log.d(TAG, "No Campaign found");
                }else{
                    new AsyncTask<Void, Void, Void>(){
                        @Override
                        protected Void doInBackground(Void... voids) {
                            long time = System.currentTimeMillis() - start;
                            if(time < 500){
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            loadData(campaigns);
                            displayLoader(false);
                        }
                    }.execute();
                }
            }

            @Override
            public void onFailure(Call<List<Campaign>> call, Throwable t) {
                Log.e(TAG, t.toString());
                displayLoader(false);
            }
        });
    }

    protected Call<List<Campaign>> getOpenCampaigns(){
        return backendService.getOpenCampaigns();
    }

    protected Call<List<Campaign>> getClosedCampaigns(){
        return backendService.getClosedCampaigns();
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

    private void updateButtonsColor(TextView selectedBtn){
        openBtn.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.campaign_list_button_unselected));
        closedBtn.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.campaign_list_button_unselected));
        openBtn.setTextColor(ContextCompat.getColor(getActivity(), R.color.btnUnselectedTextColor));
        closedBtn.setTextColor(ContextCompat.getColor(getActivity(), R.color.btnUnselectedTextColor));

        if (selectedBtn != null){
            selectedBtn.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.campaign_list_button_selected));
            selectedBtn.setTextColor(Color.WHITE);
        }
    }

    private Animation createRotateAnimation(){
        RotateAnimation rotate = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        rotate.setDuration(1000);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setRepeatCount(Animation.INFINITE);
        return rotate;
    }

    private void displayLoader(boolean display){
        if(display){
            loader.setVisibility(View.VISIBLE);
            loader.setAnimation(createRotateAnimation());
        }else{
            loader.setAnimation(null);
            loader.setVisibility(View.INVISIBLE);
        }
    }


    private void clearData(){
        this.campaigns.clear();
        adapter.notifyDataSetChanged();
    }

    private void loadData(List<Campaign> campaigns){
        this.campaigns.clear();
        this.campaigns.addAll(campaigns);
        adapter.notifyDataSetChanged();
    }
}
