package com.xuge.liteapp;

import java.util.ArrayList;
import java.util.List;

public class Constant {
    private static int[] appResIconIds = {R.drawable.icon_facebook, R.drawable.icon_twitter,
            R.drawable.icon_instagram, R.drawable.icon_weibo};

    private static int[] appTitleResIds = {R.string.app_title_facebook, R.string.app_title_twitter,
            R.string.app_title_instagram, R.string.app_title_weibo};

    private static String[] appUrls = {"https://www.facebook.com/", "https://twitter.com/",
            "https://www.instagram.com/", "https://www.weibo.com/"};

    public static List<LiteApp> getSupportLiteApp() {
        List<LiteApp> result = new ArrayList<>();

        for (int i = 0; i < appResIconIds.length; i++) {
            result.add(new LiteApp(appTitleResIds[i], appResIconIds[i], appUrls[i]));
        }

        return result;
    }
}
