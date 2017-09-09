package com.miracle.libs.utils.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.miracle.libs.utils.MLog;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @data: 2017/8/31
 * @time: 上午9:25
 *
 * 网络缓存
 */

public class NetCacheUtils extends Cache {

    private LocalCacheUtils mLocalCacheUtils;
    private MemoriesCacheUtils mMemoriesCacheUtils;

    public NetCacheUtils(LocalCacheUtils localCacheUtils, MemoriesCacheUtils memoriesCacheUtils) {
        mLocalCacheUtils = localCacheUtils;
        mMemoriesCacheUtils = memoriesCacheUtils;
    }

    @Override
    Bitmap get(String url) throws IOException {
        return null;
    }

    void getBitmapFromNet(ImageView iv, String url) {
        new BitmapTasK().execute(iv, url);
    }

    @Override
    void put(String url, Bitmap bitmap) {

    }

    /**
     * 第一个泛型 参数类型
     * 第二部泛型 更新进度的泛型
     * 第三个泛型 onPostExecute返回的结果
     */
    class BitmapTasK extends AsyncTask<Object, Void, Bitmap> {

        private ImageView ivPic;
        private String url;

        /**
         * 后台耗时操作 存在于子线程中
         * @param params
         * @return
         */
        @Override
        protected Bitmap doInBackground(Object... params) {
            ivPic = (ImageView) params[0];
            url = (String) params[1];
            return download(url);
        }

        /**
         * 更新进度 在主线程中
         * @param values
         */
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                ivPic.setImageBitmap(bitmap);
                MLog.i(TAG, "从网络获取");
                //从网络获取图片后保存到本地缓存
                mLocalCacheUtils.put(url, bitmap);
                //保存至内存缓存
                mMemoriesCacheUtils.put(url, bitmap);
            }
        }

        private Bitmap downloadBitmap(String url) {
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.setRequestMethod("GET");

                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;//宽高压缩为原来的1/2
                    options.inPreferredConfig = Bitmap.Config.ARGB_4444;
                    Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream(), null, options);
                    return bitmap;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            return null;
        }

        private Bitmap download(String url) {
            Bitmap bitmap = null;
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    byte[] bytes = response.body().bytes();
                    bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
    }
}
