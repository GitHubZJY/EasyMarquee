package com.zjy.marqueelib;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import java.util.List;

/**
 * Date: 2020/12/25
 * Author: Yang
 * Describe: The effect of running lantern that can be flipped vertically and horizontally
 */
public class EasyMarqueeView extends ViewFlipper {

    private MarqueeAdapter<?> marqueeAdapter;
    /**
     * anim duration of every flipping
     */
    private int mDuration;
    /**
     * stay duration of every page
     */
    private int mFlipInterval;
    /**
     * flip orientation {@link MarqueeOrientation}
     */
    private int mOrientation;
    /**
     * setting auto flip, if setting true, will auto flip after setData
     */
    private boolean mAutoFlip;
    /**
     * marquee listener
     */
    private MarqueeListener mListener;

    @SuppressWarnings("rawtypes")
    private DataObserver dataObserver = new DataObserver() {
        @Override
        public void onDataChange() {
            notifyChange();
        }

        @Override
        public void onItemChange(int position, Object data) {

        }

        @Override
        public void onItemInsert(int position, Object data) {

        }
    };

    public EasyMarqueeView(Context context) {
        this(context, null);
    }

    public EasyMarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        handleStyleable(context, attrs);
        initialize();
    }

    private void handleStyleable(Context context, AttributeSet attrs) {
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.EasyMarqueeView, 0, 0);
        try {
            mDuration = ta.getInteger(R.styleable.EasyMarqueeView_marquee_duration, 500);
            mFlipInterval = ta.getInteger(R.styleable.EasyMarqueeView_marquee_flip_interval, 5000);
            mOrientation = ta.getInteger(R.styleable.EasyMarqueeView_marquee_orientation, 0);
            mAutoFlip = ta.getBoolean(R.styleable.EasyMarqueeView_marquee_auto_flip, true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ta.recycle();
        }
    }

    private void initialize() {
        switch (mOrientation) {
            case MarqueeOrientation.BOTTOM_TOP_TOP:
                loadAnim(R.anim.bottom_top_enter_animation, R.anim.bottom_top_exit_animation);
                break;
            case MarqueeOrientation.TOP_TO_BOTTOM:
                loadAnim(R.anim.top_bottom_enter_animation, R.anim.top_bottom_exit_animation);
                break;
            case MarqueeOrientation.LEFT_TO_RIGHT:
                loadAnim(R.anim.left_right_enter_animation, R.anim.left_right_exit_animation);
                break;
            case MarqueeOrientation.RIGHT_TO_LEFT:
                loadAnim(R.anim.right_left_enter_animation, R.anim.right_left_exit_animation);
                break;
            default:
                break;
        }

        setFlipInterval(mFlipInterval + mDuration);

        Animation outAnimation = getOutAnimation();
        if (outAnimation != null) {
            outAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (mListener != null) {
                        mListener.onMarquee(getCurrentItem());
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }

    public void setMarqueeAdapter(MarqueeAdapter<?> adapter) {
        marqueeAdapter = adapter;
        marqueeAdapter.registerDataSetObserver(dataObserver);
    }

    public void startFlip() {
        if (!isFlipping()) {
            startFlipping();
        }
    }

    public void stopFlip() {
        if (isFlipping()) {
            stopFlipping();
        }
    }

    public int getCurrentItem() {
        assertAdapterNull();
        return marqueeAdapter.getViewIndex(getCurrentView());
    }

    public void setMarqueeListener(MarqueeListener listener) {
        mListener = listener;
    }

    @Override
    public void setFlipInterval(int milliseconds) {
        super.setFlipInterval(milliseconds + mDuration);
    }

    private void notifyChange() {
        assertAdapterNull();
        List<View> children = marqueeAdapter.getChildViews();
        if (children != null && children.size() > 0) {
            removeAllViews();
            for (View view: children) {
                addView(view);
            }
            if (mAutoFlip) {
                startFlip();
            }
        }
    }

    private void assertAdapterNull() {
        if (marqueeAdapter == null) {
           throw new RuntimeException("adapter cannot be null !");
        }
    }

    private void loadAnim(int enterAnimResId, int exitAnimResId) {
        Animation enterAnimation = AnimationUtils.loadAnimation(getContext(), enterAnimResId);
        Animation exitAnimation = AnimationUtils.loadAnimation(getContext(), exitAnimResId);
        enterAnimation.setDuration(mDuration);
        exitAnimation.setDuration(mDuration);
        setInAnimation(enterAnimation);
        setOutAnimation(exitAnimation);
    }

}
