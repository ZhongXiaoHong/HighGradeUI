package com.silang.highgradeui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.activity_test_google_flexbox_with_recyclerview_layout.*


/***
 * FlexboxLayout搭配RecyclerView使用
 */

class TestGoogleFlexboxLayoutWithRecyclerViewActivity : AppCompatActivity() {

//    private val images = arrayOf<Int>(
//            R.drawable.image1,
//            R.drawable.image2,
//            R.drawable.image3,
//            R.drawable.image4,
//            R.drawable.image5,
//            R.drawable.image6,
//            R.drawable.image7,
//            R.drawable.image8,
//            R.drawable.image9,
//            R.drawable.image10,
//            R.drawable.image11,
//            R.drawable.image12,
//            R.drawable.image13
//    )
    private val CATS = arrayOf<Int>(
            R.drawable.cat_1,
            R.drawable.cat_2,
            R.drawable.cat_3,
            R.drawable.cat_4,
            R.drawable.cat_5,
            R.drawable.cat_6,
            R.drawable.cat_7,
            R.drawable.cat_8,
            R.drawable.cat_9,
            R.drawable.cat_10,
            R.drawable.cat_11,
            R.drawable.cat_12,
            R.drawable.cat_13,
            R.drawable.cat_14,
            R.drawable.cat_15,
            R.drawable.cat_16,
            R.drawable.cat_17,
            R.drawable.cat_18,
            R.drawable.cat_19)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_test_google_flexbox_with_recyclerview_layout)


        val manager = FlexboxLayoutManager(this)
        //设置主轴排列方式
        //设置主轴排列方式
        manager.flexDirection = FlexDirection.ROW
        //设置是否换行
        //设置是否换行
        manager.flexWrap = FlexWrap.WRAP
        manager.alignItems = AlignItems.STRETCH

       var  items = ArrayList<Int>();
      //  items.addAll(images);
        items.addAll(CATS);

        rvFlex2.layoutManager = manager;
        rvFlex2.adapter = RvFlexAdapter(items, this)

    }

}