## ViewPager的wrap_content无效原因

demo地址：TestViewPagerActivity

```xml
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="100dp"
    tools:context=".TestViewPagerActivity">

    <androidx.viewpager.widget.ViewPager
        android:layout_gravity="center"
        android:background="@color/colorPrimaryDark"
        android:id="@+id/vp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>

</FrameLayout>
```



![](image\7131550.jpg)

ViewPage的OnMeasure内部逻辑是先强制设置死自己的测量宽高，再去测量孩子

getDefaultSize方法是计算ViewPager的测量宽高：

![](image\7131554.jpg)

getDefault 在计算size的时候很粗暴，不管模式是AT_MOST还是EXACTLY,直接就使用measureSpec中的大小，

而这个例子中ViewPager的height是wrap_content,所以拿到的size就是父控件的大小，在这里父控件是高是match_parent,所以失效了

![](image\7131625.jpg)





**解决方案**：