package com.king.view.viewfinderview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class ViewfinderView extends View {
    private final float DEFAULT_RANGE_RATIO;
    private final float MAX_ZOOM_RATIO;
    private final int POINT_ANIMATION_INTERVAL;
    private float currentZoomRatio;
    private Rect frame;
    private Bitmap frameBitmap;
    private int frameColor;
    private int frameCornerColor;
    private int frameCornerSize;
    private int frameCornerStrokeWidth;
    private FrameGravity frameGravity;
    private int frameHeight;
    private int frameLineStrokeWidth;
    private float framePaddingBottom;
    private float framePaddingLeft;
    private float framePaddingRight;
    private float framePaddingTop;
    private float frameRatio;
    private int frameWidth;
    private GestureDetector gestureDetector;
    private boolean isPointAnimation;
    private boolean isShowPoints;
    private String labelText;
    private int labelTextColor;
    private TextLocation labelTextLocation;
    private float labelTextPadding;
    private float labelTextSize;
    private int labelTextWidth;
    private int laserAnimationInterval;
    private Bitmap laserBitmap;
    private float laserBitmapRatio;
    private float laserBitmapWidth;
    private int laserColor;
    private int laserGridColumn;
    private int laserGridHeight;
    private int laserLineHeight;
    private int laserMovementSpeed;
    private LaserStyle laserStyle;
    private float lastZoomRatio;
    private int maskColor;
    private int minDimension;
    private OnItemClickListener onItemClickListener;
    private Paint paint;
    private int pointAnimationInterval;
    private Bitmap pointBitmap;
    private int pointColor;
    private List<Point> pointList;
    private float pointRadius;
    private float pointRangeRadius;
    private int pointStrokeColor;
    private float pointStrokeRadius;
    private float pointStrokeRatio;
    private int scannerEnd;
    private int scannerStart;
    private TextPaint textPaint;
    private int viewfinderStyle;
    private int zoomCount;
    private float zoomSpeed;

    public interface OnItemClickListener {
        void onItemClick(int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewfinderStyle {
        public static final int CLASSIC = 0;
        public static final int POPULAR = 1;
    }

    public enum LaserStyle {
        NONE(0),
        LINE(1),
        GRID(2),
        IMAGE(3);

        private final int mValue;

        LaserStyle(int i) {
            this.mValue = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static LaserStyle getFromInt(int i) {
            for (LaserStyle laserStyle : values()) {
                if (laserStyle.mValue == i) {
                    return laserStyle;
                }
            }
            return LINE;
        }
    }

    public enum TextLocation {
        TOP(0),
        BOTTOM(1);

        private final int mValue;

        TextLocation(int i) {
            this.mValue = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static TextLocation getFromInt(int i) {
            for (TextLocation textLocation : values()) {
                if (textLocation.mValue == i) {
                    return textLocation;
                }
            }
            return TOP;
        }
    }

    public enum FrameGravity {
        CENTER(0),
        LEFT(1),
        TOP(2),
        RIGHT(3),
        BOTTOM(4);

        private final int mValue;

        FrameGravity(int i) {
            this.mValue = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static FrameGravity getFromInt(int i) {
            for (FrameGravity frameGravity : values()) {
                if (frameGravity.mValue == i) {
                    return frameGravity;
                }
            }
            return CENTER;
        }
    }

    public ViewfinderView(Context context) {
        this(context, null);
    }

    public ViewfinderView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ViewfinderView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.DEFAULT_RANGE_RATIO = 1.2f;
        this.MAX_ZOOM_RATIO = 1.2f;
        this.POINT_ANIMATION_INTERVAL = 3000;
        this.scannerStart = 0;
        this.scannerEnd = 0;
        this.isPointAnimation = true;
        this.currentZoomRatio = 1.0f;
        this.zoomSpeed = 0.02f;
        this.viewfinderStyle = 0;
        this.isShowPoints = false;
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ViewfinderView);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        this.viewfinderStyle = typedArrayObtainStyledAttributes.getInt(R.styleable.ViewfinderView_vvViewfinderStyle, 0);
        this.maskColor = typedArrayObtainStyledAttributes.getColor(R.styleable.ViewfinderView_vvMaskColor, getColor(context, R.color.viewfinder_mask));
        this.frameColor = typedArrayObtainStyledAttributes.getColor(R.styleable.ViewfinderView_vvFrameColor, getColor(context, R.color.viewfinder_frame));
        this.frameWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ViewfinderView_vvFrameWidth, 0);
        this.frameHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ViewfinderView_vvFrameHeight, 0);
        this.frameRatio = typedArrayObtainStyledAttributes.getFloat(R.styleable.ViewfinderView_vvFrameRatio, 0.625f);
        this.frameLineStrokeWidth = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_vvFrameLineStrokeWidth, TypedValue.applyDimension(1, 1.0f, displayMetrics));
        this.framePaddingLeft = typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_vvFramePaddingLeft, 0.0f);
        this.framePaddingTop = typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_vvFramePaddingTop, 0.0f);
        this.framePaddingRight = typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_vvFramePaddingRight, 0.0f);
        this.framePaddingBottom = typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_vvFramePaddingBottom, 0.0f);
        this.frameGravity = FrameGravity.getFromInt(typedArrayObtainStyledAttributes.getInt(R.styleable.ViewfinderView_vvFrameGravity, FrameGravity.CENTER.mValue));
        this.frameCornerColor = typedArrayObtainStyledAttributes.getColor(R.styleable.ViewfinderView_vvFrameCornerColor, getColor(context, R.color.viewfinder_corner));
        this.frameCornerSize = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_vvFrameCornerSize, TypedValue.applyDimension(1, 16.0f, displayMetrics));
        this.frameCornerStrokeWidth = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_vvFrameCornerStrokeWidth, TypedValue.applyDimension(1, 4.0f, displayMetrics));
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ViewfinderView_vvFrameDrawable);
        this.laserLineHeight = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_vvLaserLineHeight, TypedValue.applyDimension(1, 5.0f, displayMetrics));
        this.laserMovementSpeed = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_vvLaserMovementSpeed, TypedValue.applyDimension(1, 2.0f, displayMetrics));
        this.laserAnimationInterval = typedArrayObtainStyledAttributes.getInteger(R.styleable.ViewfinderView_vvLaserAnimationInterval, 20);
        this.laserGridColumn = typedArrayObtainStyledAttributes.getInt(R.styleable.ViewfinderView_vvLaserGridColumn, 20);
        this.laserGridHeight = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_vvLaserGridHeight, TypedValue.applyDimension(1, 40.0f, displayMetrics));
        this.laserColor = typedArrayObtainStyledAttributes.getColor(R.styleable.ViewfinderView_vvLaserColor, getColor(context, R.color.viewfinder_laser));
        this.laserStyle = LaserStyle.getFromInt(typedArrayObtainStyledAttributes.getInt(R.styleable.ViewfinderView_vvLaserStyle, LaserStyle.LINE.mValue));
        this.laserBitmapRatio = typedArrayObtainStyledAttributes.getFloat(R.styleable.ViewfinderView_vvLaserDrawableRatio, 0.625f);
        Drawable drawable2 = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ViewfinderView_vvLaserDrawable);
        this.labelText = typedArrayObtainStyledAttributes.getString(R.styleable.ViewfinderView_vvLabelText);
        this.labelTextColor = typedArrayObtainStyledAttributes.getColor(R.styleable.ViewfinderView_vvLabelTextColor, getColor(context, R.color.viewfinder_label_text));
        this.labelTextSize = typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_vvLabelTextSize, TypedValue.applyDimension(2, 14.0f, displayMetrics));
        this.labelTextPadding = typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_vvLabelTextPadding, TypedValue.applyDimension(1, 24.0f, displayMetrics));
        this.labelTextWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ViewfinderView_vvLabelTextWidth, 0);
        this.labelTextLocation = TextLocation.getFromInt(typedArrayObtainStyledAttributes.getInt(R.styleable.ViewfinderView_vvLabelTextLocation, 0));
        this.pointColor = typedArrayObtainStyledAttributes.getColor(R.styleable.ViewfinderView_vvPointColor, getColor(context, R.color.viewfinder_point));
        this.pointStrokeColor = typedArrayObtainStyledAttributes.getColor(R.styleable.ViewfinderView_vvPointStrokeColor, getColor(context, R.color.viewfinder_point_stroke));
        this.pointRadius = typedArrayObtainStyledAttributes.getDimension(R.styleable.ViewfinderView_vvPointRadius, TypedValue.applyDimension(1, 15.0f, displayMetrics));
        this.pointStrokeRatio = typedArrayObtainStyledAttributes.getFloat(R.styleable.ViewfinderView_vvPointStrokeRatio, 1.2f);
        Drawable drawable3 = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ViewfinderView_vvPointDrawable);
        this.isPointAnimation = typedArrayObtainStyledAttributes.getBoolean(R.styleable.ViewfinderView_vvPointAnimation, true);
        this.pointAnimationInterval = typedArrayObtainStyledAttributes.getInt(R.styleable.ViewfinderView_vvPointAnimationInterval, 3000);
        typedArrayObtainStyledAttributes.recycle();
        if (drawable != null) {
            this.frameBitmap = getBitmapFormDrawable(drawable);
        }
        if (drawable2 != null) {
            this.laserBitmap = getBitmapFormDrawable(drawable2);
        }
        if (drawable3 != null) {
            this.pointBitmap = getBitmapFormDrawable(drawable3);
            this.pointRangeRadius = ((r10.getWidth() + this.pointBitmap.getHeight()) / 4) * 1.2f;
        } else {
            float f = this.pointRadius * this.pointStrokeRatio;
            this.pointStrokeRadius = f;
            this.pointRangeRadius = f * 1.2f;
        }
        Paint paint = new Paint(1);
        this.paint = paint;
        paint.setAntiAlias(true);
        this.textPaint = new TextPaint(1);
        this.gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() { // from class: com.king.view.viewfinderview.ViewfinderView.1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                if (ViewfinderView.this.isShowPoints && ViewfinderView.this.checkSingleTap(motionEvent.getX(), motionEvent.getY())) {
                    return true;
                }
                return super.onSingleTapUp(motionEvent);
            }
        });
    }

    private int getColor(Context context, int i) {
        return context.getColor(i);
    }

    private Bitmap getBitmapFormDrawable(Drawable drawable) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, bitmapCreateBitmap.getWidth(), bitmapCreateBitmap.getHeight());
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        initFrame(getWidth(), getHeight());
    }

    private void scaleLaserBitmap() {
        if (this.laserBitmap != null) {
            float f = this.laserBitmapWidth;
            if (f > 0.0f) {
                float width = f / r0.getWidth();
                Matrix matrix = new Matrix();
                matrix.postScale(width, width);
                this.laserBitmap = Bitmap.createBitmap(this.laserBitmap, 0, 0, this.laserBitmap.getWidth(), this.laserBitmap.getHeight(), matrix, true);
            }
        }
    }

    private void initFrame(int i, int i2) {
        int iMin = Math.min(i, i2);
        this.minDimension = iMin;
        int i3 = (int) (iMin * this.frameRatio);
        if (this.laserBitmapWidth <= 0.0f) {
            this.laserBitmapWidth = iMin * this.laserBitmapRatio;
            scaleLaserBitmap();
        }
        int i4 = this.frameWidth;
        if (i4 <= 0 || i4 > i) {
            this.frameWidth = i3;
        }
        int i5 = this.frameHeight;
        if (i5 <= 0 || i5 > i2) {
            this.frameHeight = i3;
        }
        if (this.labelTextWidth <= 0) {
            this.labelTextWidth = (i - getPaddingLeft()) - getPaddingRight();
        }
        float f = (((i - this.frameWidth) / 2) + this.framePaddingLeft) - this.framePaddingRight;
        float f2 = (((i2 - this.frameHeight) / 2) + this.framePaddingTop) - this.framePaddingBottom;
        int i6 = AnonymousClass2.$SwitchMap$com$king$view$viewfinderview$ViewfinderView$FrameGravity[this.frameGravity.ordinal()];
        if (i6 == 1) {
            f = this.framePaddingLeft;
        } else if (i6 == 2) {
            f2 = this.framePaddingTop;
        } else if (i6 == 3) {
            f = (i - this.frameWidth) + this.framePaddingRight;
        } else if (i6 == 4) {
            f2 = (i2 - this.frameHeight) + this.framePaddingBottom;
        }
        int i7 = (int) f;
        int i8 = (int) f2;
        this.frame = new Rect(i7, i8, this.frameWidth + i7, this.frameHeight + i8);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        if (this.isShowPoints) {
            drawMask(canvas, getWidth(), getHeight());
            drawResultPoints(canvas, this.pointList);
            if (this.isPointAnimation) {
                calcPointZoomAnimation();
                return;
            }
            return;
        }
        Rect rect = this.frame;
        if (rect == null) {
            return;
        }
        if (this.scannerStart == 0 || this.scannerEnd == 0) {
            this.scannerStart = rect.top;
            this.scannerEnd = this.frame.bottom - this.laserLineHeight;
        }
        int i = this.viewfinderStyle;
        if (i == 0) {
            drawExterior(canvas, this.frame, getWidth(), getHeight());
            drawLaserScanner(canvas, this.frame);
            drawFrame(canvas, this.frame);
            drawTextInfo(canvas, this.frame);
            postInvalidateDelayed(this.laserAnimationInterval, this.frame.left, this.frame.top, this.frame.right, this.frame.bottom);
            return;
        }
        if (i == 1) {
            drawLaserScanner(canvas, this.frame);
            drawTextInfo(canvas, this.frame);
            postInvalidateDelayed(this.laserAnimationInterval);
        }
    }

    private void drawTextInfo(Canvas canvas, Rect rect) {
        if (TextUtils.isEmpty(this.labelText)) {
            return;
        }
        this.textPaint.setColor(this.labelTextColor);
        this.textPaint.setTextSize(this.labelTextSize);
        this.textPaint.setTextAlign(Paint.Align.CENTER);
        StaticLayout staticLayout = new StaticLayout(this.labelText, this.textPaint, this.labelTextWidth, Layout.Alignment.ALIGN_NORMAL, 1.2f, 0.0f, true);
        if (this.labelTextLocation == TextLocation.BOTTOM) {
            canvas.translate(rect.left + (rect.width() / 2), rect.bottom + this.labelTextPadding);
        } else {
            canvas.translate(rect.left + (rect.width() / 2), (rect.top - this.labelTextPadding) - staticLayout.getHeight());
        }
        staticLayout.draw(canvas);
    }

    private void drawCorner(Canvas canvas, Rect rect) {
        this.paint.setColor(this.frameCornerColor);
        canvas.drawRect(rect.left, rect.top, rect.left + this.frameCornerStrokeWidth, rect.top + this.frameCornerSize, this.paint);
        canvas.drawRect(rect.left, rect.top, rect.left + this.frameCornerSize, rect.top + this.frameCornerStrokeWidth, this.paint);
        canvas.drawRect(rect.right - this.frameCornerStrokeWidth, rect.top, rect.right, rect.top + this.frameCornerSize, this.paint);
        canvas.drawRect(rect.right - this.frameCornerSize, rect.top, rect.right, rect.top + this.frameCornerStrokeWidth, this.paint);
        canvas.drawRect(rect.left, rect.bottom - this.frameCornerStrokeWidth, rect.left + this.frameCornerSize, rect.bottom, this.paint);
        canvas.drawRect(rect.left, rect.bottom - this.frameCornerSize, rect.left + this.frameCornerStrokeWidth, rect.bottom, this.paint);
        canvas.drawRect(rect.right - this.frameCornerStrokeWidth, rect.bottom - this.frameCornerSize, rect.right, rect.bottom, this.paint);
        canvas.drawRect(rect.right - this.frameCornerSize, rect.bottom - this.frameCornerStrokeWidth, rect.right, rect.bottom, this.paint);
    }

    private void drawImageScanner(Canvas canvas, Rect rect) {
        Bitmap bitmap = this.laserBitmap;
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, (getWidth() - this.laserBitmap.getWidth()) / 2, this.scannerStart, this.paint);
            int i = this.scannerStart;
            if (i < this.scannerEnd) {
                this.scannerStart = i + this.laserMovementSpeed;
                return;
            } else {
                this.scannerStart = rect.top;
                return;
            }
        }
        drawLineScanner(canvas, rect);
    }

    private void drawLaserScanner(Canvas canvas, Rect rect) {
        if (this.laserStyle != null) {
            this.paint.setColor(this.laserColor);
            int i = AnonymousClass2.$SwitchMap$com$king$view$viewfinderview$ViewfinderView$LaserStyle[this.laserStyle.ordinal()];
            if (i == 1) {
                drawLineScanner(canvas, rect);
            } else if (i == 2) {
                drawGridScanner(canvas, rect);
            } else if (i == 3) {
                drawImageScanner(canvas, rect);
            }
            this.paint.setShader(null);
        }
    }

    /* JADX INFO: renamed from: com.king.view.viewfinderview.ViewfinderView$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$king$view$viewfinderview$ViewfinderView$FrameGravity;
        static final /* synthetic */ int[] $SwitchMap$com$king$view$viewfinderview$ViewfinderView$LaserStyle;

        static {
            int[] iArr = new int[LaserStyle.values().length];
            $SwitchMap$com$king$view$viewfinderview$ViewfinderView$LaserStyle = iArr;
            try {
                iArr[LaserStyle.LINE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$king$view$viewfinderview$ViewfinderView$LaserStyle[LaserStyle.GRID.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$king$view$viewfinderview$ViewfinderView$LaserStyle[LaserStyle.IMAGE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[FrameGravity.values().length];
            $SwitchMap$com$king$view$viewfinderview$ViewfinderView$FrameGravity = iArr2;
            try {
                iArr2[FrameGravity.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$king$view$viewfinderview$ViewfinderView$FrameGravity[FrameGravity.TOP.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$king$view$viewfinderview$ViewfinderView$FrameGravity[FrameGravity.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$king$view$viewfinderview$ViewfinderView$FrameGravity[FrameGravity.BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    private void drawLineScanner(Canvas canvas, Rect rect) {
        this.paint.setShader(new LinearGradient(rect.centerX(), this.scannerStart, rect.centerX(), this.scannerStart + this.laserLineHeight, shadeColor(this.laserColor), this.laserColor, Shader.TileMode.MIRROR));
        if (this.scannerStart < this.scannerEnd) {
            canvas.drawOval(new RectF(rect.left + this.frameCornerSize, this.scannerStart, rect.right - this.frameCornerSize, this.scannerStart + this.laserLineHeight), this.paint);
            this.scannerStart += this.laserMovementSpeed;
        } else {
            this.scannerStart = rect.top;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0018  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void drawGridScanner(android.graphics.Canvas r14, android.graphics.Rect r15) {
        /*
            r13 = this;
            android.graphics.Paint r0 = r13.paint
            r1 = 2
            float r2 = (float) r1
            r0.setStrokeWidth(r2)
            int r0 = r13.laserGridHeight
            if (r0 <= 0) goto L18
            int r0 = r13.scannerStart
            int r2 = r15.top
            int r0 = r0 - r2
            int r2 = r13.laserGridHeight
            if (r0 <= r2) goto L18
            int r0 = r13.scannerStart
            int r0 = r0 - r2
            goto L1a
        L18:
            int r0 = r15.top
        L1a:
            android.graphics.LinearGradient r10 = new android.graphics.LinearGradient
            int r2 = r15.centerX()
            float r3 = (float) r2
            float r0 = (float) r0
            int r2 = r15.centerX()
            float r5 = (float) r2
            int r2 = r13.scannerStart
            float r6 = (float) r2
            int[] r7 = new int[r1]
            int r2 = r13.laserColor
            int r2 = r13.shadeColor(r2)
            r11 = 0
            r7[r11] = r2
            int r2 = r13.laserColor
            r12 = 1
            r7[r12] = r2
            float[] r8 = new float[r1]
            r8 = {x00c0: FILL_ARRAY_DATA , data: [0, 1065353216} // fill-array
            android.graphics.Shader$TileMode r9 = android.graphics.Shader.TileMode.CLAMP
            r2 = r10
            r4 = r0
            r2.<init>(r3, r4, r5, r6, r7, r8, r9)
            android.graphics.Paint r1 = r13.paint
            r1.setShader(r10)
            int r1 = r15.width()
            float r1 = (float) r1
            r2 = 1065353216(0x3f800000, float:1.0)
            float r1 = r1 * r2
            int r2 = r13.laserGridColumn
            float r2 = (float) r2
            float r1 = r1 / r2
        L57:
            int r2 = r13.laserGridColumn
            if (r12 >= r2) goto L74
            int r2 = r15.left
            float r2 = (float) r2
            float r3 = (float) r12
            float r3 = r3 * r1
            float r5 = r2 + r3
            int r2 = r15.left
            float r2 = (float) r2
            float r7 = r2 + r3
            int r2 = r13.scannerStart
            float r8 = (float) r2
            android.graphics.Paint r9 = r13.paint
            r4 = r14
            r6 = r0
            r4.drawLine(r5, r6, r7, r8, r9)
            int r12 = r12 + 1
            goto L57
        L74:
            int r0 = r13.laserGridHeight
            if (r0 <= 0) goto L82
            int r0 = r13.scannerStart
            int r2 = r15.top
            int r0 = r0 - r2
            int r2 = r13.laserGridHeight
            if (r0 <= r2) goto L82
            goto L88
        L82:
            int r0 = r13.scannerStart
            int r2 = r15.top
            int r2 = r0 - r2
        L88:
            float r0 = (float) r11
            float r3 = (float) r2
            float r3 = r3 / r1
            int r3 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r3 > 0) goto Laf
            int r3 = r15.left
            int r4 = r13.frameLineStrokeWidth
            int r3 = r3 + r4
            float r5 = (float) r3
            int r3 = r13.scannerStart
            float r3 = (float) r3
            float r0 = r0 * r1
            float r6 = r3 - r0
            int r3 = r15.right
            int r4 = r13.frameLineStrokeWidth
            int r3 = r3 - r4
            float r7 = (float) r3
            int r3 = r13.scannerStart
            float r3 = (float) r3
            float r8 = r3 - r0
            android.graphics.Paint r9 = r13.paint
            r4 = r14
            r4.drawLine(r5, r6, r7, r8, r9)
            int r11 = r11 + 1
            goto L88
        Laf:
            int r14 = r13.scannerStart
            int r0 = r13.scannerEnd
            if (r14 >= r0) goto Lbb
            int r15 = r13.laserMovementSpeed
            int r14 = r14 + r15
            r13.scannerStart = r14
            goto Lbf
        Lbb:
            int r14 = r15.top
            r13.scannerStart = r14
        Lbf:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.king.view.viewfinderview.ViewfinderView.drawGridScanner(android.graphics.Canvas, android.graphics.Rect):void");
    }

    private int shadeColor(int i) {
        return Integer.valueOf("01" + Integer.toHexString(i).substring(2), 16).intValue();
    }

    private void drawFrame(Canvas canvas, Rect rect) {
        this.paint.setColor(this.frameColor);
        Bitmap bitmap = this.frameBitmap;
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, (Rect) null, rect, this.paint);
            return;
        }
        canvas.drawRect(rect.left, rect.top, rect.right, rect.top + this.frameLineStrokeWidth, this.paint);
        canvas.drawRect(rect.left, rect.top, rect.left + this.frameLineStrokeWidth, rect.bottom, this.paint);
        canvas.drawRect(rect.right - this.frameLineStrokeWidth, rect.top, rect.right, rect.bottom, this.paint);
        canvas.drawRect(rect.left, rect.bottom - this.frameLineStrokeWidth, rect.right, rect.bottom, this.paint);
        drawCorner(canvas, rect);
    }

    private void drawExterior(Canvas canvas, Rect rect, int i, int i2) {
        int i3 = this.maskColor;
        if (i3 != 0) {
            this.paint.setColor(i3);
            float f = i;
            canvas.drawRect(0.0f, 0.0f, f, rect.top, this.paint);
            canvas.drawRect(0.0f, rect.top, rect.left, rect.bottom, this.paint);
            canvas.drawRect(rect.right, rect.top, f, rect.bottom, this.paint);
            canvas.drawRect(0.0f, rect.bottom, f, i2, this.paint);
        }
    }

    private void drawMask(Canvas canvas, int i, int i2) {
        int i3 = this.maskColor;
        if (i3 != 0) {
            this.paint.setColor(i3);
            canvas.drawRect(0.0f, 0.0f, i, i2, this.paint);
        }
    }

    private void drawResultPoints(Canvas canvas, List<Point> list) {
        this.paint.setColor(-1);
        if (list != null) {
            Iterator<Point> it = list.iterator();
            while (it.hasNext()) {
                drawResultPoint(canvas, it.next(), this.currentZoomRatio);
            }
        }
    }

    private void calcPointZoomAnimation() {
        float f = this.currentZoomRatio;
        if (f <= 1.0f) {
            this.lastZoomRatio = f;
            this.currentZoomRatio = f + this.zoomSpeed;
            int i = this.zoomCount;
            if (i < 2) {
                this.zoomCount = i + 1;
            } else {
                this.zoomCount = 0;
            }
        } else if (f >= 1.2f || this.lastZoomRatio > f) {
            this.lastZoomRatio = f;
            this.currentZoomRatio = f - this.zoomSpeed;
        } else {
            this.lastZoomRatio = f;
            this.currentZoomRatio = f + this.zoomSpeed;
        }
        postInvalidateDelayed((this.zoomCount == 0 && this.lastZoomRatio == 1.0f) ? this.pointAnimationInterval : this.laserAnimationInterval * 2);
    }

    private void drawResultPoint(Canvas canvas, Point point, float f) {
        if (this.pointBitmap != null) {
            float width = point.x - (this.pointBitmap.getWidth() / 2.0f);
            float height = point.y - (this.pointBitmap.getHeight() / 2.0f);
            if (this.isPointAnimation) {
                int iRound = Math.round(this.pointBitmap.getWidth() * f);
                int iRound2 = Math.round(this.pointBitmap.getHeight() * f);
                int iRound3 = point.x - Math.round(iRound / 2.0f);
                int iRound4 = point.y - Math.round(iRound2 / 2.0f);
                canvas.drawBitmap(this.pointBitmap, (Rect) null, new Rect(iRound3, iRound4, iRound + iRound3, iRound2 + iRound4), this.paint);
                return;
            }
            canvas.drawBitmap(this.pointBitmap, width, height, this.paint);
            return;
        }
        this.paint.setColor(this.pointStrokeColor);
        canvas.drawCircle(point.x, point.y, this.pointStrokeRadius * f, this.paint);
        this.paint.setColor(this.pointColor);
        canvas.drawCircle(point.x, point.y, this.pointRadius * f, this.paint);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.isShowPoints) {
            this.gestureDetector.onTouchEvent(motionEvent);
        }
        return this.isShowPoints || super.onTouchEvent(motionEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkSingleTap(float f, float f2) {
        if (this.pointList != null) {
            for (int i = 0; i < this.pointList.size(); i++) {
                Point point = this.pointList.get(i);
                if (getDistance(f, f2, point.x, point.y) <= this.pointRangeRadius) {
                    OnItemClickListener onItemClickListener = this.onItemClickListener;
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(i);
                    }
                    return true;
                }
            }
        }
        return true;
    }

    private float getDistance(float f, float f2, float f3, float f4) {
        return (float) Math.sqrt(Math.pow(f - f3, 2.0d) + Math.pow(f2 - f4, 2.0d));
    }

    public boolean isShowPoints() {
        return this.isShowPoints;
    }

    public void showScanner() {
        this.isShowPoints = false;
        invalidate();
    }

    public void showResultPoints(List<Point> list) {
        this.pointList = list;
        this.isShowPoints = true;
        this.zoomCount = 0;
        this.lastZoomRatio = 0.0f;
        this.currentZoomRatio = 1.0f;
        invalidate();
    }

    public void setMaskColor(int i) {
        this.maskColor = i;
    }

    public void setFrameColor(int i) {
        this.frameColor = i;
    }

    public void setLaserColor(int i) {
        this.laserColor = i;
    }

    public void setFrameCornerColor(int i) {
        this.frameCornerColor = i;
    }

    public void setLabelTextPadding(float f) {
        this.labelTextPadding = f;
    }

    public void setLabelTextWidth(int i) {
        this.labelTextWidth = i;
    }

    public void setLabelTextLocation(TextLocation textLocation) {
        this.labelTextLocation = textLocation;
    }

    public void setLabelText(String str) {
        this.labelText = str;
    }

    public void setLabelTextColor(int i) {
        this.labelTextColor = i;
    }

    public void setLabelTextColorResource(int i) {
        this.labelTextColor = getColor(getContext(), i);
    }

    public void setLabelTextSize(float f) {
        this.labelTextSize = f;
    }

    public void setLaserStyle(LaserStyle laserStyle) {
        this.laserStyle = laserStyle;
    }

    public void setLaserGridColumn(int i) {
        this.laserGridColumn = i;
    }

    public void setLaserGridHeight(int i) {
        this.laserGridHeight = i;
    }

    public void setFrameCornerStrokeWidth(int i) {
        this.frameCornerStrokeWidth = i;
    }

    public void setFrameCornerSize(int i) {
        this.frameCornerSize = i;
    }

    public void setLaserMovementSpeed(int i) {
        this.laserMovementSpeed = i;
    }

    public void setLaserLineHeight(int i) {
        this.laserLineHeight = i;
    }

    public void setFrameLineStrokeWidth(int i) {
        this.frameLineStrokeWidth = i;
    }

    public void setFrameDrawable(int i) {
        setFrameBitmap(BitmapFactory.decodeResource(getResources(), i));
    }

    public void setFrameBitmap(Bitmap bitmap) {
        this.frameBitmap = bitmap;
    }

    public void setLaserAnimationInterval(int i) {
        this.laserAnimationInterval = i;
    }

    public void setPointColor(int i) {
        this.pointColor = i;
    }

    public void setPointStrokeColor(int i) {
        this.pointStrokeColor = i;
    }

    public void setPointRadius(float f) {
        this.pointRadius = f;
    }

    public void setLaserDrawable(int i) {
        setLaserBitmap(BitmapFactory.decodeResource(getResources(), i));
    }

    public void setLaserBitmap(Bitmap bitmap) {
        this.laserBitmap = bitmap;
        scaleLaserBitmap();
    }

    public void setPointDrawable(int i) {
        setPointBitmap(BitmapFactory.decodeResource(getResources(), i));
    }

    public void setPointBitmap(Bitmap bitmap) {
        this.pointBitmap = bitmap;
        this.pointRangeRadius = ((bitmap.getWidth() + this.pointBitmap.getHeight()) / 4) * 1.2f;
    }

    public void setPointAnimationInterval(int i) {
        this.pointAnimationInterval = i;
    }

    public void setViewfinderStyle(int i) {
        this.viewfinderStyle = i;
    }

    public void setFrameWidth(int i) {
        this.frameWidth = i;
    }

    public void setFrameHeight(int i) {
        this.frameHeight = i;
    }

    public void setFrameRatio(float f) {
        this.frameRatio = f;
    }

    public void setFramePaddingLeft(float f) {
        this.framePaddingLeft = f;
    }

    public void setFramePaddingTop(float f) {
        this.framePaddingTop = f;
    }

    public void setFramePaddingRight(float f) {
        this.framePaddingRight = f;
    }

    public void setFramePadding(float f, float f2, float f3, float f4) {
        this.framePaddingLeft = f;
        this.framePaddingTop = f2;
        this.framePaddingRight = f3;
        this.framePaddingBottom = f4;
    }

    public void setFramePaddingBottom(float f) {
        this.framePaddingBottom = f;
    }

    public void setFrameGravity(FrameGravity frameGravity) {
        this.frameGravity = frameGravity;
    }

    public void setPointAnimation(boolean z) {
        this.isPointAnimation = z;
    }

    public void setPointStrokeRadius(float f) {
        this.pointStrokeRadius = f;
    }

    public void setZoomSpeed(float f) {
        this.zoomSpeed = f;
    }

    public void setPointRangeRadius(float f) {
        this.pointRangeRadius = f;
    }

    public void setLaserBitmapRatio(float f) {
        this.laserBitmapRatio = f;
        int i = this.minDimension;
        if (i > 0) {
            this.laserBitmapWidth = i * f;
            scaleLaserBitmap();
        }
    }

    public void setLaserBitmapWidth(float f) {
        this.laserBitmapWidth = f;
        scaleLaserBitmap();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
