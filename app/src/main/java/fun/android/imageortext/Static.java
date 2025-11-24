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
    public static Activity activity;
    public static ActivityResultLauncher<PickVisualMediaRequest> 上传图片;
    public static String[] text ={
            "在一起","亲一个","哇","哇","哇","哇","哇","好漂亮哦","好幸福哦","哇哦","天生一对","般配","生小孩","抱一抱",
            "永远幸福","百年好合","早生贵子","恭喜发财","长命百岁","亲嘴","牵手","过日子","祝福新人",
            "红红火火","结婚啦","福到","好运连连","喜气洋洋","小两口"
    };
}
