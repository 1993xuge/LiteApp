package com.xuge.liteapp;

public class LiteApp {
    private int titleResId;
    private int icon;
    private String url;

    public LiteApp(int titleResId, int icon, String url) {
        this.titleResId = titleResId;
        this.icon = icon;
        this.url = url;
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
}
