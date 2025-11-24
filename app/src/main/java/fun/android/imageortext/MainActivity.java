package fun.android.imageortext;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Static.app_path = Objects.requireNonNull(this.getExternalFilesDir("")).getPath() + "/";
        Static.activity = this;
        Static.main = findViewById(R.id.main);
        new View_Choose(this);
        Static.上传图片 = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if(uri == null){
                return;
            }
            Static.img_name = Fun.获取Uri文件名(this, uri);
            Fun.copy_Uri_File(this, uri, Static.app_path + Static.img_name);
        });
// 注册一个回调
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // 在这里处理你的返回逻辑
                // ...

                // 如果想触发默认行为（关闭 Activity），调用 finish()
                 finish();

                // 如果想将事件传递给下一个回调（如果有的话），可以禁用当前回调然后调用 dispatchOnBackPressed()
                // setEnabled(false);
                // getOnBackPressedDispatcher().dispatchOnBackPressed();
            }
        });
    }



}