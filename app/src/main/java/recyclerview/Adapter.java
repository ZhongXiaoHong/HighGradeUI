package recyclerview;

import android.view.View;

public interface Adapter {
    int getCount();

    int getItemViewType(int row);

    int getViewTypeCount();

    View getView(View position, View convertView, View parent);

    int getHeight(int index);
}
