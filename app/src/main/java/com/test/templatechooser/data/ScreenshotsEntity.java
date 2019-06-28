package com.test.templatechooser.data;

import com.google.gson.annotations.SerializedName;

public class ScreenshotsEntity {

    @SerializedName("medium")
    private String url;

    public String getUrl() {
        return url;
    }
}
