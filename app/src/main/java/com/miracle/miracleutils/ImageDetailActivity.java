package com.miracle.miracleutils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.miracle.libhttp.net.Callback;
import com.miracle.libhttp.net.DownloadManager;
import com.miracle.libs.utils.GlideUtils;
import com.miracle.libs.utils.MLog;
import com.miracle.libs.utils.cache.ImageUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Create with Android studio
 *
 * @fuction:
 * @author: chenxukun
 * @date: 2017-09-29
 * @time: 17:55
 * @age: 24
 */
public class ImageDetailActivity extends AppCompatActivity {

    PhotoView photoView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        String url = getIntent().getStringExtra("url");

        photoView = (PhotoView) findViewById(R.id.photoview);
        loadBitmap(url);
    }

    private void loadBitmap(String url) {
        Glide.with(this).load(url).asBitmap().into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                photoView.setImageBitmap(resource);
            }
        });
    }
}
