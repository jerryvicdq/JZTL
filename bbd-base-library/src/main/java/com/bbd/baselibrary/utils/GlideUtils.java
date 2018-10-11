package com.bbd.baselibrary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;

import com.bbd.baselibrary.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;

import java.io.File;
import java.math.BigDecimal;


public class GlideUtils {
    private static GlideUtils inst;

    public static GlideUtils getInstance() {
        if (inst == null) {
            inst = new GlideUtils();
        }
        return inst;
    }

    /**
     * 加载普通图片（http://或者file://）
     */
    public static void loadImage(Context context, String url, ImageView imageView) {
//        Glide.with(context).load(url).placeholder(R.drawable.img_loading).error(R.drawable.img_loading).into(imageView);
        if (!TextUtils.isEmpty(url)) {
//            if (url.startsWith("http") || url.contains(".jpg")) {
            if (url.contains(".jpg")) {
//                url = url.replaceAll("12.27.5.150","10.64.9.207");
//                url = "http://192.168.31.100:8011" + url;
                url = "http://10.64.9.207:8011" + url;
                Glide.with(context).load(url).placeholder(R.drawable.ic_defalut).into(imageView);
            } else if (url.contains("data:image/jpeg;base64")) {
                Glide.with(context).load(base64String2Byte(url.replace("data:image/jpeg;base64,", ""))).into(imageView);
            } else {
                try {
                    imageView.setImageBitmap(convertStringToIcon(context, url));
                } catch (Exception e) {
                    Glide.with(context).load(R.drawable.ic_defalut).into(imageView);
                }
            }
        } else {
//            imageView.setBackgroundResource(R.drawable.ic_defalut);
            Glide.with(context).load(R.drawable.ic_defalut).into(imageView);
        }
    }

    /**
     * 加载为圆形图片（一般为头像加载）
     */
    public static void loadCircleImage(Context context, String url, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
//            if (url.startsWith("http") || url.contains(".jpg")) {
            if (url.contains(".jpg")) {
//                url = "http://192.168.31.100:8011" + url;
                url = "http://10.64.9.207:8011" + url;
                Glide.with(context).load(url).placeholder(R.drawable.ic_defalut).transform(new GlideCircleTransform(context)).into(imageView);
            } else if (url.contains("data:image/jpeg;base64")) {
                Glide.with(context).load(base64String2Byte(url.replace("data:image/jpeg;base64,", ""))).transform(new GlideCircleTransform(context)).into(imageView);
            } else {
                Glide.with(context).load(R.drawable.ic_defalut).transform(new GlideCircleTransform(context)).into(imageView);
            }
        } else {
//            imageView.setBackgroundResource(R.drawable.ic_defalut);
            Glide.with(context).load(R.drawable.ic_defalut).transform(new GlideCircleTransform(context)).into(imageView);
        }
    }


    /**
     * 加载为圆形图片（一般为头像加载）
     */
    public static void loadCircleImg(Context context, String url, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(context).load(url).placeholder(R.drawable.ic_defalut).transform(new GlideCircleTransform(context)).into(imageView);
        } else {
            Glide.with(context).load(R.drawable.ic_defalut).transform(new GlideCircleTransform(context)).into(imageView);
        }
    }

    /**
     * 加载本地图片（资源文件）
     */
    public static void loadLocalImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }

    /**
     * 加载本地图片（byte[]）
     */
    public static void loadBytesImage(Context context, byte[] bytes, ImageView imageView) {
        Glide.with(context).load(bytes).into(imageView);
    }


    //byte[]转base64
    public static byte[] base64String2Byte(String base64Str) {
        return android.util.Base64.decode(base64Str, android.util.Base64.DEFAULT);
    }

    /**
     * string转成bitmap
     *
     * @param st
     */
    public static Bitmap convertStringToIcon(Context context, String st) {
        // OutputStream out;
        Bitmap bitmap = null;
        try {
            // out = new FileOutputStream("/sdcard/aa.jpg");
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st, Base64.DEFAULT);
            bitmap =
                    BitmapFactory.decodeByteArray(bitmapArray, 0,
                            bitmapArray.length);
            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return bitmap;
        } catch (Exception e) {
            Drawable drawable = context.
                    getApplicationContext().getResources().
                    getDrawable(R.drawable.ic_defalut);
            BitmapDrawable bd = (BitmapDrawable) drawable;
            final Bitmap bm = bd.getBitmap();
            return bm;
        }
    }

    /**
     * 清除图片磁盘缓存
     */
    public void clearImageDiskCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
// BusUtil.getBus().post(new GlideCacheClearSuccessEvent());
                    }
                }).start();
            } else {
                Glide.get(context).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除图片内存缓存
     */
    public void clearImageMemoryCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(context).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除图片所有缓存
     */
    public void clearImageAllCache(Context context) {
        clearImageDiskCache(context);
        clearImageMemoryCache(context);
        String ImageExternalCatchDir = context.getExternalCacheDir() + ExternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
        deleteFolderFile(ImageExternalCatchDir, true);
    }

    /**
     * 获取Glide造成的缓存大小
     *
     * @return CacheSize
     */
    public String getCacheSize(Context context) {
        try {
            return getFormatSize(getFolderSize(new File(context.getCacheDir() + "/" + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     * @throws Exception
     */
    private long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 删除指定目录下的文件，这里用于缓存的删除
     *
     * @param filePath       filePath
     * @param deleteThisPath deleteThisPath
     */
    private void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (File file1 : files) {
                        deleteFolderFile(file1.getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {
                        file.delete();
                    } else {
                        if (file.listFiles().length == 0) {
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 格式化单位
     *
     * @param size size
     * @return size
     */
    private static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }
}
