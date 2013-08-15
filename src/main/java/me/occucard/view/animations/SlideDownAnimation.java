package me.occucard.view.animations;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Shane on 8/7/13.
 */
public class SlideDownAnimation extends Animation {
    View view;
    ViewGroup.LayoutParams original;
    int height;

    public SlideDownAnimation(View view){
        this.view = view;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        this.view.setVisibility(View.VISIBLE);
        this.original = this.view.getLayoutParams();
        if(original != null){
            this.setAnimationListener(new SlideDownCallback());
            this.view.measure(this.original.width, this.original.height);
            this.height = view.getMeasuredHeight();
        }
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        Log.d("Interpoll", interpolatedTime + "");
        int newHeight = interpolatedTime == 1.0f ? original.height : (int) (this.height * interpolatedTime);
        Log.d("New Height", newHeight + "");
        this.view.getLayoutParams().height = newHeight;
        this.view.requestLayout();
    }

    private class SlideDownCallback implements AnimationListener{
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            view.getLayoutParams().height = original.height;
            view.requestLayout();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}

