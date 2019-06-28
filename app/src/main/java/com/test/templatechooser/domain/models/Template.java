package com.test.templatechooser.domain.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Template implements Parcelable {

    private long id;

    private String name,
                   color,
                   previewUrl;

    private List<Template> variations;

    public Template() {}

    private Template(Parcel in) {
        id = in.readLong();
        name = in.readString();
        color = in.readString();
        previewUrl = in.readString();
        variations = in.createTypedArrayList(Template.CREATOR);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public List<Template> getVariations() {
        return variations;
    }

    public void setVariations(List<Template> variations) {
        this.variations = variations;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(color);
        dest.writeString(previewUrl);
        dest.writeTypedList(variations);
    }

    public static final Creator<Template> CREATOR = new Creator<Template>() {
        @Override
        public Template createFromParcel(Parcel in) {
            return new Template(in);
        }

        @Override
        public Template[] newArray(int size) {
            return new Template[size];
        }
    };
}
