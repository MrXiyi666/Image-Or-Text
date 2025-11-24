package fun.android.imageortext;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CircleDrawable extends Drawable {
    private final Paint paint;
    private final int size; // 圆形的大小 (像素)

    /**
     * 创建一个具有固定大小的圆形 Drawable
     * @param color 颜色 (包含透明度)
     * @param sizeDp 圆形的大小 (单位: dp)
     */
    public CircleDrawable(int color, float sizeDp) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG); // 抗锯齿
        paint.setColor(color);

        // 将 dp 转换为像素
        float density = Resources.getSystem().getDisplayMetrics().density;
        this.size = (int) (sizeDp * density + 0.5f); // +0.5f 是为了四舍五入
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        // 获取 Drawable 的边界
        Rect bounds = getBounds();
        // 在边界的中心画一个圆
        float centerX = bounds.exactCenterX();
        float centerY = bounds.exactCenterY();
        // 半径取宽高中的较小值的一半
        float radius = Math.min(bounds.width(), bounds.height()) / 2f;
        canvas.drawCircle(centerX, centerY, radius, paint);
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

    // 重写这两个方法，返回 Drawable 的固有大小
    @Override
    public int getIntrinsicWidth() {
        return size;
    }

    @Override
    public int getIntrinsicHeight() {
        return size;
    }
}
