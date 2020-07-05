package com.silang.highgradeui

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import java.util.*

class KotlinFlowLayout constructor(context:Context): ViewGroup(context){

    constructor(context: Context,attrs: AttributeSet):this(context)

    constructor(context: Context,attrs: AttributeSet,defStyleAttr:Int):this(context)

    //TODO 所有行对应的实体
    private  var rowBeans = ArrayList<RowBean>()

    private val VERTICAL_SPACE = dp2px(10)





    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
       //TODO  onMeasure可能会被调用多次
        // TODO  所以这里每一次onMeasure前清除数据
        //TODO 避免重复加载
       rowBeans.clear();

        //TODO 【1】帮助孩子们计算SPec
        //TODO 【2】触发孩子计算自身的测量宽高
        for (i  in 0 until childCount){
            var child  = getChildAt(i)
            //TODO 【1 计算SPec】
           var childWSpec  = getChildMeasureSpec(widthMeasureSpec,paddingLeft+paddingRight,layoutParams.width)
           var childHSpec  = getChildMeasureSpec(heightMeasureSpec,paddingTop+paddingBottom,layoutParams.height)

            //TODO 【2 触发计算自身的测量宽高】
            child.measure(childWSpec,childHSpec)

            //TODO  保存孩子们
            saveChildView(child, MeasureSpec.getSize(widthMeasureSpec))

        }

        //TODO 【3】计算自身的测量宽高
        var wrapContentWH = getwrapContentWH();
        val measureW = if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) MeasureSpec.getSize(widthMeasureSpec) else wrapContentWH[0]
        val measureH = if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) MeasureSpec.getSize(heightMeasureSpec) else wrapContentWH[1]
        setMeasuredDimension(measureW,measureH)

    }


    private fun getLastRowBean(): RowBean {
        if (rowBeans.isEmpty()) {
            val r  = RowBean()
            rowBeans.add(r)
            return r
        }
        return rowBeans[rowBeans.size - 1]
    }
    private fun needChangeLine(child: View, specWith: Int): Boolean {
        val curRowBean = getLastRowBean()

        //TODO 判断最后一行还能不能继续添加View
        val rowChildren = curRowBean?.views
        if (rowChildren!!.isEmpty()) {
            return false
        }
        return curRowBean.with + child.measuredWidth > specWith
    }

    private fun saveChildView(child: View, specWith: Int) {
        val changeLine: Boolean = needChangeLine(child, specWith)
        val curRowBean = if (changeLine) RowBean() else getLastRowBean()
        if (changeLine) {
            rowBeans.add(curRowBean)
        }

        //TODO 行高取最高的孩子的高度
        curRowBean.height = if (child.measuredHeight > curRowBean.height) child.measuredHeight else curRowBean.height
        //TODO 行宽取孩子们宽的叠加
        curRowBean.with += child.measuredWidth
        curRowBean.views.add(child)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var left = 0
        var right = 0
        var top = 0
        var bottom = paddingTop


        for (i in rowBeans.indices) {
            val rowBean = rowBeans[i]
            val rowViews = rowBean.views
            left = paddingLeft
            right = left
            top = bottom
            if (i > 0) {
                top += VERTICAL_SPACE
            }
            bottom = top + rowBean.height
            for (rowView in rowViews) {
                right += rowView.measuredWidth
                rowView.layout(left, top, right, bottom)
                left = right + 20
            }
        }


    }

    private fun getwrapContentWH():Array<Int>{
        var totalW = 0;
        var totalH = 0;

        for (rowBean in rowBeans) {
            totalW = if (rowBean.with > totalW) rowBean.with else totalW
            totalH += rowBean.height+VERTICAL_SPACE
        }
        totalH -= VERTICAL_SPACE
        totalH += paddingBottom + paddingTop
        totalW += paddingLeft + paddingRight

         return arrayOf(totalW,totalH)
    }


}

internal class RowBean {
    var views: ArrayList<View> = ArrayList()
    var with = 0
    var height = 0
}

fun dp2px(dp: Int): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), Resources.getSystem().displayMetrics).toInt()
}
