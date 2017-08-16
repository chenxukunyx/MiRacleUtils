package com.miracle.libs.ui.jianshu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.scwang.smartrefresh.header.CircleHeader;
import com.scwang.smartrefresh.header.CircleRefreshHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

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
public class JianshuActivity extends Activity implements OnRefreshListener, OnRefreshLoadmoreListener {

    private static final String TAG = "JianshuActivity";
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    Document document;
    private List<JianshuBean> been;
    private JianshuAdapter mAdapter;

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
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("link", been.get(position).getTitleLink());
                    AppActivitySkipUtil.skipAnotherActivity(JianshuActivity.this, DetailActivity.class, (HashMap<String, ? extends Object>) map);
                } else if (i == R.id.tv_author || i == R.id.iv_avatar) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("link", been.get(position).getAuthorLink());
                    AppActivitySkipUtil.skipAnotherActivity(JianshuActivity.this, DetailActivity.class, (HashMap<String, ? extends Object>) map);
                } else if (i == R.id.tv_collectTag) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("link", been.get(position).getCollectionTagLink());
                    AppActivitySkipUtil.skipAnotherActivity(JianshuActivity.this, DetailActivity.class, (HashMap<String, ? extends Object>) map);
                }
            }
        });
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

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        refreshlayout.finishLoadmore(1000);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        getJianshuData();
    }

    private String timeFormat(String time) {
        String[] t = time.split("T");
        String[] s = t[1].split("\\+");
        return t[0] + "   " + s[0];
    }

//    @Override
//    public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//        switch (view.getId()) {
//            case R.id.tv_title:
//            case R.id.tv_content:
//            case R.id.iv_primary:
////                Intent title = new Intent(JianshuActivity.this, DetailActivity.class);
////                title.putExtra("title", been.get(position).getTitleLink());
//                AppActivitySkipUtil.skipAnotherActivity(JianshuActivity.this, DetailActivity.class,
//                        new HashMap<String, Object>().put("title", been.get(position).getTitleLink()));
//                break;
//        }
//    }
}
