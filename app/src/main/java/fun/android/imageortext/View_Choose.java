package fun.android.imageortext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class View_Choose extends View_Main{
    public View_Choose(Context context) {
        super(context);
        view = LayoutInflater.from(context).inflate(R.layout.view_choose, null, false);
        Fun.addView(view);
    }
}
