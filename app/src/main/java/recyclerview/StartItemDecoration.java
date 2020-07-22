package recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StartItemDecoration extends RecyclerView.ItemDecoration {
    private int groupHeadrHeight;
    private Paint mPaint = new Paint();

    public StartItemDecoration(Context context) {
        groupHeadrHeight = dp2px(context, 100);
        mPaint.setColor(Color.GREEN);
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (parent.getAdapter() instanceof StarAdapter) {
            StarAdapter starAdapter = (StarAdapter) parent.getAdapter();
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            for (int i = 0; i < parent.getChildCount(); i++) {
                View view = parent.getChildAt(i);
                int pos = parent.getChildAdapterPosition(view);
                boolean isHead = starAdapter.isGroupHeader(pos);
                int decorationHeight = isHead ? groupHeadrHeight : 1;
                mPaint.setColor(isHead ? Color.RED : Color.GREEN);
                c.drawRect(left, view.getTop()-decorationHeight, right, view.getTop(), mPaint);

                if(isHead){
                    String groupName = starAdapter.getGoupName(pos);
                    c.drawText(groupName,);}
            }

        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getAdapter() instanceof StarAdapter) {
            StarAdapter starAdapter = (StarAdapter) parent.getAdapter();
            // parent.getChildLayoutPosition(view);
            int pos = parent.getChildAdapterPosition(view);
            boolean isHead = starAdapter.isGroupHeader(pos);
            if (isHead) {
                outRect.set(0, groupHeadrHeight, 0, 0);
            } else {
                outRect.set(0, 1, 0, 0);
            }
        }

    }

    private int dp2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale * 0.5f);
    }
}
