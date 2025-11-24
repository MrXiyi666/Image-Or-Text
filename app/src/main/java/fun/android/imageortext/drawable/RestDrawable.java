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

public class RestDrawable extends Drawable {

    private final Paint paint;
    private final Path restPath = new Path();
    private final int size;

    public RestDrawable(int color, float sizeDp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        this.size = (int) (sizeDp * density + 0.5f);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(size * 0.09f);
        paint.setStrokeCap(Paint.Cap.ROUND);

        calculateRestPath();
    }

    /**
     * 计算四分休止符的路径
     * 它由几个曲线和直线组成，形状像一个倾斜的"z"。
     */
    private void calculateRestPath() {
        restPath.reset();
        float halfSize = size / 2f;
        float elementSize = halfSize * 0.4f; // 基本单元大小

        // 从左下角开始
        float startX = 0;
        float startY = size * 0.7f;

        // 使用相对坐标绘制，更容易控制
        restPath.moveTo(startX, startY);
        restPath.rLineTo(elementSize * 1.5f, -elementSize * 0.8f); // 右上
        restPath.rLineTo(-elementSize * 1.2f, 0);                  // 左
        restPath.rCubicTo(                                         // 下凸曲线
                -elementSize * 0.3f, elementSize * 0.2f,
                -elementSize * 0.1f, elementSize * 0.8f,
                elementSize * 1.1f, elementSize * 0.8f
        );
        restPath.rLineTo(-elementSize * 1.5f, -elementSize * 0.3f); // 左上
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        canvas.save();
        canvas.translate(bounds.left, bounds.top);
        canvas.scale((float) bounds.width() / size, (float) bounds.height() / size);
        canvas.drawPath(restPath, paint);
        canvas.restore();
    }

    // ... (其余方法与 NoteDrawable 相同)
    @Override public void setAlpha(int alpha) { paint.setAlpha(alpha); }
    @Override public void setColorFilter(@Nullable ColorFilter colorFilter) { paint.setColorFilter(colorFilter); }
    @Override public int getOpacity() { return PixelFormat.TRANSLUCENT; }
    @Override public int getIntrinsicWidth() { return size; }
    @Override public int getIntrinsicHeight() { return size; }
}
