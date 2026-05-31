package cn.baos.watch.sdk.manager.packageAlbumDial;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import cn.baos.watch.sdk.manager.message.MessageManager;
import cn.baos.watch.sdk.utils.EncryptionUtils;
import cn.baos.watch.sdk.utils.FileUtils;
import cn.baos.watch.sdk.utils.LogUtil;
import cn.baos.watch.sdk.utils.W100Utils;
import cn.baos.watch.w100.messages.CommandBleOTAFile;
import cn.baos.watch.w100.messages.Device_base_info;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class PackageAlbumDialUtil {
    public byte[] packageAlbumDial(byte[] bArr, String str, String str2, byte[] bArr2, byte[] bArr3) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(str2, options);
        LogUtil.d("打包表盘参数-压缩过后的原图片的宽:" + bitmapDecodeFile.getWidth() + " 图片的长:" + bitmapDecodeFile.getHeight() + " 图片的格式:" + bitmapDecodeFile.getConfig().name() + " 图片大小:" + bitmapDecodeFile.getByteCount());
        LogUtil.d("打包表盘参数-header长度:" + bArr.length + " 数组内容" + W100Utils.bytesToHexString(bArr));
        Bitmap bitmapDecodeFile2 = BitmapFactory.decodeFile(str, options);
        LogUtil.d("打包表盘参数-压缩过后的原图片的宽:" + bitmapDecodeFile2.getWidth() + " 图片的长:" + bitmapDecodeFile2.getHeight() + " 图片的格式:" + bitmapDecodeFile2.getConfig().name() + " 图片大小:" + bitmapDecodeFile2.getByteCount());
        int length = bArr3.length;
        bArr[12] = (byte) ((length >> 0) & 255);
        bArr[13] = (byte) ((length >> 8) & 255);
        bArr[14] = (byte) ((length >> 16) & 255);
        bArr[15] = (byte) ((length >> 24) & 255);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bitmapDecodeFile.getByteCount());
        bitmapDecodeFile.copyPixelsToBuffer(byteBufferAllocate);
        byte[] bArrRgb16Swap = rgb16Swap(byteBufferAllocate.array());
        LogUtil.d("打包表盘参数-imageData:" + bArrRgb16Swap.length + " 数组内容" + W100Utils.bytesToHexString(bArrRgb16Swap));
        ByteBuffer byteBufferAllocate2 = ByteBuffer.allocate(bitmapDecodeFile2.getByteCount());
        bitmapDecodeFile2.copyPixelsToBuffer(byteBufferAllocate2);
        byte[] bArrRgb16Swap2 = rgb16Swap(byteBufferAllocate2.array());
        LogUtil.d("打包表盘参数-imageDataPreview:" + bArrRgb16Swap2.length + " 数组内容" + W100Utils.bytesToHexString(bArrRgb16Swap2));
        int length2 = bArr.length + bArrRgb16Swap2.length + bArrRgb16Swap.length + bArr2.length + bArr3.length;
        byte[] bArr4 = new byte[length2];
        LogUtil.d("打包表盘参数-start:=header=" + bArr.length + "=imageDataPreview=" + bArrRgb16Swap2.length + "=imageData=" + bArrRgb16Swap.length + "=layoutMagic=" + bArr2.length + "=layoutData=" + bArr3.length);
        LogUtil.d("打包表盘参数-md5:=header=" + W100Utils.bytesToHexString(EncryptionUtils.getFileMD5ByteReturnByteArray(bArr)));
        System.arraycopy(bArr, 0, bArr4, 0, bArr.length);
        System.arraycopy(bArrRgb16Swap2, 0, bArr4, bArr.length, bArrRgb16Swap2.length);
        System.arraycopy(bArrRgb16Swap, 0, bArr4, bArr.length + bArrRgb16Swap2.length, bArrRgb16Swap.length);
        System.arraycopy(bArr2, 0, bArr4, bArr.length + bArrRgb16Swap2.length + bArrRgb16Swap.length, bArr2.length);
        System.arraycopy(bArr3, 0, bArr4, bArr.length + bArrRgb16Swap2.length + bArrRgb16Swap.length + bArr2.length, bArr3.length);
        LogUtil.d("打包表盘参数-allDataForMd5:" + length2 + " 数组内容" + W100Utils.bytesToHexString(bArr4));
        byte[] fileMD5ByteReturnByteArray = EncryptionUtils.getFileMD5ByteReturnByteArray(bArr4);
        LogUtil.d("打包表盘参数-md5Byte:" + fileMD5ByteReturnByteArray.length + " 数组内容" + W100Utils.bytesToHexString(fileMD5ByteReturnByteArray));
        LogUtil.d("打包表盘参数-header长度:" + bArr.length + " 数组内容" + W100Utils.bytesToHexString(bArr));
        LogUtil.d("打包表盘参数-imageDataPreview:" + bArrRgb16Swap2.length + " 数组内容" + W100Utils.bytesToHexString(bArrRgb16Swap2));
        LogUtil.d("打包表盘参数-imageData:" + bArrRgb16Swap.length + " 数组内容" + W100Utils.bytesToHexString(bArrRgb16Swap));
        LogUtil.d("打包表盘参数-layoutMagic:" + bArr2.length + " 数组内容" + W100Utils.bytesToHexString(bArr2));
        LogUtil.d("打包表盘参数-layoutData:" + bArr3.length + " 数组内容" + W100Utils.bytesToHexString(bArr3));
        LogUtil.d("打包表盘参数-md5Byte:" + fileMD5ByteReturnByteArray.length + " 数组内容" + W100Utils.bytesToHexString(fileMD5ByteReturnByteArray));
        int length3 = bArr.length + bArrRgb16Swap2.length + bArrRgb16Swap.length + bArr2.length + bArr3.length + fileMD5ByteReturnByteArray.length;
        byte[] bArr5 = new byte[length3];
        System.arraycopy(bArr, 0, bArr5, 0, bArr.length);
        System.arraycopy(bArrRgb16Swap2, 0, bArr5, bArr.length, bArrRgb16Swap2.length);
        System.arraycopy(bArrRgb16Swap, 0, bArr5, bArr.length + bArrRgb16Swap2.length, bArrRgb16Swap.length);
        int length4 = bArr.length + bArrRgb16Swap2.length + bArrRgb16Swap.length;
        System.arraycopy(bArr2, 0, bArr5, length4, bArr2.length);
        System.arraycopy(bArr3, 0, bArr5, bArr2.length + length4, bArr3.length);
        System.arraycopy(fileMD5ByteReturnByteArray, 0, bArr5, length4 + bArr2.length + bArr3.length, fileMD5ByteReturnByteArray.length);
        LogUtil.d("打包表盘参数-packageData:" + length3 + " 数组内容" + W100Utils.bytesToHexString(bArr5));
        bitmapDecodeFile.recycle();
        return bArr5;
    }

    public byte[] packageAlbumDials(byte[] bArr, String str, List<String> list, byte[] bArr2, byte[] bArr3) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        ArrayList arrayList = new ArrayList();
        int length = 0;
        for (Iterator<String> it = list.iterator(); it.hasNext(); it = it) {
            Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(it.next(), options);
            LogUtil.d("打包表盘参数-压缩过后的原图片的宽:" + bitmapDecodeFile.getWidth() + " 图片的长:" + bitmapDecodeFile.getHeight() + " 图片的格式:" + bitmapDecodeFile.getConfig().name() + " 图片大小:" + bitmapDecodeFile.getByteCount());
            LogUtil.d("打包表盘参数-header长度:" + bArr.length + " 数组内容" + W100Utils.bytesToHexString(bArr));
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bitmapDecodeFile.getByteCount());
            bitmapDecodeFile.copyPixelsToBuffer(byteBufferAllocate);
            byte[] bArrRgb16Swap = rgb16Swap(byteBufferAllocate.array());
            length += bArrRgb16Swap.length;
            arrayList.add(bArrRgb16Swap);
            bitmapDecodeFile.recycle();
        }
        LogUtil.d("打包表盘参数-byteSize=start=" + length);
        byte[] bArr4 = new byte[length];
        int length2 = 0;
        for (Iterator it2 = arrayList.iterator(); it2.hasNext(); it2 = it2) {
            byte[] bArr5 = (byte[]) it2.next();
            System.arraycopy(bArr5, 0, bArr4, length2, bArr5.length);
            length2 += bArr5.length;
        }
        LogUtil.d("打包表盘参数-byteSize=end=" + length);
        Bitmap bitmapDecodeFile2 = BitmapFactory.decodeFile(str, options);
        LogUtil.d("打包表盘参数-压缩过后的原图片的宽:" + bitmapDecodeFile2.getWidth() + " 图片的长:" + bitmapDecodeFile2.getHeight() + " 图片的格式:" + bitmapDecodeFile2.getConfig().name() + " 图片大小:" + bitmapDecodeFile2.getByteCount());
        int length3 = bArr3.length;
        bArr[12] = (byte) ((length3 >> 0) & 255);
        bArr[13] = (byte) ((length3 >> 8) & 255);
        bArr[14] = (byte) ((length3 >> 16) & 255);
        bArr[15] = (byte) ((length3 >> 24) & 255);
        LogUtil.d("打包表盘参数-imageData:" + length + " 数组内容" + W100Utils.bytesToHexString(bArr4));
        ByteBuffer byteBufferAllocate2 = ByteBuffer.allocate(bitmapDecodeFile2.getByteCount());
        bitmapDecodeFile2.copyPixelsToBuffer(byteBufferAllocate2);
        byte[] bArrRgb16Swap2 = rgb16Swap(byteBufferAllocate2.array());
        LogUtil.d("打包表盘参数-imageDataPreview:" + bArrRgb16Swap2.length + " 数组内容" + W100Utils.bytesToHexString(bArrRgb16Swap2));
        int length4 = bArr.length + bArrRgb16Swap2.length + length + bArr2.length + bArr3.length;
        byte[] bArr6 = new byte[length4];
        LogUtil.d("打包表盘参数-start:=header=" + bArr.length + "=imageDataPreview=" + bArrRgb16Swap2.length + "=imageData=" + length + "=layoutMagic=" + bArr2.length + "=layoutData=" + bArr3.length);
        LogUtil.d("打包表盘参数-md5:=header=" + W100Utils.bytesToHexString(EncryptionUtils.getFileMD5ByteReturnByteArray(bArr)));
        System.arraycopy(bArr, 0, bArr6, 0, bArr.length);
        System.arraycopy(bArrRgb16Swap2, 0, bArr6, bArr.length, bArrRgb16Swap2.length);
        System.arraycopy(bArr4, 0, bArr6, bArr.length + bArrRgb16Swap2.length, length);
        System.arraycopy(bArr2, 0, bArr6, bArr.length + bArrRgb16Swap2.length + length, bArr2.length);
        System.arraycopy(bArr3, 0, bArr6, bArr.length + bArrRgb16Swap2.length + length + bArr2.length, bArr3.length);
        LogUtil.d("打包表盘参数-allDataForMd5:" + length4 + " 数组内容" + W100Utils.bytesToHexString(bArr6));
        byte[] fileMD5ByteReturnByteArray = EncryptionUtils.getFileMD5ByteReturnByteArray(bArr6);
        LogUtil.d("打包表盘参数-md5Byte:" + fileMD5ByteReturnByteArray.length + " 数组内容" + W100Utils.bytesToHexString(fileMD5ByteReturnByteArray));
        LogUtil.d("打包表盘参数-header长度:" + bArr.length + " 数组内容" + W100Utils.bytesToHexString(bArr));
        LogUtil.d("打包表盘参数-imageDataPreview:" + bArrRgb16Swap2.length + " 数组内容" + W100Utils.bytesToHexString(bArrRgb16Swap2));
        LogUtil.d("打包表盘参数-imageData:" + length + " 数组内容" + W100Utils.bytesToHexString(bArr4));
        LogUtil.d("打包表盘参数-layoutMagic:" + bArr2.length + " 数组内容" + W100Utils.bytesToHexString(bArr2));
        LogUtil.d("打包表盘参数-layoutData:" + bArr3.length + " 数组内容" + W100Utils.bytesToHexString(bArr3));
        LogUtil.d("打包表盘参数-md5Byte:" + fileMD5ByteReturnByteArray.length + " 数组内容" + W100Utils.bytesToHexString(fileMD5ByteReturnByteArray));
        int length5 = bArr.length + bArrRgb16Swap2.length + length + bArr2.length + bArr3.length + fileMD5ByteReturnByteArray.length;
        byte[] bArr7 = new byte[length5];
        System.arraycopy(bArr, 0, bArr7, 0, bArr.length);
        System.arraycopy(bArrRgb16Swap2, 0, bArr7, bArr.length, bArrRgb16Swap2.length);
        System.arraycopy(bArr4, 0, bArr7, bArr.length + bArrRgb16Swap2.length, length);
        int length6 = bArr.length + bArrRgb16Swap2.length + length;
        System.arraycopy(bArr2, 0, bArr7, length6, bArr2.length);
        System.arraycopy(bArr3, 0, bArr7, bArr2.length + length6, bArr3.length);
        System.arraycopy(fileMD5ByteReturnByteArray, 0, bArr7, length6 + bArr2.length + bArr3.length, fileMD5ByteReturnByteArray.length);
        LogUtil.d("打包表盘参数-packageData:" + length5 + " 数组内容" + W100Utils.bytesToHexString(bArr7));
        return bArr7;
    }

    byte[] rgb16Swap(byte[] bArr) {
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        for (int i = 0; i < length; i += 2) {
            int i2 = i + 1;
            bArr2[i] = bArr[i2];
            bArr2[i2] = bArr[i];
        }
        return bArr2;
    }

    public String saveAlbumByteLocal(Context context, Device_base_info device_base_info, byte[] bArr) {
        CommandBleOTAFile commandBleOTAFile = new CommandBleOTAFile();
        commandBleOTAFile.file_type = 1;
        commandBleOTAFile.file_size = bArr.length;
        commandBleOTAFile.file_md5 = EncryptionUtils.getFileMD5Byte(bArr);
        commandBleOTAFile.old_version = device_base_info.software_version;
        commandBleOTAFile.new_version = device_base_info.software_version;
        commandBleOTAFile.hardware_id = "N27H";
        commandBleOTAFile.name = "N27H";
        commandBleOTAFile.desc = "device";
        commandBleOTAFile.file_id = 4;
        commandBleOTAFile.old_addr = 0;
        commandBleOTAFile.dest_addr = 0;
        commandBleOTAFile.data = bArr;
        byte[] bArrPackMessage = MessageManager.packMessage(commandBleOTAFile);
        String path = new File(FileUtils.getDirAndCreate(context, "imgCropper"), "albumByte" + System.currentTimeMillis() + ".bin").getPath();
        LogUtil.d("打包表盘参数-表盘bin文件存储路径:" + path);
        LogUtil.d("打包表盘参数-表盘bin文件大小:" + bArrPackMessage.length);
        FileOutputStream fileOutputStream = null;
        try {
            try {
                try {
                    FileOutputStream fileOutputStream2 = new FileOutputStream(new File(path));
                    try {
                        fileOutputStream2.write(bArrPackMessage);
                        fileOutputStream2.flush();
                        fileOutputStream2.close();
                    } catch (FileNotFoundException e) {
                        e = e;
                        fileOutputStream = fileOutputStream2;
                        e.printStackTrace();
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        return path;
                    } catch (IOException e2) {
                        e = e2;
                        fileOutputStream = fileOutputStream2;
                        e.printStackTrace();
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        return path;
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream = fileOutputStream2;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (FileNotFoundException e4) {
                    e = e4;
                } catch (IOException e5) {
                    e = e5;
                }
            } catch (IOException e6) {
                e6.printStackTrace();
            }
            return path;
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
