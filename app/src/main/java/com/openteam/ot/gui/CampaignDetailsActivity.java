package com.openteam.ot.gui;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.openteam.ot.R;
import com.openteam.ot.model.Campaign;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


/**
 * Created by zoz on 05/10/2016.
 */

public class CampaignDetailsActivity extends AppCompatActivity {

    private static final String TAG = "CampaignDetailsActivity";
    private Handler handler;
    private Toolbar toolbar;

    public CampaignDetailsActivity(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.campaign_details);
        overridePendingTransition(0, R.anim.splash_fade_out);
        handler = new Handler();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        //toolbar.setPadding(50,0,50,0);//for tab otherwise give space in tab

        TextView title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        title.setText(getResources().getString(R.string.title_campaigns_list).toUpperCase());

        ImageView arrow = (ImageView) toolbar.findViewById(R.id.arrow);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                CampaignDetailsActivity.this.overridePendingTransition(0, R.anim.splash_fade_out);
            }
        });

        Object obj = getIntent().getSerializableExtra("obj");
        if(obj != null){
            bindData((Campaign) obj);
            /*BackendService backendService = BackendManager.getBackendService();
            Call<Campaign> call = backendService.getCampaign(Integer.valueOf(""+id));
            call.enqueue(new retrofit2.Callback<Campaign>() {
                @Override
                public void onResponse(Call<Campaign> call, Response<Campaign> response) {
                    Campaign campaign = response.body();
                    if(campaign == null){
                        Log.d(TAG, "No Campaign found");
                    }else{
                        bindData(campaign);
                    }
                }

                @Override
                public void onFailure(Call<Campaign> call, Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });*/
        }
    }

    private void bindData(final Campaign campaign) {
        ImageView mainImage = (ImageView) findViewById(R.id.main_image);
        ImageView themeImage = (ImageView) findViewById(R.id.theme_image);

        final TextView subtitle = (TextView) findViewById(R.id.sub_title);
        final TextView country = (TextView) findViewById(R.id.country);
        final TextView mission = (TextView) findViewById(R.id.mission_text);
        final TextView whySupport = (TextView) findViewById(R.id.why_support_text);

        Callback callback = new Callback() {
            @Override
            public void onSuccess() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        subtitle.setText(campaign.getProject_title());
                        country.setText(campaign.getCountry());
                        mission.setText(campaign.getMission());
                        whySupport.setText(campaign.getWhy_support());
                    }
                });
            }

            @Override
            public void onError() {
                Log.e(TAG,"image not loaded");
            }
        };

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        Picasso.with(this).load(campaign.getPicture_url()).resize(width,width).centerInside().into(mainImage, callback);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CampaignDetailsActivity.this.overridePendingTransition(0, R.anim.splash_fade_out);
    }
}
