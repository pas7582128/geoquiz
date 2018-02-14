package com.bignerdranch.android.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class
CheatActivity extends AppCompatActivity {

    private static final String ANSWER_IS_TRUE="com.bignerdranch.android.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN="com.bignerdranch.android.geoquiz.answer_shown";

    public static Intent newIntent(Context packageContext,boolean answerIsTrue){
        Intent intent=new Intent(packageContext,CheatActivity.class);
        intent.putExtra(ANSWER_IS_TRUE,answerIsTrue);
        return intent;
    }
    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);
    }
    private boolean mAnswerTrue;
    private TextView mAnswerTextView;
    private TextView mApiLevel;
    private Button mShowAnswerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mAnswerTrue=getIntent().getBooleanExtra(ANSWER_IS_TRUE,false);
        mAnswerTextView=(TextView)findViewById(R.id.answer_text_view);
        mShowAnswerButton=(Button)findViewById(R.id.show_answer_button);
        mApiLevel=(TextView)findViewById(R.id.api_level);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnswerTrue){
                    mAnswerTextView.setText(R.string.true_button);
                }
                else{
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShown(true);
                String android_os = getAndroidVersion(android.os.Build.VERSION.SDK_INT);
                mApiLevel.setText(android_os);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = mShowAnswerButton.getWidth() / 2;
                    int cy = mShowAnswerButton.getHeight() / 2;
                    float radius = mShowAnswerButton.getWidth();
                    Animator anim = ViewAnimationUtils
                            .createCircularReveal(mShowAnswerButton, cx, cy,
                                    radius, 0);
                    anim.addListener(new
                                             AnimatorListenerAdapter() {
                                                 @Override
                                                 public void onAnimationEnd(Animator
                                                                                    animation) {
                                                     super.onAnimationEnd(animation);
                                                     mShowAnswerButton.setVisibility(View.INVISIBLE);
                                                 }
                                             });
                    anim.start();
                }
                else {
                    mShowAnswerButton.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    private String getAndroidVersion(int sdk) {
        switch (sdk) {
            case 1: return "Android 1.0";
            case 2: return "Petit Four (Android 1.1)";
            case 3: return "Cupcake (Android 1.5)";
            case 4: return "Donut (Android 1.6)";
            case 5: return "Eclair (Android 2.0)";
            case 6: return "Eclair (Android 2.0.1)";
            case 7: return "Eclair (Android 2.1)";
            case 8: return "Froyo (Android 2.2)";
            case 9: return "Gingerbread (Android 2.3)";
            case 10: return "Gingerbread (Android 2.3.3)";
            case 11: return "Honeycomb (Android 3.0)";
            case 12: return "Honeycomb (Android 3.1)";
            case 13: return "Honeycomb (Android 3.2)";
            case 14: return "Ice Cream Sandwich (Android 4.0)";
            case 15: return "Ice Cream Sandwich (Android 4.0.3)";
            case 16: return "Jelly Bean (Android 4.1)";
            case 17: return "Jelly Bean (Android 4.2)";
            case 18: return "Jelly Bean (Android 4.3)";
            case 19: return "KitKat (Android 4.4)";
            case 20: return "KitKat Watch (Android 4.4)";
            case 21: return "Lollipop (Android 5.0)";
            case 22: return "Lollipop (Android 5.1)";
            case 23: return "Marshmallow (Android 6.0)";
            case 24: return "Nougat (Android 7.0)";
            case 25: return "Nougat (Android 7.1.1)";
            case 26: return "Oreo (Android 8.0)";
            default: return "hhhhh";
        }
    }
    private void setAnswerShown(boolean isAnswerShown){
        Intent data=new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown);
        setResult(RESULT_OK,data);
    }
}
