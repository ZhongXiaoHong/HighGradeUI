package recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.silang.highgradeui.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestItemTouchHelperActivity extends AppCompatActivity {

    RecyclerView rv;
    List<String> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_item_touch_helper);
        for (int i = 0; i < 100; i++) {
            datas.add("第" + i + "个Item");
        }
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.test_item_touch_helper, parent, false);
                return new RecyclerView.ViewHolder(view) {
                };
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                TextView tv = (TextView) holder.itemView.findViewById(R.id.tv);
                tv.setText(datas.get(position));
                if (position % 2 == 0) {
                    holder.itemView.setBackgroundColor(Color.WHITE);
                } else {
                    holder.itemView.setBackgroundColor(Color.RED);
                }

            }

            @Override
            public int getItemCount() {
                return datas.size();
            }
        });
        ItemTouchHelper helper = new ItemTouchHelper(new MyCallback(rv));
        helper.attachToRecyclerView(rv);
    }

    class MyCallback extends ItemTouchHelper.SimpleCallback {
        RecyclerView recyclerView;

        public MyCallback(RecyclerView rv) {
            super(ItemTouchHelper.UP|ItemTouchHelper.DOWN, ItemTouchHelper.LEFT);
            this.recyclerView = rv;
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                              @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            Collections.swap(datas, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int pos = viewHolder.getAdapterPosition();
            datas.remove(pos);
            recyclerView.getAdapter().notifyDataSetChanged();


        }
    }
}