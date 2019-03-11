package com.xuge.liteapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcContent;
    private List<LiteApp> liteAppList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

        initView();
        liteAppList = new ArrayList<>();
        liteAppList.addAll(Constant.getSupportLiteApp());
    }

    private void initView() {
        rcContent = findViewById(R.id.rv_content);
        rcContent.setLayoutManager(new GridLayoutManager(this, 5));

        ContentAdapter contentAdapter = new ContentAdapter();
        rcContent.setAdapter(contentAdapter);
    }

    private void startLiteApp(LiteApp liteApp) {
        if (liteApp == null) {
            return;
        }
        /*Intent intent = new Intent(this, LiteAppActivity.class);
        intent.putExtra(LiteAppActivity.INTENT_EXTRA_LITEAPP, liteApp);
        startActivity(intent);*/

        createSystemSwitcherShortCut(this, liteApp.getIcon(), liteApp.getTitleResId());
    }


    public static void createSystemSwitcherShortCut(Context context, int iconResId, int labelResId) {
        final Intent addIntent = new Intent(
                "com.android.launcher.action.INSTALL_SHORTCUT");
        final Parcelable icon = Intent.ShortcutIconResource.fromContext(
                context, iconResId); // 获取快捷键的图标
        addIntent.putExtra("duplicate", false);
        final Intent myIntent = new Intent(context,
                LiteAppActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME,
                context.getString(labelResId));// 快捷方式的标题
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);// 快捷方式的图标
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, myIntent);// 快捷方式的动作
        context.sendBroadcast(addIntent);
        Log.d("xuge", "createSystemSwitcherShortCut: ");
    }


    /**
     * 为程序创建桌面快捷方式
     */
    private void addShortcut() {
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");

        //快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
        shortcut.putExtra("duplicate", false); //不允许重复创建

        /****************************此方法已失效*************************/
        //ComponentName comp = new ComponentName(this.getPackageName(), "."+this.getLocalClassName());
        //shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN).setComponent(comp));  　　
        /******************************end*******************************/
        Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
        shortcutIntent.setClassName(this, this.getClass().getName());
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);

        //快捷方式的图标
        Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(this, R.drawable.icon_weibo);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);

        sendBroadcast(shortcut);
    }

    private class ContentAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ItemViewHolder(LayoutInflater.from(
                    viewGroup.getContext()).inflate(R.layout.layout_main_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            LiteApp liteApp = liteAppList.get(i);
            ((ItemViewHolder) viewHolder).bindData(liteApp);
        }

        @Override
        public int getItemCount() {
            return liteAppList.size();
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private AppCompatImageView ivAppIcon;
        private TextView tvAppLabel;
        private LiteApp liteApp;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAppIcon = itemView.findViewById(R.id.iv_app_icon);
            tvAppLabel = itemView.findViewById(R.id.tv_app_label);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (liteApp == null) {
                        return;
                    }
                    startLiteApp(liteApp);
                }
            });
        }

        public void bindData(LiteApp liteApp) {
            if (liteApp == null) {
                return;
            }

            this.liteApp = liteApp;
            ivAppIcon.setImageResource(liteApp.getIcon());
            tvAppLabel.setText(liteApp.getTitleResId());
        }
    }
}
