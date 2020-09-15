package com.jason.www.config;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.io.File;

/**
 * @author : xiaoxiaoda
 * date: 16-5-6
 * email: wonderfulifeel@gmail.com
 */
public class AppData {

    private static Context sContext;

    private static int versionCode = -1;

    public static Context getContext() {
        return sContext;
    }

    public static Resources getResources() {
        return sContext.getResources();
    }

    /**
     * 获取App版本号
     *
     * @return
     */
    public static int getVersionCode() {
        if (versionCode >= 0) {
            return versionCode;
        }
        PackageManager manager = sContext.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(sContext.getPackageName(), 0);
            return versionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * 获取versionName
     *
     * @return
     */
    public static String getVersionName() {
        PackageManager manager = sContext.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(sContext.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取手机机型
     *
     * @return
     */
    public static String getMobileModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机品牌
     *
     * @return
     */
    public static String getMobileBrand() {
        return Build.BRAND;
    }

    /**
     * 获取手机系统版本号
     *
     * @return
     */
    public static int getMobileOsVersionCode() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机系统版本名称
     *
     * @return
     */
    public static String getMobileOsVersionName() {
        return Build.VERSION.RELEASE;
    }


    /**
     * 获取wlan芯片的mac地址
     *
     * @return
     */
    public static String getWlanId() {
        WifiManager wm = (WifiManager) sContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        String m_szWLANMAC = "";
        if (wm != null && wm.getConnectionInfo() != null) {
            m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
        }
        return m_szWLANMAC;
    }

    /**
     * 获取手机制造商
     *
     * @return
     */
    public static String getMobileProduct() {
        return Build.PRODUCT;
    }


    public static void init(Context context) {
        sContext = context.getApplicationContext();
    }

    /**
     * 获取App的包名
     *
     * @return
     */
    public static String getPackageName() {
        return sContext.getPackageName();
    }

    /**
     * 获取私有缓存目录的根目录
     *
     * @return
     */
    public static File cacheDir() {
        return sContext.getCacheDir();
    }

    /**
     * 获取私有缓存目录下的自定义目录
     *
     * @param dir
     * @return
     */
    public static File cacheDir(String dir) {
        return new File(sContext.getCacheDir(), dir);
    }

    public static String getSignature(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi;
        StringBuilder sb = new StringBuilder();
        try {
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signatures = pi.signatures;
            for (Signature signature : signatures) {
                sb.append(signature.toCharsString());
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * @param context
     * @param originalSign
     * @return
     */
    public static boolean verifySignature(Context context, String originalSign) {
        String currentSign = getSignature(context);
        if (originalSign.equals(currentSign)) {
            return true;//签名正确
        }
        return false;//签名被篡改
    }

    /**
     * @return
     */
    public static String getSignature() {
        return getSignature(sContext);
    }
}
