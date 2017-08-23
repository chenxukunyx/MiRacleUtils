package com.miracle.libs.view;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.miracle.libs.R;
import com.miracle.libs.utils.MLog;

/**
 * Created with Android Studio
 *
 * @author: chenxukun
 * @data: 2017/8/16
 * @time: 下午9:49
 */

public class SuccessFailView extends View {

    private Context mContext;
    private static final int DEFAULT_PROGRESS_COLOR = Color.parseColor("#3F51B5");
    private static final int DEFAULT_SUCCESS_COLOR = Color.parseColor("#03a44e");
    private static final int DEFAULT_FAILURE_COLOR = Color.parseColor("#de0e26");
    private static final float DEFAULT_PROGRESS_WIDTH = 6f;
    private static final float DEFAULT_PROGRESS_RADIUS = 100f;

    private int mProgressColor;//loading颜色
    private int mSuccessColor;//success颜色
    private int mFailureColor;
    private float mProgressWidth;
    private float mProgressRadius;

    private Paint mPaint;
    private Status mStatus;

    private int startAngle = -90;
    private int minAngle = -90;
    private int sweepAngle = 120;
    private int currentAndle = 0;

    private PathMeasure mPathMeasure;
    private Path mPathCicle;

    private Path mPathCicleDst;
    private Path successPath;
    private Path failurePathLeft;
    private Path failurePathRight;

    private ValueAnimator circleAnimator;
    private float circleValue;
    private float successValue;
    private float failureValueLeft;
    private float failureValueRight;

    public SuccessFailView(Context context) {
        this(context, null);
    }

    public SuccessFailView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuccessFailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SuccessFailView);
        mProgressColor = array.getColor(R.styleable.SuccessFailView_sf_progress_color, DEFAULT_PROGRESS_COLOR);
        mSuccessColor = array.getColor(R.styleable.SuccessFailView_sf_success_color, DEFAULT_SUCCESS_COLOR);
        mFailureColor = array.getColor(R.styleable.SuccessFailView_sf_failure_color, DEFAULT_FAILURE_COLOR);
        mProgressWidth = array.getDimension(R.styleable.SuccessFailView_sf_progress_width, DEFAULT_PROGRESS_WIDTH);
        mProgressRadius = array.getDimension(R.styleable.SuccessFailView_sf_progress_radius, DEFAULT_PROGRESS_RADIUS);
        array.recycle();

        initPaint();
        initPath();
        initAnim();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(mProgressColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mProgressWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void initPath() {
        mPathCicle = new Path();
        mPathMeasure = new PathMeasure();
        mPathCicleDst = new Path();
        successPath = new Path();
        failurePathLeft = new Path();
        failurePathRight = new Path();
    }

    private void initAnim() {
        circleAnimator = ValueAnimator.ofFloat(0, 1);
        circleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleValue = (float) animation.getAnimatedValue();
                MLog.i("circleValue" + circleValue);
                invalidate();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        int height;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = (int) (widthSize + mProgressWidth);
        } else {
            width = (int) (2 * mProgressRadius +  3 * mProgressWidth + getPaddingLeft() + getPaddingRight());
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = (int) (heightSize + mProgressWidth);
        } else {
            height = (int) (2 * mProgressRadius + 3 * mProgressWidth + getPaddingTop() + getPaddingBottom());
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getPaddingLeft() + mProgressWidth, getPaddingTop() + mProgressWidth);
        if (mStatus == Status.Loading) {
            if (startAngle == minAngle) {
                sweepAngle += 6;
            }
            if (sweepAngle > 300 || startAngle > minAngle) {
                startAngle += 6;
                if (sweepAngle > 20) {
                    sweepAngle -= 6;
                }
            }
            if (startAngle > minAngle + 360) {
                startAngle %= 360;
                minAngle = startAngle;
                sweepAngle = 20;
            }
            canvas.rotate(currentAndle += 4, mProgressRadius, mProgressRadius);
            canvas.drawArc(new RectF(0, 0, mProgressRadius * 2, mProgressRadius * 2), startAngle, sweepAngle, false, mPaint);
            invalidate();
        } else if (mStatus == Status.LoadSuccess) {
            mPathCicleDst.reset();
            mPaint.setColor(mSuccessColor);
            mPathCicle.addCircle(getWidth() / 2, getHeight() / 2, mProgressRadius, Path.Direction.CW);
            mPathMeasure.setPath(mPathCicle, false);
            mPathMeasure.getSegment(0, circleValue * mPathMeasure.getLength(), mPathCicleDst, true);
            canvas.drawPath(mPathCicleDst, mPaint);

            if (circleValue == 1) {      //表示圆画完了,可以钩了
                successPath.moveTo(getWidth() / 8 * 3, getWidth() / 2);
                successPath.lineTo(getWidth() / 2, getWidth() / 5 * 3);
                successPath.lineTo(getWidth() / 3 * 2, getWidth() / 5 * 2);
                mPathMeasure.nextContour();
                mPathMeasure.setPath(successPath, false);
                mPathMeasure.getSegment(0, successValue * mPathMeasure.getLength(), mPathCicleDst, true);
                canvas.drawPath(mPathCicleDst, mPaint);
            }
        } else if (mStatus == Status.LoadFailure) {
            mPathCicleDst.reset();
            mPaint.setColor(mFailureColor);
            mPathCicle.addCircle(getWidth() / 2, getWidth() / 2, mProgressRadius, Path.Direction.CW);
            mPathMeasure.setPath(mPathCicle, false);
            mPathMeasure.getSegment(0, circleValue * mPathMeasure.getLength(), mPathCicleDst, true);
            canvas.drawPath(mPathCicleDst, mPaint);

            if (circleValue == 1) {  //表示圆画完了,可以画叉叉的右边部分
                failurePathRight.moveTo(getWidth() / 3 * 2, getWidth() / 3);
                failurePathRight.lineTo(getWidth() / 3, getWidth() / 3 * 2);
                mPathMeasure.nextContour();
                mPathMeasure.setPath(failurePathRight, false);
                mPathMeasure.getSegment(0, failureValueRight * mPathMeasure.getLength(), mPathCicleDst, true);
                canvas.drawPath(mPathCicleDst, mPaint);
            }

            if (failureValueRight == 1) {    //表示叉叉的右边部分画完了,可以画叉叉的左边部分
                failurePathLeft.moveTo(getWidth() / 3, getWidth() / 3);
                failurePathLeft.lineTo(getWidth() / 3 * 2, getWidth() / 3 * 2);
                mPathMeasure.nextContour();
                mPathMeasure.setPath(failurePathLeft, false);
                mPathMeasure.getSegment(0, failureValueLeft * mPathMeasure.getLength(), mPathCicleDst, true);
                canvas.drawPath(mPathCicleDst, mPaint);
            }
        }
    }

    private void setStatus(Status status) {
        mStatus = status;
    }

    public void loadingStatus() {
        setStatus(Status.Loading);
        invalidate();
    }

    public void successStatus() {
        setStatus(Status.LoadSuccess);
        startSuccessAnim();
    }

    public void failureStatus() {
        setStatus(Status.LoadFailure);
        startFailureAnim();
    }

    private void startSuccessAnim() {
        ValueAnimator success = ValueAnimator.ofFloat(0f, 1.0f);
        success.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                successValue = (float) animation.getAnimatedValue();
                MLog.i(successValue);
                invalidate();
            }
        });
        AnimatorSet set = new AnimatorSet();
        set.play(success).after(circleAnimator);
        set.setDuration(500);
        set.start();
    }

    private void startFailureAnim() {
        ValueAnimator failLeft = ValueAnimator.ofFloat(0f, 1.0f);
        failLeft.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                failureValueRight = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        ValueAnimator failRight = ValueAnimator.ofFloat(0f, 1.0f);
        failRight.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                failureValueLeft = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(failLeft).after(circleAnimator).before(failRight);
        animatorSet.setDuration(500);
        animatorSet.start();
    }

    public enum Status{
        Loading,
        LoadSuccess,
        LoadFailure
    }
}
