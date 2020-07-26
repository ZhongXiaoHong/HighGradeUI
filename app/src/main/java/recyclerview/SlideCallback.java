package recyclerview;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SlideCallback extends ItemTouchHelper.SimpleCallback {

    RecyclerView rv;
    RecyclerView.Adapter adapter;
    List<Integer> ids;

    public SlideCallback(RecyclerView rv,
                         RecyclerView.Adapter adapter,
                         List<Integer> ids) {
        //dragDirs  表示可以拖拽方向  0  表示不可以拖拽
        //swipeDirs 表示可以滑动方向，这里是上下左右都允许滑动
        super(0, ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.rv = rv;
        this.adapter = adapter;
        this.ids = ids;

    }

    //TODO  拖拽回调
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    //TODO 滑动回调
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        //TODO 实现原理：
        //Item滑动的时候先将该Item删除，计算新的位置之后在添加会数据集中
        Integer remove = ids.remove(viewHolder.getLayoutPosition());
        ids.add(0,remove);

        //---- mObservable.notifyChanged();-----RecyclerViewDataObserver#onChanged
        //----requestLayout----  最后会ondraw
        adapter.notifyDataSetChanged();
        System.out.println("##################"+viewHolder.getAdapterPosition());

    }

    //ondraw的时候回调
    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

       double maxDistance = recyclerView.getWidth()*0.5f;
        double distance = Math.sqrt(dX*dX+dY*dY);
        double fraction = distance/maxDistance;

        if(fraction>1){
            fraction = 1;
        }

        int itemCount = recyclerView.getChildCount();
        for (int i = 0; i < itemCount; i++) {
            View view = recyclerView.getChildAt(i);
            int level = itemCount-i-1;
            if(level>0){
                if(level<Const.MAX_COUNT-1){
                    view.setTranslationY((float)(Const.TRANSY_GAP*level-fraction*Const.TRANSY_GAP));
                    view.setScaleX((float)(1-Const.SCALE_GAP*level+fraction*Const.SCALE_GAP));
                    view.setScaleY((float)(1-Const.SCALE_GAP*level+fraction*Const.SCALE_GAP));
                }
//                else{
//                    view.setTranslationY(Const.TRANSY_GAP*(level-1));
//                    view.setScaleX(1-Const.SCALE_GAP*(level-1));
//                    view.setScaleY(1-Const.SCALE_GAP*(level-1));
//                }

            }
        }


    }

    //TODO 设置回弹时间
    @Override
    public long getAnimationDuration(@NonNull RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
        return  1500;//super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy);
    }
}
