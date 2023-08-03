package com.example.ph26503_and_net_assignment;

import java.util.List;

public class ApiResponse<T> {
    private int status;
    private String msg;
    private T data;

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}