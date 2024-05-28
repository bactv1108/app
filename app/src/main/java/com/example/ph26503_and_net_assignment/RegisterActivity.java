package com.example.ph26503_and_net_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ph26503_and_net_assignment.Service.UserResponse;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout edUsername, edPassword, edRepassword,edEmail,edImg;
    private ApiSerivce apiService;
    private Button btn_reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.24.30.145:3000/api/") // Replace with your API base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiSerivce.class);
        edUsername = findViewById(R.id.ed_username_req);
        edPassword = findViewById(R.id.ed_password_req);
        edEmail=findViewById(R.id.ed_email_req);
        edImg=findViewById(R.id.ed_image_req);
        edRepassword = findViewById(R.id.ed_repassword_req);

        btn_reg= findViewById(R.id.btn_dk);
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edUsername.getEditText().getText().toString().trim();
                String password = edPassword.getEditText().getText().toString().trim();
                String repassword = edRepassword.getEditText().getText().toString().trim();
                String email = edEmail.getEditText().getText().toString().trim();
                String image = edImg.getEditText().getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    edUsername.setError("Username is required");
                    return;
                } else {
                    edUsername.setErrorEnabled(false);
                }

                if (TextUtils.isEmpty(password)) {
                    edPassword.setError("Password is required");
                    return;
                } else {
                    edPassword.setErrorEnabled(false);
                }

                if (!password.equals(repassword)) {
                    edRepassword.setError("Passwords do not match");
                    return;
                } else {
                    edRepassword.setErrorEnabled(false);
                }if (TextUtils.isEmpty(email)) {
                    edEmail.setError("Email is required");
                    return;
                } else {
                    edEmail.setErrorEnabled(false);
                }if (TextUtils.isEmpty(image)) {
                    edImg.setError("Email is required");
                    return;
                } else {
                    edImg.setErrorEnabled(false);
                }


                register(username, password,email,image);
            }
        });

    }

    public void register(String username, String password,String email, String image) {


        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setImage(image);

        Call<UserResponse> call = apiService.register(user);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    Toast.makeText(getApplicationContext(),"Đăng ký thành công",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),"Đăng ký không thành công",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                // Handle failure response
            }
        });
    }
}