package com.openteam.ot.gui.fragment;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.openteam.ot.R;
import com.openteam.ot.model.Campaign;
import com.squareup.picasso.Picasso;

/**
 * Created by zoz on 07/10/2016.
 */

public class CampaignDetailsFragment extends AbstractFragment{

    private static final String TAG = "CampaignDetailsFrag";
    private TextView supportButton;
    private ImageView mainImage;
    private ImageView themeImage;
    private TextView subtitle;
    private TextView country;
    private TextView mission;
    private TextView whySupport;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.campaign_details,container,false);
        getActivity().overridePendingTransition(0, R.anim.splash_fade_out);

        mainImage = (ImageView) view.findViewById(R.id.main_image);
        themeImage = (ImageView) view.findViewById(R.id.theme_image);

        subtitle = (TextView) view.findViewById(R.id.sub_title);
        country = (TextView) view.findViewById(R.id.country);
        mission = (TextView) view.findViewById(R.id.mission_text);
        whySupport = (TextView) view.findViewById(R.id.why_support_text);

        supportButton = (TextView) view.findViewById(R.id.support_button);

        initListeners();

        Object obj = getArguments().getSerializable("obj");
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


        return view;
    }

    @Override
    public void initListeners() {
        supportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAbsActivity().replaceFragment(new PaymentConfirmationFragment());
            }
        });
    }

    private void bindData(final Campaign campaign) {
        com.squareup.picasso.Callback callback = new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //title.setText(campaign.getProject_title().toUpperCase());
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

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        Picasso.with(getActivity()).load(campaign.getPicture_url()).resize(width,width).centerInside().into(mainImage, callback);
    }
}
