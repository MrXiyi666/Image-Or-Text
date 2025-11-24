package fun.android.imageortext.drawable;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CakeDrawable extends Drawable {
    private final Paint cakePaint;
    private final Paint creamPaint;
    private final Paint decorationPaint;

    private final int size; // 蛋糕的整体大小 (像素)

    /**
     * 创建一个蛋糕形状的 Drawable
     * @param baseColor 蛋糕主体的颜色 (会自动生成奶油和装饰的颜色)
     * @param sizeDp    蛋糕的大小 (单位: dp)
     */
    public CakeDrawable(int baseColor, float sizeDp) {
        // 将 dp 转换为像素
        float density = Resources.getSystem().getDisplayMetrics().density;
        this.size = (int) (sizeDp * density + 0.5f);

        // --- 初始化画笔 ---

        // 蛋糕胚画笔
        cakePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        cakePaint.setColor(baseColor);
        cakePaint.setStyle(Paint.Style.FILL);

        // 奶油画笔 (比主体颜色亮一些)
        creamPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        creamPaint.setColor(lightenColor(baseColor, 0.3f));
        creamPaint.setStyle(Paint.Style.FILL);

        // 装饰画笔 (用主体颜色的互补色或者对比色)
        decorationPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        decorationPaint.setColor(getComplementaryColor(baseColor));
        decorationPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        float centerX = bounds.exactCenterX();
        float cakeWidth = size * 0.8f; // 蛋糕宽度比整体大小稍小
        float cakeHeight = size * 0.6f; // 蛋糕高度

        // 计算蛋糕底部的位置
        float bottomY = bounds.bottom - (bounds.height() - cakeHeight) / 2f;

        // --- 绘制蛋糕层 ---

        // 1. 绘制底层蛋糕胚
        float layerHeight = cakeHeight / 3f;
        RectF cakeLayer = new RectF(
                centerX - cakeWidth / 2f,
                bottomY - layerHeight,
                centerX + cakeWidth / 2f,
                bottomY
        );
        canvas.drawRoundRect(cakeLayer, 10, 10, cakePaint);

        // 2. 绘制中间奶油层 (稍微窄一点)
        float creamWidth = cakeWidth * 0.95f;
        RectF creamLayer = new RectF(
                centerX - creamWidth / 2f,
                bottomY - layerHeight * 2f,
                centerX + creamWidth / 2f,
                bottomY - layerHeight
        );
        canvas.drawRoundRect(creamLayer, 10, 10, creamPaint);

        // 3. 绘制顶部蛋糕胚
        RectF topLayer = new RectF(
                centerX - cakeWidth / 2f,
                bottomY - layerHeight * 3f,
                centerX + cakeWidth / 2f,
                bottomY - layerHeight * 2f
        );
        canvas.drawRoundRect(topLayer, 10, 10, cakePaint);

        // --- 绘制装饰 ---

        // 在顶部绘制一些小圆点作为装饰
        float decorationRadius = size * 0.05f;
        float startX = centerX - cakeWidth / 2f + decorationRadius * 2f;
        float decorationY = bottomY - layerHeight * 3f + decorationRadius;

        for (int i = 0; i < 5; i++) {
            canvas.drawCircle(startX + i * (decorationRadius * 3f), decorationY, decorationRadius, decorationPaint);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        cakePaint.setAlpha(alpha);
        creamPaint.setAlpha(alpha);
        decorationPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        cakePaint.setColorFilter(colorFilter);
        creamPaint.setColorFilter(colorFilter);
        decorationPaint.setColorFilter(colorFilter);
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

    /**
     * 辅助方法：将颜色调亮
     */
    private int lightenColor(int color, float factor) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] = Math.min(1.0f, hsv[2] + (1.0f - hsv[2]) * factor);
        return Color.HSVToColor(hsv);
    }

    /**
     * 辅助方法：获取颜色的互补色
     */
    private int getComplementaryColor(int color) {
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return Color.rgb(255 - r, 255 - g, 255 - b);
    }
}
