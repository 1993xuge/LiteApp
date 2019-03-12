package com.xuge.liteapp;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.os.Build;
import android.support.v4.content.pm.ShortcutInfoCompat;
import android.support.v4.content.pm.ShortcutManagerCompat;
import android.support.v4.graphics.drawable.IconCompat;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

public class Utils {
    private static final String TAG = "xuge";

    public static void addShortcut(Context context, LiteApp liteApp, Intent actionIntent) {
        if (liteApp == null) {
            return;
        }
        if (hasShortcut(context, liteApp.getId())) {
            return;
        }

        Log.d(TAG, "addNew: name = " + context.getString(liteApp.getTitleResId()));
        if (ShortcutManagerCompat.isRequestPinShortcutSupported(context)) {
            actionIntent.setAction(Intent.ACTION_VIEW);

            ShortcutInfoCompat shortcut = new ShortcutInfoCompat.Builder(context, liteApp.getId())
                    .setShortLabel(context.getString(liteApp.getTitleResId()))
                    .setIcon(IconCompat.createWithResource(context, liteApp.getIcon()))
                    .setIntent(actionIntent)
                    .build();

            Intent pinnedShortcutCallbackIntent = ShortcutManagerCompat.createShortcutResultIntent(context, shortcut);

            PendingIntent successCallback = PendingIntent.getBroadcast(context, 0,
                    pinnedShortcutCallbackIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            ShortcutManagerCompat.requestPinShortcut(context, shortcut, successCallback.getIntentSender());

        } else {
            Log.d(TAG, "addNew: not support");
        }
    }

    public static boolean hasShortcut(Context context, String id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
            List<ShortcutInfo> shortcutInfoList = shortcutManager.getPinnedShortcuts();
            for (ShortcutInfo shortcutInfo : shortcutInfoList) {
                if (TextUtils.equals(shortcutInfo.getId(), id)) {
                    return true;
                }
            }
        } else {
            // Android8.0以下直接返回
        }
        return false;
    }

    public static void getShortcut(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);

            List<ShortcutInfo> shortcutInfoList = shortcutManager.getPinnedShortcuts();
            Log.d(TAG, "getShortcut: shortcutInfoList size = " + shortcutInfoList.size());
            for (ShortcutInfo shortcutInfo : shortcutInfoList) {
                Log.d(TAG, "getShortcut: shortcutInfo = " + shortcutInfo);
                Log.d(TAG, "getShortcut: id = " + shortcutInfo.getId());
                Log.d(TAG, "getShortcut: ShortLabel = " + shortcutInfo.getShortLabel());
                Log.d(TAG, "getShortcut: Intent = " + shortcutInfo.getIntent());
            }
        }
    }
}
