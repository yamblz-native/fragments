package ru.yandex.yamblz.ui.other;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.transition.ChangeBounds;
import android.transition.ChangeTransform;
import android.transition.TransitionSet;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by Volha on 08.08.2016.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class TransitionAnimation extends TransitionSet {
    public TransitionAnimation() {
        init();
    }

    /**
     * This constructor allows us to use this transition in XML
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TransitionAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void init() {
        setOrdering(ORDERING_TOGETHER);
//        setInterpolator(new AccelerateInterpolator());
//        setDuration(500);
        addTransition(new ChangeBounds()).
        addTransition(new ChangeTransform());
    }
}