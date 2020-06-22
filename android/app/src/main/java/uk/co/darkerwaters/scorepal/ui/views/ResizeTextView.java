package uk.co.darkerwaters.scorepal.ui.views;

import android.content.Context;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

public class ResizeTextView extends AppCompatTextView {

    private final float originalTextSize;
    private final String referenceText;

    public ResizeTextView(Context context, String referenceText) {
        this(context, referenceText, null);
    }

    public ResizeTextView(Context context, AttributeSet attrs) {
        this(context, "AZ", attrs);
    }

    public ResizeTextView(Context context, String referenceText, AttributeSet attrs) {
        super(context, attrs);
        this.referenceText = referenceText;
        this.originalTextSize = getTextSize();
    }

    private void refitText(int textWidth) {
        if (textWidth > 0) {
            float availableWidth = textWidth - this.getPaddingLeft() - this.getPaddingRight();
            if (availableWidth <= 0)
                return;
            // setup the original size to measure our string
            TextPaint tp = getPaint();
            setTextSize(TypedValue.COMPLEX_UNIT_PX, this.originalTextSize);
            // and get the bounds of the text
            float ratio = availableWidth / tp.measureText(this.referenceText);
            if (ratio > 0f) {
                setTextSize(TypedValue.COMPLEX_UNIT_PX, Math.max(this.originalTextSize, this.originalTextSize * ratio * 0.9f));
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int parentWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = View.MeasureSpec.getSize(heightMeasureSpec);
        refitText(parentWidth);
        this.setMeasuredDimension(parentWidth, parentHeight);
    }

    @Override
    protected void onTextChanged(final CharSequence text, final int start,
                                 final int before, final int after) {
        refitText(this.getWidth());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw) {
            refitText(w);
        }
    }
}