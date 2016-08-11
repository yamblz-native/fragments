package ru.yandex.yamblz.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.facebook.drawee.view.SimpleDraweeView;

import ru.yandex.yamblz.R;

/**
 * Extension of Fresco ImageView specifies square shape of an image.
 */
public class SquareDraweeView extends SimpleDraweeView {
    private final boolean useLargerSide;

    public SquareDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SquareDraweeView);
        useLargerSide = typedArray.getBoolean(R.styleable.SquareDraweeView_useLargerSide, true);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int maxSpec = (width > height) ? widthMeasureSpec : heightMeasureSpec;
        int minSpec = (width < height) ? widthMeasureSpec : heightMeasureSpec;

        int sideMeasureSpec = useLargerSide ? maxSpec : minSpec;
        super.onMeasure(sideMeasureSpec, sideMeasureSpec);
    }
}
