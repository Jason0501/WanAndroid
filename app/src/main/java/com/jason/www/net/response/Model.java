package com.jason.www.net.response;

public class Model {

    String userId;
    String id;
    String title;

    public Model() {
    }

    public Model(String userId, String id, String title) {
        this.userId = userId;
        this.id = id;
        this.title = title;
    }

    @Override
    public String toString() {
        return "Model{" +
                "userId='" + userId + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
