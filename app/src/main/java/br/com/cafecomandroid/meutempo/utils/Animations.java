package br.com.cafecomandroid.meutempo.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.View;

/**
 * Created by Lucas Marciano on 21/02/2017.
 * MeuTempo
 * br.com.cafecomandroid.meutempo.utils
 */

public class Animations {
    private int mTimeAnimation;
    private Context context;

    public Animations(Context context) {
        this.context = context;
    }

    public void crossfade(View main, View load){
        mTimeAnimation = context.getResources().getInteger(android.R.integer.config_mediumAnimTime);

        main.setAlpha(0f);
        main.setVisibility(View.VISIBLE);

        main.animate()
                .alpha(1f)
                .setDuration(mTimeAnimation)
                .setListener(null);

        load.animate()
                .alpha(0f)
                .setDuration(mTimeAnimation)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        load.setVisibility(View.GONE);
                    }
                });
    }
}
