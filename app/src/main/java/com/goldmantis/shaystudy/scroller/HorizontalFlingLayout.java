package com.goldmantis.shaystudy.scroller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * @author: Shay-Patrick-Cormac
 * @email: fang47881@126.com
 * @ltd: 金螳螂企业（集团）有限公司
 * @date: 2017/12/1 14:24
 * @version: 1.0
 * @description: 学习Scroller的弹性滑动
 * 
 * 注意 getScorllX和getScrollerY的值，要明白，负值，表示相对于自己，他的孩子往右，往下滑动，而正值，则表示
 * 向左，向上滑动。
 */

public class HorizontalFlingLayout extends LinearLayout {
    private Scroller mScroller;
    /**
     * 假设这个父布局里面就两个子View
     */
    private View mLeftView;
    private View mRightView;
    /**
     * 手指触摸的位置（相对于该View的左上角位置）
     */
    private float mInitX, mInitY;
    /**
     * 偏移的距离
     */
    private float mOffsetX, mOffsetY;

    public HorizontalFlingLayout(Context context) {
        this(context, null);
    }

    public HorizontalFlingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalFlingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setOrientation(LinearLayout.HORIZONTAL);
        mScroller = new Scroller(getContext(), new AccelerateInterpolator(), true);
    }

    //加载完毕后的校验
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (getChildCount() != 2) {
            throw new RuntimeException("Only need two child view! Please check you xml file!");
        }

        mLeftView = getChildAt(0);
        mRightView = getChildAt(1);
    }

    //这个方法是在View中的draw方法的空实现，需要继承的子View进行自己逻辑处理
    @Override
    public void computeScroll() {
        //mScroller.computeScrollOffset() 这个方法返回true，表示上一次偏移已经完成，需要再对下一个偏移进行处理
        if (mScroller.computeScrollOffset()) {
            //表示偏移到当前的位置
            this.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //异步回调重绘，最后还是会调用draw方法，再次调用这个computeScroll() 方法
            postInvalidate();
        }
    }

    //手指在触摸滑动。
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                //初始化位置
                mInitX = ev.getX();
                mInitY = ev.getY();
                // 需要把分发事件返回去
                super.dispatchTouchEvent(ev);
                return true;
            case MotionEvent.ACTION_MOVE:
                //和初始的位置比较，之所以拿初始位置减去move后的坐标，其实表达的是，如果得到的结果为正，
                //表示子View向左滑动，同理，为负值，表示子View向右滑动。
                mOffsetX =  mInitX-ev.getX();
                mOffsetY =  mInitY-ev.getY();
                //横向手势移动 (达到可以最小移动的距离了)
                if (Math.abs(mOffsetX)-Math.abs(mOffsetY)> ViewConfiguration.getTouchSlop())
                {
                    int offset = (int) mOffsetX;
                    //注意，这都是手指在触摸
                    //边界条件 右边的距离滑动超过右边的view 或者左边的滑动距离？？？ todo 
                    //第一个条件表示，向左滑动的时候，要是滑动的距离大于右边的view,那么则不能让其再往右偏移了
                    //否则，露出白边了，对吧？
                    //同理，右边的边界条件，表示，想向右滑动，就禁止。
                    if (getScrollX()+offset>mRightView.getWidth() || getScrollX()+offset<-mLeftView.getWidth()/2)
                    {
                        return true;
                    }
                    //相对的位移为：
                    this.scrollBy(offset, 0);
                    //移动的过程不断重新赋值
                    mInitX = ev.getX();
                    mInitY = ev.getY();
                    return true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                //结束。处理??? todo 
                //因为向右已经禁止滑动了，所以getScrollX()肯定是大于0的，所以这个边界条件的意思就是，如果
                //滑动的距离超过右边view的一半，就使整个右边的view全部显示出来，否则不显示。
                int offset = ((getScrollX() / (float)mRightView.getWidth()) > 0.5) ? mRightView.getWidth() : 0;
                //重要的点！！
                mScroller.startScroll(getScrollX(),getScrollY(),offset-getScrollX(),0);
                invalidate();
                //初始化
                mInitX = 0;
                mInitY = 0;
                mOffsetX = 0;
                mOffsetY = 0;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
