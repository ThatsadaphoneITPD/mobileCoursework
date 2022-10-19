package com.example.tripexpense;

public class UserUpload {

    private String uploadResponseCode;
    private String userId;
    private String names;
    private int number;
    private String message;

    public UserUpload(String uploadResponseCode, String userId, int number, String names,  String message) {
        this.uploadResponseCode = uploadResponseCode;
        this.userId = userId;
        this.number = number;
        this.names = names;
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUploadResponseCode() {
        return uploadResponseCode;
    }

}
