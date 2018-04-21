package com.demo.registactionmodemenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

public class ProcessTextActivity extends Activity {

    private static String TAG = "Demo";

    private static final String EXTRA_KEY_PROCESS_TEXT = "android.intent.extra.PROCESS_TEXT";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        // 长按选取的内容
        String content = (String) intent.getCharSequenceExtra(EXTRA_KEY_PROCESS_TEXT);

        Log.d(TAG, "process intent : " + intent.toString());
        Log.d(TAG, "process text : " + content);

        // Activity.getReferrer()可以获取是从哪个app调用过来
        // 例如：从今日头条：android-app://com.ss.android.article.news
        Log.d(TAG, "process getReferrer : " + getReferrer());

        Toast.makeText(this, "ProcessTextActivity handled.", Toast.LENGTH_SHORT).show();
    }
}