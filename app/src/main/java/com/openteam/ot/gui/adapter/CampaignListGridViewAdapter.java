package com.openteam.ot.gui.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.openteam.ot.R;
import com.openteam.ot.gui.custom.RoundedCornersTransformation;
import com.openteam.ot.model.Campaign;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zoz on 04/10/2016.
 */

public class CampaignListGridViewAdapter extends BaseAdapter{

    private Context context;
    private List<Campaign> campaigns;

    public CampaignListGridViewAdapter(Context context, List<Campaign> campaigns){
        this.context = context;
        this.campaigns = campaigns;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final Handler handler = new Handler();
        final View itemView;

        if(campaigns == null){
            itemView = parent;
        }else if (convertView == null) {
            itemView = inflater.inflate(R.layout.campaign_list_item, null);
            itemView.setVisibility(View.INVISIBLE);
            final Campaign campaign = campaigns.get(position);
            final TextView title = (TextView) itemView.findViewById(R.id.title);
            final TextView description = (TextView) itemView.findViewById(R.id.description);
            //load image
            ImageView imageView = (ImageView) itemView.findViewById(R.id.image);

            Callback callback = new Callback() {
                @Override
                public void onSuccess() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //insert title
                            title.setText(campaign.getCampaign_title());

                            //insert description
                            description.setText(campaign.getId());

                            itemView.setVisibility(View.VISIBLE);
                        }
                    });
                }

                @Override
                public void onError() {

                }
            };

            Picasso.with(context).load(campaign.getPicture_url()).transform(new RoundedCornersTransformation(15,15)).into(imageView, callback);

        } else {
            itemView = convertView;
        }

        return itemView;
    }

    @Override
    public int getCount() {
        return campaigns.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
