package com.jason.www.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import java.util.Objects;

/**
 * @author：Jason
 * @date：2020/7/25 10:13
 * @email：1129847330@qq.com
 * @description:
 */
public class SystemUtils {
    /**
     * 复制文本到系统剪贴板
     *
     * @param context
     * @param copyContent
     */
    public static void copyToClipboard(Context context, String copyContent) {
        copyToClipboard(context, copyContent, true);
    }

    public static void copyToClipboard(Context context, String copyContent, boolean isShowSuccessToast) {
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager clip = (ClipboardManager) Objects.requireNonNull(context).getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        clip.setPrimaryClip(ClipData.newPlainText(null, copyContent));
    }

    /**
     * 结束当前app自身进程
     */
    public static void killMySelfProcess() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
} 