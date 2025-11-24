package fun.android.imageortext;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import androidx.annotation.NonNull;

public class PentagonDrawable extends Drawable {
    private final Paint mPaint;
    private final Path mPentagonPath;
    private final float mSizePx;

    public PentagonDrawable(int color, float sizeDp) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(color);

        mSizePx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeDp, Resources.getSystem().getDisplayMetrics());
        mPentagonPath = new Path();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        if (bounds.isEmpty()) return;

        float centerX = bounds.exactCenterX();
        float centerY = bounds.exactCenterY();
        float radius = mSizePx / 2f; // 外接圆半径
        mPentagonPath.reset();

        // 正五边形顶点角度：每个顶点间隔 72°，起始角度 -90°（朝上）
        for (int i = 0; i < 5; i++) {
            float angle = (float) (Math.PI * 2 * i / 5 - Math.PI / 2);
            float x = centerX + radius * (float) Math.cos(angle);
            float y = centerY + radius * (float) Math.sin(angle);
            if (i == 0) mPentagonPath.moveTo(x, y);
            else mPentagonPath.lineTo(x, y);
        }
        mPentagonPath.close();

        canvas.drawPath(mPentagonPath, mPaint);
    }

    @Override public void setAlpha(int alpha) { mPaint.setAlpha(alpha); invalidateSelf(); }
    @Override public void setColorFilter(ColorFilter filter) { mPaint.setColorFilter(filter); invalidateSelf(); }
    @Override public int getOpacity() { return PixelFormat.TRANSLUCENT; }
    @Override public int getIntrinsicWidth() { return (int) mSizePx; }
    @Override public int getIntrinsicHeight() { return (int) mSizePx; }
}
