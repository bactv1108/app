package com.example.ph26503_and_net_assignment.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.ObjectKey;
import com.example.ph26503_and_net_assignment.ApiResponse;
import com.example.ph26503_and_net_assignment.ApiSerivce;
import com.example.ph26503_and_net_assignment.LoginActivity;
import com.example.ph26503_and_net_assignment.R;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class InfoFragment extends Fragment {
    private TextView usernameTextView;
    private TextView emailTextView;
    private ImageView info_img_ava;
    Button updateava;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        usernameTextView = view.findViewById(R.id.txt_username);
        emailTextView = view.findViewById(R.id.txt_email);
        info_img_ava = view.findViewById(R.id.info_img_ava);
        Bundle arguments = getArguments();
        if (arguments != null) {
            String username = arguments.getString("username");
            String email = arguments.getString("email");
            String image = arguments.getString("image");
            Glide.with(this)
                    .load(image)
                    .circleCrop()
                    .skipMemoryCache(true) // Skip memory cache
                    .diskCacheStrategy(DiskCacheStrategy.NONE) // Disable disk caching
                    .signature(new ObjectKey(System.currentTimeMillis())) // Add a unique signature to force Glide to load the updated image
                    .into(info_img_ava);

            usernameTextView.setText(username);
            emailTextView.setText(email);
        }
        updateava=view.findViewById(R.id.btn_updateava);
        updateava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateImageDialog();
            }
        });
        Button logoutButton = view.findViewById(R.id.btn_logout);

        // Set click listener for the logout button
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        return view;
    }
    private void logout() {
        // Start the login activity
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }
    private void showUpdateImageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Update Image");

        // Create a LinearLayout to hold the ImageView and EditText
        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.setPadding(32, 32, 32, 32);

        // Create the ImageView to display the old avatar
        ImageView oldAvatarImageView = new ImageView(requireContext());
        // Load the old avatar image using Glide
        String oldImageUrl = getArguments().getString("image");
        Glide.with(this)
                .load(oldImageUrl)
                .circleCrop()
                .skipMemoryCache(true) // Skip memory cache
                .diskCacheStrategy(DiskCacheStrategy.NONE) // Disable disk caching
                .signature(new ObjectKey(System.currentTimeMillis())) // Add a unique signature to force Glide to load the updated image
                .into(oldAvatarImageView);
        layout.addView(oldAvatarImageView);

        // Create a LinearLayout to hold the EditText with margin
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 16, 0, 0); // Set top margin of 16dp for EditText
        LinearLayout editTextLayout = new LinearLayout(requireContext());
        editTextLayout.setLayoutParams(params);
        editTextLayout.setBackgroundResource(R.drawable.edittext_box_bg); // Set background drawable for the box

        // Create the EditText for the new image URL
        final EditText input = new EditText(requireContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Enter new image URL");
        // Add the EditText to the layout
        editTextLayout.addView(input);

        // Add the EditText layout to the main layout
        layout.addView(editTextLayout);

        builder.setView(layout);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newImageUrl = input.getText().toString().trim();
                updateInfoImage(newImageUrl);
                Glide.with(requireContext())
                        .load(newImageUrl)
                        .circleCrop()
                        .into(info_img_ava);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
    private void updateInfoImage(String newImageUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.24.30.145:3000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiSerivce apiService = retrofit.create(ApiSerivce.class);

        Map<String, String> imageMap = new HashMap<>();
        imageMap.put("image", newImageUrl);

        Call<ApiResponse> call = apiService.updateUserImage("64c4c815aafa4021b75aab06", imageMap);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Handle the response as needed
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Handle API call failure
            }
        });
    }
}