package com.miracle.libs.gift.ui;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.miracle.libs.R;
import com.miracle.libs.utils.FileCleanUtils;
import com.miracle.libs.utils.FileUtils;
import com.miracle.libs.utils.MLog;
import com.miracle.libs.utils.cache.ImageUtils;

import java.io.File;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @data: 2017/8/26
 * @time: 下午9:02
 */

public class GiftFragment extends Fragment {

    TextView mTextView, tvCache;
    ImageView ivPic;
    Button mButton;
    Button clear;
    String picUrl = "https://user-gold-cdn.xitu.io/2017/8/31/06f80205b839e1e6c184a3f1fd5eee89?imageView2/0/w/1280/h/960";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gift, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        mTextView = (TextView) view.findViewById(R.id.textView);
        mTextView.setText("1:" + Build.BOARD + " 2:" + Build.BRAND + " 3:" + Build.DEVICE +
                " 4:" + Build.DISPLAY + " 5:" + Build.FINGERPRINT + " 6:" + Build.SERIAL);

        PackageManager packageManager = getContext().getPackageManager();
        try {
            PackageInfo info = packageManager.getPackageInfo(getActivity().getPackageName(), 0);
            MLog.i(info.versionCode + " " + info.versionName + " ");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        tvCache = (TextView) view.findViewById(R.id.tv_cache);

        ivPic = (ImageView) view.findViewById(R.id.ivPic);
        mButton = (Button) view.findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageUtils.getInstance().displayImage(ivPic, picUrl);
                tvCache.setText(FileUtils.formatFileSize(FileUtils.getFileSize(new File(FileUtils.IMG_CACHE_PATH))));
            }
        });

        clear = (Button) view.findViewById(R.id.clear_cache);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/bitmap_cache");
//                if (file.exists() && file.isDirectory()) {
//                    MLog.i("size" + FileUtils.formatFileSize(FileUtils.getFileSize(file)));
//                    File[] imgCache = file.listFiles();
//                    if (imgCache.length >0) {
//                        for (File f : imgCache) {
//                            f.delete();
//                        }
//                    }
//                }

                FileCleanUtils.cleanBitmapCache(getActivity(), new File(FileUtils.IMG_CACHE_PATH));
            }
        });
    }


}
