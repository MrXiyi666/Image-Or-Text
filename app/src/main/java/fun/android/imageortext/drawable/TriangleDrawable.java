package fun.android.imageortext.drawable;

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
import androidx.annotation.Nullable;

public class TriangleDrawable extends Drawable {
    private final Paint mPaint;
    private final Path mTrianglePath;
    private final float mSizePx; // 三角形大小（像素，由 10dp 转换而来）
    private final Orientation mOrientation; // 三角形朝向

    // 定义三角形的朝向
    public enum Orientation {
        UP, DOWN, LEFT, RIGHT
    }

    /**
     * 构造方法
     * @param color 三角形颜色（支持透明度）
     * @param sizeDp 三角形大小（单位：dp，此处为三角形的边长）
     * @param orientation 三角形朝向（UP, DOWN, LEFT, RIGHT）
     */
    public TriangleDrawable(int color, float sizeDp, Orientation orientation) {
        // 1. 初始化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(color);

        // 2. 将 dp 转换为像素
        mSizePx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                sizeDp,
                Resources.getSystem().getDisplayMetrics()
        );

        // 3. 保存朝向并初始化路径
        mOrientation = orientation;
        mTrianglePath = new Path();
    }

    /**
     * 根据边界和朝向重新计算三角形路径
     * @param bounds 绘制边界
     */
    private void calculateTrianglePath(Rect bounds) {
        mTrianglePath.reset();

        float centerX = bounds.exactCenterX();
        float centerY = bounds.exactCenterY();
        // 使用边界的最小尺寸来绘制，确保是正三角形
        float size = Math.min(bounds.width(), bounds.height());

        switch (mOrientation) {
            case UP:
                mTrianglePath.moveTo(centerX, bounds.top);
                mTrianglePath.lineTo(bounds.left, bounds.bottom);
                mTrianglePath.lineTo(bounds.right, bounds.bottom);
                break;
            case DOWN:
                mTrianglePath.moveTo(centerX, bounds.bottom);
                mTrianglePath.lineTo(bounds.left, bounds.top);
                mTrianglePath.lineTo(bounds.right, bounds.top);
                break;
            case LEFT:
                mTrianglePath.moveTo(bounds.left, centerY);
                mTrianglePath.lineTo(bounds.right, bounds.top);
                mTrianglePath.lineTo(bounds.right, bounds.bottom);
                break;
            case RIGHT:
                mTrianglePath.moveTo(bounds.right, centerY);
                mTrianglePath.lineTo(bounds.left, bounds.top);
                mTrianglePath.lineTo(bounds.left, bounds.bottom);
                break;
        }
        mTrianglePath.close();
    }

    // 重写：绘制三角形
    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        if (bounds.isEmpty()) {
            return;
        }
        calculateTrianglePath(bounds);
        canvas.drawPath(mTrianglePath, mPaint);
    }

    // 重写：设置透明度
    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
        invalidateSelf();
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
        // 根据朝向返回不同的固有宽高，使其更符合直觉
        if (mOrientation == Orientation.LEFT || mOrientation == Orientation.RIGHT) {
            return (int) (mSizePx * 0.866f); // 等边三角形的高 ≈ 边长 * 0.866
        }
        return (int) mSizePx;
    }

    // 重写：获取固有高度
    @Override
    public int getIntrinsicHeight() {
        if (mOrientation == Orientation.UP || mOrientation == Orientation.DOWN) {
            return (int) (mSizePx * 0.866f);
        }
        return (int) mSizePx;
    }
}