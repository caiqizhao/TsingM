package com.example.guaiwei.tsingm.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by heqingqing on 2019/5/22
 * 信息提示.
 */

public class ToastUtil {
    public static void showToast(Context context , String str){
        Toast.makeText(context, str,Toast.LENGTH_SHORT).show();
    }
}
