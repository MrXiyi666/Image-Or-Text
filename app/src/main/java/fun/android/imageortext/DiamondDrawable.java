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

public class DiamondDrawable extends Drawable {
    private final Paint mPaint;
    private final Path mDiamondPath;
    private final float mSizePx;

    public DiamondDrawable(int color, float sizeDp) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(color);

        mSizePx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeDp, Resources.getSystem().getDisplayMetrics());
        mDiamondPath = new Path();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        if (bounds.isEmpty()) return;

        // 计算菱形 4 个顶点（中心在边界中心）
        float centerX = bounds.exactCenterX();
        float centerY = bounds.exactCenterY();
        float halfSize = mSizePx / 2f;

        mDiamondPath.reset();
        mDiamondPath.moveTo(centerX, centerY - halfSize); // 上
        mDiamondPath.lineTo(centerX + halfSize, centerY); // 右
        mDiamondPath.lineTo(centerX, centerY + halfSize); // 下
        mDiamondPath.lineTo(centerX - halfSize, centerY); // 左
        mDiamondPath.close();

        canvas.drawPath(mDiamondPath, mPaint);
    }

    @Override public void setAlpha(int alpha) { mPaint.setAlpha(alpha); invalidateSelf(); }
    @Override public void setColorFilter(ColorFilter filter) { mPaint.setColorFilter(filter); invalidateSelf(); }
    @Override public int getOpacity() { return PixelFormat.TRANSLUCENT; }
    @Override public int getIntrinsicWidth() { return (int) mSizePx; }
    @Override public int getIntrinsicHeight() { return (int) mSizePx; }
}
