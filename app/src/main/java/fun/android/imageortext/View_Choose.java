package fun.android.imageortext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.AppCompatButton;

public class View_Choose extends View_Main{
    @SuppressLint("InflateParams")
    public View_Choose(Context context) {
        super(context);
        view = LayoutInflater.from(context).inflate(R.layout.view_choose, null, false);
        AppCompatButton button_choose_img = view.findViewById(R.id.button_choose_img);
        AppCompatButton button_start = view.findViewById(R.id.button_start);

        button_choose_img.setOnClickListener(V->{
            Static.上传图片.launch( new PickVisualMediaRequest.Builder().setMediaType(ActivityResultContracts.PickVisualMedia.ImageAndVideo.INSTANCE).build());
        });

        button_start.setOnClickListener(V->{
            if(Static.img_name == null){
                return;
            }
            if(Static.img_name.isEmpty()){
               return;
            }
            new View_Bullet_Comments(context);
        });
        Fun.addView(view);
    }

}