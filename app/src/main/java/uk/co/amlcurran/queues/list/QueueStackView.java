package uk.co.amlcurran.queues.list;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import uk.co.amlcurran.queues.R;

public class QueueStackView extends View {

    private final Paint queueStackPaint;
    private final Rect drawRect;
    private final int stackRectHeight;
    private final int stackPadding;
    private final int stackItemQuantity;
    private int queueSize;

    public QueueStackView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QueueStackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        queueStackPaint = new Paint();
        drawRect = new Rect();

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QueueStackView);
        stackRectHeight = array.getDimensionPixelSize(R.styleable.QueueStackView_stackItemHeight, getResources().getDimensionPixelSize(R.dimen.qsv_stack_height));
        stackPadding = array.getDimensionPixelSize(R.styleable.QueueStackView_stackPadding, getResources().getDimensionPixelSize(R.dimen.qsv_stack_padding));
        stackItemQuantity = array.getInteger(R.styleable.QueueStackView_stackItemQuantity, 4);
        initStackPaint();
        array.recycle();
    }

    private void initStackPaint() {
        queueStackPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = resolveSizeAndState(getSuggestedMinimumWidth(), widthMeasureSpec, 1);
        int minHeight = stackPadding * (stackItemQuantity + 1) + stackRectHeight * stackItemQuantity;
        int height = resolveSizeAndState(minHeight, heightMeasureSpec, 1);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.RED);
        for (int i = 0; i < queueSize; i++) {
            int bottom = canvas.getHeight() - stackPadding - i * (stackRectHeight + stackPadding);
            int top = bottom - stackRectHeight;
            drawRect.set(getStackRectOffset(i), top, canvas.getWidth() - getStackRectOffset(i), bottom);
            canvas.drawRect(drawRect, queueStackPaint);
        }
    }

    private int getStackRectOffset(int i) {
        return i == 0 ? stackPadding : stackPadding * 4;
    }

    public void setSize(int size) {
        this.queueSize = size;
        invalidate();
    }
}
