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

public class GiftDrawable extends Drawable {
    private final Paint boxPaint;
    private final Paint ribbonPaint;
    private final int size;

    /**
     * @param boxColor 盒子颜色 (含透明度)
     * @param sizeDp 大小 (dp)
     */
    public GiftDrawable(int boxColor, float sizeDp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        this.size = (int) (sizeDp * density + 0.5f);

        // 盒子画笔
        boxPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        boxPaint.setColor(boxColor);
        boxPaint.setStyle(Paint.Style.FILL);

        // 丝带画笔 (自动生成对比色)
        ribbonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ribbonPaint.setColor(getComplementaryColor(boxColor));
        ribbonPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        float centerX = bounds.exactCenterX();
        float centerY = bounds.exactCenterY();
        float boxSize = Math.min(bounds.width(), bounds.height()) * 0.7f; // 盒子大小
        float ribbonWidth = boxSize * 0.15f; // 丝带宽度

        // 1. 绘制盒子 (圆角矩形)
        RectF boxRect = new RectF(
                centerX - boxSize / 2f,
                centerY - boxSize / 2f,
                centerX + boxSize / 2f,
                centerY + boxSize / 2f
        );
        canvas.drawRoundRect(boxRect, boxSize * 0.1f, boxSize * 0.1f, boxPaint);

        // 2. 绘制横向丝带
        RectF horizontalRibbon = new RectF(
                centerX - boxSize / 2f,
                centerY - ribbonWidth / 2f,
                centerX + boxSize / 2f,
                centerY + ribbonWidth / 2f
        );
        canvas.drawRoundRect(horizontalRibbon, ribbonWidth / 2f, ribbonWidth / 2f, ribbonPaint);

        // 3. 绘制纵向丝带
        RectF verticalRibbon = new RectF(
                centerX - ribbonWidth / 2f,
                centerY - boxSize / 2f,
                centerX + ribbonWidth / 2f,
                centerY + boxSize / 2f
        );
        canvas.drawRoundRect(verticalRibbon, ribbonWidth / 2f, ribbonWidth / 2f, ribbonPaint);

        // 4. 绘制丝带蝴蝶结 (四个小椭圆)
        float bowSize = ribbonWidth * 1.5f; // 蝴蝶结大小
        // 左上蝴蝶结
        canvas.drawOval(centerX - bowSize, centerY - boxSize / 2f - bowSize,
                centerX, centerY - boxSize / 2f, ribbonPaint);
        // 右上蝴蝶结
        canvas.drawOval(centerX, centerY - boxSize / 2f - bowSize,
                centerX + bowSize, centerY - boxSize / 2f, ribbonPaint);
        // 左下蝴蝶结
        canvas.drawOval(centerX - bowSize, centerY + boxSize / 2f,
                centerX, centerY + boxSize / 2f + bowSize, ribbonPaint);
        // 右下蝴蝶结
        canvas.drawOval(centerX, centerY + boxSize / 2f,
                centerX + bowSize, centerY + boxSize / 2f + bowSize, ribbonPaint);
    }

    // 获取互补色
    private int getComplementaryColor(int color) {
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return Color.rgb(255 - r, 255 - g, 255 - b);
    }

    @Override
    public void setAlpha(int alpha) {
        boxPaint.setAlpha(alpha);
        ribbonPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        boxPaint.setColorFilter(colorFilter);
        ribbonPaint.setColorFilter(colorFilter);
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
