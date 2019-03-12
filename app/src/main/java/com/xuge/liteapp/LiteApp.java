package com.xuge.liteapp;

import android.os.Parcel;
import android.os.Parcelable;

public class LiteApp implements Parcelable {

    private String id;
    private int titleResId;
    private int icon;
    private String url;

    public LiteApp(int titleResId, int icon, String url) {
        this.titleResId = titleResId;
        this.icon = icon;
        this.url = url;
        id = generateId();
    }

    protected LiteApp(Parcel in) {
        id = in.readString();
        titleResId = in.readInt();
        icon = in.readInt();
        url = in.readString();
    }

    private String generateId() {
        StringBuilder builder = new StringBuilder();
        builder.append(titleResId).append("_").append(icon).append("_").append(url);
        return builder.toString();
    }

    public static final Creator<LiteApp> CREATOR = new Creator<LiteApp>() {
        @Override
        public LiteApp createFromParcel(Parcel in) {
            return new LiteApp(in);
        }

        @Override
        public LiteApp[] newArray(int size) {
            return new LiteApp[size];
        }
    };

    public String getId() {
        return id;
    }

    public int getTitleResId() {
        return titleResId;
    }

    public int getIcon() {
        return icon;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(titleResId);
        dest.writeInt(icon);
        dest.writeString(url);
    }
}
