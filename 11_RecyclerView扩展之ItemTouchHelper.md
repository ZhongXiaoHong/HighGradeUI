> ItemTouchHelper是什么

```java
class ItemTouchHelper extends RecyclerView.ItemDecoration
        implements RecyclerView.OnChildAttachStateChangeListener
```

> ItemTouchHelper的基本用法

**ItemTouchHelper.Callback**

使用ItemTouchHelper必须自定义一个ItemTouchHelper.Callback,

重要的方法如下：

| 方法名           | 作用                                                         |
| ---------------- | ------------------------------------------------------------ |
| getMovementFlags | 返回一个标志来定义每一种状态能够滑动的方向，可以调用makeFlag来生产这个标记 |
| onMove           | 当ItemView从老位置被拖到新位置时候回调，返回true,表示拖到了新位置，在此方法里面，我们通常会更新数据源，就比如说，一个`ItemView`从0拖到了1位置，那么对应的数据源也需要更改位置 |
| onSwiped         | 当一个Item View被侧滑的时候回调，在此方法里面，我们也会更新数据源。与`onMove`方法不同到的是，我们在这个方法里面从数据源里面移除相应的数据，然后调用`notifyXXX`方法就行了。 |

**因为onMove onswiped中的逻辑常常要用到Adapter中的数据和调用Adapter的方法，所以通常会让Adapter实现**

**ItemTouchHelper.Callback接口。**

> 实战小例子

- 模仿探探卡片式滑动选择
- 左滑删除ItemView
- 拖动交换位置