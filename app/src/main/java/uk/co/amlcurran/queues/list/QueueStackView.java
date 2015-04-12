package uk.co.amlcurran.queues.list;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import uk.co.amlcurran.queues.R;
import uk.co.amlcurran.queues.core.Queue;

public class QueueStackView extends View {

    private final Paint queueStackFirstPaint;
    private final Paint queueStackPaint;
    private final RectF drawRect;
    private final int stackRectHeight;
    private final int stackPadding;
    private final int stackItemQuantity;
    private final int stackItemColor;
    private final TextPaint textPaint;
    private final float rectRadius;
    private int queueSize;
    private CharSequence firstText;

    public QueueStackView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QueueStackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        queueStackFirstPaint = new Paint();
        queueStackPaint = new Paint();
        textPaint = new TextPaint();
        drawRect = new RectF();

        rectRadius = context.getResources().getDimension(R.dimen.qsv_rect_radius);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QueueStackView);
        stackRectHeight = array.getDimensionPixelSize(R.styleable.QueueStackView_stackItemHeight, getResources().getDimensionPixelSize(R.dimen.qsv_stack_height));
        stackPadding = array.getDimensionPixelSize(R.styleable.QueueStackView_stackPadding, getResources().getDimensionPixelSize(R.dimen.qsv_stack_padding));
        stackItemQuantity = array.getInteger(R.styleable.QueueStackView_stackItemQuantity, 4);
        stackItemColor = array.getColor(R.styleable.QueueStackView_stackItemColor, Color.BLUE);
        initTextPaint();
        initStackPaint();
        array.recycle();
    }

    private void initTextPaint() {
        textPaint.setTextSize(getResources().getDimension(R.dimen.qlv_first_item_text_size));
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
    }

    private void initStackPaint() {
        queueStackFirstPaint.setColor(stackItemColor);
        queueStackFirstPaint.setAntiAlias(true);
        queueStackPaint.set(queueStackFirstPaint);
        queueStackPaint.setAlpha(153);
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
        int maxStackSize = Math.min(queueSize, stackItemQuantity);
        if (maxStackSize > 0) {
            updateStackItemRect(0, stackPadding);
            canvas.drawRoundRect(drawRect, rectRadius, rectRadius, queueStackFirstPaint);
        }
        for (int i = 1; i < maxStackSize; i++) {
            updateStackItemRect(i, stackPadding * 4);
            canvas.drawRoundRect(drawRect, rectRadius, rectRadius, queueStackPaint);
        }
        drawFirstSummary(canvas);
    }

    private void drawFirstSummary(Canvas canvas) {
        if (firstText != null) {
            updateStackItemRect(0, stackPadding);
            float textLength = textPaint.measureText(firstText, 0, firstText.length());
            int clippedWidth = (int) Math.min(textLength, drawRect.width() - 2 * stackPadding);
            StaticLayout layout = new StaticLayout(firstText, 0, firstText.length(), textPaint, (int) textLength,
                    Layout.Alignment.ALIGN_NORMAL, 1, 0, true, TextUtils.TruncateAt.END, clippedWidth);
            float centerInRectYOffset = (drawRect.height() - layout.getHeight()) / 2f;
            float centerInRectXOffset = (drawRect.width() - clippedWidth) / 2f;

            float textX = drawRect.left + centerInRectXOffset;
            float textY = drawRect.top + centerInRectYOffset;

            // Draw
            canvas.save();
            canvas.translate(textX, textY);
            layout.draw(canvas);
            canvas.restore();
        }
    }

    private void updateStackItemRect(int position, int stackRectOffset) {
        int bottom = getHeight() - stackPadding - position * (stackRectHeight + stackPadding);
        int top = bottom - stackRectHeight;
        drawRect.set(stackRectOffset, top, getWidth() - stackRectOffset, bottom);
    }

    public void setSize(Queue queue) {
        this.queueSize = queue.size();
        this.firstText = queue.firstItemSummary();
        invalidate();
    }
}
