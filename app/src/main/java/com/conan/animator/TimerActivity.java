package com.conan.animator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Toast;

public class TimerActivity extends AppCompatActivity {

    com.conan.animator.TimerBinding binding;

    public boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_timer);


        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimator();
                startThread();
            }
        });


        binding.imageCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ObjectAnimator animatorCenter = ObjectAnimator.ofFloat(
                        binding.imageCenter,
                        "alpha",
                        isOpen?0.5F:1F,
                        isOpen?1F:0.5F);

                ObjectAnimator animatorUp = ObjectAnimator.ofFloat(
                        binding.imageUp,
                        "translationY",
                        isOpen?0:-200F);

                ObjectAnimator animatorDown = ObjectAnimator.ofFloat(
                        binding.imageDown,
                        "translationY",
                        isOpen?0:200F);

                ObjectAnimator animatorStart = ObjectAnimator.ofFloat(
                        binding.imageStart,
                        "translationX",
                        isOpen?0:-200F);

                ObjectAnimator animatorEnd = ObjectAnimator.ofFloat(
                        binding.imageEnd,
                        "translationX",
                        isOpen?0:200F);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(500);
                animatorSet.setInterpolator(new BounceInterpolator());
                animatorSet.playTogether(animatorCenter, animatorUp, animatorDown, animatorStart, animatorEnd);

                animatorSet.start();

                isOpen = !isOpen;
            }
        });

        binding.imageDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TimerActivity.this, "imageDown", Toast.LENGTH_SHORT).show();
            }
        });

        binding.imageUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TimerActivity.this, "imageUp", Toast.LENGTH_SHORT).show();
            }
        });

        binding.imageStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TimerActivity.this, "imageStart", Toast.LENGTH_SHORT).show();
            }
        });

        binding.imageEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TimerActivity.this, "imageEnd", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 动画写法
    public void startAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                String text = "$ " + valueAnimator.getAnimatedValue();
                binding.textView.setText(text);
            }
        });
        valueAnimator.setDuration(3000);
        valueAnimator.start();
    }

    // 线程写法
    public void startThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long duration = 3000;
                int maxNumber = 100;
                int number = 0;
                long per = duration / maxNumber;

                while (number<=100) {
                    handler.sendEmptyMessage(number);
                    try {
                        Thread.sleep(per);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    number = number + 1;
                }
            }
        }).start();
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {

            String text = "$ " + message.what;
            binding.textView2.setText(text);

            return true;
        }
    });

}
