package fun.android.imageortext;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class StarDrawable extends Drawable {
    private final Paint mPaint;
    private final Path mStarPath;
    private final float mSizePx; // 五角星大小（像素，由 10dp 转换而来）

    /**
     * 构造方法
     * @param color 五角星颜色（支持透明度，如 Color.argb(128, 255, 215, 0)）
     * @param sizeDp 五角星大小（单位：dp，此处固定为 10dp）
     */
    public StarDrawable(int color, float sizeDp) {
        // 1. 初始化画笔（抗锯齿、填充样式）
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(color);

        // 2. 将 dp 转换为像素（适配不同分辨率）
        mSizePx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                sizeDp,
                Resources.getSystem().getDisplayMetrics()
        );

        // 3. 初始化五角星路径（核心：计算五角星的 5 个顶点坐标）
        mStarPath = new Path();
        calculateStarPath();
    }

    /**
     * 计算五角星的顶点路径（正五角星，基于正五边形的外接圆）
     */
    private void calculateStarPath() {
        float centerX = mSizePx / 2f; // 五角星中心 X 坐标
        float centerY = mSizePx / 2f; // 五角星中心 Y 坐标
        float radius = mSizePx / 2f;  // 五角星外接圆半径（等于大小的一半）

        mStarPath.reset(); // 清空路径

        // 遍历计算 5 个顶点坐标（角度从 -90° 开始，确保五角星尖角朝上）
        for (int i = 0; i < 5; i++) {
            // 计算当前顶点的角度（正五边形每个顶点间隔 72°）
            float angle = (float) (Math.PI * 2 * i / 5 - Math.PI / 2);
            float x = centerX + radius * (float) Math.cos(angle);
            float y = centerY + radius * (float) Math.sin(angle);

            if (i == 0) {
                mStarPath.moveTo(x, y); // 第一个顶点：移动画笔
            } else {
                mStarPath.lineTo(x, y); // 后续顶点：连接线段
            }
        }

        mStarPath.close(); // 闭合路径（连接最后一个顶点到第一个顶点）
    }

    // 重写：绘制五角星
    @Override
    public void draw(@NonNull Canvas canvas) {
        // 保存画布状态（避免影响其他绘制）
        canvas.save();
        // 缩放画布：确保五角星大小固定为 10dp（适配 View 大小变化）
        canvas.scale(
                getBounds().width() / mSizePx,
                getBounds().height() / mSizePx
        );
        // 绘制五角星路径
        canvas.drawPath(mStarPath, mPaint);
        // 恢复画布状态
        canvas.restore();
    }

    // 重写：设置透明度（直接作用于画笔）
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

    // 重写：获取透明度模式（支持半透明）
    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    // 重写：获取固有宽度（10dp 转换后的像素值）
    @Override
    public int getIntrinsicWidth() {
        return (int) mSizePx;
    }

    // 重写：获取固有高度（与宽度一致，确保五角星是正的）
    @Override
    public int getIntrinsicHeight() {
        return (int) mSizePx;
    }
}
