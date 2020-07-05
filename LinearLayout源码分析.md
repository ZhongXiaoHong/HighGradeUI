# LinearLayout分析

```java
    void measureVertical(int widthMeasureSpec, int heightMeasureSpec) {
       
        //...
   for (int i = 0; i < count; ++i) {
            
          final View child = getVirtualChildAt(i);

          //TODO 排除GONE的View，自定义ViewGroup漏洞检查清单之一
          if (child.getVisibility() == View.GONE) {
               i += getChildrenSkipCount(child, i);
               continue;
          }
       
            
          final boolean useExcessSpace = lp.height == 0 && lp.weight > 0;
          if (heightMode == MeasureSpec.EXACTLY && useExcessSpace) {
      
              final int totalLength = mTotalLength;
              mTotalLength = Math.max(totalLength, totalLength + lp.topMargin + 																				lp.bottomMargin);
              skippedMeasure = true;
          } else {
              
                if (useExcessSpace) {
                    lp.height = LayoutParams.WRAP_CONTENT;
                }

      
                final int usedHeight = totalWeight == 0 ? mTotalLength : 0;
                measureChildBeforeLayout(child, i, widthMeasureSpec, 0,
                        heightMeasureSpec, usedHeight);

                final int childHeight = child.getMeasuredHeight();
                if (useExcessSpace) {
 
                    lp.height = 0;
                    consumedExcessSpace += childHeight;
                }

                final int totalLength = mTotalLength;
                mTotalLength = Math.max(totalLength, totalLength + childHeight + lp.topMargin +
                       lp.bottomMargin + getNextLocationOffset(child));

                if (useLargestChild) {
                    largestChildHeight = Math.max(childHeight, largestChildHeight);
                }
            }
            
            
        }
        
        
        mTotalLength += mPaddingTop + mPaddingBottom;
        int heightSize = mTotalLength;
        heightSize = Math.max(heightSize, getSuggestedMinimumHeight());
        int heightSizeAndState = resolveSizeAndState(heightSize, heightMeasureSpec, 0);
        heightSize = heightSizeAndState & MEASURED_SIZE_MASK;
        maxWidth += mPaddingLeft + mPaddingRight;
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());

        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
                heightSizeAndState);
        
        
    }
```

