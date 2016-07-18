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
