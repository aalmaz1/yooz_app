package cn.baos.watch.sdk.manager.packageAlbumDial;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import cn.baos.watch.sdk.base.AppDataConfig;
import cn.baos.watch.sdk.entitiy.Constant;
import cn.baos.watch.sdk.utils.FileUtils;
import cn.baos.watch.sdk.utils.LogUtil;
import cn.baos.watch.w100.messages.Wallpaper_info;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/* JADX INFO: loaded from: classes.dex */
public class WatchImageUtil {
    public int IMG_DEFAULT_WIDTH = 240;
    public int IMG_DEFAULT_HEIGHT = 280;

    public List<String> compressList(Context context, List<String> list, int i, int i2) {
        ArrayList arrayList = new ArrayList();
        if (list != null && list.size() > 0) {
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(compressImage240280(context, it.next(), i, i2));
            }
        }
        return arrayList;
    }

    public String compressImage240280Small(Context context, String str, int i, int i2) throws Throwable {
        this.IMG_DEFAULT_WIDTH = i;
        this.IMG_DEFAULT_HEIGHT = i2;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(str, options);
        LogUtil.d("打包表盘参数-原图片的宽:" + bitmapDecodeFile.getWidth() + " 图片的长:" + bitmapDecodeFile.getHeight() + " 图片的格式:" + bitmapDecodeFile.getConfig().name() + " 图片大小:" + bitmapDecodeFile.getByteCount());
        Bitmap watchDialImg = getWatchDialImg(bitmapDecodeFile, this.IMG_DEFAULT_WIDTH, this.IMG_DEFAULT_HEIGHT);
        File file = new File(FileUtils.getDirAndCreate(context, "imgCropper"), "imgCropper" + System.currentTimeMillis() + ".jpg");
        saveBitmap(watchDialImg, file.getPath());
        LogUtil.d("打包表盘参数-目标图片的宽:" + watchDialImg.getWidth() + " 图片的长:" + watchDialImg.getHeight() + " 图片的格式:" + watchDialImg.getConfig().name() + " 图片大小:" + watchDialImg.getByteCount());
        bitmapDecodeFile.recycle();
        watchDialImg.recycle();
        return file.getPath();
    }

    public String compressImage240280(Context context, String str, int i, int i2) throws Throwable {
        this.IMG_DEFAULT_WIDTH = i;
        this.IMG_DEFAULT_HEIGHT = i2;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(str, options);
        LogUtil.d("打包表盘参数-原图片的宽:" + bitmapDecodeFile.getWidth() + " 图片的长:" + bitmapDecodeFile.getHeight() + " 图片的格式:" + bitmapDecodeFile.getConfig().name() + " 图片大小:" + bitmapDecodeFile.getByteCount());
        Bitmap watchDialImg = getWatchDialImg(bitmapDecodeFile, this.IMG_DEFAULT_WIDTH, this.IMG_DEFAULT_HEIGHT);
        File file = new File(FileUtils.getDirAndCreate(context, "imgCropper"), "imgCropper" + System.currentTimeMillis() + ".jpg");
        saveBitmap(watchDialImg, file.getPath());
        LogUtil.d("打包表盘参数-目标图片的宽:" + watchDialImg.getWidth() + " 图片的长:" + watchDialImg.getHeight() + " 图片的格式:" + watchDialImg.getConfig().name() + " 图片大小:" + watchDialImg.getByteCount());
        bitmapDecodeFile.recycle();
        watchDialImg.recycle();
        return file.getPath();
    }

    public String compressImage152176(Context context, String str) throws Throwable {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(str, options);
        LogUtil.d("打包表盘参数-原图片的宽:" + bitmapDecodeFile.getWidth() + " 图片的长:" + bitmapDecodeFile.getHeight() + " 图片的格式:" + bitmapDecodeFile.getConfig().name() + " 图片大小:" + bitmapDecodeFile.getByteCount());
        Bitmap watchDialImg = getWatchDialImg(bitmapDecodeFile, 152, 176);
        File file = new File(FileUtils.getDirAndCreate(context, "imgCropper"), "imgCropper" + System.currentTimeMillis() + ".jpg");
        saveBitmap(watchDialImg, file.getPath());
        LogUtil.d("打包表盘参数-目标预览图片的宽:" + watchDialImg.getWidth() + " 预览图片的长:" + watchDialImg.getHeight() + " 预览图片的格式:" + watchDialImg.getConfig().name() + " 预览图片大小:" + watchDialImg.getByteCount());
        bitmapDecodeFile.recycle();
        watchDialImg.recycle();
        return file.getPath();
    }

    public String drawControlOnCompressed(Context context, String str, Wallpaper_info wallpaper_info, int i, int i2) throws Throwable {
        this.IMG_DEFAULT_WIDTH = i;
        this.IMG_DEFAULT_HEIGHT = i2;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(str, options);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.IMG_DEFAULT_WIDTH, this.IMG_DEFAULT_HEIGHT, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.drawBitmap(bitmapDecodeFile, 0.0f, 0.0f, (Paint) null);
        Wallpaper_info.Control_info[] control_infoArr = wallpaper_info.controls;
        Paint paint = new Paint();
        if (control_infoArr != null) {
            for (int i3 = 0; i3 < control_infoArr.length; i3++) {
                int i4 = control_infoArr[i3].id;
                if (i4 == 1) {
                    if (control_infoArr[i3].visible == 1) {
                        paint.setTextSize(sp2px(context, 11.0f));
                        paint.setColor(Color.argb(control_infoArr[i3].text_color.alpha, control_infoArr[i3].text_color.red, control_infoArr[i3].text_color.green, control_infoArr[i3].text_color.blue));
                        paint.setTextAlign(Paint.Align.LEFT);
                        canvas.drawText(getWeekWithLanguage(context), control_infoArr[i3].left, Math.abs(paint.ascent()) + control_infoArr[i3].top, paint);
                    }
                } else if (i4 == 2 && control_infoArr[i3].visible == 1) {
                    paint.setTextSize(sp2px(context, 19.0f));
                    paint.setColor(Color.argb(control_infoArr[i3].text_color.alpha, control_infoArr[i3].text_color.red, control_infoArr[i3].text_color.green, control_infoArr[i3].text_color.blue));
                    paint.setTextAlign(Paint.Align.LEFT);
                    canvas.drawText("12:36", control_infoArr[i3].left, Math.abs(paint.ascent()) + control_infoArr[i3].top, paint);
                }
            }
        }
        File file = new File(FileUtils.getDirAndCreate(context, "imgCropper"), "mergeImageForPreview" + System.currentTimeMillis() + ".jpg");
        saveBitmap(bitmapCreateBitmap, file.getPath());
        LogUtil.d("打包表盘参数-合并图片的宽:" + bitmapCreateBitmap.getWidth() + " 图片的长:" + bitmapCreateBitmap.getHeight() + " 图片的格式:" + bitmapCreateBitmap.getConfig().name() + " 图片大小:" + bitmapCreateBitmap.getByteCount());
        bitmapDecodeFile.recycle();
        bitmapCreateBitmap.recycle();
        return file.getPath();
    }

    public String drawControlOnCompressedSmall(Context context, String str, Wallpaper_info wallpaper_info) throws Throwable {
        this.IMG_DEFAULT_WIDTH = 240;
        this.IMG_DEFAULT_HEIGHT = 280;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(str, options);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.IMG_DEFAULT_WIDTH, this.IMG_DEFAULT_HEIGHT, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.drawBitmap(bitmapDecodeFile, 0.0f, 0.0f, (Paint) null);
        Wallpaper_info.Control_info[] control_infoArr = wallpaper_info.controls;
        Paint paint = new Paint();
        if (control_infoArr != null) {
            for (int i = 0; i < control_infoArr.length; i++) {
                int i2 = control_infoArr[i].id;
                if (i2 == 1) {
                    if (control_infoArr[i].visible == 1) {
                        paint.setTextSize(sp2px(context, 11.0f));
                        paint.setColor(Color.argb(control_infoArr[i].text_color.alpha, control_infoArr[i].text_color.red, control_infoArr[i].text_color.green, control_infoArr[i].text_color.blue));
                        paint.setTextAlign(Paint.Align.LEFT);
                        canvas.drawText(getWeekWithLanguage(context), control_infoArr[i].left, Math.abs(paint.ascent()) + control_infoArr[i].top, paint);
                    }
                } else if (i2 == 2 && control_infoArr[i].visible == 1) {
                    paint.setTextSize(sp2px(context, 19.0f));
                    paint.setColor(Color.argb(control_infoArr[i].text_color.alpha, control_infoArr[i].text_color.red, control_infoArr[i].text_color.green, control_infoArr[i].text_color.blue));
                    paint.setTextAlign(Paint.Align.LEFT);
                    canvas.drawText("12:36", control_infoArr[i].left, Math.abs(paint.ascent()) + control_infoArr[i].top, paint);
                }
            }
        }
        File file = new File(FileUtils.getDirAndCreate(context, "imgCropper"), "mergeImageForPreview" + System.currentTimeMillis() + ".jpg");
        saveBitmap(bitmapCreateBitmap, file.getPath());
        LogUtil.d("打包表盘参数-合并图片的宽:" + bitmapCreateBitmap.getWidth() + " 图片的长:" + bitmapCreateBitmap.getHeight() + " 图片的格式:" + bitmapCreateBitmap.getConfig().name() + " 图片大小:" + bitmapCreateBitmap.getByteCount());
        bitmapDecodeFile.recycle();
        bitmapCreateBitmap.recycle();
        return file.getPath();
    }

    private static float sp2px(Context context, float f) {
        return f * context.getResources().getDisplayMetrics().scaledDensity;
    }

    private Bitmap getWatchDialImg(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale((i * 1.0f) / width, (i2 * 1.0f) / height);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r0v18 */
    /* JADX WARN: Type inference failed for: r0v19 */
    /* JADX WARN: Type inference failed for: r0v20 */
    /* JADX WARN: Type inference failed for: r0v21 */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r4v0, types: [android.graphics.Bitmap] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x004c -> B:36:0x004f). Please report as a decompilation issue!!! */
    private void saveBitmap(Bitmap bitmap, String str) throws Throwable {
        LogUtil.d("打包表盘参数-图片保存路径:" + str);
        ?? r0 = 0;
        FileOutputStream fileOutputStream = null;
        FileOutputStream fileOutputStream2 = null;
        r0 = 0;
        try {
        } catch (IOException e) {
            e.printStackTrace();
            r0 = r0;
        }
        try {
            try {
                FileOutputStream fileOutputStream3 = new FileOutputStream(new File(str));
                try {
                    r0 = 100;
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream3);
                    fileOutputStream3.flush();
                    fileOutputStream3.close();
                } catch (FileNotFoundException e2) {
                    e = e2;
                    fileOutputStream = fileOutputStream3;
                    e.printStackTrace();
                    r0 = fileOutputStream;
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                        r0 = fileOutputStream;
                    }
                } catch (IOException e3) {
                    e = e3;
                    fileOutputStream2 = fileOutputStream3;
                    e.printStackTrace();
                    r0 = fileOutputStream2;
                    if (fileOutputStream2 != null) {
                        fileOutputStream2.close();
                        r0 = fileOutputStream2;
                    }
                } catch (Throwable th) {
                    th = th;
                    r0 = fileOutputStream3;
                    if (r0 != 0) {
                        try {
                            r0.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (FileNotFoundException e5) {
                e = e5;
            } catch (IOException e6) {
                e = e6;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public String getWeekWithLanguage(Context context) {
        String str;
        boolean zIsWatchLuangh = AppDataConfig.getInstance().isWatchLuangh();
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(7);
        int i2 = calendar.get(5);
        String str2 = "";
        if (!zIsWatchLuangh) {
            switch (i) {
                case 1:
                    str2 = "Sun";
                    break;
                case 2:
                    str2 = "Mon";
                    break;
                case 3:
                    str2 = "Tue";
                    break;
                case 4:
                    str2 = "Wed";
                    break;
                case 5:
                    str2 = "Thu";
                    break;
                case 6:
                    str2 = "Fri";
                    break;
                case 7:
                    str2 = "Sat";
                    break;
            }
        } else {
            switch (i) {
                case 1:
                    str = Constant.CYCLE_TYPE_SUNDAY_STR;
                    break;
                case 2:
                    str = Constant.CYCLE_TYPE_MONDAY_STR;
                    break;
                case 3:
                    str = Constant.CYCLE_TYPE_TUESDAY_STR;
                    break;
                case 4:
                    str = Constant.CYCLE_TYPE_WEDNESDAY_STR;
                    break;
                case 5:
                    str = Constant.CYCLE_TYPE_THURSDAY_STR;
                    break;
                case 6:
                    str = Constant.CYCLE_TYPE_FRIDAY_STR;
                    break;
                case 7:
                    str = Constant.CYCLE_TYPE_SATURDAY_STR;
                    break;
            }
            str2 = str;
        }
        return str2 + StringUtils.SPACE + i2;
    }
}
