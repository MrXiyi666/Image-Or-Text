package fun.android.imageortext;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;



public class View_Bullet_Comments extends View_Main{
    private RelativeLayout relati_view;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Disappear_TextView disappear_textView = new Disappear_TextView(context);
            disappear_textView.setText(Static.text[Fun.随机数(0, Static.text.length-1)]);
            disappear_textView.setTextColor(Color.WHITE);
            disappear_textView.setTextSize(Fun.sp2px(context, 16));
            disappear_textView.setView(relati_view);

            handler.postDelayed(runnable, 10);

        }
    };
    public View_Bullet_Comments(Context context) {
        super(context);
        view = LayoutInflater.from(context).inflate(R.layout.view_bullet_comments, null, false);
        relati_view = view.findViewById(R.id.relati_view);
        Fun.addView(view);
        view.post(()->{
            handler.postDelayed(runnable, 10);
        });
    }
}
