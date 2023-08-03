package com.example.ph26503_and_net_assignment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CommentService {
    @GET("comments")
    Call<ApiResponse<List<Comment>>> getCommentsByComicId(@Query("id_comic") String comicId);
    @POST("comments")
    Call<ApiResponse<Comment>> addComment(@Body Comment comment);
}
