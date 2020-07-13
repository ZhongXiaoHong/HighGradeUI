# LayoutInflate原理与插件化换肤

> AS上阅读源码回退步骤快捷键

ctr+art+←



> 插件化换肤好处

不会出现闪烁，方便扩展，侵入性小



> Activity界面的布局层级构成



**ActivityThread**

```java
public Activity handleLaunchActivity(ActivityClientRecord r,
            PendingTransactionActions pendingActions, Intent customIntent) {
    
    // .......
    
    		//TODO        
             final Activity a = performLaunchActivity(r, customIntent); 
    		return a;
}
```



```java
private Activity performLaunchActivity(ActivityClientRecord r, Intent customIntent) {

    //....
    //TODO 
     activity = mInstrumentation.newActivity(
                    cl, component.getClassName(), r.intent);
    
      activity.attach(appContext, this, getInstrumentation(), r.token,
                        r.ident, app, r.intent, r.activityInfo, title, r.parent,
                        r.embeddedID, r.lastNonConfigurationInstances, config,
                        r.referrer, r.voiceInteractor, window, r.configCallback,
                        r.assistToken);
        // ....
    
  return activity;
}
```



**Activity**:

```java
final void attach(Context context, ActivityThread aThread,
            Instrumentation instr, IBinder token, int ident,
            Application application, Intent intent, ActivityInfo info,
            CharSequence title, Activity parent, String id,
            NonConfigurationInstances lastNonConfigurationInstances,
            Configuration config, String referrer, IVoiceInteractor voiceInteractor,
            Window window, ActivityConfigCallback activityConfigCallback, IBinder assistToken) {
            
      mWindow = new PhoneWindow(this, window, activityConfigCallback);
         // ....   
}
```



```java
public void setContentView(@LayoutRes int layoutResID) {
    //TODO 调用PhoneWindow的setcontentView
        getWindow().setContentView(layoutResID);
        initWindowDecorActionBar();
    }
```



**PhoneWindow**

```java
   public void setContentView(int layoutResID) {

        if (mContentParent == null) {
            //TODO 创建DecorView
            installDecor(); 
        } else if (!hasFeature(FEATURE_CONTENT_TRANSITIONS)) {
            mContentParent.removeAllViews();
        }

        if (hasFeature(FEATURE_CONTENT_TRANSITIONS)) {
            final Scene newScene = Scene.getSceneForLayout(mContentParent, layoutResID,
                    getContext());
            transitionTo(newScene);
        } else {
            //TODO layoutResID是开发者自己传入的布局Id
            //TODO 在这个布局再包一层mContentParent
            mLayoutInflater.inflate(layoutResID, mContentParent);
        }
        mContentParent.requestApplyInsets();
        final Callback cb = getCallback();
        if (cb != null && !isDestroyed()) {
            cb.onContentChanged();
        }
        mContentParentExplicitlySet = true;
    }

```



```java
    private void installDecor() {
        mForceDecorInstall = false;

            //TODO 生成DercorView
            mDecor = generateDecor(-1);
            mDecor.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
       
            //TODO 
            mContentParent = generateLayout(mDecor);
//.....

    }
```



```java
protected DecorView generateDecor(int featureId) {
//...
    //生成DercoView   本质是FrameLayout
        return new DecorView(context, featureId, this, getAttributes());
    }
```





**生成contentParent**

screen_simple.xml

```java
  protected ViewGroup generateLayout(DecorView decor) {
      
      
     layoutResource = R.layout.screen_simple;
      //.....
//TODO 
       mDecor.onResourcesLoaded(mLayoutInflater, layoutResource);

      //ID_ANDROID_CONTENT  ==  com.android.internal.R.id.content;
        ViewGroup contentParent = (ViewGroup)findViewById(ID_ANDROID_CONTENT);
      
      return contentParent;

  
  }
```



**Dercoview**

```java

    void onResourcesLoaded(LayoutInflater inflater, int layoutResource) {
 
        mDecorCaptionView = createDecorCaptionView(inflater);
        final View root = inflater.inflate(layoutResource, null);

            //TODO
         addView(root, 0, new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));
       
        mContentRoot = (ViewGroup) root;
        
    }

```



层级关系如下：

![621015](image\761727.png)





> LayoutInflate工作原理



```java
inflate(XmlPullParser parser, @Nullable ViewGroup root, boolean attachToRoot) {

    final AttributeSet attrs = Xml.asAttributeSet(parser);
    
    
    //TODO name 标签名  
      final View temp = createViewFromTag(root, name, inflaterContext, attrs);

      ViewGroup.LayoutParams params = null;

    //TODO 【很重要】 root不空，会将xml的lp属性赋值给当前View
    //TODO root 指的是父容器
    //TODO  比如  LayoutInflater.from(R.layout.item,myLinearLayout,false)
      if (root != null) {
          
           // 生成lp
            params = root.generateLayoutParams(attrs);
          //并且attchTORoot 为false，才会为当前view设置xml中的lp
          //所以使用Layout|Inflater传参要注意不能乱用
          //LayoutInflater.from(R.layout.item,myLinearLayout,false)
          //则R.layout.item能够设置xml中的lp
           if (!attachToRoot) {
      
                temp.setLayoutParams(params);
            }
      }
    
    //temp 是当前节点的View
    //当前节点inflate之后递归inflate孩子
      rInflateChildren(parser, temp, attrs, true);

}
```



```java
View createViewFromTag(View parent, String name, Context context, AttributeSet attrs,
            boolean ignoreThemeAttr) {
    
		View view = tryCreateView(parent, name, context, attrs);
    
 		if (view == null) {
                final Object lastContext = mConstructorArgs[0];
                mConstructorArgs[0] = context;
                try {
                    //TODO 如果标签包含.，则表示是自定义View的标签
                    //TODO  这里是创建android系统提供的View
                    if (-1 == name.indexOf('.')) {
                        view = onCreateView(context, parent, name, attrs);
                    } else {
                        //TODO 自定义View，name是全名
                        view = createView(context, name, null, attrs);
                    }
                } finally {
                    mConstructorArgs[0] = lastContext;
                }
            }

            return view;
            
}
```

创建android系统提供 的View，默认增加"android.view."作为前缀

```java
 protected View onCreateView(String name, AttributeSet attrs)
            throws ClassNotFoundException {
        return createView(name, "android.view.", attrs);
    }
```









资源加载的原理

```
handleBindApplication----            app = data.info.makeApplication(data.restrictedBackupMode, null);
---- ContextImpl.createAppContext(mActivityThread, this);-----      context.setResources(packageInfo.getResources());----  mResources = ResourcesManager.getInstance().getResources------ return getOrCreateResources(activityToken, key, classLoader);------         ResourcesImpl resourcesImpl = createResourcesImpl(key)------AssetManager assets = createAssetManager(key);-----addApkAssets
```

LoadedAPK---持有-Resource----ResourcesImpl---持有---AssetManager（addApkAssets）







/////////////////////////////LLLL



静态换肤：

动态换肤：













