package com.example.ph26503_and_net_assignment.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ph26503_and_net_assignment.R;


public class InfoFragment extends Fragment {
    private TextView usernameTextView;
    private TextView emailTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        usernameTextView = view.findViewById(R.id.txt_username);
        emailTextView = view.findViewById(R.id.txt_email);

        Bundle arguments = getArguments();
        if (arguments != null) {
            String username = arguments.getString("username");
            String email = arguments.getString("email");

            usernameTextView.setText(username);
            emailTextView.setText(email);
        }

        return view;
    }
}