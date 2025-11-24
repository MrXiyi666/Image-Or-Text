package fun.android.imageortext.drawable;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import androidx.annotation.NonNull;

public class RingDrawable extends Drawable {
    private final Paint mPaint;
    private final float mSizePx;
    private final float mRingWidthPx; // 圆环宽度（dp 转换后）

    /**
     * @param color 圆环颜色
     * @param sizeDp 整体大小（10dp）
     * @param ringWidthDp 圆环宽度（如 2dp）
     */
    public RingDrawable(int color, float sizeDp, float ringWidthDp) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.STROKE); // 空心样式
        mPaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ringWidthDp, Resources.getSystem().getDisplayMetrics()));

        mSizePx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeDp, Resources.getSystem().getDisplayMetrics());
        mRingWidthPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ringWidthDp, Resources.getSystem().getDisplayMetrics());
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        if (bounds.isEmpty()) return;

        float centerX = bounds.exactCenterX();
        float centerY = bounds.exactCenterY();
        float radius = (mSizePx - mRingWidthPx) / 2f; // 圆环内半径（避免环宽超出边界）

        canvas.drawCircle(centerX, centerY, radius, mPaint);
    }

    @Override public void setAlpha(int alpha) { mPaint.setAlpha(alpha); invalidateSelf(); }
    @Override public void setColorFilter(ColorFilter filter) { mPaint.setColorFilter(filter); invalidateSelf(); }
    @Override public int getOpacity() { return PixelFormat.TRANSLUCENT; }
    @Override public int getIntrinsicWidth() { return (int) mSizePx; }
    @Override public int getIntrinsicHeight() { return (int) mSizePx; }
}
