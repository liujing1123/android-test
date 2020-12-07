package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class LogMessageActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_message);//为 Activity 加载布局资源，对应res/layout/activity_display_message.xml文件
        // Get the Intent that started this activity and extract the string
//         Intent intent = getIntent();
// //        Log.e("Line-MainActivity", "onCreate");
//         String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

//         // Capture the layout's TextView and set the string as its text
//         TextView textView = findViewById(R.id.textView1);
//         textView.setText("LogMessageActivity");
    }
}
