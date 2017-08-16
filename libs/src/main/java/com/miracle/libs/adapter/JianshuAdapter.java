package com.miracle.libs.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.libs.R;
import com.miracle.libs.bean.JianshuBean;
import com.miracle.libs.utils.GlideUtils;

import java.util.List;

/**
 * Create with Android studio
 *
 * @fuction:
 * @author: chenxukun
 * @date: 2017-08-16
 * @time: 14:08
 * @age: 24
 */
public class JianshuAdapter extends BaseQuickAdapter<JianshuBean, BaseViewHolder> {

    private Context context;
    public JianshuAdapter(Context context) {
        super(R.layout.item_home);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, JianshuBean item) {
        helper.setText(R.id.tv_author, item.getAuthorName())
                .setText(R.id.tv_time, item.getTime())
                .setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_content, item.getContent())
                .setText(R.id.tv_collectTag, item.getCollectionTag())
                .setText(R.id.tv_read, item.getReadNum())
                .setText(R.id.tv_talk, item.getTalkNum())
                .setText(R.id.tv_like, item.getLikeNum())
                .addOnClickListener(R.id.iv_primary)
                .addOnClickListener(R.id.tv_content)
                .addOnClickListener(R.id.tv_title)
                .addOnClickListener(R.id.tv_collectTag)
                .addOnClickListener(R.id.iv_avatar)
                .addOnClickListener(R.id.tv_author);
//        Glide.with(context).load(item.getAvatarImg()).into((ImageView) helper.getView(R.id.iv_avatar));
//        Glide.with(context).load(item.getPrimaryImg()).into((ImageView) helper.getView(R.id.iv_primary));
        GlideUtils.getInstance().LoadContextBitmap(context, item.getAvatarImg(), (ImageView) helper.getView(R.id.iv_avatar), 0, 0, GlideUtils.LOAD_BITMAP);
        GlideUtils.getInstance().LoadContextBitmap(context, item.getPrimaryImg(), (ImageView) helper.getView(R.id.iv_primary), 0, 0, GlideUtils.LOAD_BITMAP);
    }
}
