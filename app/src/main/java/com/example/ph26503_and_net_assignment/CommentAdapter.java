package com.example.ph26503_and_net_assignment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.text.format.DateUtils;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<Comment> commentList;
    private List<CommentUpdate> commentUpdates;
    public CommentAdapter() {
        commentList = new ArrayList<>();
    }

    public void setCommentList(List<Comment> comments) {
        commentList = comments;
        notifyDataSetChanged();
        Log.d("CommentAdapter", "Comment list size: " + commentList.size());
    }
    public void addComment(Comment comment) {
        commentList.add(comment);
        notifyItemInserted(commentList.size() - 1);
    }


    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);

        // Check if the comment or user data is null
        if (comment == null || comment.getId_user() == null) {
            holder.textUsername.setText("Loading...");
            holder.textComment.setText("Loading...");
            holder.textDate.setText("Loading...");
            holder.img_avatar.setImageResource(R.drawable.user);
            return;
        }
        holder.textUsername.setText(comment.getId_user().getUsername());
        holder.textComment.setText(comment.getComment());
        holder.textDate.setText(getTimeDifference(comment.getDate(), holder.itemView.getContext()));
        Glide.with(holder.itemView.getContext())
                .load(comment.getId_user().getImage()).circleCrop()
                .into(holder.img_avatar);
        SharedPreferences prefs = holder.itemView.getContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String currentUserId = prefs.getString("currentUserId", null);
        SharedPreferences prefs1 = holder.itemView.getContext().getSharedPreferences("luuidcomic", Context.MODE_PRIVATE);
        String currentcomicid = prefs.getString("currentcomicid", null);

        Log.d("CommentAdapter", "currentUserId: " + currentUserId);
        Log.d("Logthu", "currentUserId: " + comment.getId_user());
        if (comment.getId_user().get_id().equals(currentUserId)) {
            holder.deleteButton.setImageResource(R.drawable.delete);
            holder.deleteButton.setVisibility(View.VISIBLE);
        } else {
            holder.deleteButton.setVisibility(View.GONE);
        }
        if (comment.getId_user().get_id().equals(currentUserId)) {
            holder.updateButton.setImageResource(R.drawable.refress);
            holder.updateButton.setVisibility(View.VISIBLE);
        } else {
            holder.updateButton.setVisibility(View.GONE);
        }

        holder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());
                View dialogView = inflater.inflate(R.layout.dialog_update_comment, null);
                builder.setView(dialogView);
                EditText editTextComment = dialogView.findViewById(R.id.editTextComment);
                Button buttonUpdate = dialogView.findViewById(R.id.buttonUpdate);
                Button buttonCancel = dialogView.findViewById(R.id.buttonCancel);
                User user = new User();
                user.set_id(currentUserId);
                editTextComment.setText(comment.getComment());
                Comment comment = new Comment();
                comment.setComment(editTextComment.getText().toString());
                comment.setId_user(user);
                comment.setId_comic(currentcomicid);
                AlertDialog dialog = builder.create();
                dialog.show();

                buttonUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newCommentText = editTextComment.getText().toString().trim();
                        if (newCommentText.isEmpty()) {
                            editTextComment.setError("Comment cannot be empty");
                            editTextComment.requestFocus();
                            return;
                        }

                        int position = holder.getAdapterPosition();
                        Comment comment = commentList.get(position);

                        // Update the comment object with the new text
                        comment.setComment(newCommentText);

                        // Set the id_user and id_comic fields of the comment object
                        comment.getId_user().set_id(currentUserId);
                        comment.setId_comic(currentcomicid);

                        // Create a new Retrofit instance and call the updateComment() method
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://10.24.30.145:3000/api/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        CommentService commentService = retrofit.create(CommentService.class);

                        // Call the updateComment() method and pass the updated comment object and comment ID
                        Call<Comment> call = commentService.updateComment(comment.get_id(), comment);
                        call.enqueue(new Callback<Comment>() {
                            @Override
                            public void onResponse(Call<Comment> call, Response<Comment> response) {
                                if (response.isSuccessful()) {
                                    // Update the comment object in the list
                                    commentList.set(position, response.body());

                                    // Notify the adapter that the data has changed
                                    notifyDataSetChanged();
                                } else {
                                    // Handle the error case
                                }
                            }

                            @Override
                            public void onFailure(Call<Comment> call, Throwable t) {
                                // Handle the error case
                            }
                        });

                        dialog.dismiss();
                    }
                });

                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });


        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                    builder.setTitle("Xóa Bình Luận");
                    builder.setMessage("Bạn có muốn xóa bình luận này không?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("http://10.24.30.145:3000/api/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            CommentService apiService = retrofit.create(CommentService.class);

                            Call<Comment> call = apiService.deleteComment(commentList.get(position).get_id());
                            call.enqueue(new Callback<Comment>() {
                                @Override
                                public void onResponse(Call<Comment> call, Response<Comment> response) {
                                    if (response.isSuccessful()) {
                                        commentList.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, commentList.size());
                                    }
                                }

                                @Override
                                public void onFailure(Call<Comment> call, Throwable t) {
                                    Log.e("CommentAdapter", "Error deleting comment: " + t.getMessage());
                                }
                            });
                        }
                    });
                    builder.setNegativeButton("Không", null);
                    builder.show();
                }
            }
        });
    }


    public void addComment(String comment) {
        Comment newComment = new Comment();
        newComment.setComment(comment);
        commentList.add(newComment);
        notifyItemInserted(commentList.size() - 1);
    }
    @Override
    public int getItemCount() {
        return commentList.size();
    }
    private String getTimeDifference(String date, Context context) {
        long commentTime = Utils.convertStringToTimestamp(date);
        long currentTime = System.currentTimeMillis();
        long timeDifference = currentTime - commentTime;
        return DateUtils.getRelativeTimeSpanString(commentTime, currentTime, DateUtils.SECOND_IN_MILLIS).toString();
    }


    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView textUsername;
        TextView textComment;
        TextView textDate;
        ImageView img_avatar;
         ImageView deleteButton,updateButton;


        public CommentViewHolder(View itemView) {
            super(itemView);
            textUsername = itemView.findViewById(R.id.text_username);
            textComment = itemView.findViewById(R.id.text_comment);
            textDate = itemView.findViewById(R.id.text_date);
            img_avatar = itemView.findViewById(R.id.avatar);
            deleteButton = itemView.findViewById(R.id.img_deleteSP);
            updateButton = itemView.findViewById(R.id.img_updateSP);

        }
    }
}
