package fun.android.imageortext.drawable;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MoonDrawable  extends Drawable {
    private final Paint paint;
    private final Path moonPath = new Path();
    private final int size;

    /**
     * @param color 月亮颜色 (含透明度)
     * @param sizeDp 大小 (dp)
     */
    public MoonDrawable(int color, float sizeDp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        this.size = (int) (sizeDp * density + 0.5f);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);

        calculateMoonPath();
    }

    // 用两个圆的差集绘制月牙
    private void calculateMoonPath() {
        moonPath.reset();
        float centerX = size / 2f;
        float centerY = size / 2f;
        float outerRadius = size / 2f; // 外圆半径
        float innerRadius = outerRadius * 0.7f; // 内圆半径 (用于裁剪)
        float offset = outerRadius * 0.2f; // 内圆偏移量

        // 1. 添加外圆
        moonPath.addCircle(centerX, centerY, outerRadius, Path.Direction.CW);
        // 2. 减去内圆 (形成月牙)
        moonPath.addCircle(centerX + offset, centerY, innerRadius, Path.Direction.CW);
        moonPath.setFillType(Path.FillType.EVEN_ODD); // 差集填充
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        float scaleX = (float) bounds.width() / size;
        float scaleY = (float) bounds.height() / size;
        canvas.save();
        canvas.translate(bounds.left, bounds.top);
        canvas.scale(scaleX, scaleY);
        canvas.drawPath(moonPath, paint);
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