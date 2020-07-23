package recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.silang.highgradeui.R;

import java.util.List;

public class StarAdapter extends RecyclerView.Adapter<StarAdapter.StarHolder> {

    List<Star> list;
    Context context;

    public StarAdapter(List<Star> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public StarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item, parent, false);

        return new StarHolder(view);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void onBindViewHolder(@NonNull StarHolder holder, int position) {
        holder.textView.setText(list.get(position).getName());
    }

    public String getGoupName(int pos) {
        return list == null ? "" : list.get(pos).getGroupName();
    }

    class StarHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public StarHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
        }
    }

    public boolean isGroupHeader(int position) {
 /*       if (position == 0) {
            return true;
        } else {
            String cutGroupName = list.get(position).getGroupName();
            String preGroupName = list.get(position - 1).getGroupName();
            if (cutGroupName.equals(preGroupName)) {
                return false;
            } else {
                return true;
            }
        }*/

        if (position > 0) {
            String cutGroupName = list.get(position).getGroupName();
            String preGroupName = list.get(position - 1).getGroupName();
            if (cutGroupName.equals(preGroupName)) {
                return false;
            }
        }
        return true;
    }

}



