package com.example.didoy.flippingcardview.Util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;

import com.example.didoy.flippingcardview.fragments.FlipActivity;
import com.example.didoy.flippingcardview.fragments.FragmentBackCard;
import com.example.didoy.flippingcardview.fragments.FragmentFrontCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Didoy on 9/25/2016.
 */
public class AnimateUtil {

    private static float origDegree = 0;
    private static float ToDegree = 0;
    private static ObjectAnimator animation;
    private static List<CardFlipper> list = new ArrayList<CardFlipper>();


    public  ObjectAnimator rotateX(View v, float toDegrees, long durationMillis, Animator.AnimatorListener listener, boolean reversable) {

        origDegree = v.getRotationX();

        if (reversable) {
            reverseDegree(toDegrees);

            return ofFloat(v, "rotationX", origDegree, ToDegree, durationMillis, listener);
        } else {
            return ofFloat(v, "rotationX", 0.0f, toDegrees, durationMillis, listener);
        }
    }

    public  ObjectAnimator rotateY(View v, float toDegrees, long durationMillis, Animator.AnimatorListener listener, boolean reversable) {
        origDegree = v.getRotationY();

        if (reversable) {
            reverseDegree(toDegrees);

            return ofFloat(v, "rotationY", origDegree, ToDegree, durationMillis, listener);
        } else {
            return ofFloat(v, "rotationY", 0.0f, toDegrees, durationMillis, listener);
        }
    }

    public  ObjectAnimator fadeIn(View v, long durationMillis, Animator.AnimatorListener listener) {
        return ofFloat(v, "alpha", 0.0F, 1.0F, durationMillis, listener);
    }

    public  ObjectAnimator fadeOut(View v, long durationMillis, Animator.AnimatorListener listener) {
        return ofFloat(v, "alpha", 1.0F, 0.0F, durationMillis, listener);
    }

    public  ObjectAnimator rotate(View v, float toDegrees, long durationMillis, Animator.AnimatorListener listener, boolean reversable) {
        origDegree = v.getRotation();

        if (reversable) {
            reverseDegree(toDegrees);

            return ofFloat(v, "rotation", origDegree, ToDegree, durationMillis, listener);
        } else {
            return ofFloat(v, "rotation", 0.0f, toDegrees, durationMillis, listener);
        }
    }

    /**
     */
    public  void zoomIn(View v, float SizeX, float SizeY, long durationMillis, Animator.AnimatorListener listener) {
        scaleWidth(v, 0, SizeX, durationMillis, null);
        scaleHeight(v, 0, SizeY, durationMillis, null);
    }

    public  void zoomOut(View v, float SizeX, float SizeY, long durationMillis, Animator.AnimatorListener listener) {
        scaleWidth(v, SizeX, 1, durationMillis, null);
        scaleHeight(v, SizeY, 1, durationMillis, null);
    }

    public  ObjectAnimator scaleWidth(View v, float fromWidth, float toWidth, long durationMillis, Animator.AnimatorListener listener) {
        return ofFloat(v, "scaleX", fromWidth, toWidth, durationMillis, listener);
    }

    public  ObjectAnimator scaleHeight(View v, float fromHeight, float toHeight, long durationMillis, Animator.AnimatorListener listener) {
        return ofFloat(v, "scaleY", fromHeight, toHeight, durationMillis, listener);
    }

    public  ObjectAnimator moveY(View v, float fromY, float toY, long durationMillis, Animator.AnimatorListener listener) {
        return ofFloat(v, "translationY", fromY, toY, durationMillis, listener);
    }

    public  ObjectAnimator moveX(View v, float fromX, float toX, long durationMillis, Animator.AnimatorListener listener) {
        return ofFloat(v, "translationX", fromX, toX, durationMillis, listener);
    }

    public  ObjectAnimator moveYBounce(View v, float fromY, float toY, long durationMillis, Animator.AnimatorListener listener) {
        ObjectAnimator animation = ofFloat(v, "translationY", fromY, toY, durationMillis, listener);
        animation.setInterpolator(new BounceInterpolator());
        return animation;
    }

    public  ObjectAnimator moveXBounce(View v, float fromX, float toX, long durationMillis, Animator.AnimatorListener listener) {
        ObjectAnimator animation = ofFloat(v, "translationX", fromX, toX, durationMillis, listener);
        animation.setInterpolator(new BounceInterpolator());
        return animation;
    }


    public  void cardViewFlipY(View container, final FragmentManager fragmentManager, final Fragment front, final Fragment back, long duration){
        synchronized (this){
            CardFlipper cardFlipper = getCardFlipper(container.getId());
            cardFlipper.cardViewFlip(container, fragmentManager, front, back, duration, "Y");
        }
    }

    public  void cardViewFlipX(View container, final FragmentManager fragmentManager, final Fragment front, final Fragment back, long duration){
        synchronized (this) {

            CardFlipper cardFlipper = getCardFlipper(container.getId());
            cardFlipper.cardViewFlip(container, fragmentManager, front, back, duration, "X");
        }
    }

    public static ObjectAnimator ofFloat(View v, String propertyName, float from, float to, long durationMillis, Animator.AnimatorListener listener) {
        animation = ObjectAnimator.ofFloat(v, propertyName, from, to);
        animation.setDuration(durationMillis);
        v.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        animation.setInterpolator(new LinearInterpolator());
        if (listener != null) animation.addListener(listener);
        animation.start();

        return animation;
    }

    private static void reverseDegree(float toDegree) {
        if (origDegree != 0) {
            ToDegree = 0;
        } else {
            ToDegree = toDegree;
        }
    }


    public  void setUpdateListener(ValueAnimator.AnimatorUpdateListener updateListener) {
        animation.addUpdateListener(updateListener);

    }

    private CardFlipper getCardFlipper(int id){
        CardFlipper cardFlipper = null;

            for (CardFlipper card : list){  // check if cardflipper is already existing

                if (card.getID()  == id ){
                    cardFlipper = card;
                }
            }


        if (cardFlipper == null){
            cardFlipper = new CardFlipper(id); // initialize new CardFlipper  object
            list.add(cardFlipper);
        }


        return cardFlipper;
    }

    //====================== CARDFLIPPER CLASS ======================//

    public class CardFlipper {

        /**
         * RotateY the cardview
         * @param  container the framelayout to be used at runtime
         * @param  fragmentManager fragment manager for transactions
         * @param  front fragment for the front of the cardview
         * @param  back fragment for back of the cardview
         * @param duration  how long the animation
         *
         */

        private  int     containerID ;
        private  boolean isUpdated = false;
        private  boolean flipback = false;
        private  boolean editable = true;


        public  CardFlipper(int containerID){
            this.containerID = containerID;
        }

        public  int getID(){
            return containerID;
        }

        public  void cardViewFlip(final View container, final FragmentManager fragmentManager, final Fragment front, final Fragment back, long duration, String XorY) {
            final int id = container.getId();

            System.out.println(list.size());
            Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    isUpdated = false;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isUpdated = true;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            };


            if (XorY.equals("Y")){
                rotateY(container, 180, duration, animatorListener, true);   // rotate the container

            }

            if (XorY.equals("X")){
                rotateX(container, 180, duration, animatorListener, true);    // rotate the container
            }

            setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                        synchronized (this) {
                            if (!isUpdated) {
                                    if (animation.getCurrentPlayTime() >= animation.getDuration() / 2) {
                                        if (flipback) {
                                            fragmentManager.beginTransaction().replace(id, front).commit();
                                            fragmentManager.executePendingTransactions();
                                            System.out.println("FRONT " + container.getId()  + " Class ID" + getID());
                                            flipback = false;
                                        } else {
                                            fragmentManager.beginTransaction().replace(id, back).commit();
                                            fragmentManager.executePendingTransactions();
                                            System.out.println("Back " + container.getId() + " Class ID" + getID());
                                            flipback = true;
                                        }
                                        isUpdated = true;
                                    }
                            }
                        }

                    }

            });

        }

    }

}

