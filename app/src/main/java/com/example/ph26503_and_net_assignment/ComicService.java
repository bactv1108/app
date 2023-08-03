package com.example.ph26503_and_net_assignment;

import com.example.ph26503_and_net_assignment.Service.ComicResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ComicService {
    @GET("comics")
    Call<ComicResponse> getComics();
}
