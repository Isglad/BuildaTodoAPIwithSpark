package com.teamtreehouse.techdegrees.exc;

public class ApiError extends RuntimeException {
    private final int status;


    public ApiError(int status, String msg) {
        super(msg);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
