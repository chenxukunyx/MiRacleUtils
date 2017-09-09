package com.miracle.libs.gift;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.miracle.libs.R;
import com.miracle.libs.gift.ui.GiftFragment;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @data: 2017/8/26
 * @time: 下午9:00
 */

public class GiftActivity extends AppCompatActivity {

    RelativeLayout mRelativeLayout;
    GiftFragment mGiftFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);
        mGiftFragment = new GiftFragment();
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        replaceFragment();
    }


    private void replaceFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.relativeLayout, mGiftFragment);
        fragmentTransaction.commit();
    }
}
