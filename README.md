# HighGradeUI


> 自定义View的主要工作

自定义View主要是实现OnMeasure+OnDraw，因此阅读View类源码这两个方法是阅读重点

自定义ViewGroup主要是实现OnMeasure+OnLayout，因此阅读ViewGroup类源码这两个方法是阅读重点



> View构造方法参数的含义

```java
 //TODO  java代码中使用
    public JavaFlowLayout(Context context) {
        super(context);
    }

    //TODO XML LayoutInflate 反射
    public JavaFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //TODO 有主题style相关的属性
    public JavaFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //TODO 第四个参数是第三个参数的备胎，如果第三个设置为0，或者属性找不到的时候会去第四个中找
    //TODO 第四个如果设置为0，则不起作用
    public JavaFlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

```



> 自定义ViewGroup调用OnMeasured的时候是先做Child的Measure还是自己的Measure

都可以，看自身ViewGroup的算法需要。比如ViewPager就是先Measure自身再Measure孩子，除了ViewPager其他的ViewGroup基本都是先孩子后自己。



> 自定义ViewGroup的OnMeasure一般的重写流程

**measure孩子：**

生成孩子的Spec

调用孩子的measure方法，从而导致onMeasure被回调从而计算孩子的测量宽高



**measure自身：**

往往需要根据ViewGroup自身布局特点（算法）+孩子的大小，计算自身测量宽高

调用setMeasuredDimension保存自身的测量宽高大小 



> 自身的测量宽高如何计算

1. 使用getDefaultSize,会使得孩子的wrap_content失效，如ViewPager

   ```java
   //TODO ViewPager的 onMeasure
   @Override
       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
   
           setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                   getDefaultSize(0, heightMeasureSpec));
                   
      }
   ```

   ```java
      public static int getDefaultSize(int size, int measureSpec) {
           int result = size;
           int specMode = MeasureSpec.getMode(measureSpec);
           int specSize = MeasureSpec.getSize(measureSpec);
   
           switch (specMode) {
           case MeasureSpec.UNSPECIFIED:
               result = size;
               break;
           case MeasureSpec.AT_MOST:
           case MeasureSpec.EXACTLY:
           //TODO 可以看到不管ViewPager自身是wrap_content、match_parent、
           //TODO 还是具体某个数值，通通都是设置成SpecSize
           //TODO SpecSize是什么就是ViewPager的父类给的
                  //TODO 会使得孩子的wrap_content失效
               result = specSize;
               break;
           }
           return result;
       }
   ```

   

2. 









> MeasureSpec测量规模

MeasureSpec是View的内部类，它是一个类，不是一个int值，它可以被当作是一个工具类，里面有一套算法封包、拆包



> getMeasureWith、getMeasureHeight与getWith、getHeight的区别

getMeasureWith、getMeasureHeight是测量后的数值，也就是onMeasure之后的值

getWith、getHeight是Layout之后的值，也就是最终的值。

所以在onLayout方法中如果要获取宽高应该使用getMeasureWith、getMeasureHeight

在onDraw方法中如果要使用宽高应该使用getWith、getHeight



> 为什么onMeasure会调用多次

一个View的onMeasure是可能会调用多次的，这个不是View本身决定的，而是父辈View决定的，比如FrameLayout ：

```java
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //.....
	//TODO 调用ViewGroup的，里面会调用child.measure
     measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
    //.....
    //TODO 本身又会调用measure
    child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
}
```

**注意点**：

由于onMeasure可能被多次调用，所以在自定义ViewGroup的时候，在onMeasure经常会使用集合来保存子View，在onMeasure方法开头应该调用集合的clear方法，不然子View就会被重复的添加到集合中，对于存放的孩子的容器最好的定义应该是private final的，如：**private final  List<View> views  = new ArrayList<>()**



> 自定义View的逻辑漏洞检查清单

- 孩子的View  GONE 不测量不保存

- 孩子的padding margin 适配，宽高都要把padding、margin加进去

- SuggestedMinimumXXX适配

- Drawable MinimumXXX适配

  



> 自定义View常用的一些逻辑代码

- 取两数中的最值，不要使用if else用Math.max Math.min代替



> 宽高如何取

1.计算孩子的宽高，保存值，一般的逻辑是叠加或者取最大值，比如FrameLayout,宽高都是取最大值，比如竖直方向的LinearLayout,宽取孩子中的最大值，高取孩子们的高度的叠加，记得适配孩子的margin,加上孩子的margin去算孩子的宽高

2.适配viewgroup自身的padding,【1】中值加上Viewgoup自身的padding



> 自定义ViewGroup的流程



> View的绘制流程





> 面试题1：LayoutParams是什么与MeasureSpec有什么关系

LayoutParams是被用来告诉父View怎么去layout子View的，LayoutParams描述了一个View的宽高有多大，MatchParent、FillParent值为-1，wrapcontent值为-2.另外有时候可能需要自定义LayoutParams,比如ViewPager,里面自定义了LayoutParams，增加了childIndex，position等属性。





> 面试题2：MeasureSpec是什么



一个View的onMeasure(int widthMeasureSpec, int heightMeasureSpec)拿到的只是父亲帮忙计算好的MeasureSpec，这个MeasureSpec包含模式+大小，这个大小不是测量值只是参考值，测量值还是要子View在onMeasure根据具体需求去计算，然后最终去setMeasureDimen保存。



也就是一个View的OnMeasure主要的任务就是计算自身的测量宽高，如果这个View是ViewGroup的话还会帮忙计算孩子们的MeasureSpec,并调用孩子们的measure让他们计算自身的测量宽高



> 面试题3：为什么要measure



拓展：20：54

1--50--29

https://www.jianshu.com/p/0723ff4123e1





