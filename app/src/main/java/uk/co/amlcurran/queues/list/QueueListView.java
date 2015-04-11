package uk.co.amlcurran.queues.list;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import uk.co.amlcurran.queues.R;
import uk.co.amlcurran.queues.core.QueueItem;

public class QueueListView extends View {

    private final TextPaint paintFirstItem;
    private CharSequence firstItemText = "No items";

    public QueueListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paintFirstItem = new TextPaint();
        initFirstItemPaint();
    }

    public QueueListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paintFirstItem = new TextPaint();
        initFirstItemPaint();
    }

    private void initFirstItemPaint() {
        paintFirstItem.setTextSize(getResources().getDimension(R.dimen.qlv_first_item_text_size));
        paintFirstItem.setColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = resolveSizeAndState(getSuggestedMinimumWidth(), widthMeasureSpec, 1);
        int height = resolveSizeAndState(getResources().getDimensionPixelSize(R.dimen.queue_list_view_min_height), heightMeasureSpec, 1);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.RED);
        canvas.drawText(firstItemText, 0, firstItemText.length(), 0, paintFirstItem.getTextSize(), paintFirstItem);
    }

    public void setFirstItem(QueueItem queueItem) {
        if (queueItem == null) {
            firstItemText = "No items";
        } else {
            firstItemText = queueItem.getLabel();
        }
        invalidate();
    }
}
