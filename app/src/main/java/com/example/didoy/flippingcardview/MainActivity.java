package com.example.didoy.flippingcardview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.didoy.flippingcardview.Util.AnimateUtil;
import com.example.didoy.flippingcardview.fragments.FlipActivity;
import com.example.didoy.flippingcardview.fragments.FragmentBackCard;
import com.example.didoy.flippingcardview.fragments.FragmentFrontCard;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.frame_container) FrameLayout  frameLayout;
    @BindView(R.id.frame_container2) FrameLayout  frameLayout2;

    private boolean isUpdated = false;
    private boolean flipback = false;

    private  AnimateUtil animateUtil = new AnimateUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

       getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new FragmentFrontCard()).commit();
       getSupportFragmentManager().beginTransaction().replace(R.id.frame_container2, new FragmentFrontCard()).commit();


//        getFragmentManager()
//                .beginTransaction()
//                .replace(R.id.frame_container, new FlipActivity.CardFrontFragment())
//                .addToBackStack("")
//                .commit();

    }

    @OnClick(R.id.frame_container) void flip(){

        animateUtil.cardViewFlipY(frameLayout, getSupportFragmentManager(), new FragmentFrontCard(), new FragmentBackCard(), 1000);
       // flipCardViewByXML();

    }
    @OnClick(R.id.frame_container2) void flip2(){
        animateUtil.cardViewFlipX(frameLayout2, getSupportFragmentManager(), new FragmentFrontCard(), new FragmentBackCard(), 1000);

    }
    private void flipCardViewByXML(){
        if (flipback) {
            getFragmentManager().popBackStack();
            flipback = false;
            return;
        }
        flipback = true;
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.card_flip_right_in,
                        R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in,
                        R.animator.card_flip_left_out)

                .replace(R.id.frame_container, new FlipActivity.CardBackFragment())
                .addToBackStack(null)
                .commit();

    }

    }


