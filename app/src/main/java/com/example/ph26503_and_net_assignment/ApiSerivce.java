package com.example.ph26503_and_net_assignment;

import com.example.ph26503_and_net_assignment.Service.UserResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiSerivce {
    @GET("users")
    Call<UserResponse> login(
            @Query("username") String username,
            @Query("password") String password
    );
    @POST("users")
    Call<UserResponse> register(
            @Body User user
    );
    @PUT("users/edit/{id}")
    Call<ApiResponse> updateUserImage(@Path("id") String userId, @Body Map<String, String> imageMap);
}
