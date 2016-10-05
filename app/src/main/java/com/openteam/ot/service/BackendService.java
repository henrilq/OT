package com.openteam.ot.service;

import com.openteam.ot.model.Campaign;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by zoz on 04/10/2016.
 */

public interface BackendService {

    String ENDPOINT = "http://private-3afef-openteam.apiary-mock.com";

    @GET("/project/{id}")
    Call<Campaign> getCampaign(@Path("id") int id);

    @GET("/projects/closed")
    Call<List<Campaign>> getClosedCompaigns();

    @GET("/projects/open")
    Call<List<Campaign>> getOpenCampaigns();
}
