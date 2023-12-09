package com.peecko.admin.service.data;

public class Member {
    String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Member{" +
            "username='" + username + '\'' +
            '}';
    }
}
