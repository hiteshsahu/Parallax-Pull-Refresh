package com.hiteshsahu.parallaxpullrefreshconcept.data;

public class TestDataModel {

    private String heading;
    private String detail;
    private String imageUrl;

    public String getHeading() {
        return heading;
    }

    public String getDetail() {
        return detail;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public TestDataModel(String title, String artist, String path) {
        this.heading = title;
        this.detail = artist;
        this.imageUrl = path;
    }
}
