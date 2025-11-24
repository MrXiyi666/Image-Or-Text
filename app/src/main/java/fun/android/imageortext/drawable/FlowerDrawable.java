package fun.android.imageortext.drawable;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FlowerDrawable extends Drawable {
    private final Paint paint = new Paint();
    private final Path petalPath = new Path();

    // 花朵的颜色
    private final int flowerColor;
    // 花朵的大小，单位是像素
    private final int size;

    /**
     * 创建一个花朵形状的 Drawable
     * @param color 花朵的颜色 (包含透明度)
     * @param sizeDp 花朵的大小 (单位: dp)
     */
    public FlowerDrawable(int color, float sizeDp) {
        this.flowerColor = color;

        // 将 dp 单位转换为像素单位
        float density = Resources.getSystem().getDisplayMetrics().density;
        this.size = (int) (sizeDp * density + 0.5f);

        // 初始化画笔
        paint.setAntiAlias(true);
        paint.setColor(flowerColor);
        paint.setStyle(Paint.Style.FILL);

        // 预先计算花瓣的路径
        calculatePetalPath();
    }

    /**
     * 计算单个花瓣的路径
     */
    private void calculatePetalPath() {
        petalPath.reset();

        // 假设花朵的中心在原点 (0,0)
        float centerToTip = size / 2f; // 从中心到花瓣尖端的距离
        float petalWidth = centerToTip / 2f; // 花瓣的宽度

        // 使用贝塞尔曲线定义一个优美的花瓣形状
        petalPath.moveTo(0, -centerToTip); // 花瓣尖端
        petalPath.cubicTo(
                petalWidth, -centerToTip / 1.5f,
                petalWidth / 2f, 0,
                0, 0
        );
        petalPath.cubicTo(
                -petalWidth / 2f, 0,
                -petalWidth, -centerToTip / 1.5f,
                0, -centerToTip
        );
        petalPath.close();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        float centerX = bounds.exactCenterX();
        float centerY = bounds.exactCenterY();

        // 保存并旋转画布，使花朵居中绘制
        canvas.save();
        canvas.translate(centerX, centerY);

        // 绘制花蕊
        paint.setColor(darkenColor(flowerColor, 0.3f));
        canvas.drawCircle(0, 0, size / 8f, paint);
        paint.setColor(flowerColor); // 恢复画笔颜色

        // 绘制多个花瓣
        int numPetals = 6;
        float rotationAngle = 360f / numPetals;
        for (int i = 0; i < numPetals; i++) {
            canvas.drawPath(petalPath, paint);
            canvas.rotate(rotationAngle);
        }

        canvas.restore();
    }

    /**
     * 辅助方法：将颜色调暗
     */
    private int darkenColor(int color, float factor) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= (1.0f - factor); // 降低亮度
        return Color.HSVToColor(hsv);
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
