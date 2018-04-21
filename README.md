
## 注册或修改长按选词菜单：
![image](http://wx2.sinaimg.cn/mw690/6d06c489ly1ffwq1wd3q7j21kw0l7tdl.jpg)

## 实现方案：

```
Android6.0以上（>=23）支持

注册Action事件：android.intent.action.PROCESS_TEXT

获取选词：android.intent.extra.PROCESS_TEXT

Activity.getReferrer()可以获取是从哪个app调用过来：
例如：从今日头条：android-app://com.ss.android.article.news

```

1. Activity中实现如下代码：

```
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
```

2. 自定义Activity，AndroidManifest.xml中声明如下：


```
<activity
    android:name=".ProcessTextActivity"
    android:label="@string/action_mode_menu"
    android:screenOrientation="portrait"
    android:theme="@style/MyTransparentActivityStyle">
        <intent-filter>
            <action android:name="android.intent.action.PROCESS_TEXT" />
            <category android:name="android.intent.category.DEFAULT" />
            <data android:mimeType="text/plain" />
        </intent-filter>
</activity>
```

3. ProcessTestActivity类里处理相应menu点击事件：


```
// 获取选词
CharSequence txt = getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT);

// Activity.getReferrer()可以获取是从哪个app调用过来
// 例如：从今日头条：android-app://com.ss.android.article.news
Log.d(TAG, "process getReferrer : " + getReferrer());
```
