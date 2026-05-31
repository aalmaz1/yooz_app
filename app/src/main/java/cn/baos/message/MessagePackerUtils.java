package cn.baos.message;

import cn.baos.message.Serializable;
import java.io.IOException;
import java.nio.ByteBuffer;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;

/* JADX INFO: loaded from: classes.dex */
public class MessagePackerUtils<T extends Serializable> {
    public static final int RESERVE_SIZE = 4;

    public byte[] packObject(T t) throws IOException {
        MessageBufferPacker messageBufferPackerNewDefaultBufferPacker = MessagePack.newDefaultBufferPacker();
        t.put(messageBufferPackerNewDefaultBufferPacker);
        messageBufferPackerNewDefaultBufferPacker.close();
        byte[] byteArray = messageBufferPackerNewDefaultBufferPacker.toByteArray();
        return ByteBuffer.allocate(byteArray.length + 4).putInt(byteArray.length + 4).put(byteArray).array();
    }

    public T uppackObject(byte[] bArr, Class<T> cls) throws IOException {
        int i = ByteBuffer.wrap(bArr).getInt();
        if (i < bArr.length) {
            System.out.println("expected len:" + bArr.length + "real len:" + i);
            throw new IOException("tructed data");
        }
        try {
            return (T) cls.newInstance().load(MessagePack.newDefaultUnpacker(bArr, 4, bArr.length - 4));
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Serializable uppackObject(byte[] bArr) throws IOException {
        int i = ByteBuffer.wrap(bArr).getInt();
        if (i < bArr.length) {
            System.out.println("expected len:" + bArr.length + "real len:" + i);
            throw new IOException("tructed data");
        }
        return newInstanceFromCategory(new Serializable().load(MessagePack.newDefaultUnpacker(bArr, 4, bArr.length - 4)).catagory).load(MessagePack.newDefaultUnpacker(bArr, 4, bArr.length - 4));
    }

    public static Serializable newInstanceFromCategory(int i) {
        try {
            return (Serializable) Class.forName(CatagoryEnum.classCatagoryMap.get(Integer.valueOf(i))).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return null;
        }
    }
}
