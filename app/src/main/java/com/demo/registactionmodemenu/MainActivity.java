package com.demo.registactionmodemenu;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends FragmentActivity {

    private static String TAG = "Demo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("ActionModeMenu");

        WebView webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("https://baike.baidu.com/item/android/60243");
    }

    protected ActionMode mActionMode = null;

    @Override
    public void onActionModeStarted(ActionMode mode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && mActionMode == null) {
            Menu menu = mode.getMenu();
            if (menu.size() > 0) {
                for (int i = 0; i < menu.size(); i++) {
                    android.view.MenuItem menuItem = menu.getItem(i);
                    String title = (String) menuItem.getTitle();

                    Log.d(TAG, "action menu item title : " + title);
                    Log.d(TAG, "action menu item intent : " + menuItem.getIntent());

                    // 修改app内部显示的ActionMode菜单名
                    if (menuItem.getIntent() != null
                            && menuItem.getIntent().getComponent() != null
                            && menuItem.getIntent().getComponent().getPackageName().equals("com.demo.registactionmodemenu")) {

                        String pkgName = menuItem.getIntent().getComponent().getPackageName();
                        String className = menuItem.getIntent().getComponent().getClassName();

                        Log.d(TAG, "action menu pkg : " + pkgName);
                        Log.d(TAG, "action menu className : " + className);

                        if (className.equals("com.demo.registactionmodemenu.ProcessTextActivity")) {
                            menuItem.setTitle("自定义Menu");
                        }
                    } else {
                        /**
                         * 有选择的保留或屏蔽菜单项
                         */
                        if (title.equals("复制")
                                || title.equalsIgnoreCase("copy")
                                || title.equalsIgnoreCase("全选")
                                || title.equalsIgnoreCase("select all")
                                || title.equalsIgnoreCase("粘贴")
                                || title.equalsIgnoreCase("paste")
                                || title.equalsIgnoreCase("粘贴并转到")
                                || title.equalsIgnoreCase("paste and go to")) {

                        } else {
                            menuItem.setEnabled(false);
                        }
                    }
                }
            }
        }
        mActionMode = mode;
        super.onActionModeStarted(mode);
    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        super.onActionModeFinished(mode);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mActionMode = null;
        }
    }
}
