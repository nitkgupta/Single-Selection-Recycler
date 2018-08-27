package com.jungleworks.adapter;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetImage {
    @GET("/photos")
    Call<List<RetroPhoto>> getAllPhotos();
}
