package com.test.templatechooser.data.entities;

import com.google.gson.annotations.SerializedName;

public class ScreenshotsEntity {

    @SerializedName("iphone")
    private String url;

    public String getUrl() {
        return url;
    }
}
