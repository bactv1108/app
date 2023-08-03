package com.example.ph26503_and_net_assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentActivity extends AppCompatActivity {
    CommentService commentService;
    private CommentAdapter commentAdapter;
    private EditText ed_comment;
    private Button btn_postcomment;
    private static final String TAG = "aaaaaaaa";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.102.12:3000/api/") // Update with your API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        commentService = retrofit.create(CommentService.class);
        RecyclerView recyclerView = findViewById(R.id.recycler_comments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentAdapter = new CommentAdapter();
        recyclerView.setAdapter(commentAdapter);
        ed_comment=findViewById(R.id.edit_comment);
        btn_postcomment = findViewById(R.id.btn_send_comment);
        btn_postcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Extract the comicId and idUser from the intent
                String comicId = getIntent().getStringExtra("comicId");
                String idUser = getIntent().getStringExtra("idUser");
                User user = new User();
                user.set_id(idUser);
                String commentText = ed_comment.getText().toString();

                Comment comment = new Comment();
                comment.setComment(commentText);
                comment.setId_user(user);
                comment.setId_comic(comicId);


                Call<ApiResponse<Comment>> call = commentService.addComment(comment);
                call.enqueue(new Callback<ApiResponse<Comment>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<Comment>> call, Response<ApiResponse<Comment>> response) {
                        if (response.isSuccessful()) {
                            ApiResponse<Comment> apiResponse = response.body();
                            if (apiResponse != null && apiResponse.getStatus() == 1) {
                                Comment addedComment = apiResponse.getData();
                                commentAdapter.addComment(addedComment);
                                String comicId = getIntent().getStringExtra("comicId");

                                // Make API request to retrieve comments
                                Call<ApiResponse<List<Comment>>> call1 = commentService.getCommentsByComicId(comicId);
                                call1.enqueue(new Callback<ApiResponse<List<Comment>>>() {
                                    @Override
                                    public void onResponse(Call<ApiResponse<List<Comment>>> call, Response<ApiResponse<List<Comment>>> response) {
                                        if (response.isSuccessful()) {
                                            ApiResponse<List<Comment>> apiResponse = response.body();
                                            if (apiResponse != null && apiResponse.getStatus() == 1) {
                                                List<Comment> comments = apiResponse.getData();
                                                commentAdapter.setCommentList(comments);
                                            } else {
                                                Log.e(TAG, "API response error: " + apiResponse.getMsg());
                                            }
                                        } else {
                                            Log.e(TAG, "API request failed. Code: " + response.code());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ApiResponse<List<Comment>>> call, Throwable t) {
                                        Log.e(TAG, "API request failed. Error: " + t.getMessage());
                                    }
                                });
                                ed_comment.setText("");
                            } else {
                                Log.e(TAG, "API response error: " + apiResponse.getMsg());
                            }
                        } else {
                            Log.e(TAG, "API request failed. Code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<Comment>> call, Throwable t) {
                        Log.e(TAG, "API request failed. Error: " + t.getMessage());
                    }
                });
            }
        });

        String comicId = getIntent().getStringExtra("comicId");

        // Make API request to retrieve comments
        Call<ApiResponse<List<Comment>>> call = commentService.getCommentsByComicId(comicId);
        call.enqueue(new Callback<ApiResponse<List<Comment>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Comment>>> call, Response<ApiResponse<List<Comment>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<Comment>> apiResponse = response.body();
                    if (apiResponse != null && apiResponse.getStatus() == 1) {
                        List<Comment> comments = apiResponse.getData();
                        commentAdapter.setCommentList(comments);
                    } else {
                        Log.e(TAG, "API response error: " + apiResponse.getMsg());
                    }
                } else {
                    Log.e(TAG, "API request failed. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Comment>>> call, Throwable t) {
                Log.e(TAG, "API request failed. Error: " + t.getMessage());
            }
        });
    }
}