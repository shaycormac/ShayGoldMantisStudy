package com.goldmantis.shaystudy.scroller;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.BounceInterpolator;
import android.widget.Scroller;

/**
 * @author: Shay-Patrick-Cormac
 * @email: fang47881@126.com
 * @ltd: 金螳螂企业（集团）有限公司
 * @date: 2017/12/1 16:53
 * @version: 1.0  由于NestScrollView只允许一个子View,所以就对这个View进行操作即可。
 * @description: 尝试使用Scroller, 制作一个可以回弹的NestScrollView
 */

public class BundleNestedScrollView extends NestedScrollView {
    private Scroller scroller;

    private float initX;
    private float initY;


    private float offsetX;
    private float offsetY;

    //唯一的孩子
    private View onlyView;

    public BundleNestedScrollView(Context context) {
        this(context, null);
    }

    public BundleNestedScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BundleNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() 
    {
        scroller = new Scroller(getContext(), new BounceInterpolator(), true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        onlyView = getChildAt(0);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        switch(ev.getActionMasked())
        {
            case MotionEvent.ACTION_DOWN:
                //记下此时的值
                initX = ev.getX();
                initY = ev.getY();
                super.dispatchTouchEvent(ev);
               return true;
            case MotionEvent.ACTION_MOVE: 
                //记下此时的值
                offsetX = initX - ev.getX();
                offsetY = initY - ev.getY();
                //判断的滑动，为垂直方向即可
                if (Math.abs(offsetY)-Math.abs(offsetX)> ViewConfiguration.getTouchSlop())
                {
                    //滑动的距离为
                    int offSet = (int) offsetY;
                    //设置一个边界条件。(假如已子view的一半为边界)
                    if (offSet + getScrollY() > onlyView.getHeight() / 2 || offSet + getScrollY() < -onlyView.getHeight() / 2) {
                        return true;
                    }
                    
                    this.scrollBy(0, offSet);
                    //重新赋值
                    initX = ev.getX();
                    initY = ev.getY();
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP: 
            case MotionEvent.ACTION_CANCEL:  
                //松手的时候，反弹回去
                scroller.startScroll(getScrollX(), getScrollY(), 0, -getScrollY());
                invalidate();
                //恢复值
                initX = 0;
                initY = 0;
                offsetX = 0;
                offsetY = 0;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void computeScroll() 
    {
        if (scroller.computeScrollOffset())
        {
            //重新移动到要移动的地方去
            this.scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }
}
