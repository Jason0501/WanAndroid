package com.jason.www.utils;

import android.content.Intent;

import com.jason.www.activity.AgentWebActivity;
import com.jason.www.base.ActivityManagerDelegate;
import com.jason.www.config.AppData;
import com.jason.www.config.Constants;

/**
 * @author：Jason
 * @date：2020/8/19 11:49
 * @email：1129847330@qq.com
 * @description:
 */
public class IntentUtils {
    public static void goToWebViewActivity(String url) {
        Intent intent = new Intent(AppData.getContext(), AgentWebActivity.class);
        intent.putExtra(Constants.IntentKey.WEBVIEW_URL, url);
        ActivityManagerDelegate.getInstance().currentActivity().startActivity(intent);
    }
}