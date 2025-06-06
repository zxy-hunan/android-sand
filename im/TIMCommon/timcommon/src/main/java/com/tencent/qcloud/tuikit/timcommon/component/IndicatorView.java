package com.tencent.qcloud.tuikit.timcommon.component;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tencent.qcloud.tuikit.timcommon.R;
import com.tencent.qcloud.tuikit.timcommon.util.ScreenUtil;

import java.util.ArrayList;

public class IndicatorView extends LinearLayout {
    private Context mContext;
    private ArrayList<ImageView> mImageViews;
    private Bitmap bmpSelect;
    private Bitmap bmpNormal;
    private int mHeight = 6;
    private int mMaxHeight;
    private AnimatorSet mPlayByInAnimatorSet;
    private AnimatorSet mPlayByOutAnimatorSet;

    public IndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.setOrientation(HORIZONTAL);
        mMaxHeight = ScreenUtil.dip2px(mHeight);
        bmpSelect = BitmapFactory.decodeResource(getResources(), R.drawable.indicator_point_select);
        bmpNormal = BitmapFactory.decodeResource(getResources(), R.drawable.indicator_point_nomal);
    }

    public IndicatorView(Context context) {
        this(context, null);
    }

    public void init(int count) {
        mImageViews = new ArrayList<>();
        this.removeAllViews();
        for (int i = 0; i < count; i++) {
            LayoutParams params = new LinearLayout.LayoutParams(mMaxHeight, mMaxHeight);
            ImageView imageView = new ImageView(mContext);
            params.setMarginStart(12);
            params.setMarginEnd(12);
            if (i == 0) {
                imageView.setImageBitmap(bmpSelect);
            } else {
                imageView.setImageBitmap(bmpNormal);
            }
            this.addView(imageView, params);
            mImageViews.add(imageView);
        }
    }

    public void playBy(int startPosition, int nextPosition) {
        final boolean isShowInAnimOnly = false;
        if (startPosition < 0 || nextPosition < 0 || nextPosition == startPosition) {
            startPosition = nextPosition = 0;
        }
        if (mImageViews == null || mImageViews.isEmpty()) {
            return;
        }
        if (startPosition >= mImageViews.size() || nextPosition >= mImageViews.size()) {
            return;
        }

        final ImageView imageViewStrat = mImageViews.get(startPosition);
        final ImageView imageViewNext = mImageViews.get(nextPosition);

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(imageViewStrat, "scaleX", 1.0f, 0.25f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(imageViewStrat, "scaleY", 1.0f, 0.25f);

        if (mPlayByOutAnimatorSet != null && mPlayByOutAnimatorSet.isRunning()) {
            mPlayByOutAnimatorSet.cancel();
            mPlayByOutAnimatorSet = null;
        }
        mPlayByOutAnimatorSet = new AnimatorSet();
        mPlayByOutAnimatorSet.play(anim1).with(anim2);
        mPlayByOutAnimatorSet.setDuration(100);

        ObjectAnimator animIn1 = ObjectAnimator.ofFloat(imageViewNext, "scaleX", 0.25f, 1.0f);
        ObjectAnimator animIn2 = ObjectAnimator.ofFloat(imageViewNext, "scaleY", 0.25f, 1.0f);

        if (mPlayByInAnimatorSet != null && mPlayByInAnimatorSet.isRunning()) {
            mPlayByInAnimatorSet.cancel();
            mPlayByInAnimatorSet = null;
        }
        mPlayByInAnimatorSet = new AnimatorSet();
        mPlayByInAnimatorSet.play(animIn1).with(animIn2);
        mPlayByInAnimatorSet.setDuration(100);

        if (isShowInAnimOnly) {
            mPlayByInAnimatorSet.start();
            return;
        }

        anim1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                imageViewStrat.setImageBitmap(bmpNormal);
                ObjectAnimator animFil1l = ObjectAnimator.ofFloat(imageViewStrat, "scaleX", 1.0f);
                ObjectAnimator animFill2 = ObjectAnimator.ofFloat(imageViewStrat, "scaleY", 1.0f);
                AnimatorSet mFillAnimatorSet = new AnimatorSet();
                mFillAnimatorSet.play(animFil1l).with(animFill2);
                mFillAnimatorSet.start();
                imageViewNext.setImageBitmap(bmpSelect);
                mPlayByInAnimatorSet.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
        mPlayByOutAnimatorSet.start();
    }
}
