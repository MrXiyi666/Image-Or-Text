package fun.android.imageortext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

public class StrokeTextView extends AppCompatTextView {

    private TextView borderText; // 描边层文字

    public StrokeTextView(Context context) {
        super(context);
        init(context);
    }

    public StrokeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        borderText = new TextView(context);
        // 初始化描边画笔
        TextPaint paint = borderText.getPaint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Fun.dp2px(context, 2)); // 建议用dp（描边宽度用sp不合理）
        paint.setColor(Color.BLACK); // 描边颜色
        paint.setAntiAlias(true); // 抗锯齿，避免边缘模糊

        // 初始同步基础属性
        borderText.setTypeface(getTypeface());
        borderText.setGravity(getGravity());
    }

    // 重写setText，同步文字内容到borderText
    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if (borderText != null) {
            borderText.setText(text, type); // 同步文字内容
        }
    }

    // 重写setTextSize，同步文字大小到borderText
    @Override
    public void setTextSize(float size) {
        super.setTextSize(size);
        if (borderText != null) {
            borderText.setTextSize(size); // 同步文字大小
        }
    }

    // 重写setTextSize（带单位的重载方法，避免遗漏）
    @Override
    public void setTextSize(int unit, float size) {
        super.setTextSize(unit, size);
        if (borderText != null) {
            borderText.setTextSize(unit, size);
        }
    }

    // 重写onLayout，确保borderText与当前View的布局一致
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // 让borderText与当前View的位置和大小完全一致
        borderText.layout(left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 1. 先绘制描边层（borderText）
        borderText.draw(canvas);
        // 2. 再绘制自身文字（覆盖在描边层上）
        super.onDraw(canvas);
    }

    // 可选：对外提供方法修改描边属性
    public void setStrokeColor(int color) {
        if (borderText != null) {
            borderText.getPaint().setColor(color);
            invalidate();
        }
    }

    public void setStrokeWidth(float widthDp) {
        if (borderText != null) {
            float widthPx = Fun.dp2px(getContext(), widthDp);
            borderText.getPaint().setStrokeWidth(widthPx);
            invalidate();
        }
    }
}