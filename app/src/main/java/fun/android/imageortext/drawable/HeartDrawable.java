package fun.android.imageortext.drawable;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HeartDrawable extends Drawable {
    private final Paint paint;
    private final Path heartPath = new Path();
    private final int size;

    /**
     * @param color 心形颜色 (含透明度)
     * @param sizeDp 大小 (dp)
     */
    public HeartDrawable(int color, float sizeDp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        this.size = (int) (sizeDp * density + 0.5f);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);

        calculateHeartPath();
    }

    // 用贝塞尔曲线绘制心形
    private void calculateHeartPath() {
        heartPath.reset();
        float centerX = size / 2f;
        float centerY = size / 2f;
        float radius = size / 3f; // 控制心形大小

        // 左半圆
        RectF leftOval = new RectF(
                centerX - radius,
                centerY - radius / 2f,
                centerX,
                centerY + radius / 2f
        );
        // 右半圆
        RectF rightOval = new RectF(
                centerX,
                centerY - radius / 2f,
                centerX + radius,
                centerY + radius / 2f
        );
        // 底部三角形
        float bottomY = centerY + radius * 1.2f;

        heartPath.arcTo(leftOval, 135, 180, false);
        heartPath.arcTo(rightOval, 225, 180, false);
        heartPath.lineTo(centerX, bottomY);
        heartPath.close();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        float scaleX = (float) bounds.width() / size;
        float scaleY = (float) bounds.height() / size;
        canvas.save();
        canvas.translate(bounds.left, bounds.top);
        canvas.scale(scaleX, scaleY);
        canvas.drawPath(heartPath, paint);
        canvas.restore();
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return size;
    }

    @Override
    public int getIntrinsicHeight() {
        return size;
    }
}
