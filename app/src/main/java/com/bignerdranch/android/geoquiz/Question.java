package com.bignerdranch.android.geoquiz;

/**
 * Created by Akash on 05-12-2017.
 */

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;

    public Question(int textResId, boolean answerTrue)
    {
        mTextResId=textResId;

        mAnswerTrue=answerTrue;
    }
    public int getTextResId() {
        return mTextResId;
    }


    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }


}

