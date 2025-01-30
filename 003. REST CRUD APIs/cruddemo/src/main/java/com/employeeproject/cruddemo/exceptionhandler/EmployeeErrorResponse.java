package com.employeeproject.cruddemo.exceptionhandler;

public class EmployeeErrorResponse {

    private int status;
    private String message;
    private long timestamp;

    public EmployeeErrorResponse() {}

    public EmployeeErrorResponse(int status, long timestamp, String message) {
        this.status = status;
        this.timestamp = timestamp;
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
