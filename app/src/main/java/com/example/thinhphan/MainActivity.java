package com.example.thinhphan;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener {
    private Animation animRotate1;

    private TextView tv;
    private ImageView myView;
    private ImageView myView1;
    private ImageView myView2;
    private ImageView myView3;
    private ImageView resetView;
    private int cash = CONSTANTS.STARTUP_CASH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView)findViewById(R.id.textView1);
        myView = (ImageView)findViewById(R.id.imageView);
        myView1 = (ImageView)findViewById(R.id.imageView1);
        myView2 = (ImageView)findViewById(R.id.imageView2);
        myView3 = (ImageView)findViewById(R.id.imageView3);
        resetView = (ImageView)findViewById(R.id.imageView5);
        String text = "$" + cash;
        tv.setText(text);
        resetView.setVisibility(View.GONE);
        
        animRotate1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.rotate);
        animRotate1.setAnimationListener(this);
    }

    public void doClick(View view) {
        resetView.setVisibility(View.VISIBLE);
        animateIt();
    }

    public void doReset(View view){
        if (animRotate1.hasEnded()) { // avoid reset while animation is running
            cash = CONSTANTS.STARTUP_CASH;
            setImage(R.drawable.f1);
            String startupCash = "$" + CONSTANTS.STARTUP_CASH;
            tv.setText(startupCash);
            myView.setVisibility(View.VISIBLE);
            resetView.setVisibility(View.GONE);
        }
    }

    private int processCash(int n1, int n2, int n3){
        if ((n1 == n2) && (n2 == n3))
            return CONSTANTS.MATCH_3;
        else if ( (n1 != n2) && (n2 != n3) && (n1 != n3))
            return 0;
        else
            return CONSTANTS.MATCH_2;
    }

    private void randomFlower() {
        Random random = new Random();
        int n1 = random.nextInt(CONSTANTS.NUMB_FLOWERS);
        int n2 = random.nextInt(CONSTANTS.NUMB_FLOWERS);
        int n3 = random.nextInt(CONSTANTS.NUMB_FLOWERS);

        randomImage(myView1, n1);
        randomImage(myView2, n2);
        randomImage(myView3, n3);

        cash += processCash(n1, n2, n3);
        String text = "$" + cash;
        tv.setText(text);
        if (cash <= CONSTANTS.YOUR_BROKE)
            myView.setVisibility(View.GONE);
    }

    private void animateIt(){
        myView1.startAnimation(animRotate1);
        myView2.startAnimation(animRotate1);
        myView3.startAnimation(animRotate1);
    }

    private void randomImage(ImageView img, int random){
        if (random == 0)
            img.setImageResource(R.drawable.f1);
        else if (random == 1)
            img.setImageResource(R.drawable.f2);
        else
            img.setImageResource(R.drawable.f3);
    }

    private void setImage(int imgId){      // setImage on the reset state and when animation starts
        myView1.setImageResource(imgId);
        myView2.setImageResource(imgId);
        myView3.setImageResource(imgId);
    }
    @Override
    public void onAnimationStart(Animation animation) {
        setImage(R.drawable.tmp);
        cash -= CONSTANTS.COST_PER_ROLL;
        String text = "$" + cash;
        tv.setText(text);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        randomFlower();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}