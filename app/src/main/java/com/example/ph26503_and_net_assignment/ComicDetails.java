package com.example.ph26503_and_net_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_details);
        author = findViewById(R.id.textAuthor);
        imgcover = findViewById(R.id.imageCover);
        imgcontent = findViewById(R.id.imageViewComic);
        comicname = findViewById(R.id.textName);
        year = findViewById(R.id.textYear);
        description = findViewById(R.id.textDescription);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("comic")) {
            Comic comic = intent.getParcelableExtra("comic");
            author.setText(comic.getAuthor());
            comicname.setText(comic.getName());
            year.setText(comic.getYear());
            description.setText(comic.getDescription());
            List<String> images = comic.getContent();

            // Get the parent layout in your activity's XML layout file
            LinearLayout imageContainer = findViewById(R.id.imageContainer);

            // Create an ImageView for each image URL in the content array
            if (images != null) {
                // Create an ImageView for each image URL in the content array
                for (String imageUrl : images) {
                    ImageView imageView = new ImageView(this);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    imageView.setLayoutParams(layoutParams);

                    // Load the image using an image-loading library like Glide or Picasso
                    Glide.with(this)
                            .load(imageUrl)
                            .into(imageView);

                    // Add the ImageView to the parent layout
                    imageContainer.addView(imageView);
                }

                // Rest of your code
                // ...

            }


        }
    }

}