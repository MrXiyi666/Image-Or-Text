package fun.android.imageortext;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import androidx.annotation.NonNull;

public class CrossDrawable extends Drawable {
    private final Paint mPaint;
    private final float mSizePx;
    private final float mLineWidthPx; // 线条宽度（dp 转换后）

    /**
     * @param color 颜色
     * @param sizeDp 整体大小（10dp）
     * @param lineWidthDp 线条宽度（如 2dp）
     */
    public CrossDrawable(int color, float sizeDp, float lineWidthDp) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        mPaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, lineWidthDp, Resources.getSystem().getDisplayMetrics()));
        mPaint.setStrokeCap(Paint.Cap.ROUND); // 线条端点圆角

        mSizePx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeDp, Resources.getSystem().getDisplayMetrics());
        mLineWidthPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, lineWidthDp, Resources.getSystem().getDisplayMetrics());
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        if (bounds.isEmpty()) return;

        float centerX = bounds.exactCenterX();
        float centerY = bounds.exactCenterY();
        float halfLength = (mSizePx - mLineWidthPx) / 2f; // 线条长度（减去线宽避免超出边界）

        // 绘制水平线（左右方向）
        canvas.drawLine(centerX - halfLength, centerY, centerX + halfLength, centerY, mPaint);
        // 绘制垂直线（上下方向）
        canvas.drawLine(centerX, centerY - halfLength, centerX, centerY + halfLength, mPaint);
    }

    @Override public void setAlpha(int alpha) { mPaint.setAlpha(alpha); invalidateSelf(); }
    @Override public void setColorFilter(ColorFilter filter) { mPaint.setColorFilter(filter); invalidateSelf(); }
    @Override public int getOpacity() { return PixelFormat.TRANSLUCENT; }
    @Override public int getIntrinsicWidth() { return (int) mSizePx; }
    @Override public int getIntrinsicHeight() { return (int) mSizePx; }
}
