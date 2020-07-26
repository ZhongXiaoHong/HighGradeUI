package recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.silang.highgradeui.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

//TODO 展示探探附近人的ui
public class TanTanActivity extends AppCompatActivity {

    RecyclerView rv;
    List<Integer> ids = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tan_tan);
        ids.clear();
        for (int i = 0; i < 200; i++) {
            mockData();
        }

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new TanTanLayoutManager());
        //  rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(TanTanActivity.this).inflate(R.layout.tantan_item, parent, false);
                return new RecyclerView.ViewHolder(view) {
                };
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                View iv = holder.itemView.findViewById(R.id.iv);

                iv.setBackgroundResource(ids.get(position));
            }

            @Override
            public int getItemCount() {
                return ids.size();
            }
        });
        SlideCallback callback = new SlideCallback(rv,rv.getAdapter(),ids);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(rv);

    }



    private void mockData() {
        ids.add(R.drawable.beauty);
        ids.add(R.drawable.beauty2);
        ids.add(R.drawable.beauty3);
        ids.add(R.drawable.beauty4);
        ids.add(R.drawable.beauty5);
    }

    public void get(View view) {

        try {
            Field mChildHelperField = rv.getClass().getDeclaredField("mChildHelper");
            mChildHelperField.setAccessible(true);
            Object mChildHelper = mChildHelperField.get(rv);
            System.out.println(mChildHelper == null);
        } catch (Exception e) {


        }

    }
}