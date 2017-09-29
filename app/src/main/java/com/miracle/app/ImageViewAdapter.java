package com.miracle.app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.miracle.libhttp.TestEntity;
import com.miracle.libs.utils.GlideUtils;
import com.miracle.miracleutils.R;


/**
 * Create with Android studio
 *
 * @fuction:
 * @author: chenxukun
 * @date: 2017-09-27
 * @time: 11:25
 * @age: 24
 */
public class ImageViewAdapter extends RecyclerView.Adapter<ImageViewAdapter.MyViewHolder> {

    private Context mContext;
    private TestEntity testEntity;

    public ImageViewAdapter(Context context, TestEntity entity) {
        this.mContext = context;
        this.testEntity = entity;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_image_view, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GlideUtils.getInstance().LoadContextBitmap(mContext, testEntity.getResults().get(position).getUrl(),
                holder.imageView, GlideUtils.LOAD_BITMAP);
    }

    @Override
    public int getItemCount() {
        return testEntity.getResults().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_image_view);
        }
    }
}
