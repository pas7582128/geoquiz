package com.bignerdranch.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private Button mCheatButton;
    private boolean mIsCheater;
    private static final String TAG="QuizActivity";
    private static final String Key_Index="Index";
    private static final String Key_Array="Array";
    private static final String Key_answer="Ans";
    private static final int REQUEST_CODE_CHEAT=0;
    private TextView mQuestionTextView;
    private TextView mCheatRemainingTextView;
    private int counter=0,correct=0,incorrect=0,cheat=0;

    private Question[] mQuestionBank=new Question[]{
            new Question(R.string.question_india,
                    true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast,
                    false),
            new Question(R.string.question_africa,
                    false),
            new Question(R.string.question_americas,
                    true),
            new Question(R.string.question_asia, true)
    };
    private int a[]=new int[mQuestionBank.length];//a is for already answered
    private int v[]=new int[mQuestionBank.length];
    private boolean b[]=new boolean[mQuestionBank.length];
    private int mCurrentIndex=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"Oncreate bundle called");
        setContentView(R.layout.activity_quiz);
        if(savedInstanceState!=null){
            mCurrentIndex=savedInstanceState.getInt(Key_Index,0);
            boolean temp[]=savedInstanceState.getBooleanArray(Key_answer);//key answer stores boolean value true or false
            int res[]=savedInstanceState.getIntArray(Key_Array);//key array stores a
            for(int i=0;i<mQuestionBank.length;i++){
                mQuestionBank[mCurrentIndex].setAnswerTrue(temp[i]);
                a[i]=res[i];
            }
        }
        mQuestionTextView=(TextView)findViewById(R.id.question_text_view);
        mCheatRemainingTextView=(TextView)findViewById(R.id.cheat_remaining);
        mTrueButton=(Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        mFalseButton=(Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });
        mNextButton=(Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex=(mCurrentIndex+1)%mQuestionBank.length;
                mIsCheater=false;
                upDateQuestion();
            }
        });
        mPrevButton=(Button)findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentIndex==0){
                    mCurrentIndex=mQuestionBank.length-1;
                    mIsCheater=false;
                    upDateQuestion();
                }
                else{
                    mCurrentIndex=mCurrentIndex-1;
                    mIsCheater=false;
                    upDateQuestion();
                }
            }
        });
        mCheatButton=(Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent=new Intent(
                        QuizActivity.this,CheatActivity.class
                );*/
                    cheat++;
                   boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                   a[mCurrentIndex] = 2;
                   Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                   startActivityForResult(intent, REQUEST_CODE_CHEAT);
                   mIsCheater = true;



            }
        });
        upDateQuestion();

    }
    private void upDateQuestion(){
        int question=mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }
    private void checkAnswer(boolean userPressedTrue) {
        if (mIsCheater || a[mCurrentIndex]==2) {
            counter++;
            if(correct+incorrect!=mQuestionBank.length) {
                incorrect++;
            }
            int messageResId=R.string.judge_toast;
            Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
        }
        else{

            if (a[mCurrentIndex] == 0) {
                a[mCurrentIndex] = 1;
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                int messageResId = 0;
                counter++;
                if (userPressedTrue == answerIsTrue) {
                    correct++;
                    messageResId = R.string.correct_toast;

                } else {
                    incorrect++;
                    messageResId = R.string.incorrect_toast;

                }
                Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
            } else {
                int messageResId = R.string.que_toast;
                Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
            }
        }
        if (counter >= mQuestionBank.length) {
            Toast.makeText(this, "Result\nCorrect : " + correct + "\nIncorrect : " + incorrect, Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onStart() {

        super.onStart();
        Log.d(TAG, "onStart() called");
        if(cheat>=3)
            mCheatRemainingTextView.setText(R.string.cheat_count_toast);
        else{
            int a=3-cheat;
            mCheatRemainingTextView.setText(a+" cheat remaining");
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState");
        savedInstanceState.putInt(Key_Index,mCurrentIndex);
        savedInstanceState.putIntArray(Key_Array,a);
        for(int i=0;i<mQuestionBank.length;i++)
        {
            b[i]=mQuestionBank[mCurrentIndex].isAnswerTrue();
        }
        savedInstanceState.putBooleanArray(Key_answer,b);
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(resultCode== Activity.RESULT_OK){
            return;
        }
        if(requestCode==REQUEST_CODE_CHEAT){
            if(data==null) {
                return;
            }
            mIsCheater=CheatActivity.wasAnswerShown(data);
        }
    }
}
