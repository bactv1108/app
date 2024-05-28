package com.example.ph26503_and_net_assignment;

public class CommentUpdate {
    private String _id;
    private String comment;
    private String date;
    private String id_user;
    private String id_comic;

    public CommentUpdate(String _id, String comment, String date, String id_user, String id_comic) {
        this._id = _id;
        this.comment = comment;
        this.date = date;
        this.id_user = id_user;
        this.id_comic = id_comic;
    }

    public CommentUpdate() {}

    public CommentUpdate(String comment) {
        this.comment = comment;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_comic() {
        return id_comic;
    }

    public void setId_comic(String id_comic) {
        this.id_comic = id_comic;
    }
}
