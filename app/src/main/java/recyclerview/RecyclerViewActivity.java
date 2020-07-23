package recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.silang.highgradeui.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    RecyclerView rv;
    List<Star> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        mockData();
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new StartItemDecoration(this));
        //rv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        rv.setAdapter(new StarAdapter(datas, this));


    }

    private void mockData() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                Star start = null;
                if (i % 2 == 0) {
                    start = new Star("何炅_" + j, "快乐家族" + i);
                } else {
                    start = new Star("汪涵_" + j, "天天兄弟" + i);
                }
                datas.add(start);
            }

        }
    }
}