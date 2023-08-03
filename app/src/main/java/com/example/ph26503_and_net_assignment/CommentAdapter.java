package com.example.ph26503_and_net_assignment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<Comment> commentList;

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
        holder.textDate.setText(comment.getDate());
        Glide.with(holder.itemView.getContext())
                .load(comment.getId_user().getImage()).circleCrop()
                .into(holder.img_avatar);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView textUsername;
        TextView textComment;
        TextView textDate;
        ImageView img_avatar;

        public CommentViewHolder(View itemView) {
            super(itemView);
            textUsername = itemView.findViewById(R.id.text_username);
            textComment = itemView.findViewById(R.id.text_comment);
            textDate = itemView.findViewById(R.id.text_date);
            img_avatar = itemView.findViewById(R.id.avatar);
        }
    }
}
