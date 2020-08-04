## ViewPager



> ViewPager设置wrap_content/100dp无效







> ViewPager的缓存机制







> 缓存页面与预加载

![729635](/image/729635.png)

如果设置的是一帧，从T1跳转到T3的时候，首先会缓存T2与T4,预加载T4

**缓存什么预加载什么**，T2不会预加载吗

**setOffscreenPageLimit**

设置缓存的页面，默认是1，则会左右缓存一页，设置成0是无效的，因为内部会将0强制改成1

预加载的方向与跳转的方向有关

预加载就是Fragment切换的时候会预先加载不可见的Fragment

**预加载带来的问题**

1.预加载越多会越卡

2.一个Fragment占用1M，5个就5M，累计到后面就可能OOM

3.如果预加载的Fragment在请求网络，不仅浪费流量，还会卡顿



**懒加载**

解决预加载带来的问题。可见才加载，不可见不加载

其实也就是延迟加载,就是等到该页面的UI展示给用 

户时,再加载该页面的数据(从网络、数据库等),而不是依靠 

ViewPager预加载机制提前加载两三个，甚至更多页面的数 

据

目的： 这样可以提高所属Activity的初始化速度,也可以为 

用户节省流量.而这种懒加载的方式也已经/正在被诸多APP 

所采用。





> ViewPager源码

![729635](/image/729707.png)



**FragmentPagerAdapter**



```java
public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Fragment fragment = (Fragment)object;
        if (fragment != mCurrentPrimaryItem) {
            if (mCurrentPrimaryItem != null) {
             
                mCurrentPrimaryItem.setMenuVisibility(false);
                if (mBehavior == BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
                    if (mCurTransaction == null) {
                        mCurTransaction = mFragmentManager.beginTransaction();
                    }
                    mCurTransaction.setMaxLifecycle(mCurrentPrimaryItem, Lifecycle.State.STARTED);
                } else {
                    //TODO 当前的mcurrentFragment设置hint为false
                    mCurrentPrimaryItem.setUserVisibleHint(false);
                }
            }
            fragment.setMenuVisibility(true);
            if (mBehavior == BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
                if (mCurTransaction == null) {
                    mCurTransaction = mFragmentManager.beginTransaction();
                }
                mCurTransaction.setMaxLifecycle(fragment, Lifecycle.State.RESUMED);
            } else {
                //TODO 马上要显示的mcurrentFragment设置hint为true
                fragment.setUserVisibleHint(true);
            }

            mCurrentPrimaryItem = fragment;
        }
```

所以setprimaryItem中会调用方法setUserVisiableHint来设置当前Fragment不可见，马上要加载的fragment为可见，

```java
  @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        if (mCurTransaction != null) {
            //TODO  这里才是真正的开始提交Fragment,fragment开始生命周期调用
            mCurTransaction.commitNowAllowingStateLoss();
            mCurTransaction = null;
        }
    }
```

因此ViewPager中的fragment的setUservisiableHint的调用是早于生命周期的调用的

**总结：setuserVivisiableHit函数不是Fragment的生命周期函数，但是比Fragment生命周期函数先执行**

















