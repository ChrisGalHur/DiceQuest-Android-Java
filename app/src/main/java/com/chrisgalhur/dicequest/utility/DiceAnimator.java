package com.chrisgalhur.dicequest.utility;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

public class DiceAnimator {

    //region ATTRIBUTES
    private static final int DURATION = 300;
    private final View viewToAnimate;
    private final int screenWidth;
    private final int screenHeight;
    private final float initXPosition;
    private final float initYPosition;
    private final LinearLayout linLayMain;
    //endregion ATTRIBUTES

    //region CONSTRUCTOR
    public DiceAnimator(View viewToAnimate, int screenWidth, int screenHeight, float initXPosition, float initYPosition, LinearLayout linLayMain) {
        this.viewToAnimate = viewToAnimate;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.initXPosition = initXPosition;
        this.initYPosition = initYPosition;
        this.linLayMain =linLayMain;
    }
    //endregion CONSTRUCTOR

    //region ROLL MAIN
    public void rollMain() {
        rollToCenterRight();
    }

    private void rollToCenterRight() {

        //Obtenemos la posición actual de la imagen
        float finalXRight = (float) screenWidth - (float) viewToAnimate.getWidth();
        float finalYRight = (float) screenHeight / 2 - (float) viewToAnimate.getHeight() / 2;

        AnimatorSet animatorSet = createRollAnimator(finalXRight, finalYRight, DURATION);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                rollToCenterTop();
            }
        });
        //Utilizar interpolador para que la animación sea más suave
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());

        animatorSet.start();
    }

    private void rollToCenterTop() {

        //Obtenemos la posición actual de la imagen
        float finalXTop = (float) screenWidth / 2 - (float) viewToAnimate.getWidth() / 2;
        float finalYTop = 0;

        AnimatorSet animatorSet = createRollAnimator(finalXTop, finalYTop, DURATION);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                rollToCenterLeft();
            }
        });
        //Utilizar interpolador para que la animación sea más suave
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());

        animatorSet.start();
    }

    private void rollToCenterLeft() {

            //Obtenemos la posición actual de la imagen
            float finalXLeft = 0;
            float finalYLeft = (float) screenHeight / 2 - (float) viewToAnimate.getHeight() / 2;

            AnimatorSet animatorSet = createRollAnimator(finalXLeft, finalYLeft, DURATION);
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    backToInitialPosition();
                }
            });
            //Utilizar interpolador para que la animación sea más suave
            animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());

            animatorSet.start();
    }

    private void backToInitialPosition() {

            AnimatorSet animatorSet = createRollAnimator(initXPosition, initYPosition, DURATION);
            animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());

            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    //Activar el layout para que se pueda hacer click
                    linLayMain.setEnabled(true);
                }
            });
            animatorSet.start();
    }

    private AnimatorSet createRollAnimator(float finalXRight, float finalYRight, long duration) {

        ObjectAnimator animatorY = ObjectAnimator.ofFloat(viewToAnimate, "y", viewToAnimate.getY(), finalYRight);
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(viewToAnimate, "x", viewToAnimate.getX(), finalXRight);
        ObjectAnimator animatorRotation = ObjectAnimator.ofFloat(viewToAnimate, "rotation", 0f, 360f);

        //Creamos AnimatorSet para ejecutar ambos animadores al mismo tiempo
        AnimatorSet rollAnimator = new AnimatorSet();
        rollAnimator.playTogether(animatorY, animatorRotation, animatorX);
        rollAnimator.setDuration(duration);

        return rollAnimator;
    }
    //endregion ROLL MAIN
}
