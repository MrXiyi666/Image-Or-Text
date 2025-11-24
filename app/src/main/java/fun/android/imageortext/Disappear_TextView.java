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

import fun.android.imageortext.drawable.CakeDrawable;
import fun.android.imageortext.drawable.CircleDrawable;
import fun.android.imageortext.drawable.CrossDrawable;
import fun.android.imageortext.drawable.DiamondDrawable;
import fun.android.imageortext.drawable.EighthNoteDrawable;
import fun.android.imageortext.drawable.FlowerDrawable;
import fun.android.imageortext.drawable.GiftDrawable;
import fun.android.imageortext.drawable.HeartDrawable;
import fun.android.imageortext.drawable.HexagonDrawable;
import fun.android.imageortext.drawable.MoonDrawable;
import fun.android.imageortext.drawable.NoteDrawable;
import fun.android.imageortext.drawable.PentagonDrawable;
import fun.android.imageortext.drawable.RestDrawable;
import fun.android.imageortext.drawable.RingDrawable;
import fun.android.imageortext.drawable.RoundedRectDrawable;
import fun.android.imageortext.drawable.SixteenthNoteDrawable;
import fun.android.imageortext.drawable.SquareDrawable;
import fun.android.imageortext.drawable.StarDrawable;
import fun.android.imageortext.drawable.TriangleDrawable;

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
        return switch (Fun.随机数(0, 22)) {
            case 0 ->
                    new TriangleDrawable(color, 20f, TriangleDrawable.Orientation.UP);
            case 1 ->
                    new TriangleDrawable(color, 20f, TriangleDrawable.Orientation.RIGHT);
            case 2 ->
                    new TriangleDrawable(color, 20f, TriangleDrawable.Orientation.DOWN);
            case 3 ->
                    new TriangleDrawable(color, 20f, TriangleDrawable.Orientation.LEFT);
            case 4 ->
                    new StarDrawable(color, 20f);
            case 5 ->
                    new SquareDrawable(color, 20f);
            case 6 ->
                    new RoundedRectDrawable(color, 20f, 10f);
            case 7 ->
                    new DiamondDrawable(color, 20f);
            case 8 ->
                    new PentagonDrawable(color, 20f);
            case 9 ->
                    new HexagonDrawable(color, 20f);
            case 10 ->
                    new CrossDrawable(color, 20f, 2f);
            case 11 ->
                    new RingDrawable(color, 20f, 2f);
            case 12 ->
                    new FlowerDrawable(color, 20f);
            case 13 ->
                    new CakeDrawable(color, 20f);
            case 14 ->
                    new HeartDrawable(color, 20f);
            case 15 ->
                    new GiftDrawable(color, 20f);
            case 16 ->
                    new MoonDrawable(color, 20f);
            case 17 ->
                    new NoteDrawable(color, 20f);
            case 18 ->
                    new EighthNoteDrawable(color, 20f);
            case 19 ->
                    new SixteenthNoteDrawable(color, 20f);
            case 20 ->
                    new RestDrawable(color, 20f);
            default ->
                    new CircleDrawable(color, 20f);
        };
    }
}