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
import androidx.annotation.Nullable;

public class SquareDrawable extends Drawable {
    private final Paint mPaint;
    private final float mSizePx; // 正方形的边长（像素，由 10dp 转换而来）

    /**
     * 构造方法
     * @param color 正方形的颜色（支持透明度）
     * @param sizeDp 正方形的边长（单位：dp）
     */
    public SquareDrawable(int color, float sizeDp) {
        // 1. 初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(color);

        // 2. 将 dp 转换为像素，以适配不同分辨率
        mSizePx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                sizeDp,
                Resources.getSystem().getDisplayMetrics()
        );
    }

    // 重写：绘制正方形
    @Override
    public void draw(@NonNull Canvas canvas) {
        // 获取当前 Drawable 被分配到的绘制区域
        Rect bounds = getBounds();

        // 确保边界有效
        if (bounds.isEmpty()) {
            return;
        }

        // 计算出在当前边界内能够容纳的最大正方形的边长和位置
        // 正方形的边长是边界宽和高中的较小值
        float squareSize = Math.min(bounds.width(), bounds.height());
        // 计算正方形左上角的坐标，使其在边界内居中
        float left = bounds.left + (bounds.width() - squareSize) / 2f;
        float top = bounds.top + (bounds.height() - squareSize) / 2f;
        float right = left + squareSize;
        float bottom = top + squareSize;

        // 绘制正方形
        canvas.drawRect(left, top, right, bottom, mPaint);
    }

    // 重写：设置透明度
    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
        invalidateSelf(); // 触发重绘
    }

    // 重写：设置颜色过滤器
    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    // 重写：获取透明度模式
    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    // 重写：获取固有宽度
    @Override
    public int getIntrinsicWidth() {
        return (int) mSizePx;
    }

    // 重写：获取固有高度
    @Override
    public int getIntrinsicHeight() {
        return (int) mSizePx;
    }
}
