package cn.baos.watch.sdk.old.test;

import android.util.Base64;
import cn.baos.watch.sdk.manager.message.MessageManager;
import cn.baos.watch.sdk.utils.LogUtil;
import cn.baos.watch.w100.messages.CommandContentReturnRequest;
import cn.baos.watch.w100.messages.MessageBase;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;

/* JADX INFO: loaded from: classes.dex */
public class TestMessageManager {
    private static TestMessageManager instance;
    int byteNumBig = 240;
    int byteNumSmall = 0;
    String mCurrentBigPackage;
    String mCurrentSmallPackage;

    public static TestMessageManager getInstance() {
        if (instance == null) {
            synchronized (TestMessageManager.class) {
                if (instance == null) {
                    instance = new TestMessageManager();
                }
            }
        }
        return instance;
    }

    public String getCurrentSmallPackage() {
        return this.mCurrentSmallPackage;
    }

    public String startSmallPackageTest(int i) {
        this.mCurrentSmallPackage = getStringHexForSmallPackageNew();
        LogUtil.d("st:小包发送内容字节长度:" + this.mCurrentSmallPackage.getBytes(StandardCharsets.UTF_8).length);
        CommandContentReturnRequest commandContentReturnRequest = new CommandContentReturnRequest();
        commandContentReturnRequest.content = this.mCurrentSmallPackage;
        MessageManager.makeMessageToSend(commandContentReturnRequest, "100");
        LogUtil.d("st:发送小包数据测试,写入蓝牙通道数据:" + commandContentReturnRequest.content.length());
        MessageManager.getInstance().sendMessage((MessageBase) commandContentReturnRequest);
        return this.mCurrentSmallPackage;
    }

    public String startBigPackageTest(int i) {
        this.mCurrentBigPackage = getStringHexForBigPackageNew();
        LogUtil.d("st:大包发送内容字节长度:" + this.mCurrentBigPackage.getBytes(StandardCharsets.UTF_8).length);
        CommandContentReturnRequest commandContentReturnRequest = new CommandContentReturnRequest();
        commandContentReturnRequest.content = this.mCurrentBigPackage;
        commandContentReturnRequest.id = (int) (System.currentTimeMillis() / 1000);
        MessageManager.makeMessageToSend(commandContentReturnRequest, String.valueOf(System.currentTimeMillis()));
        LogUtil.d("st:发送大包数据测试,写入蓝牙通道数据:" + commandContentReturnRequest.content.length());
        MessageManager.getInstance().sendMessage((MessageBase) commandContentReturnRequest);
        return this.mCurrentBigPackage;
    }

    public String getCurrentBigPackage() {
        return this.mCurrentBigPackage;
    }

    private StringBuffer getStringHexForBigPackage() {
        byte[] bArr = new byte[752];
        for (int i = 0; i < 188; i++) {
            int i2 = i * 4;
            bArr[i2] = (byte) ((i >> 24) & 255);
            bArr[i2 + 1] = (byte) ((i >> 16) & 255);
            bArr[i2 + 2] = (byte) ((i >> 8) & 255);
            bArr[i2 + 3] = (byte) (i & 255);
        }
        return new StringBuffer(Base64.encodeToString(bArr, 2));
    }

    private String getStringHexForBigPackageNew() {
        if (this.byteNumBig >= 1024) {
            this.byteNumBig = 240;
        }
        this.byteNumBig++;
        return getRandStr(1024);
    }

    private String getStringHexForSmallPackageNew() {
        if (this.byteNumSmall >= 240) {
            this.byteNumSmall = 0;
        }
        int i = this.byteNumSmall + 1;
        this.byteNumSmall = i;
        return getRandStr(i);
    }

    public static String getRandStr(int i) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 1; i2 <= i; i2++) {
            stringBuffer.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt((int) (Math.random() * 26.0d)));
        }
        return stringBuffer.toString();
    }

    public static byte[] saveBigPackageDate(CommandContentReturnRequest commandContentReturnRequest) {
        byte[] bArrArray = new byte[0];
        try {
            MessageBufferPacker messageBufferPackerNewDefaultBufferPacker = MessagePack.newDefaultBufferPacker();
            commandContentReturnRequest.put(messageBufferPackerNewDefaultBufferPacker);
            byte[] byteArray = messageBufferPackerNewDefaultBufferPacker.toByteArray();
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(byteArray.length + 4);
            byteBufferAllocate.putInt(byteArray.length + 4);
            byteBufferAllocate.put(byteArray);
            bArrArray = byteBufferAllocate.array();
            LogUtil.d("发送大包message序列化总长度：" + bArrArray.length);
            return bArrArray;
        } catch (IOException e) {
            e.printStackTrace();
            return bArrArray;
        }
    }
}
