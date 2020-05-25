package com.example.materialdesigntest;

public class Partner {
    private String name;  // 伙伴名称

    private int imageId; // 伙伴对应头像的资源id

    private int profileId; // 人物简介的资源id

    public Partner(String name, int imageId, int profileId) {
        this.name = name;
        this.imageId = imageId;
        this.profileId = profileId;
    }

    public String getName(){
        return name;
    }

    public int getImageId(){
        return imageId;
    }

    public int getProfileId() {
        return profileId;
    }
}
