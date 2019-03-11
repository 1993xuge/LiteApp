package com.xuge.liteapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Created at 2019/3/11 下午2:24.
 *
 * @author yixu.wang
 */
public class ExampleAppWidgetProvider extends AppWidgetProvider {
    private static final String TAG = ExampleAppWidgetProvider.class.getSimpleName();
    public static final String ACTION_CLICK = "action_click";

    public ExampleAppWidgetProvider() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d(TAG, "onReceive: action = " + intent.getAction());

        if (TextUtils.equals(intent.getAction(), ACTION_CLICK)) {
            Log.d(TAG, "onReceive: click");
        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d(TAG, "onEnabled: ");
    }

    /**
     * 每个桌面小部件 更新时  都会调用一次该方法
     *
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "onUpdate: ");

        int counter = appWidgetIds.length;
        Log.d(TAG, "onUpdate: counter = " + counter);

        for (int i = 0; i < counter; i++) {
            onWidgetUpdate(context, appWidgetManager, appWidgetIds[i]);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Log.d(TAG, "onDeleted: " + appWidgetIds.length);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.d(TAG, "onDisabled: ");
    }

    private void onWidgetUpdate(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Log.d(TAG, "onWidgetUpdate: appWidgetId = " + appWidgetId);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), android.R.layout.activity_list_item);
        remoteViews.setImageViewResource(R.id.icon, R.drawable.icon_weibo);
        remoteViews.setTextViewText(android.R.id.text1, context.getText(R.string.app_title_weibo));

        // 设置点击事件
        Intent intentClick = new Intent();
        intentClick.setAction(ACTION_CLICK);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentClick, 0);
        remoteViews.setOnClickPendingIntent(R.id.icon, pendingIntent);

        appWidgetManager.updateAppWidget(new ComponentName(context, ExampleAppWidgetProvider.class), remoteViews);

    }
}
