package com.example.cardano.retrofitcall;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface SearchInterface {
    @FormUrlEncoded
    @POST("/api/search")
    public void details(
            @Field("search") String query,

            Callback<Response> callback);
}
