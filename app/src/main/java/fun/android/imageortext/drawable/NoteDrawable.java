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

public class NoteDrawable extends Drawable {
    private final Paint paint;
    private final Path notePath = new Path();
    private final int size;

    /**
     * 创建一个四分音符 Drawable
     * @param color 音符颜色
     * @param sizeDp 音符大小 (单位: dp)
     */
    public NoteDrawable(int color, float sizeDp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        this.size = (int) (sizeDp * density + 0.5f);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(size * 0.07f); // 线条宽度
        paint.setStrokeCap(Paint.Cap.ROUND); // 线条端点圆润

        calculateNotePath();
    }

    /**
     * 计算四分音符的路径
     * 四分音符 = 一个符头 + 一条符干
     */
    private void calculateNotePath() {
        notePath.reset();
        float halfSize = size / 2f;
        float noteHeadRadius = halfSize * 0.35f; // 符头大小

        // 1. 绘制符头 (椭圆形)
        RectF noteHeadOval = new RectF(
                0,
                halfSize - noteHeadRadius,
                noteHeadRadius * 2,
                halfSize + noteHeadRadius
        );
        notePath.addOval(noteHeadOval, Path.Direction.CW);

        // 2. 绘制符干 (直线)
        notePath.moveTo(noteHeadRadius, halfSize); // 从符头中心开始
        notePath.lineTo(noteHeadRadius, 0); // 向上延伸
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        canvas.save();
        // 将音符移动并缩放到视图中心
        canvas.translate(bounds.left, bounds.top);
        canvas.scale((float) bounds.width() / size, (float) bounds.height() / size);
        canvas.drawPath(notePath, paint);
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
