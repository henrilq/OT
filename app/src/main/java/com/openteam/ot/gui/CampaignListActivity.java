package com.openteam.ot.gui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.openteam.ot.R;
import com.openteam.ot.gui.adapter.CampaignListGridViewAdapter;
import com.openteam.ot.model.Campaign;
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

public class CampaignListActivity extends AppCompatActivity {

    private static final String TAG = "CampaignListActivity";
    private GridView gridView;
    private List<Campaign> campaigns;
    private CampaignListGridViewAdapter adapter;
    private Button openBtn;
    private Button closedBtn;
    private BackendService backendService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.tool_bar);

        setContentView(R.layout.campaign_list);

        backendService = getBackendService();
        campaigns = new ArrayList<>();

        gridView = (GridView) findViewById(R.id.grid);
        adapter = new CampaignListGridViewAdapter(CampaignListActivity.this, campaigns);
        gridView.setAdapter(adapter);

        openBtn = (Button) findViewById(R.id.open);
        openBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButtonsColor(openBtn);
                Call<List<Campaign>> open = backendService.getOpenCampaigns();
                open.enqueue(new Callback<List<Campaign>>() {
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
        });

        closedBtn = (Button) findViewById(R.id.closed);
        closedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButtonsColor(closedBtn);
                Call<List<Campaign>> closed = backendService.getClosedCompaigns();
                closed.enqueue(new Callback<List<Campaign>>() {
                    @Override
                    public void onResponse(Call<List<Campaign>> call, Response<List<Campaign>> response) {
                        List<Campaign> campaigns = response.body();
                        campaigns.remove(0);
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
        });
        updateButtonsColor(null);
    }

    private void updateButtonsColor(Button selectedBtn){
        openBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.campaign_list_button_unselected));
        closedBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.campaign_list_button_unselected));
        openBtn.setTextColor(ContextCompat.getColor(this, R.color.btnUnselectedTextColor));
        closedBtn.setTextColor(ContextCompat.getColor(this, R.color.btnUnselectedTextColor));

        if (selectedBtn != null){
            selectedBtn.setBackground(ContextCompat.getDrawable(this, R.drawable.campaign_list_button_selected));
            selectedBtn.setTextColor(Color.WHITE);
        }
    }

    private BackendService getBackendService(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BackendService.ENDPOINT)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(BackendService.class);
    }

    private void loadData(List<Campaign> campaigns){
        this.campaigns.clear();
        this.campaigns.addAll(campaigns);
        adapter.notifyDataSetChanged();
        gridView.setAdapter(adapter);
    }
}
