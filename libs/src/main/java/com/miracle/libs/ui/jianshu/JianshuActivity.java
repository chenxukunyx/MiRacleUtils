package com.miracle.libs.ui.jianshu;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.miracle.libs.R;
import com.miracle.libs.adapter.JianshuAdapter;
import com.miracle.libs.bean.JianshuBean;
import com.miracle.libs.utils.AppActivitySkipUtil;
import com.miracle.libs.utils.MLog;
import com.miracle.libs.vassonic.BrowserActivity;
import com.miracle.libs.vassonic.SonicRuntimeImpl;
import com.scwang.smartrefresh.header.CircleRefreshHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicEngine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create with Android studio
 *
 * @fuction:
 * @author: chenxukun
 * @date: 2017-08-16
 * @time: 13:58
 * @age: 24
 */
public class JianshuActivity extends Activity implements OnRefreshListener, OnRefreshLoadmoreListener{

    private static final String TAG = "JianshuActivity";
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    Document document;
    private List<JianshuBean> been;
    private JianshuAdapter mAdapter;

    public static final int MODE_DEFAULT = 0;

    public static final int MODE_SONIC = 1;

    public static final int MODE_SONIC_WITH_OFFLINE_CACHE = 2;

    private static final int PERMISSION_REQUEST_CODE_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jianshu);

        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new CircleRefreshHeader(this));
        refreshLayout.setOnLoadmoreListener(this);
        refreshLayout.setOnRefreshListener(this);

        mAdapter = new JianshuAdapter(this);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);

        been = new ArrayList<>();

        getJianshuData();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                int i = view.getId();
                if (i == R.id.iv_primary || i == R.id.tv_title || i == R.id.tv_content) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put(BrowserActivity.PARAM_URL, been.get(position).getTitleLink());
                    map.put(BrowserActivity.PARAM_MODE, MODE_SONIC);
                    AppActivitySkipUtil.skipAnotherActivity(JianshuActivity.this, DetailActivity.class, (HashMap<String, Object>) map);
//                    startBrowserActivity(been.get(position).getTitleLink(), MODE_SONIC);
                } else if (i == R.id.tv_author || i == R.id.iv_avatar) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put(BrowserActivity.PARAM_MODE, MODE_SONIC);
                    map.put(BrowserActivity.PARAM_URL, been.get(position).getAuthorLink());
                    AppActivitySkipUtil.skipAnotherActivity(JianshuActivity.this, DetailActivity.class, (HashMap<String, Object>) map);
//                    startBrowserActivity(been.get(position).getAuthorLink(), MODE_SONIC);
                } else if (i == R.id.tv_collectTag) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put(BrowserActivity.PARAM_URL, been.get(position).getCollectionTagLink());
                    map.put(BrowserActivity.PARAM_MODE, MODE_SONIC);
                    AppActivitySkipUtil.skipAnotherActivity(JianshuActivity.this, BrowserActivity.class, (HashMap<String, Object>) map);
//                    startBrowserActivity(been.get(position).getCollectionTagLink(), MODE_SONIC);
                }
            }
        });

        if (hasPermission()) {
            init();
        } else {
            requestPermission();
        }
    }

    private void init() {
        // init sonic engine
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(new SonicRuntimeImpl(getApplication()), new SonicConfig.Builder().build());
        }
    }


    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (PERMISSION_REQUEST_CODE_STORAGE == requestCode) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                requestPermission();
            } else {
                init();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        refreshlayout.finishLoadmore(1000);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        getJianshuData();
        getLeetCodeData();
    }

    private String timeFormat(String time) {
        String[] t = time.split("T");
        String[] s = t[1].split("\\+");
        return t[0] + "   " + s[0];
    }

    private void startBrowserActivity(String url, int mode) {
        Intent intent = new Intent(JianshuActivity.this, BrowserActivity.class);
        intent.putExtra(BrowserActivity.PARAM_URL, url);
        intent.putExtra(BrowserActivity.PARAM_MODE, mode);
        startActivityForResult(intent, -1);
    }

    private void getJianshuData() {
        been.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    document = Jsoup.connect("http://www.jianshu.com/")
                            .timeout(10000)
                            .get();
                    Elements noteList = document.select("ul.note-list");
                    Elements li = noteList.select("li");
                    for (Element element : li) {
                        String strText = "";
                        JianshuBean jianshuBean = new JianshuBean();
                        jianshuBean.setAuthorName(element.select("div.name").text());
                        jianshuBean.setAuthorLink(element.select("a.blue-link").attr("abs:href"));
                        jianshuBean.setTime(timeFormat(element.select("span.time").attr("data-shared-at")));
                        jianshuBean.setPrimaryImg(element.select("img").attr("src"));
                        jianshuBean.setAvatarImg(element.select("a.avatar").select("img").attr("src"));
                        jianshuBean.setTitle(element.select("a.title").text());
                        jianshuBean.setTitleLink(element.select("a.title").attr("abs:href"));
                        jianshuBean.setContent(element.select("p.abstract").text());
                        jianshuBean.setCollectionTagLink(element.select("a.collection-tag").attr("abs:href"));
                        String[] text = element.select("div.meta").text().split(" ");
//                        Log.i(TAG, "----->>priImg: " + element.select("img").attr("src"));
//                        Log.i(TAG, "----->>avaImg: " + element.select("a.avatar").select("img").attr("src"));
                        for (int i = 0; i < text.length; i++) {
                            strText += text[i] + " ";
                        }
//                        Log.i(TAG, "----->>text: " + strText);
                        if (text[0].matches("[0-9]+")) {
                            jianshuBean.setReadNum(text[0]);
                            jianshuBean.setTalkNum(text[1]);
                            jianshuBean.setLikeNum(text[2]);
                        } else {
                            jianshuBean.setCollectionTag(text[0]);
                            jianshuBean.setReadNum(text[1]);
                            jianshuBean.setTalkNum(text[2]);
                            jianshuBean.setLikeNum(text[3]);
                        }
                        been.add(jianshuBean);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i(TAG, "----->>runOnUIThread: ");
                            mAdapter.setNewData(been);
                            refreshLayout.finishRefresh();
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "----->>错误: " + e.getMessage());
                }
            }
        }).start();
    }

    private void getLeetCodeData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    document = Jsoup.connect("https://leetcode.com/problemset/all/")
                            .timeout(10000)
                            .get();
                    Elements noteList = document.select("[class=reactable-data]");
                    MLog.i("li" + noteList);
                } catch (Exception e) {
                    MLog.i(e.getMessage());
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
