package cc.buddies.app.appdemo.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import cc.buddies.app.appdemo.R;

public class DYLoadingView extends View {

    // 默认值
    private final float RADIUS = dp2px(6);
    private final float GAP = dp2px(0.8f);
    private static final float RTL_SCALE = 0.7f;
    private static final float LTR_SCALE = 1.3f;
    private static final int LEFT_COLOR = 0XFFFF4040;
    private static final int RIGHT_COLOR = 0XFF00EEEE;
    private static final int MIX_COLOR = Color.BLACK;
    private static final int DURATION = 350;
    private static final int PAUSE_DURATION = 80;
    private static final float SCALE_START_FRACTION = 0.2f;
    private static final float SCALE_END_FRACTION = 0.8f;

    // 属性
    private float radius1;      // 初始化左小球半径
    private float radius2;      // 初始化右小球半径
    private float gap;          // 两小球之间的间隔(可以为)
    private float rtlScale;     // 小球从右边移动到左边的时候大小倍数变化
    private float ltrScale;     // 小球从左边移动到右边的时候大小倍数变化
    private int color1;         // 初始左小球颜色
    private int color2;         // 初始右小球颜色
    private int mixColor;       // 重叠处颜色
    private int duration;       // 小球一次移动时长
    private int pauseDuration;  // 小球一次移动后停顿时长
    private float scaleStartFraction;   // 小球一次移动期间，进度在[0, scaleStartFraction]期间根据rtlScale、ltrScale逐渐缩放，取值为[0, 0.5]
    private float scaleEndFraction;   // 小球一次移动期间，进度在[scaleEndFraction, 1]期间逐渐恢复初始大小，取值为[0.5, 1]

    // 绘图
    private Paint paint1, paint2, mixPaint;
    private Path ltrPath, rtlPath, mixPath;
    private float distance;     // 小球圆点间距离（小球一次移动距离）

    // 动画
    private ValueAnimator anim;
    private float fraction;         // 小球一次移动动画的进度百分比
    boolean isAnimCanceled = false;
    boolean isLtr = true;           // true:[初始小球]当前正[从左往右]移动，false:[初始小球]当前正[从右往左]移动。

    public DYLoadingView(Context context) {
        this(context, null);
    }

    public DYLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DYLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 获取属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DYLoadingView);
        radius1 = ta.getDimension(R.styleable.DYLoadingView_radius1, RADIUS);
        radius2 = ta.getDimension(R.styleable.DYLoadingView_radius1, RADIUS);
        gap = ta.getFloat(R.styleable.DYLoadingView_gap, GAP);
        rtlScale = ta.getFloat(R.styleable.DYLoadingView_rtlScale, RTL_SCALE);
        ltrScale = ta.getFloat(R.styleable.DYLoadingView_ltrScale, LTR_SCALE);
        color1 = ta.getInt(R.styleable.DYLoadingView_color1, LEFT_COLOR);
        color2 = ta.getInt(R.styleable.DYLoadingView_color2, RIGHT_COLOR);
        mixColor = ta.getInt(R.styleable.DYLoadingView_mixColor, MIX_COLOR);
        duration = ta.getInt(R.styleable.DYLoadingView_duration, DURATION);
        pauseDuration = ta.getInt(R.styleable.DYLoadingView_pauseDuration, PAUSE_DURATION);
        scaleStartFraction = ta.getFloat(R.styleable.DYLoadingView_scaleStartFraction, SCALE_START_FRACTION);
        scaleEndFraction = ta.getFloat(R.styleable.DYLoadingView_scaleEndFraction, SCALE_END_FRACTION);
        ta.recycle();

        // 属性检查
        checkAttr();
        distance = radius1 + radius2 + gap;

        // 初始化绘制数据
        initDraw();

        // 初始化动画
        initAnim();
    }

    /**
     * 属性合法化检查校正
     */
    private void checkAttr() {
        radius1 = radius1 > 0 ? radius1 : RADIUS;
        radius2 = radius2 > 0 ? radius2 : RADIUS;
        gap = gap > 0 ? gap : GAP;
        rtlScale = rtlScale > 0 ? rtlScale : RTL_SCALE;
        ltrScale = ltrScale > 0 ? ltrScale : LTR_SCALE;
        duration = duration > 0 ? duration : DURATION;
        pauseDuration = pauseDuration > 0 ? pauseDuration : PAUSE_DURATION;
        if (scaleStartFraction < 0 || scaleStartFraction > 0.5) {
            scaleStartFraction = SCALE_START_FRACTION;
        }
        if (scaleEndFraction < 0.5 || scaleEndFraction > 1) {
            scaleEndFraction = SCALE_END_FRACTION;
        }
    }

    /**
     * 初始化绘图工具
     */
    private void initDraw() {
        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mixPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint1.setColor(color1);
        paint2.setColor(color2);
        mixPaint.setColor(mixColor);

        ltrPath = new Path();
        rtlPath = new Path();
        mixPath = new Path();
    }

    /**
     * 初始化动画
     */
    private void initAnim() {
        fraction = 0.0f;
        stop();

        anim = ValueAnimator.ofFloat(0.0f, 1.0f);
        anim.setDuration(duration);
        if (pauseDuration > 0) {
            anim.setStartDelay(pauseDuration);
            anim.setInterpolator(new AccelerateDecelerateInterpolator());
        } else {
            anim.setRepeatCount(ValueAnimator.INFINITE);
            anim.setRepeatMode(ValueAnimator.RESTART);
            anim.setInterpolator(new LinearInterpolator());
        }
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fraction = animation.getAnimatedFraction();
                invalidate();
            }
        });
        anim.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isLtr = !isLtr;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                isLtr = !isLtr;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                isAnimCanceled = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!isAnimCanceled) {
                    anim.start();
                }
            }

        });
    }

    public void start() {
        if (anim == null) {
            initAnim();
        }
        if (anim.isRunning()) {
            anim.cancel();
        }
        post(new Runnable() {
            @Override
            public void run() {
                isAnimCanceled = false;
                isLtr = false;
                anim.start();
            }
        });
    }

    public void stop() {
        if (anim != null) {
            anim.cancel();
            anim = null;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);

        float maxScale = Math.max(ltrScale, rtlScale);
        maxScale = Math.max(maxScale, 1);

        if (wMode != MeasureSpec.EXACTLY) {
            wSize = (int) ((2 * radius1 + 2 * radius2) * maxScale + gap + dp2px(1));  // 宽度 = （左球直径 + 右球直径）* 最大比例 + 间隔 + 1dp
        }
        if (hMode != MeasureSpec.EXACTLY) {
            hSize = (int) (Math.max(radius1, radius2) * 2 * maxScale + dp2px(1));       // 高度 = 最大球直径 * 最大比例 + 1dp
        }
        setMeasuredDimension(wSize, hSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float centerY = getMeasuredHeight() / 2.0f;
        float ltrInitRadius, rtlInitRadius;
        Paint ltrPaint, rtlPaint;

        // 确定当前【从左往右】移动的是哪个小球
        if (isLtr) {
            ltrInitRadius = radius1;
            rtlInitRadius = radius2;
            ltrPaint = paint1;
            rtlPaint = paint2;
        } else {
            ltrInitRadius = radius2;
            rtlInitRadius = radius1;
            ltrPaint = paint2;
            rtlPaint = paint1;
        }

        float ltrX = getMeasuredWidth() / 2.0f - distance / 2.0f;
        ltrX = ltrX + (distance * fraction);        // 当前从左往右的球的X坐标

        float rtlX = getMeasuredWidth() / 2.0f + distance / 2.0f;
        rtlX = rtlX - (distance * fraction);

        // 计算小球移动过程中大小变化
        float ltrBallRadius, rtlBallRadius;
        if (fraction <= scaleStartFraction) {               // 动画进度[0, scaleStartFraction]时，球大小由1倍逐渐缩放至ltrScale/rtlScale倍
//            float scaleFraction = 1.0f / scaleStartFraction * fraction;     // 百分比转换[0, scaleStartFraction] -> [0, 1]
            float scaleFraction = (fraction * 1.0f) / scaleStartFraction;

            ltrBallRadius = ltrInitRadius * (1 + (ltrScale - 1) * scaleFraction);   // 总的变化倍率-基本大小倍率=差异变化倍率，差异变化倍率*当前阶段进度=当前进度变化倍率
            rtlBallRadius = rtlInitRadius * (1 + (rtlScale - 1) * scaleFraction);
        } else if (fraction >= scaleEndFraction) {          // 动画进度[scaleEndFraction, 1]，球大小由ltrScale/rtlScale倍逐渐恢复至1倍
//            float scaleFraction = (fraction - 1) / (scaleEndFraction - 1);  // 百分比转换，[scaleEndFraction, 1] -> [1, 0]
            float scaleFraction = (1 - fraction) / (1 - scaleEndFraction);      // [scaleEndFraction, 1]阶段和[0, scaleStartFraction]阶段对称，在对称的位置缩放倍率是相同的。

            ltrBallRadius = ltrInitRadius * (1 + (ltrScale - 1) * scaleFraction);
            rtlBallRadius = rtlInitRadius * (1 + (rtlScale - 1) * scaleFraction);
        } else {                                            // 动画进度[scaleStartFraction, scaleEndFraction]，球保持缩放后的大小
            ltrBallRadius = ltrInitRadius * ltrScale;
            rtlBallRadius = rtlInitRadius * rtlScale;
        }

        ltrPath.reset();
        ltrPath.addCircle(ltrX, centerY, ltrBallRadius, Path.Direction.CW);
        rtlPath.reset();
        rtlPath.addCircle(rtlX, centerY, rtlBallRadius, Path.Direction.CW);
        mixPath.op(ltrPath, rtlPath, Path.Op.INTERSECT);

        canvas.drawPath(ltrPath, ltrPaint);
        canvas.drawPath(rtlPath, rtlPaint);
        canvas.drawPath(mixPath, mixPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        stop();
        super.onDetachedFromWindow();
    }

    private float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

}
