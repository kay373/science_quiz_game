package com.example.andriod.biologyquiz;

import android.graphics.drawable.Drawable;

/**
 * Created by karapet on 3/29/2018.
 */

public class question {

    Drawable image;
    String question;
    String a_Answer;
    String b_Answer;
    String c_Answer;
    int answerChoiceChar;
    boolean chosen;




    public question(Drawable image, String question, String a_Answer, String b_Answer, String c_Answer, int answerChoiceChar, boolean chosen) {
        this.image = image;
        this.question = question;
        this.a_Answer = a_Answer;
        this.b_Answer = b_Answer;
        this.c_Answer = c_Answer;
        this.answerChoiceChar = answerChoiceChar;
        this.chosen=chosen;
    }

    public Drawable getImage() {
        return image;
    }

    public String getQuestion() {
        return question;
    }

    public String get_a_Answer() {
        return a_Answer;
    }

    public String get_b_Answer() {
        return b_Answer;
    }

    public String get_c_Answer() {
        return c_Answer;
    }

    public int getAnswerChoiceChar() {
        return answerChoiceChar;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void set_a_Answer(String a_Answer) {
        this.a_Answer = a_Answer;
    }

    public void set_b_Answer(String b_Answer) {
        this.b_Answer = b_Answer;
    }

    public void set_c_Answer(String c_Answer) {
        this.c_Answer = c_Answer;
    }

    public void setAnswerChoiceChar(int answerChoiceChar) {
        this.answerChoiceChar = answerChoiceChar;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }


}
