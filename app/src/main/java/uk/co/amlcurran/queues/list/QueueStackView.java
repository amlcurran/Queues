package uk.co.amlcurran.queues.list;

import android.content.Context;
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
    private int queueSize;

    public QueueStackView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QueueStackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        queueStackPaint = new Paint();
        drawRect = new Rect();
        stackRectHeight = getResources().getDimensionPixelSize(R.dimen.qsv_stack_height);
        stackPadding = getResources().getDimensionPixelSize(R.dimen.qsv_stack_padding);
        initStackPaint();
    }

    private void initStackPaint() {
        queueStackPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = resolveSizeAndState(getSuggestedMinimumWidth(), widthMeasureSpec, 1);
        int minHeight = stackPadding * 5 + stackRectHeight * 4;
        int height = resolveSizeAndState(minHeight, heightMeasureSpec, 1);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.RED);
        for (int i = 0; i < queueSize; i++) {
            int bottom = canvas.getHeight() - stackPadding - i * (stackRectHeight + stackPadding);
            int top = bottom - stackRectHeight;
            drawRect.set(stackPadding, top, canvas.getWidth() - stackPadding, bottom);
            canvas.drawRect(drawRect, queueStackPaint);
        }
    }

    public void setSize(int size) {
        this.queueSize = size;
        invalidate();
    }
}
