package fun.android.imageortext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.widget.RelativeLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;

public class Static {
    public static String app_path;
    public static String img_name;
    @SuppressLint("StaticFieldLeak")
    public static RelativeLayout main;
    @SuppressLint("StaticFieldLeak")
    public static Activity activity;
    public static ActivityResultLauncher<PickVisualMediaRequest> 上传图片;
    public static String[] text ={
            "在一起","亲一个","哇","哇","哇","哇","哇","哇哦","般配","生小孩","抱一抱","亲嘴","牵手","过日子", "结婚啦","小两口"
    };
}
