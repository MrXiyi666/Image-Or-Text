package fun.android.imageortext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class Disappear_TextView extends StrokeTextView {


    private Context context;
    private RelativeLayout relati_view;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable = this::remove;
    public void setView(RelativeLayout relati_view){
        this.relati_view = relati_view;
        this.relati_view.post(()->{
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            int x = Fun.随机数(0, this.relati_view.getWidth());
            int y =  Fun.随机数(0, this.relati_view.getHeight());
            params.leftMargin = x;
            params.topMargin = y;
            this.relati_view.addView(this, params);
        });
    }
    private void remove(){
        relati_view.removeView(this);
    }
    public Disappear_TextView(Context context) {
        super(context);
        init(context);
    }

    public Disappear_TextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        this.post(()->{
            handler.postDelayed(runnable, 600);
        });

    }
}
