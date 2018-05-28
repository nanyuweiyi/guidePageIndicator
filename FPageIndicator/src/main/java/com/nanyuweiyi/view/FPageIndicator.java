package com.nanyuweiyi.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class FPageIndicator extends View {

    private int count;
    private float outRadius;
    private float innerRadius;

    private int unFocusColor;
    private int focusColor;

    private float padding;

    private int selectedPos;

    private int drawPosition;
    private float drawPositionOffset;

    private static final int DEFAULT_OUT_RADIUS_SIZE = 5;
    private static final int DEFAULT_INNER_RADIUS_SIZE = 3;

    private static final String DEFAULT_UN_FOCUS_COLOR = "#D9D9D9";
    private static final String DEFAULT_FOCUS_COLOR = "#F7F7F7";

    private Paint innerUnFocusPaint = new Paint();
    private Paint innerFocusPaint = new Paint();
    private Paint outPaint = new Paint();

    private Callback callback;

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            drawPosition = position;
            drawPositionOffset = positionOffset;
            invalidate();
        }

        @Override
        public void onPageSelected(int position) {
            setSelectedPos(position);
            callback.isLastListener(isLastPos());
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public FPageIndicator(Context context) {
        this(context, null, 0);
    }

    public FPageIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FPageIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(attrs);
        initPaint();
    }

    private void initAttributes(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PageIndicator);
        count = typedArray.getInteger(R.styleable.PageIndicator_pi_count, 1);
        outRadius = typedArray.getDimension(R.styleable.PageIndicator_pi_out_radius,
                dpToPx(DEFAULT_OUT_RADIUS_SIZE));
        innerRadius = typedArray.getDimension(R.styleable.PageIndicator_pi_radius,
                dpToPx(DEFAULT_INNER_RADIUS_SIZE));
        unFocusColor = typedArray.getColor(R.styleable.PageIndicator_pi_un_focus_color,
                Color.parseColor(DEFAULT_UN_FOCUS_COLOR));
        focusColor = typedArray.getColor(R.styleable.PageIndicator_pi_focus_color,
                Color.parseColor(DEFAULT_FOCUS_COLOR));
        padding = typedArray.getDimension(R.styleable.PageIndicator_pi_padding, 0F);
        typedArray.recycle();
    }

    private void initPaint() {
        innerUnFocusPaint.setStyle(Paint.Style.FILL);
        innerUnFocusPaint.setColor(unFocusColor);

        innerFocusPaint.setStyle(Paint.Style.FILL);
        innerFocusPaint.setColor(focusColor);

        outPaint.setStyle(Paint.Style.STROKE);
        outPaint.setColor(focusColor);
        outPaint.setStrokeWidth(2F);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width;
        int height;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int desiredWidth;
        if (count == 1) {
            desiredWidth = (int) outRadius;
        } else {
            desiredWidth = (int) (innerRadius * 2 * count + padding * (count - 1) + (outRadius - innerRadius) * 2);
        }
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }

        int desiredHeight = (int) outRadius;
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        // 对继承 ViewGroup 类需要做这样的工作，对于继承自 View 的情况就不需要做处理了。
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        for (int i = 0; i < count; i++) {
            canvas.drawCircle(
                    outRadius - innerRadius + padding * i + innerRadius * (1 + 2 * i),
                    height / 2,
                    innerRadius,
                    i == selectedPos ? innerFocusPaint : innerUnFocusPaint);
        }

        if (drawPosition == 0 && drawPositionOffset == 0) {
            canvas.drawCircle(
                    innerRadius * (2 * selectedPos) + padding * selectedPos + outRadius,
                    height / 2,
                    outRadius,
                    outPaint);
        } else {
            canvas.drawCircle(
                    innerRadius * 2 * drawPosition + innerRadius
                            + (outRadius - innerRadius)
                            + padding * drawPosition
                            + (padding + innerRadius * 2) * drawPositionOffset,
                    height / 2,
                    outRadius,
                    outPaint);
        }

    }

    private static int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    /**
     * 设置当前选中的位置.
     *
     * @param pos 当前位置.
     */
    public void setSelectedPos(int pos) {
        if (this.selectedPos != pos) {
            this.selectedPos = pos;
            invalidate();
        }
    }

    /**
     * 是否是最后一张.
     */
    public boolean isLastPos() {
        return this.selectedPos + 1 == count;
    }

    /**
     * Page Indicator 总共的数目
     *
     * @param count 总数目.
     */
    public void setCount(int count) {
        if (count != this.count) {
            this.count = count;
            requestLayout();
        }
    }

    /**
     * 如果想和 ViewPager 绑定起来，直接调用这个接口即可.
     *
     * @param viewPager viewpager.
     */
    public void attachToViewPager(ViewPager viewPager, Callback callback) {
        this.callback = callback;
        if (viewPager == null) {
            return;
        }
        viewPager.addOnPageChangeListener(pageChangeListener);
        if (viewPager.getAdapter() != null) {
            setCount(viewPager.getAdapter().getCount());
        }
    }

    public interface Callback{
        void isLastListener(boolean isLastPos);
    }

}
