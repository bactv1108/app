package com.example.ph26503_and_net_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.List;

public class ReadComic extends AppCompatActivity {
    ImageView imgcontent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_comic);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("comic1")) {
            Comic comic = intent.getParcelableExtra("comic1");
            List<String> images = comic.getContent();

            // Get the parent layout in your activity's XML layout file
            LinearLayout imageContainer = findViewById(R.id.imageContainer);

            if (images != null) {
                for (String imageUrl : images) {
                    ImageView imageView = new ImageView(this);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    layoutParams.setMargins(0, 0, 0, -18);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setAdjustViewBounds(true);
                    Glide.with(this)
                            .load(imageUrl)
                            .into(imageView);
                    imageContainer.addView(imageView);
                }
            }
        }
    }
}