package com.fykj.dcoreview.utils.imageUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.socks.library.KLog;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by dengzh on 2018/3/6.
 * 图片压缩
 * 1.按比例压缩，尽量压缩成所需要的大小
 * 2.按质量压缩，设置一个大小值，使其不会传太大的图片到服务器
 */

public class BitmapCompressUtils {


    public static Bitmap getCompressBitmap(String path){
        Bitmap bitmap = getResizeBitmap(path,480,640);
        return compressBitmap(bitmap,1024);
    }

    /**
     * 根据宽高压缩
     *
     * @param filePath
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap getResizeBitmap(String filePath, int reqWidth, int reqHeight) {
        Bitmap resizedBitmap = null;
        File f = new File(filePath);
        long fileSize = f.length();
        if (f.exists() && fileSize > 0) {  //1.判断图片是否存在
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                //options.inPreferredConfig = Bitmap.Config.RGB_565;
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(filePath, options);
                //2.计算图片缩放值
                int picWidth = options.outWidth;
                int picHeight = options.outHeight;

                KLog.d("压缩前图片宽度"+ picWidth + "，高度为" + picHeight);

                if(picWidth > reqWidth || picHeight > reqHeight){  //图片宽高大于所需宽高才压缩
                    options.inSampleSize = Math.max(options.outWidth / reqWidth,
                            options.outHeight / reqHeight);
                }else{
                    options.inSampleSize = 1;
                }
                options.inJustDecodeBounds = false;
                resizedBitmap = BitmapFactory.decodeFile(filePath, options);

                KLog.d("压缩后图片宽度"+ resizedBitmap.getWidth() + "，高度为" + resizedBitmap.getHeight());
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return resizedBitmap;
    }


    /**
     * 2.质量压缩
     *   质量压缩Bitmap,这个方法只会改变图片的存储大小,不会改变bitmap的大小
     *   最大压缩比例为100
     * @param bitmap  bitmap
     * @param maxFileSize 最大大小
     * @return Bitmap Bitmap
     */
    public static Bitmap compressBitmap(Bitmap bitmap, int maxFileSize) {
        if(bitmap == null){
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options_ = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG,options_,baos);
        int baosLength = baos.toByteArray().length;
        while (baosLength/1024 > maxFileSize){
            //循环判断如果压缩后图片是否大于maxMemmorrySize,大于继续压缩
            baos.reset();
            if (options_ > 10) {
                options_ -= 10;
            } else {
                options_ -= 1;
            }
            if (options_ == 0) {
                break;
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG,options_,baos);
            //将压缩后的图片保存到baos中
            baosLength = baos.toByteArray().length;
            if (options_ == 0)//如果图片的质量已降到最低则，不再进行压缩
                break;
        }
        KLog.d("压缩后图片的大小" + (bitmap.getByteCount() / 1024 +"KB")
                + "bytes.length=  " + (baosLength/ 1024) + "KB"
                + "quality=" + options_);
        bitmap.recycle();
        bitmap = null;
        return BitmapFactory.decodeByteArray(baos.toByteArray(),0,baosLength);
    }

}
