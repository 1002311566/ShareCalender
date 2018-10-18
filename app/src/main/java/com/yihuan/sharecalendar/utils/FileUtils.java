package com.yihuan.sharecalendar.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.yihuan.sharecalendar.global.App;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Ronny on 2017/9/28.
 */

public class FileUtils {

    public static String uriToPath(Activity context, Uri uri){
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.managedQuery(uri, proj, null,null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filepath = cursor.getString(column_index);
        return filepath;
    }

    /**\
     * 关闭流
     * @param closeables
     */
    @SuppressWarnings("WeakerAccess")
    public static void close(Closeable... closeables) {
        if (closeables == null || closeables.length == 0)
            return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
