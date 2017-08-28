package com.example.android.quizapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app is a quiz about soccer world cup facts
 */
public class MainActivity extends AppCompatActivity {

    TextView infoTextView, mainTextView, scoreTextView, feedbackTextView;
    RadioGroup optionsRadioGroup;
    RadioButton option1RadioButton, option2RadioButton, option3RadioButton, option4RadioButton, option5RadioButton;
    Button startButton, checkButton, nextButton, restartButton;
    int currentQuizQuestion = 1;
    String correctAnswer;
    int quizScore = 0;
    /**
     * Quiz Limit
     */
    int quizLimit = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        infoTextView = (TextView) findViewById(R.id.info_text_view);
        mainTextView = (TextView) findViewById(R.id.main_text_view);
        scoreTextView = (TextView) findViewById(R.id.score_text_view);
        feedbackTextView = (TextView) findViewById(R.id.feedback_text_view);
        optionsRadioGroup = (RadioGroup) findViewById(R.id.options_radio_group);
        option1RadioButton = (RadioButton) findViewById(R.id.option1_radio);
        option2RadioButton = (RadioButton) findViewById(R.id.option2_radio);
        option3RadioButton = (RadioButton) findViewById(R.id.option3_radio);
        option4RadioButton = (RadioButton) findViewById(R.id.option4_radio);
        option5RadioButton = (RadioButton) findViewById(R.id.option5_radio);
        startButton = (Button) findViewById(R.id.start_button);
        checkButton = (Button) findViewById(R.id.check_button);
        nextButton = (Button) findViewById(R.id.next_button);
        restartButton = (Button) findViewById(R.id.restart_button);
    }

    /**
     * This method is called when the start button is clicked
     */
    public void startQuiz(View view) {
        setQuizQuestion(currentQuizQuestion);
        startButton.setVisibility(View.GONE);
        checkButton.setVisibility(View.VISIBLE);
        infoTextView.setVisibility(View.VISIBLE);
        optionsRadioGroup.setVisibility(View.VISIBLE);

    }

    /**
     * This method is called when the restart button is clicked
     */
    public void restartQuiz(View view) {
        currentQuizQuestion = 1;
        quizScore = 0;
        mainTextView.setText(R.string.welcome_text);
        mainTextView.setGravity(Gravity.NO_GRAVITY);
        scoreTextView.setVisibility(View.GONE);
        startButton.setVisibility(View.VISIBLE);
        restartButton.setVisibility(View.GONE);
    }

    /**
     * This method is called when the check button is clicked
     */
    public void checkAnswer(View view) {
        int selectedAnswerId = optionsRadioGroup.getCheckedRadioButtonId();
        if (selectedAnswerId == -1) {
            Toast.makeText(getApplicationContext(), getString(R.string.no_selected_text), Toast.LENGTH_SHORT).show();
        } else {
            String selectedAnswer = ((RadioButton) findViewById(selectedAnswerId)).getText().toString();
            if (selectedAnswer.equals(correctAnswer)) {
                quizScore += 1;
                feedbackTextView.setText(R.string.feedback_correct);
                feedbackTextView.setTextColor(Color.parseColor("#4CAF50"));
            } else {
                feedbackTextView.setText(R.string.feedback_incorrect);
                feedbackTextView.setTextColor(Color.RED);
            }
            optionsRadioGroup.setEnabled(false);
            for (int i = 0; i < optionsRadioGroup.getChildCount(); i++) {
                optionsRadioGroup.getChildAt(i).setEnabled(false);
            }
            feedbackTextView.setVisibility(View.VISIBLE);
            checkButton.setVisibility(View.GONE);
            nextButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * This method is called when the next button is clicked
     */
    public void nextQuestion(View view) {
        if (currentQuizQuestion < quizLimit) {
            optionsRadioGroup.setEnabled(true);
            for (int i = 0; i < optionsRadioGroup.getChildCount(); i++) {
                optionsRadioGroup.getChildAt(i).setEnabled(true);
            }
            feedbackTextView.setVisibility(View.GONE);
            checkButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.GONE);
            setQuizQuestion(currentQuizQuestion += 1);
        } else {
            optionsRadioGroup.setEnabled(true);
            for (int i = 0; i < optionsRadioGroup.getChildCount(); i++) {
                optionsRadioGroup.getChildAt(i).setEnabled(true);
            }
            infoTextView.setVisibility(View.GONE);
            mainTextView.setText(R.string.finish_text);
            mainTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            scoreTextView.setText(String.valueOf(quizScore));
            scoreTextView.setVisibility(View.VISIBLE);
            optionsRadioGroup.setVisibility(View.GONE);
            feedbackTextView.setVisibility(View.GONE);
            checkButton.setVisibility(View.GONE);
            nextButton.setVisibility(View.GONE);
            restartButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * This method is called to show a new question on the screen
     * @param index is the index of the question to be shown
     */
    private void setQuizQuestion(int index) {
        int resId;

        optionsRadioGroup.clearCheck();

        infoTextView.setText(getString(R.string.info_text, index, quizLimit));

        resId = getResources().getIdentifier("quiz_" + index + "_question", "string", getPackageName());
        mainTextView.setText(resId);

        resId = getResources().getIdentifier("quiz_" + index + "_option_1", "string", getPackageName());
        option1RadioButton.setText(resId);

        resId = getResources().getIdentifier("quiz_" + index + "_option_2", "string", getPackageName());
        option2RadioButton.setText(resId);

        resId = getResources().getIdentifier("quiz_" + index + "_option_3", "string", getPackageName());
        option3RadioButton.setText(resId);

        resId = getResources().getIdentifier("quiz_" + index + "_option_4", "string", getPackageName());
        option4RadioButton.setText(resId);

        resId = getResources().getIdentifier("quiz_" + index + "_option_5", "string", getPackageName());
        option5RadioButton.setText(resId);

        resId = getResources().getIdentifier("quiz_" + index + "_answer", "string", getPackageName());
        correctAnswer = getString(resId);
    }
}
