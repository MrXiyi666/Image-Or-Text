package fun.android.imageortext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.File;

public class View_Bullet_Comments extends View_Main{
    private final RelativeLayout relati_view;
    private ImageView lihui_img;
    private final Handler handler = new Handler(Looper.getMainLooper());
    public View_Bullet_Comments(Context context) {
        super(context);
        view = LayoutInflater.from(context).inflate(R.layout.view_bullet_comments, null, false);
        relati_view = view.findViewById(R.id.relati_view);
        lihui_img = view.findViewById(R.id.lihui_img);
        Fun.addView(view);
        view.post(()-> handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Disappear_TextView disappear_textView = new Disappear_TextView(context);
                disappear_textView.setText(Static.text[Fun.随机数(0, Static.text.length-1)]);
                disappear_textView.setTextColor(Color.WHITE);
                disappear_textView.setView(relati_view);

                handler.postDelayed(this, 10);
            }
        }, 10));
        if (new File(Static.app_path + Static.img_name).exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(Static.app_path + Static.img_name);
            lihui_img.setImageBitmap(bitmap);
        }
    }
}
