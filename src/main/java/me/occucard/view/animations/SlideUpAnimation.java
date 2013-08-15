package me.occucard.view.animations;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Shane on 8/7/13.
 */
public class SlideUpAnimation extends Animation {
    View view;
    ViewGroup.LayoutParams original;
    int height;

    public SlideUpAnimation(View view){
        this.view = view;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        this.view.setVisibility(View.GONE);
        this.original = this.view.getLayoutParams();
        this.setAnimationListener(new SlideUpCallback());
        if(original != null){
            this.view.measure(this.view.getLayoutParams().width, this.view.getLayoutParams().height);
            this.height = view.getMeasuredHeight();
        }
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int newHeight = interpolatedTime == 1.0 ? 0 : (int) (this.height * (1 - interpolatedTime));
        Log.d("New Height", newHeight + "");
        this.view.getLayoutParams().height = newHeight;
        this.view.requestLayout();
    }

    private class SlideUpCallback implements AnimationListener{
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            view.getLayoutParams().height = 0;
            view.requestLayout();
            view.getLayoutParams().height = original.height;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}