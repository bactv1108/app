package com.example.ph26503_and_net_assignment.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ph26503_and_net_assignment.Comic;
import com.example.ph26503_and_net_assignment.ComicAdapter;
import com.example.ph26503_and_net_assignment.ComicDetails;
import com.example.ph26503_and_net_assignment.ComicService;
import com.example.ph26503_and_net_assignment.R;
import com.example.ph26503_and_net_assignment.Service.ComicResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment implements ComicAdapter.OnItemClickListener {

    private RecyclerView comicsRecyclerView;
    private ComicAdapter comicAdapter;
    private List<Comic> comicList;
    private ComicService comicService;
    private String id_user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        comicsRecyclerView = view.findViewById(R.id.comicsRecyclerView);
        comicsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        comicList = new ArrayList<>();
        comicAdapter = new ComicAdapter(comicList);
        comicAdapter.setOnItemClickListener(this);
        comicsRecyclerView.setAdapter(comicAdapter);
        Bundle arguments = getArguments();
        if (arguments != null) {
            String username = arguments.getString("username");
            String email = arguments.getString("email");
           id_user = arguments.getString("idUser");
        }

        // Create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.102.12:3000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create the service
        comicService = retrofit.create(ComicService.class);

        // Retrieve comic data and update the RecyclerView
        fetchComicsData();

        return view;
    }

    private void fetchComicsData() {
        // Perform network request to retrieve comic data
        Call<ComicResponse> call = comicService.getComics();
        call.enqueue(new Callback<ComicResponse>() {
            @Override
            public void onResponse(Call<ComicResponse> call, Response<ComicResponse> response) {
                if (response.isSuccessful()) {
                    ComicResponse comicResponse = response.body();
                    if (comicResponse != null && comicResponse.getStatus() == 1) {
                        List<Comic> comics = comicResponse.getData();
                        if (comics != null) {
                            for (Comic comic : comics) {
                                comicList.add(comic);
                            }
                            comicAdapter.notifyDataSetChanged();
                        }
                    } else {
                        // Handle error in onResponse()
                    }
                } else {
                    // Handle error in onResponse()
                }
            }

            @Override
            public void onFailure(Call<ComicResponse> call, Throwable t) {
                // Handle failure
            }
        });
    }


    @Override
    public void onItemClick(Comic comic) {
        Intent intent = new Intent(requireContext(), ComicDetails.class);
        intent.putExtra("comic", comic);
        intent.putExtra("idUser",id_user);
        startActivity(intent);
    }
}