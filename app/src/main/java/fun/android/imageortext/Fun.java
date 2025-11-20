package fun.android.imageortext;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.Random;

public class Fun {
    // 创建 Random 实例（建议全局复用，避免频繁创建）
    static Random random = new Random();
    public static void addView(View view){
        Static.main.removeAllViews();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        Static.main.addView(view, params);
    }

    // 生成 [min, max] 之间的随机整数（含min和max）
    public static int 随机数(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("min不能大于max");
        }
        Random random = new Random();
        return min + random.nextInt(max - min + 1); // 关键：确保范围正确
    }

    /**
     * dp 转 px
     * @param context 上下文（用于获取屏幕密度）
     * @param dpValue dp值
     * @return 转换后的 px 值
     */
    public static int dp2px(Context context, float dpValue) {
        // 获取屏幕密度（缩放因子）
        float scale = context.getResources().getDisplayMetrics().density;
        // 四舍五入取整
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px 转 dp
     * @param context 上下文
     * @param pxValue px值
     * @return 转换后的 dp 值
     */
    public static int px2dp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp 转 px（sp用于文字，与dp类似但受用户字体大小设置影响）
     * @param context 上下文
     * @param spValue sp值
     * @return 转换后的 px 值
     */
    public static int sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                spValue,
                context.getResources().getDisplayMetrics()
        );
    }
}