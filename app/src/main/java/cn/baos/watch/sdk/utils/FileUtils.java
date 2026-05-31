package cn.baos.watch.sdk.utils;

import android.content.ContentUris;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import cn.baos.watch.sdk.entitiy.Constant;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

/* JADX INFO: loaded from: classes.dex */
public class FileUtils {
    private static int audioByteLenght;
    private static FileOutputStream outputStream;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r0v18 */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r3v0, types: [android.graphics.Bitmap] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x0039 -> B:34:0x003c). Please report as a decompilation issue!!! */
    public static void saveBitmap(Bitmap bitmap, String str) throws Throwable {
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
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream3);
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

    public String getPath(Context context, Uri uri) {
        Uri uri2 = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                String[] strArrSplit = DocumentsContract.getDocumentId(uri).split(":");
                if ("primary".equalsIgnoreCase(strArrSplit[0])) {
                    return Environment.getExternalStorageDirectory() + "/" + strArrSplit[1];
                }
            } else {
                if (isDownloadsDocument(uri)) {
                    return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue()), null, null);
                }
                if (isMediaDocument(uri)) {
                    String[] strArrSplit2 = DocumentsContract.getDocumentId(uri).split(":");
                    String str = strArrSplit2[0];
                    if ("image".equals(str)) {
                        uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(str)) {
                        uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(str)) {
                        uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    return getDataColumn(context, uri2, "_id=?", new String[]{strArrSplit2[1]});
                }
            }
        } else {
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(context, uri, null, null);
            }
            if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }
        return null;
    }

    public String getDataColumn(Context context, Uri uri, String str, String[] strArr) throws Throwable {
        Cursor cursor = null;
        try {
            Cursor cursorQuery = context.getContentResolver().query(uri, new String[]{"_data"}, str, strArr, null);
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.moveToFirst()) {
                        String string = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("_data"));
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return string;
                    }
                } catch (Throwable th) {
                    th = th;
                    cursor = cursorQuery;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            return null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isSDCardAvailable() {
        return "mounted".equals(Environment.getExternalStorageState());
    }

    public static boolean copyConfigFinished(Context context, String str) {
        String dir = getDir(context, str);
        File file = new File(dir);
        LogUtil.d("has " + dir + "?:" + file.exists());
        return file.exists();
    }

    public static int decodeHEX(String str) {
        return new BigInteger(str, 16).intValue();
    }

    public static long decodeHEXLong(String str) {
        return Long.parseLong(str, 16);
    }

    public static void copyDirToSDCardFromAsserts(Context context, String str, String str2) {
        try {
            AssetManager assets = context.getAssets();
            String[] list = assets.list(str2);
            outputStr(str2, list);
            if (list == null || list.length <= 0) {
                return;
            }
            String dirAndCreate = getDirAndCreate(context, str);
            if (dirAndCreate == null) {
                LogUtil.d("make dir failed!");
                return;
            }
            byte[] bArr = new byte[1024];
            int length = list.length;
            InputStream inputStreamOpen = null;
            int i = 0;
            FileOutputStream fileOutputStream = null;
            while (i < length) {
                String str3 = list[i];
                File file = new File(dirAndCreate, str3);
                LogUtil.d("assets--absolute path:" + file.toString());
                if (!file.exists() && !file.createNewFile()) {
                    LogUtil.d("create new file failed!");
                    return;
                }
                inputStreamOpen = assets.open(str2 + File.separator + str3);
                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                while (true) {
                    int i2 = inputStreamOpen.read(bArr);
                    if (i2 != -1) {
                        fileOutputStream2.write(bArr, 0, i2);
                    }
                }
                fileOutputStream2.flush();
                i++;
                fileOutputStream = fileOutputStream2;
            }
            if (inputStreamOpen != null) {
                inputStreamOpen.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        } catch (IOException e) {
            LogUtil.d("FileUtils:+" + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void outputStr(String str, String[] strArr) {
        if (strArr != null) {
            if (strArr.length <= 0) {
                LogUtil.d("assets " + str + " has no folder!");
                return;
            }
            for (String str2 : strArr) {
                LogUtil.d("assets " + str + " has:" + str2);
            }
        }
    }

    public static String getDir(Context context, String str) {
        StringBuilder sb = new StringBuilder();
        if (isSDCardAvailable()) {
            sb.append(getExternalStoragePath(context));
        } else {
            sb.append(getCachePath(context));
        }
        sb.append(str);
        sb.append(File.separator);
        return sb.toString();
    }

    public static String getDirAndCreate(Context context, String str) {
        if (context == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if (isSDCardAvailable()) {
            sb.append(getExternalStoragePath(context));
        } else {
            LogUtil.d("sdcard 不存在");
            sb.append(getCachePath(context));
        }
        sb.append(str);
        sb.append(File.separator);
        String string = sb.toString();
        if (createDirs(string)) {
            return string;
        }
        LogUtil.d("创建文件夹失败，请打开app读写本地数据权限，之后重试！path:" + string);
        return null;
    }

    public static String getExternalStoragePath(Context context) {
        String str = "Android/data/" + context.getPackageName();
        return context.getExternalFilesDirs(Environment.DIRECTORY_DOCUMENTS)[0].getAbsolutePath() + File.separator;
    }

    public static String getCachePath(Context context) {
        File cacheDir = context.getCacheDir();
        if (cacheDir == null) {
            return null;
        }
        return cacheDir.getAbsolutePath() + "/";
    }

    public static boolean createDirs(String str) {
        File file = new File(str);
        if (file.exists() && file.isDirectory()) {
            return true;
        }
        LogUtil.d("创建文件夹成功，path:" + file.getPath());
        return file.mkdirs();
    }

    public static void saveAudioByteInSdcard_create(Context context) {
        if (Constant.isLocalSave) {
            try {
                FileOutputStream fileOutputStream = outputStream;
                if (fileOutputStream != null) {
                    fileOutputStream.flush();
                    outputStream.close();
                    outputStream = null;
                }
                String dirAndCreate = getDirAndCreate(context, "audio");
                LogUtil.d("日志路径:" + dirAndCreate);
                File file = new File(dirAndCreate, "audio" + System.currentTimeMillis() + ".opus");
                LogUtil.d("创建create_audio_path:" + file.getAbsolutePath());
                outputStream = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void saveAudioByteInSdcard_write(byte[] bArr) {
        if (Constant.isLocalSave) {
            audioByteLenght += bArr.length;
            LogUtil.d("audioByteLenght:" + audioByteLenght + "write byte:" + W100Utils.byte2hex(bArr));
            try {
                FileOutputStream fileOutputStream = outputStream;
                if (fileOutputStream != null) {
                    fileOutputStream.write(bArr, 0, bArr.length);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveAudioByteInSdcard_close() {
        if (Constant.isLocalSave) {
            LogUtil.d("saveAudioByteInSdcard_close");
            try {
                audioByteLenght = 0;
                FileOutputStream fileOutputStream = outputStream;
                if (fileOutputStream != null) {
                    fileOutputStream.flush();
                    outputStream.close();
                    outputStream = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static byte[] file2Bytes(File file) {
        byte[] bArr = new byte[1024];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
            while (true) {
                int i = fileInputStream.read(bArr);
                if (i != -1) {
                    byteArrayOutputStream.write(bArr, 0, i);
                } else {
                    fileInputStream.close();
                    byteArrayOutputStream.close();
                    return byteArrayOutputStream.toByteArray();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeValueToLocal(final Context context, final String str) {
        new Thread(new Runnable() { // from class: cn.baos.watch.sdk.utils.FileUtils.1
            @Override // java.lang.Runnable
            public void run() throws Throwable {
                String dirAndCreate = FileUtils.getDirAndCreate(context, "watchType");
                LogUtil.d("写入本地文件目录:" + dirAndCreate);
                if (dirAndCreate == null) {
                    LogUtil.d("make dir failed!");
                    return;
                }
                File file = new File(dirAndCreate, "watchType.txt");
                LogUtil.d("写入本地文件路径:" + file.getPath());
                if (file.exists()) {
                    LogUtil.d("watchType.txt已存在,开始写入");
                } else {
                    LogUtil.d("watchType.txt不存在,写入开始");
                }
                BufferedWriter bufferedWriter = null;
                try {
                    try {
                        try {
                            BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(file, true));
                            try {
                                bufferedWriter2.write(str + "\n");
                                LogUtil.d("写入文件内容:" + str);
                                bufferedWriter2.flush();
                                bufferedWriter2.close();
                            } catch (Exception e) {
                                e = e;
                                bufferedWriter = bufferedWriter2;
                                e.printStackTrace();
                                if (bufferedWriter == null) {
                                    return;
                                }
                                bufferedWriter.flush();
                                bufferedWriter.close();
                            } catch (Throwable th) {
                                th = th;
                                bufferedWriter = bufferedWriter2;
                                if (bufferedWriter != null) {
                                    try {
                                        bufferedWriter.flush();
                                        bufferedWriter.close();
                                    } catch (IOException e2) {
                                        e2.printStackTrace();
                                    }
                                }
                                throw th;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    } catch (Exception e3) {
                        e = e3;
                    }
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
        }).start();
    }

    public static void writeZplValueToLocal(final Context context, final String str, final String str2) {
        new Thread(new Runnable() { // from class: cn.baos.watch.sdk.utils.FileUtils.2
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r1v11 */
            /* JADX WARN: Type inference failed for: r1v12, types: [java.io.BufferedWriter] */
            /* JADX WARN: Type inference failed for: r1v13 */
            /* JADX WARN: Type inference failed for: r1v14 */
            /* JADX WARN: Type inference failed for: r1v15 */
            /* JADX WARN: Type inference failed for: r1v16 */
            /* JADX WARN: Type inference failed for: r1v22 */
            /* JADX WARN: Type inference failed for: r1v23 */
            /* JADX WARN: Type inference failed for: r1v24 */
            /* JADX WARN: Type inference failed for: r1v25 */
            /* JADX WARN: Type inference failed for: r1v7 */
            /* JADX WARN: Type inference failed for: r1v8 */
            /* JADX WARN: Type inference failed for: r1v9, types: [java.io.BufferedWriter] */
            @Override // java.lang.Runnable
            public void run() throws Throwable {
                String dirAndCreate = FileUtils.getDirAndCreate(context, "zpl");
                LogUtil.d("写入本地文件目录:" + dirAndCreate);
                if (dirAndCreate == null) {
                    LogUtil.d("make dir failed!");
                    return;
                }
                File file = new File(dirAndCreate, str.replace(":", "") + ".txt");
                LogUtil.d("写入本地文件路径:" + file.getPath());
                if (file.exists()) {
                    LogUtil.d("watchType.txt已存在,开始写入");
                } else {
                    LogUtil.d("watchType.txt不存在,写入开始");
                }
                ?? r1 = 0;
                r1 = 0;
                r1 = 0;
                try {
                    try {
                        try {
                            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
                            try {
                                bufferedWriter.write(str2 + "\n");
                                StringBuilder sb = new StringBuilder("写入文件内容:\n");
                                LogUtil.d(sb.append(str2).toString());
                                bufferedWriter.flush();
                                bufferedWriter.close();
                                r1 = sb;
                            } catch (Exception e) {
                                e = e;
                                r1 = bufferedWriter;
                                e.printStackTrace();
                                if (r1 != 0) {
                                    r1.flush();
                                    r1.close();
                                    r1 = r1;
                                }
                            } catch (Throwable th) {
                                th = th;
                                r1 = bufferedWriter;
                                if (r1 != 0) {
                                    try {
                                        r1.flush();
                                        r1.close();
                                    } catch (IOException e2) {
                                        e2.printStackTrace();
                                    }
                                }
                                throw th;
                            }
                        } catch (Exception e3) {
                            e = e3;
                        }
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            }
        }).start();
    }
}
