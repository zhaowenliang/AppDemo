package cc.buddies.app.appdemo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import cc.buddies.app.treasury.utils.LogUtils;

public class MyViewGroup extends LinearLayout {

    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtils.e("MyViewGroup----dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtils.e("MyViewGroup----onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.e("MyViewGroup----onTouchEvent");
        return super.onTouchEvent(event);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
////        super.onLayout(changed, l, t, r, b);
//        // 参数说明
//        // changed 当前View的大小和位置改变了
//        // left 左部位置
//        // top 顶部位置
//        // right 右部位置
//        // bottom 底部位置
//
//        for (int i = 0; i < getChildCount(); i++) {
//            View view = getChildAt(i);
//
//            int width = view.getMeasuredWidth();
//            int height = view.getMeasuredHeight();
//
//            int mLeft = (r - width) / 2;
//            int mTop = (b - height) / 2;
//            int mRight = width + mLeft;
//            int mBottom = height + mTop;
//
//            view.layout(mLeft, mTop, mRight, mBottom);
//        }
//    }


    // onMeasure() -> onLayout() -> draw
    // draw(): drawBackground（）-> onDraw() -> dispatchDraw() -> onDrawScrollBars(canvas)

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//    }

}
