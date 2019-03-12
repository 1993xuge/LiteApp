package com.xuge.liteapp;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private static final String TAG = MainActivity.class.getSimpleName();
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
        Intent intent = new Intent(this, Main2Activity.class);

        Utils.addShortcut(this, getString(liteApp.getTitleResId()),
                liteApp.getIcon(), intent);

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
