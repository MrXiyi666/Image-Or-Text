package fun.android.imageortext;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import com.plattysoft.leonids.ParticleSystem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Disappear_TextView extends StrokeTextView {
    public void setView(RelativeLayout relati_view){

        relati_view.post(()->{
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            int x = Fun.随机数(0, relati_view.getWidth());
            int y =  Fun.随机数(0, relati_view.getHeight());
            params.leftMargin = x;
            params.topMargin = y;
            relati_view.addView(this, params);
        });
        this.post(()-> new Handler(Looper.getMainLooper()).postDelayed(() -> {
            ParticleSystem particleSystem = new ParticleSystem((ViewGroup) relati_view.getParent(), 50, getDrawable(), 800)
                    .setSpeedRange(0.2f, 3f)
                    .setScaleRange(0.2f, 1.5f)
                    .setRotationSpeedRange(0, 360)
                    .setFadeOut(15);
            particleSystem.oneShot(Disappear_TextView.this, 300);
            relati_view.removeView(Disappear_TextView.this);
        }, 600));
    }

    public Disappear_TextView(Context context) {
        super(context);
        init();
    }

    public Disappear_TextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private final List<Drawable> list = new ArrayList<>();
    private void init(){
        this.setElevation(1);

    }
    List<String> color_list = Arrays.asList(
            "#FF5E8F",  // 亮粉（高亮度，不暗沉）
            "#FF7A30",  // 亮橙红（火焰感十足）
            "#FFFF66",  // 荧光黄（烟花核心高亮色）
            "#33FF99",  // 荧光绿（刺眼醒目）
            "#4DCCFF",  // 亮天蓝（夜空下超突出）
            "#FF66FF",  // 亮洋红（梦幻高饱和）
            "#FFAA33",  // 亮橘黄（火花感）
            "#9966FF",  // 亮紫（艳丽不暗沉）
            "#33CCFF",  // 冰蓝（冷色高亮）
            "#FF3366"   // 亮桃红（热烈夺目）
    );

    private Drawable getDrawable(){
        int color = Color.parseColor(color_list.get(Fun.随机数(0, color_list.size()-1)));
        return switch (Fun.随机数(0, 12)) {
            case 0 ->//向上三角形
                    new TriangleDrawable(color, 10f, TriangleDrawable.Orientation.UP);
            case 1 ->//向右三角形
                    new TriangleDrawable(color, 10f, TriangleDrawable.Orientation.RIGHT);
            case 2 ->//向下三角形
                    new TriangleDrawable(color, 10f, TriangleDrawable.Orientation.DOWN);
            case 3 ->//向左三角形
                    new TriangleDrawable(color, 10f, TriangleDrawable.Orientation.LEFT);
            case 4 ->//五角星
                    new StarDrawable(color, 10f);
            case 5 ->

                    new SquareDrawable(color, 10f);
            case 6 ->
                    new RoundedRectDrawable(color, 10f, 10f);
            case 7 ->
                    new DiamondDrawable(color, 10f);
            case 8 ->
                    new PentagonDrawable(color, 10f);
            case 9 ->
                    new HexagonDrawable(color, 10f);
            case 10 ->
                    new CrossDrawable(color, 10f, 2f);
            case 11 ->
                    new RingDrawable(color, 10f, 2f);
            default ->//圆形
                    new CircleDrawable(color, 10f);
        };
    }
}