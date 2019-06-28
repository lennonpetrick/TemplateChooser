package com.test.templatechooser.data.entities;

import java.util.List;


public class TemplateEntity {

    private long id;
    private String name;
    private MetaEntity meta;
    private ScreenshotsEntity screenshots;
    private List<VariationEntity> variations;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MetaEntity getMeta() {
        return meta;
    }

    public ScreenshotsEntity getScreenshots() {
        return screenshots;
    }

    public List<VariationEntity> getVariations() {
        return variations;
    }
}