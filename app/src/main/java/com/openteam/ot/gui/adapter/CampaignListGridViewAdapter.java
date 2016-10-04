package com.openteam.ot.gui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.openteam.ot.R;
import com.openteam.ot.gui.custom.RoundedCornersTransformation;
import com.openteam.ot.model.Campaign;
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

        View gridView;

        if(campaigns == null){
            gridView = parent;
        }else if (convertView == null) {
            gridView = new View(context);
            gridView = inflater.inflate(R.layout.campaign_list_item, null);

            Campaign campaign = campaigns.get(position);

            //load image
            ImageView imageView = (ImageView) gridView.findViewById(R.id.image);
            Picasso.with(context).load(campaign.getPicture_url()).transform(new RoundedCornersTransformation(15,15)).into(imageView);
            //insert title
            TextView title = (TextView) gridView.findViewById(R.id.title);
            title.setText(campaign.getCampaign_title());

            //insert description
            TextView description = (TextView) gridView.findViewById(R.id.description);
            description.setText(campaign.getId());

        } else {
            gridView = (View) convertView;
        }

        return gridView;
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
