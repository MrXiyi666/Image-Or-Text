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

public class SixteenthNoteDrawable extends Drawable {

    private final Paint paint;
    private final Path notePath = new Path();
    private final int size;

    public SixteenthNoteDrawable(int color, float sizeDp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        this.size = (int) (sizeDp * density + 0.5f);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(size * 0.07f);
        paint.setStrokeCap(Paint.Cap.ROUND);

        calculateNotePath();
    }

    /**
     * 计算十六分音符的路径
     * 十六分音符 = 一个符头 + 一条符干 + 两条符尾
     */
    private void calculateNotePath() {
        notePath.reset();
        float halfSize = size / 2f;
        float noteHeadRadius = halfSize * 0.35f;
        float stemHeight = halfSize * 1.8f;
        float flagWidth = halfSize * 0.6f;
        float flagSpacing = noteHeadRadius * 0.6f;

        // 1. 符头
        RectF noteHeadOval = new RectF(
                0,
                halfSize - noteHeadRadius,
                noteHeadRadius * 2,
                halfSize + noteHeadRadius
        );
        notePath.addOval(noteHeadOval, Path.Direction.CW);

        // 2. 符干
        notePath.moveTo(noteHeadRadius, halfSize);
        notePath.lineTo(noteHeadRadius, halfSize - stemHeight);

        // 3. 符尾 (两条平行的斜线)
        notePath.moveTo(noteHeadRadius, halfSize - stemHeight);
        notePath.lineTo(noteHeadRadius + flagWidth, halfSize - stemHeight + flagWidth * 0.3f);

        notePath.moveTo(noteHeadRadius, halfSize - stemHeight + flagSpacing);
        notePath.lineTo(noteHeadRadius + flagWidth, halfSize - stemHeight + flagWidth * 0.3f + flagSpacing);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        canvas.save();
        canvas.translate(bounds.left, bounds.top);
        canvas.scale((float) bounds.width() / size, (float) bounds.height() / size);
        canvas.drawPath(notePath, paint);
        canvas.restore();
    }

    // ... (其余方法与 NoteDrawable 相同)
    @Override public void setAlpha(int alpha) { paint.setAlpha(alpha); }
    @Override public void setColorFilter(@Nullable ColorFilter colorFilter) { paint.setColorFilter(colorFilter); }
    @Override public int getOpacity() { return PixelFormat.TRANSLUCENT; }
    @Override public int getIntrinsicWidth() { return size; }
    @Override public int getIntrinsicHeight() { return size; }
}
