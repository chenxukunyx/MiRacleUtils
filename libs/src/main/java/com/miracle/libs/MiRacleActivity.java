package com.miracle.libs;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.miracle.libs.bean.JianshuBean;
import com.miracle.libs.utils.AppCharacterParser;
import com.miracle.libs.utils.AppFileMgr;
import com.miracle.libs.view.SuccessFailView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Create with Android studio
 *
 * @author: chenxukun
 * @date: 2017-08-11
 * @time: 12:04
 * @age: 24
 */
public class MiRacleActivity extends Activity implements View.OnClickListener{

    private static final String TAG = "MiRacleActivity";

    String str = "向";
    TextView testView;
    Document document;
    private List<JianshuBean> mList;
    private SuccessFailView successFailView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        String result = AppCharacterParser.getInstace().getSelling(str);
        Log.i(TAG, "----->>str: " + str);
        testView = (TextView) findViewById(R.id.test_view);

        testView.setText(AppFileMgr.getSdCardSize() + "");
        mList = new ArrayList<>();

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        successFailView = (SuccessFailView) findViewById(R.id.successfail_view);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            successFailView.loadingStatus();
        }
    }
}
