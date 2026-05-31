package androidx.heifwriter;

import android.graphics.Bitmap;
import android.graphics.Rect;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/* JADX INFO: loaded from: classes.dex */
public class EglRectBlt {
    private static final FloatBuffer FULL_RECTANGLE_BUF;
    private static final float[] FULL_RECTANGLE_COORDS;
    private static final int SIZEOF_FLOAT = 4;
    private Texture2dProgram mProgram;
    private final FloatBuffer mTexCoordArray;
    private final float[] mTexCoords;
    private final int mTexHeight;
    private final int mTexWidth;

    static {
        float[] fArr = {-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};
        FULL_RECTANGLE_COORDS = fArr;
        FULL_RECTANGLE_BUF = createFloatBuffer(fArr);
    }

    public static FloatBuffer createFloatBuffer(float[] fArr) {
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(fArr.length * 4);
        byteBufferAllocateDirect.order(ByteOrder.nativeOrder());
        FloatBuffer floatBufferAsFloatBuffer = byteBufferAllocateDirect.asFloatBuffer();
        floatBufferAsFloatBuffer.put(fArr);
        floatBufferAsFloatBuffer.position(0);
        return floatBufferAsFloatBuffer;
    }

    public EglRectBlt(Texture2dProgram texture2dProgram, int i, int i2) {
        float[] fArr = new float[8];
        this.mTexCoords = fArr;
        this.mTexCoordArray = createFloatBuffer(fArr);
        this.mProgram = texture2dProgram;
        this.mTexWidth = i;
        this.mTexHeight = i2;
    }

    public void release(boolean z) {
        Texture2dProgram texture2dProgram = this.mProgram;
        if (texture2dProgram != null) {
            if (z) {
                texture2dProgram.release();
            }
            this.mProgram = null;
        }
    }

    public int createTextureObject() {
        return this.mProgram.createTextureObject();
    }

    public void loadTexture(int i, Bitmap bitmap) {
        this.mProgram.loadTexture(i, bitmap);
    }

    public void copyRect(int i, float[] fArr, Rect rect) {
        setTexRect(rect);
        this.mProgram.draw(Texture2dProgram.IDENTITY_MATRIX, FULL_RECTANGLE_BUF, 0, 4, 2, 8, fArr, this.mTexCoordArray, i, 8);
    }

    void setTexRect(Rect rect) {
        this.mTexCoords[0] = rect.left / this.mTexWidth;
        this.mTexCoords[1] = 1.0f - (rect.bottom / this.mTexHeight);
        this.mTexCoords[2] = rect.right / this.mTexWidth;
        this.mTexCoords[3] = 1.0f - (rect.bottom / this.mTexHeight);
        this.mTexCoords[4] = rect.left / this.mTexWidth;
        this.mTexCoords[5] = 1.0f - (rect.top / this.mTexHeight);
        this.mTexCoords[6] = rect.right / this.mTexWidth;
        this.mTexCoords[7] = 1.0f - (rect.top / this.mTexHeight);
        this.mTexCoordArray.put(this.mTexCoords);
        this.mTexCoordArray.position(0);
    }
}
