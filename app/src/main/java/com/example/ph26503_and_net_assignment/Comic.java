package com.example.ph26503_and_net_assignment;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Comic implements Parcelable {

        private String _id;
        private List<String> content;
        private String name;
        private String description;
        private String author;
        private String year;
        private String cover;

    public Comic(String _id, ArrayList<String> content, String name, String description, String author, String year, String cover) {
        this._id = _id;
        this.content = content;
        this.name = name;
        this.description = description;
        this.author = author;
        this.year = year;
        this.cover = cover;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = _id;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(ArrayList<String> content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(name);
        dest.writeString(author);
        dest.writeString(year);
        dest.writeStringList(content);
        dest.writeString(cover);
        dest.writeString(description);

    }
    protected Comic(Parcel in) {
        _id = in.readString();
        name = in.readString();
        author = in.readString();
        year = in.readString();
        content = new ArrayList<>();
        in.readStringList(content);  // Read the list of images
        cover = in.readString();
        description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Comic> CREATOR = new Creator<Comic>() {
        @Override
        public Comic createFromParcel(Parcel in) {
            return new Comic(in);
        }

        @Override
        public Comic[] newArray(int size) {
            return new Comic[size];
        }
    };
}