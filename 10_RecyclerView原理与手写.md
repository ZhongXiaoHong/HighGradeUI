> RecyclerView的复用流程

onTouchEvent（ACTION_MOVE）----->scrollByInternal----------scrollStep--------mLayout.scrollVerticallyBy------scrollBy

--------------fill--------------layoutChunk----------    View view = layoutState.next(recycler)///addView


next---->getViewForPosition-----------tryGetViewHolderForPositionByDeadline从缓存中获取获取View,四级缓存

四级缓存查找：分几种情况获取VH

getChangedScrapViewForPosition-----find by position///find by id    与动画相关


getScrapOrHiddenOrCachedHolderForPosition  主要根据位置从mAttachedScrap  mCachedViews中回收
getScrapOrCachedViewForId 主要ViewType Id位置从mAttachedScrap  mCachedViews中回收


getViewForPositionAndType  从自定义缓存中找


getRecycledViewPool().getRecycledView(type); 从缓存池中获取

> RecyclerView的四级缓存





> onCreat、onBind的调用时机

tryGetViewHolderForPositionByDeadline 经过四级缓存查找还是没有命中，则

```
holder = mAdapter.createViewHolder(RecyclerView.this, type);
-----》onCreateViewHolder  自己重写的


tryBindViewHolderByDeadline(holder, offsetPosition, position, deadlineNs);------ mAdapter.bindViewHolder(holder, offsetPosition);-----  onBindViewHolder(holder, position, holder.getUnmodifiedPayloads());


```

> 缓存机制

```
LM#onLayoutChildren-----detachAndScrapAttachedViews----scrapOrRecycleView----【if】recycleViewHolderInternal(处理CacheView、recyclerPool的缓存)///VH不变缓存到CacheView ，cacheView的size大于规定数目，调recycleCachedViewAt将多出的VH移除清数据梵高recyclerPool( addViewHolderToRecycledViewPool)   ///缓存到RecyclerPool  addViewHolderToRecycledViewPool--   getRecycledViewPool().putRecycledView(holder);

【else】   recycler.scrapView(view);---     mAttachedScrap.add(holder);---- mChangedScrap.add(holder);
```





> RecyclerPool的数据结构

有点类似hashMap，数组+List,数据下标表示ViewType,元素是List,长度为5，保存该Viewtype下的VH,  list满的时候不会再保存VH  直接丢掉

保存的时候会清空VH（scrap.resetInternal();）,保存一个VH类型而已。

```java
public void putRecycledView(ViewHolder scrap) {
            final int viewType = scrap.getItemViewType();
            final ArrayList<ViewHolder> scrapHeap = getScrapDataForType(viewType).mScrapHeap;
            if (mScrap.get(viewType).mMaxScrap <= scrapHeap.size()) {
                return;
            }
            if (DEBUG && scrapHeap.contains(scrap)) {
                throw new IllegalArgumentException("this scrap item already exists");
            }
            scrap.resetInternal();
            scrapHeap.add(scrap);
        }
```





> onLayoutChildrent复用流程