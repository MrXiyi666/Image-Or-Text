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

public class HexagonDrawable extends Drawable {
    private final Paint mPaint;
    private final Path mHexagonPath;
    private final float mSizePx;

    public HexagonDrawable(int color, float sizeDp) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(color);

        mSizePx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeDp, Resources.getSystem().getDisplayMetrics());
        mHexagonPath = new Path();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        if (bounds.isEmpty()) return;

        float centerX = bounds.exactCenterX();
        float centerY = bounds.exactCenterY();
        float radius = mSizePx / 2f; // 外接圆半径
        mHexagonPath.reset();

        // 正六边形顶点角度：间隔 60°
        for (int i = 0; i < 6; i++) {
            float angle = (float) (Math.PI * 2 * i / 6 - Math.PI / 2);
            float x = centerX + radius * (float) Math.cos(angle);
            float y = centerY + radius * (float) Math.sin(angle);
            if (i == 0) mHexagonPath.moveTo(x, y);
            else mHexagonPath.lineTo(x, y);
        }
        mHexagonPath.close();

        canvas.drawPath(mHexagonPath, mPaint);
    }

    @Override public void setAlpha(int alpha) { mPaint.setAlpha(alpha); invalidateSelf(); }
    @Override public void setColorFilter(ColorFilter filter) { mPaint.setColorFilter(filter); invalidateSelf(); }
    @Override public int getOpacity() { return PixelFormat.TRANSLUCENT; }
    @Override public int getIntrinsicWidth() { return (int) mSizePx; }
    @Override public int getIntrinsicHeight() { return (int) mSizePx; }
}
