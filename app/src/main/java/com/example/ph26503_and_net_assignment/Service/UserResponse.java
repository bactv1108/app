package com.example.ph26503_and_net_assignment.Service;

import com.example.ph26503_and_net_assignment.User;

import java.util.List;

public class UserResponse {
    private int status;
    private String msg;
    private List<User> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }
}

