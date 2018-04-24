package com.fykj.dcoreview.utils.imageUtils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/7/28.
 */

public class BitmapUtils {

    /**
     * 将 路径转换为 bitmap
     * 根据图片路径 处理后返回bitmap
     * 相对于直接使用 BitmapFactory.decodeFile(filePath); 有效防止OOM
     * @param filePath  文件路径
     * @param zoomLevel 缩放级别（建议是2的整倍数）
     * @return
     */
    public static Bitmap pathToBitmap(String filePath,int zoomLevel) {
        try {
            InputStream is = new FileInputStream(filePath);
            return inputstremToBitmap(is,zoomLevel);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 将 inputStrem 转换为bitmap
     *
     * @param is 文件字节流
     * @param zoomLevel 缩放级别（建议为2的整倍数）
     * @return
     */
    public static Bitmap inputstremToBitmap(InputStream is, int zoomLevel) {
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            // 内存中申请100k缓存空间
            opts.inTempStorage = new byte[100 * 1024];
            opts.inPreferredConfig = Bitmap.Config.RGB_565;
            //设置图片可以被回收，创建Bitmap用于存储Pixel的内存空间在系统内存不足时可以被回收
            opts.inPurgeable = true;
            //设置位图缩放比例 width，hight设为原来的四分一（该参数请使用2的整数倍）
            // 这也减小了位图占用的内存大小；例如，一张分辨率为2048*1536px的图像使用inSampleSize值为4的设置来解码，产生的Bitmap大小约为512*384px。相较于完整图片占用12M的内存，这种方式只需0.75M内存(假设Bitmap配置为ARGB_8888)。
            opts.inSampleSize = zoomLevel;
            //设置解码位图的尺寸信息
            opts.inInputShareable = true;
            //解码位图
            Bitmap bitmap = BitmapFactory.decodeStream(is, null, opts);
            // 返回所需bitmap
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




    // 缩放图片
    public static Bitmap zoomImg(Bitmap bm, int newWidth , int newHeight){
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }
   //保存图片到sd
    public static  void saveImageSdcard(Context context,Bitmap bitmap){
        if (Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED)) // 判断是否可以对SDcard进行操作
        {    // 获取SDCard指定目录下
            String  sdCardDir = Environment.getExternalStorageDirectory()+ "/nntb/";
            File dirFile  = new File(sdCardDir);  //目录转化成文件夹
            if (!dirFile .exists()) {              //如果不存在，那就建立这个文件夹
                dirFile .mkdirs();
            }                          //文件夹有啦，就可以保存图片啦
            File file = new File(sdCardDir, System.currentTimeMillis()+".jpg");// 在SDcard的目录下创建图片文,以当前时间为其命名
            FileOutputStream out = null;
            try {
                 out = new FileOutputStream(file);
                 bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                 System.out.println("_________保存到____sd______指定目录文件夹下____________________");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Toast.makeText(context,"保存已经至"+sdCardDir+"目录文件夹下", Toast.LENGTH_LONG).show();
        }
    }


    //保存文件到指定路径
    public static boolean saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "nntb";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            //把文件插入到系统图库
            //MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);

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
}
