package com.example.ph26503_and_net_assignment;

import com.example.ph26503_and_net_assignment.Service.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiSerivce {
    @GET("users")
    Call<UserResponse> login(
            @Query("username") String username,
            @Query("password") String password
    );

}
