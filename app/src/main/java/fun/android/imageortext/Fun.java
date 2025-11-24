package fun.android.imageortext;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    public static String 获取Uri文件名(Activity context, Uri fileUri) {
        return FileUri.getFileName(context, fileUri);
    }

    /**
     * 复制文件（优化版）
     *
     * @param context     上下文
     * @param fromUri     源文件Uri
     * @param toFilePath  目标文件路径
     */
    public static void copy_Uri_File(@NonNull Context context, @NonNull Uri fromUri, @NonNull String toFilePath) {
        final int BUFFER_SIZE = 8 * 1024; // 8KB缓冲区
        ContentResolver contentResolver = context.getContentResolver();

        File targetFile = new File(toFilePath);
        // 确保目标目录存在
        File parentDir = targetFile.getParentFile();
        if (parentDir != null && !parentDir.exists() && !parentDir.mkdirs()) {
            return;
        }

        try (InputStream input = contentResolver.openInputStream(fromUri);
             OutputStream output = new FileOutputStream(targetFile)) {
            if (input == null) {
                throw new FileNotFoundException("未找到源文件: " + fromUri);
            }

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            output.flush();
        } catch (IOException e) {
            // 删除不完整的文件
            if (targetFile.exists() && !targetFile.delete()) {
                Log.w("文件复制", "无法删除未完成的文件: " + targetFile);
            }
        }
    }

}