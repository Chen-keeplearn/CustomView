package com.al.custom_view.hen_coder_course

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import kotlin.math.max

/**
 * 1.在onMeasure中将子view的大小测量和计算并保存
 * 2.将自身的尺寸也计算出来
 * 3.在onLayout布局过程中，将它们都调用，将值传给过去
 */
class TagLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defaultAttr: Int = 0
) : ViewGroup(context, attributeSet, defaultAttr) {

    //用一个Rect集合存放每个子view在onMeasure中获得的左上右下的值(每个子view都可以看成是在一个相应的矩形得到的)
    private val childBounds = arrayListOf<Rect?>()

    /**
     * 测量子view
     * 获取每个子view的左上右下的值即位置和尺寸
     * 1.测量计算子view:child.measure(childWidthMeasureSpec,childHeightMeasureSpec)
     *   1.1计算childWidthMeasureSpec和childHeightMeasureSpec（都不用，用系统的方法）
     * 2.得到子view的childBound:childBounds[i]，将测量计算的值存入childBound中:childBound.set()
     * 3.自身ViewGroup也是一个view，大小也需要计算：setMeasuredDimension(width, height)
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //已使用的宽度
        var usedWidth = 0
        //已使用的高度
        var usedHeight = 0
        //行内最大的高度
        var lineMaxHeight = 0
        //记录一行使用的宽度
        var lineUsedWidth = 0
        for (i in 0 until childCount) {
            //得到子view
            val child = getChildAt(i)
            //测量计算子view尺寸前,需要先计算childWidthMeasureSpec，childHeightMeasureSpec
            //然后调用child.measure(childWidthMeasureSpec,childHeightMeasureSpec)
            /* 1.val layoutParams = child.layoutParams
            val specWidthMode = MeasureSpec.getMode(widthMeasureSpec)
            val specWidthSize = MeasureSpec.getSize(widthMeasureSpec)
            var childSpecMode: Int = 0
            var childWidthSize: Int = 0
            var childWidthMeasureSpec: Int = 0
            when (layoutParams.width) {
                LayoutParams.MATCH_PARENT -> {
                    when (specWidthMode) {
                        MeasureSpec.EXACTLY,
                        MeasureSpec.AT_MOST -> {
                            childSpecMode = MeasureSpec.EXACTLY
                            //子view的宽度是可用宽度,即需要加上每次用过宽度
                            childWidthSize = specWidthSize + usedWidth

                        }
                        MeasureSpec.UNSPECIFIED -> {
                            childSpecMode = MeasureSpec.UNSPECIFIED
                            //子view的宽度是可用宽度,即需要加上每次用过宽度
                            childWidthSize = 0
                        }
                    }
                    //拿到子view的MeasureSpec
                    childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, childSpecMode)
                }
                LayoutParams.WRAP_CONTENT -> {
                    //...
                }
                //...
            }
            //测量计算子view
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec)*/
            //以上 1. 测量了子view的childWidthMeasureSpec，同样的，还要测量出childHeightMeasureSpec
            //最后以上都不用那么麻烦，系统已经提供了方法
            //measureChild() or measureChildWithMargins()
            measureChildWithMargins(
                child,
                widthMeasureSpec,
                0,
                heightMeasureSpec,
                usedHeight
            )
            //换行
            if (lineUsedWidth + child.measuredWidth > MeasureSpec.getSize(widthMeasureSpec)) {
                lineUsedWidth = 0
                usedHeight += lineMaxHeight
                lineMaxHeight = 0
                measureChildWithMargins(
                    child,
                    widthMeasureSpec,
                    0,
                    heightMeasureSpec,
                    usedHeight
                )
            }
            //保存child的左上右下的值,这里具体子view测量的尺寸根据对应的布局来计算：线性、相对...
            var childBound: Rect
            if (childBounds.size <= i) {
                childBound = Rect()
                childBounds.add(childBound)
            } else {
                childBound = childBounds[i]!!
            }
            //用过的宽度加上测量的宽度
            childBound.set(
                lineUsedWidth,
                usedHeight,
                lineUsedWidth + child.measuredWidth,
                usedHeight + child.measuredHeight
            )
            lineUsedWidth += child.measuredWidth
            usedWidth = max(usedWidth, lineUsedWidth)
            lineMaxHeight = max(lineMaxHeight, child.measuredHeight)
        }
        //同时也需要测量viewGroup自身的宽高
        val width = usedWidth
        val height = usedHeight + lineMaxHeight
        //保存测量的宽高
        setMeasuredDimension(width, height)
    }

    /**
     * 子view布局
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        //子view布局
        for (i in 0 until childCount) {
            //获取每个子view的Rect bound
            val childBound = childBounds[i]!!
            //获取每个子view
            val child = getChildAt(i)
            //传入每个子view的左上右下,4个值就可以了
            child.layout(childBound.left, childBound.top, childBound.right, childBound.bottom)
        }
    }

    /**
     * measureChildWithMargins方法获得child的Margin时,需要特殊的即MarginLayoutParams
     * 不然就是默认的LayoutParams
     * 重写次方法measureChildWithMargins（）此方法就会获取一个带margin的LayoutParams
     */
    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
}
