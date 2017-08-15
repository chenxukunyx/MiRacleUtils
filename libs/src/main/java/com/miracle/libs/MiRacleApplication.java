package com.miracle.libs;

import android.app.Application;
import android.util.Log;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Create with Android studio
 *
 * @fuction:
 * @author: chenxukun
 * @date: 2017-08-15
 * @time: 16:48
 * @age: 24
 */
public class MiRacleApplication extends Application {

    private static final String TAG = "MiRacleApplication";

//    private static final String appId = "1254172882";
    /** cos的appid */
    public String appid;
    /** appid的一个空间名称 */
    public  String bucket;
    /** cos sdk 的用户接口  */
//    public  COSClient cosClient;
    /** 设置园区；根据创建的cos空间时选择的园区
     * 华南园区：gz 或 COSEndPoint.COS_GZ(已上线)
     * 华北园区：tj 或 COSEndPoint.COS_TJ(已上线)
     * 华东园区：sh 或 COSEndPoint.COS_SH*/
    public String region;
    /** cos sdk 配置设置; 根据需要设置*/
//    private COSConfig cosConfig;

    public String secretID = "AKID460iJktDAwWv8B8lLn7GE1c8TfgWHew7";
    public String secretKey = "AAzfBWfhZGmrpdHkMoBNQIzVp7FYjjYB";

    @Override
    public void onCreate() {
        super.onCreate();
//        init();
//        getSign();
    }

//    protected void init() {
//        synchronized (this) {
//            if (cosClient == null) {
//                cosConfig = new COSConfig();
//                region = "sh";
//                cosConfig.setEndPoint(region);
//                appid = "1254172882";
//                bucket = "memory";
//                cosClient = new COSClient(this, appid, cosConfig, "xxx");
//            }
//        }
//    }

    public String getSign(){
        StringBuilder sign = new StringBuilder();
        sign.append("a=").append(appid).append("&b=").append(bucket).append("&k=").append(secretID)
                .append("&e=1437995704&t=").append(System.currentTimeMillis()).append("&r=2081660421").append("&f=");

        String signTmp = HmacSHA1Encrypt(secretKey, sign.toString());
        Log.i(TAG, "----->>sign: " + sign);
        Log.i(TAG, "----->>getSign: " + signTmp);
        return signTmp;
    }

    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";
    /**
     * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
     * @param encryptText 被签名的字符串
     * @param encryptKey 密钥
     * @return 返回被加密后的字符串
     * @throws Exception
     */
    public static String HmacSHA1Encrypt( String encryptText,
                                          String encryptKey ) {
        byte[] data = new byte[0];
        try {
            data = encryptKey.getBytes( ENCODING );
            // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
            SecretKey secretKey = new SecretKeySpec( data, MAC_NAME );
            // 生成一个指定 Mac 算法 的 Mac 对象
            Mac mac = Mac.getInstance( MAC_NAME );
            // 用给定密钥初始化 Mac 对象
            mac.init( secretKey );
            byte[] text = encryptText.getBytes( ENCODING );
            // 完成 Mac 操作
            byte[] digest = mac.doFinal( text );
            StringBuilder sBuilder = bytesToHexString( digest );
            return sBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转换成Hex
     *
     * @param bytesArray
     */
    public static StringBuilder bytesToHexString( byte[] bytesArray ){
        if ( bytesArray == null ){
            return null;
        }
        StringBuilder sBuilder = new StringBuilder();
        for ( byte b : bytesArray ){
            String hv = String.format("%02x", b);
            sBuilder.append( hv );
        }
        return sBuilder;
    }

    /**
     * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
     *
     * @param encryptData 被签名的字符串
     * @param encryptKey 密钥
     * @return 返回被加密后的字符串
     * @throws Exception
     */
    public static String hmacSHA1Encrypt( byte[] encryptData, String encryptKey ) throws Exception{
        byte[] data = encryptKey.getBytes( ENCODING );
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec( data, MAC_NAME );
        // 生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance( MAC_NAME );
        // 用给定密钥初始化 Mac 对象
        mac.init( secretKey );
        // 完成 Mac 操作
        byte[] digest = mac.doFinal( encryptData );
        StringBuilder sBuilder = bytesToHexString( digest );
        return sBuilder.toString();
    }
}
