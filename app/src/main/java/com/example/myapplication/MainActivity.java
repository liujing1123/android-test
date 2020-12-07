package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myapplication.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
    
    private static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
    
        }
        return hasNavigationBar;
    }

    public static boolean isNavigationBarShowing(Context context) {
        //判断手机底部导航栏是否显示
        boolean haveNavigationBar = checkDeviceHasNavigationBar(context);
        if (haveNavigationBar) {
            if (Build.VERSION.SDK_INT >= 17) {
                String brand = Build.BRAND;
                String mDeviceInfo;
                if (brand.equalsIgnoreCase("HUAWEI")) {
                    mDeviceInfo = "navigationbar_is_min";
                } else if (brand.equalsIgnoreCase("XIAOMI")) {
                    mDeviceInfo = "force_fsg_nav_bar";
                } else if (brand.equalsIgnoreCase("VIVO")) {
                    mDeviceInfo = "navigation_gesture_on";
                } else if (brand.equalsIgnoreCase("OPPO")) {
                    mDeviceInfo = "navigation_gesture_on";
                } else {
                    mDeviceInfo = "navigationbar_is_min";
                }
    
                if (Settings.Global.getInt(context.getContentResolver(), mDeviceInfo, 0) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int getNavHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //判断底部导航栏是否为显示状态
            boolean navigationBarShowing = isNavigationBarShowing(context);
            if (navigationBarShowing) {
                int height = resources.getDimensionPixelSize(resourceId);
                return height;
            }
        }
        return 0;
    }

    @Override
    protected void onResume() {
        Log.v("MainActivity","Log.v输入日志信息");
        Button myButton = (Button) findViewById(R.id.button3);
        myButton.setPadding(10,0,0,0);//给按钮button3设置左边padding为40
        /**
         * 设置为横屏
         */
        if(isTablet(this) && getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("onPause","切后台");
    }

    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);//获取activity_main.xml中id为editText的EditText对象
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void logNavHeight(View view) {
        String bottomNavState ="隐藏";
        if(isNavigationBarShowing(this)){
            bottomNavState="显示";
        }else{
            bottomNavState="隐藏";
        }
        TextView textView1= findViewById(R.id.bottomNavHeight);
        textView1.setText("底部导航栏状态："+bottomNavState+"；"+"高度："+getNavHeight(this));
        
        System.out.println(getNavHeight(this));
        // Do something in response to button
        // System.out.println(isNavigationBarShowing(this));
    }

    
    public void navToLogMessActivity(View view) {
        Intent intent = new Intent(this, LogMessageActivity.class);
        intent.putExtra(EXTRA_MESSAGE, 2222);
        startActivity(intent);
        // Do something in response to button
        // System.out.println(isNavigationBarShowing(this));
    }
}
