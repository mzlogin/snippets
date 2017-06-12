# Android 实用代码片段

**目录**

* [不加载到内存获取图片尺寸](#不加载到内存获取图片尺寸)

## 不加载到内存获取图片尺寸

```java
BitmapFactory.Options opt = new BitmapFactory.Options();
opt.inJustDecodeBounds = true;
BitmapFactory.decodeResource(context.getResources(), R.drawable.demo_img, opt);

int width = opt.outWidth;
int height = opt.outHeight;
```

参考：<http://blog.csdn.net/javy_codercoder/article/details/49684761>

## 模态 loading

```java
public abstract class DialogUtil {
    private static AlertDialog sDialog;

    public static void startLoading(Context context) {
        if (sDialog != null && sDialog.isShowing()) {
            return;
        }

        if (sDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setView(R.layout.window_loading)
                    .setCancelable(false);
            sDialog = builder.create();
            Window window = sDialog.getWindow();
            if (window != null) {
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }
        sDialog.show();
    }

    public static void stopLoading() {
        if (sDialog != null && sDialog.isShowing()) {
            sDialog.dismiss();
            sDialog = null;
        }
    }

    public static boolean isLoading() {
        return sDialog != null && sDialog.isShowing();
    }
}
```

```xml
<?xml version="1.0" encoding="utf-8"?>
<ProgressBar xmlns:android="http://schemas.android.com/apk/res/android"
    style="?android:attr/progressBarStyleLarge"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:indeterminate="true" />
```
