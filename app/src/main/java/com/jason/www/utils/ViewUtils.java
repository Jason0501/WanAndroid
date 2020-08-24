package com.jason.www.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author：Jason
 * @date：2020/8/24 12:10
 * @email：1129847330@qq.com
 * @description:
 */
public class ViewUtils {
    //保存文件到指定路径
    public static boolean saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "WanAndroid";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 60, fos);
            fos.flush();
            fos.close();
            //把文件插入到系统图库
//            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static final Bitmap screenShot(View view) {
        return screenShot(view, view.getWidth(), view.getHeight());
    }

    public static final Bitmap screenShot(View view, int width, int height) {
        Bitmap bitmap = null;
        try {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
            saveImageToGallery(view.getContext(), bitmap);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
                System.gc();
            }
        }
        return bitmap;
    }

    public static final Bitmap screenShot(LinearLayout linearLayout) {
        int childCount = linearLayout.getChildCount();
        int orientation = linearLayout.getOrientation();
        int height = 0;
        int width = 0;
        if (orientation == LinearLayout.VERTICAL) {
            width = linearLayout.getWidth();
            for (int i = 0; i < childCount; i++) {
                height += linearLayout.getChildAt(i).getHeight();
            }
        } else {
            height = linearLayout.getHeight();
            for (int i = 0; i < childCount; i++) {
                width += linearLayout.getChildAt(i).getWidth();
            }
        }
        return screenShot(linearLayout, width, height);
    }
}