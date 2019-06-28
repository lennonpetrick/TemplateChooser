package com.test.templatechooser.data;

public class VariationEntity {

    private long id;

    private String name,
                   type,
                   icon;

    private ScreenshotsEntity screenshots;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getIcon() {
        return icon;
    }

    public ScreenshotsEntity getScreenshots() {
        return screenshots;
    }
}
