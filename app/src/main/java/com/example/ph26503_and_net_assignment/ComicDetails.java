package com.example.ph26503_and_net_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.Glide;

import java.util.List;

public class ComicDetails extends AppCompatActivity {
    TextView author, comicname, year, description;
    ImageView imgcover, imgcontent;
    Button btn_read,btn_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_details);
        author = findViewById(R.id.textAuthor);
        imgcover = findViewById(R.id.imageCover);
        comicname = findViewById(R.id.textName);
        year = findViewById(R.id.textYear);
        description = findViewById(R.id.textDescription);
        btn_read = findViewById(R.id.btn_read);
        btn_comment = findViewById(R.id.btn_comment);
        Intent intent = getIntent();

        if (intent != null && intent.hasExtra("comic")) {
            Comic comic = intent.getParcelableExtra("comic");
            author.setText("Tác giả: "+comic.getAuthor());
            comicname.setText("Tên Truyện: "+comic.getName());
            year.setText("Năm: "+comic.getYear());
            Glide.with(this)
                    .load(comic.getCover())
                    .into(imgcover);


            description.setText("Mô tả: "+comic.getDescription());






        }
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comic comic = intent.getParcelableExtra("comic");
                String comicId = comic.getId(); // Assuming the Comic class has a method to retrieve the _id
                String idU=intent.getStringExtra("idUser");
                // Open the comment activity with the comicId
                Intent commentIntent = new Intent(ComicDetails.this, CommentActivity.class);
                commentIntent.putExtra("comicId", comicId);
                commentIntent.putExtra("idUser",idU);
                startActivity(commentIntent);
            }
        });
        btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comic comic = intent.getParcelableExtra("comic");
                Intent intent1 = new Intent(ComicDetails.this, ReadComic.class);
                intent1.putExtra("comic1", comic);
                startActivity(intent1);
            }
        });

    }

}