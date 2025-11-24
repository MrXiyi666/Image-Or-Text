package fun.android.imageortext.drawable;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import androidx.annotation.NonNull;

public class RoundedRectDrawable extends Drawable {
    private final Paint mPaint;
    private final float mSizePx; // 10dp 转换后的像素
    private final float mCornerRadiusPx; // 圆角大小（dp 转换后）

    /**
     * @param color 颜色（含透明度）
     * @param sizeDp 整体大小（10dp）
     * @param cornerRadiusDp 圆角大小（如 2dp）
     */
    public RoundedRectDrawable(int color, float sizeDp, float cornerRadiusDp) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(color);

        // dp 转像素
        mSizePx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeDp, Resources.getSystem().getDisplayMetrics());
        mCornerRadiusPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, cornerRadiusDp, Resources.getSystem().getDisplayMetrics());
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        if (bounds.isEmpty()) return;

        // 绘制圆角矩形（居中显示）
        float left = bounds.left + (bounds.width() - mSizePx) / 2f;
        float top = bounds.top + (bounds.height() - mSizePx) / 2f;
        RectF rectF = new RectF(left, top, left + mSizePx, top + mSizePx);
        canvas.drawRoundRect(rectF, mCornerRadiusPx, mCornerRadiusPx, mPaint);
    }

    @Override public void setAlpha(int alpha) { mPaint.setAlpha(alpha); invalidateSelf(); }
    @Override public void setColorFilter(ColorFilter filter) { mPaint.setColorFilter(filter); invalidateSelf(); }
    @Override public int getOpacity() { return PixelFormat.TRANSLUCENT; }
    @Override public int getIntrinsicWidth() { return (int) mSizePx; }
    @Override public int getIntrinsicHeight() { return (int) mSizePx; }
}
