package com.example.tripexpense;

public class UserUpload {
//    private String name;
//    private String job;
//    private String id;
//    private String createdAt;
//
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

//    public void setUploadResponseCode(String uploadResponseCode) {
//        this.uploadResponseCode = uploadResponseCode;
//    }

//        public UserUpload(String name, String job, String id, String createdAt) {
//        this.name = name;
//        this.job = job;
//        this.id = id;
//        this.createdAt = createdAt;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getJob() {
//        return job;
//    }
//
//    public void setJob(String job) {
//        this.job = job;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(String createdAt) {
//        this.createdAt = createdAt;
//    }
}
