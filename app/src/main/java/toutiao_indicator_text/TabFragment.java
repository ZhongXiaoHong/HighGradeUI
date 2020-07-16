package toutiao_indicator_text;


import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Random;

public class TabFragment extends Fragment
{
    // private String[] mTitles = new String[]{"关注", "热点", "推荐", "长沙", "广州", "深圳", "东莞", "佛山", "惠州", "珠海", "中山", "江门"};
    public static final String TITLE = "title";
    private String mTitle = "Defaut Value";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mTitle = getArguments().getString(TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        TextView tv = new TextView(getActivity());
        tv.setTextSize(60);
        Random r = new Random();
        tv.setBackgroundColor(Color.argb(r.nextInt(120), r.nextInt(255),
                r.nextInt(255), r.nextInt(255)));
        tv.setText(mTitle);
        tv.setGravity(Gravity.CENTER);
        return tv;

    }

    public static TabFragment newInstance(String title)
    {
        TabFragment tabFragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

}
