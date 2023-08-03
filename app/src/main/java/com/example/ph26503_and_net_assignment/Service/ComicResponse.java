package com.example.ph26503_and_net_assignment.Service;

import com.example.ph26503_and_net_assignment.Comic;

import java.util.List;

public class ComicResponse {
    private int status;
    private String msg;
    private List<Comic> data;

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

    public List<Comic> getData() {
        return data;
    }

    public void setData(List<Comic> data) {
        this.data = data;
    }
}
