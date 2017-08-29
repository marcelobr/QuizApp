package com.example.android.quizapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * This app is a quiz about soccer world cup facts
 */
public class MainActivity extends AppCompatActivity {

    TextView infoTextView, mainTextView, scoreTextView, feedbackTextView, optionTextView;
    RadioGroup optionsRadioGroup;
    RadioButton option1RadioButton, option2RadioButton, option3RadioButton, option4RadioButton, option5RadioButton;
    LinearLayout optionsCheckGroup;
    CheckBox option1CheckBox, option2CheckBox, option3CheckBox, option4CheckBox, option5CheckBox;
    Button startButton, checkButton, nextButton, restartButton;
    int currentQuizQuestion = 1;
    String correctAnswer;
    int questionType;
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
        optionsCheckGroup = (LinearLayout) findViewById(R.id.options_check_group);
        option1CheckBox = (CheckBox) findViewById(R.id.option1_check);
        option2CheckBox = (CheckBox) findViewById(R.id.option2_check);
        option3CheckBox = (CheckBox) findViewById(R.id.option3_check);
        option4CheckBox = (CheckBox) findViewById(R.id.option4_check);
        option5CheckBox = (CheckBox) findViewById(R.id.option5_check);
        optionTextView = (TextView) findViewById(R.id.option_text);
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
        //setVisibleOptions();
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

        switch (questionType) {
            case 1: checkRadioButtons();
                break;
            case 2: checkCheckBoxes();
                break;
            case 3: checkText();
                break;
        }
    }

    /**
     * This method returns the answer feedback to the user
     * @param isCorrect is the flag that corresponds to the correct or incorrect answer
     */
    private void showFeedbackAnswer(boolean isCorrect) {
        if (isCorrect) {
            quizScore += 1;
            feedbackTextView.setText(R.string.feedback_correct);
            feedbackTextView.setTextColor(Color.parseColor("#4CAF50"));
        } else {
            feedbackTextView.setText(R.string.feedback_incorrect);
            feedbackTextView.setTextColor(Color.RED);
        }
    }

    /**
     * This method checks the user's answer when the question is a unique answer
     */
    private void checkRadioButtons() {
        int selectedAnswerId = optionsRadioGroup.getCheckedRadioButtonId();
        if (selectedAnswerId == -1) {
            Toast.makeText(getApplicationContext(), getString(R.string.no_selected_text), Toast.LENGTH_SHORT).show();
        } else {
            String selectedAnswer = ((RadioButton) findViewById(selectedAnswerId)).getText().toString();
            showFeedbackAnswer(selectedAnswer.equals(correctAnswer));
            setDisabledRadioButtons();
            feedbackTextView.setVisibility(View.VISIBLE);
            checkButton.setVisibility(View.GONE);
            nextButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * This method gets the user selected answer when the question is multiple choice
     * @return a list of integers containing the indices of the selected options
     */
    private List<Integer> getBoxesChecked() {
        List<Integer> checks = new ArrayList<>();
        if (option1CheckBox.isChecked())
            checks.add(1);
        if (option2CheckBox.isChecked())
            checks.add(2);
        if (option3CheckBox.isChecked())
            checks.add(3);
        if (option4CheckBox.isChecked())
            checks.add(4);
        if (option5CheckBox.isChecked())
            checks.add(5);

        return checks;
    }

    /**
     * This method checks the user's answers when the question is multiple choice
     */
    private void checkCheckBoxes() {
        List<Integer> selectedAnswerList = getBoxesChecked();
        if (selectedAnswerList.isEmpty()) {
            Toast.makeText(getApplicationContext(), getString(R.string.no_selected_text), Toast.LENGTH_SHORT).show();
        } else {
            List<Integer> correctAnswerList = new ArrayList<>();
            for (String item : correctAnswer.split(","))
                correctAnswerList.add(Integer.parseInt(item));

            if (selectedAnswerList.size() != correctAnswerList.size()) {
                Toast.makeText(getApplicationContext(), getString(R.string.quantity_selected, correctAnswerList.size()), Toast.LENGTH_SHORT).show();
            } else {
                showFeedbackAnswer(correctAnswerList.equals(selectedAnswerList));
                setDisabledCheckBoxes();
                feedbackTextView.setVisibility(View.VISIBLE);
                checkButton.setVisibility(View.GONE);
                nextButton.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * This method checks the user's answers when the question is a dissertation
     */
    private void checkText() {
        String answer = optionTextView.getText().toString().toLowerCase().trim();
        if (answer.isEmpty()) {
            Toast.makeText(getApplicationContext(), getString(R.string.no_selected_text), Toast.LENGTH_SHORT).show();
        } else {
            showFeedbackAnswer(answer.equals(correctAnswer.toLowerCase().trim()));
            setDisabledOptionText();
            feedbackTextView.setVisibility(View.VISIBLE);
            checkButton.setVisibility(View.GONE);
            nextButton.setVisibility(View.VISIBLE);
        }
    }

    private void clearOptions() {
        option1RadioButton.setChecked(false);
        option2RadioButton.setChecked(false);
        option3RadioButton.setChecked(false);
        option4RadioButton.setChecked(false);
        option5RadioButton.setChecked(false);

        option1CheckBox.setChecked(false);
        option2CheckBox.setChecked(false);
        option3CheckBox.setChecked(false);
        option4CheckBox.setChecked(false);
        option5CheckBox.setChecked(false);

        optionTextView.setText("");
    }

    /**
     * This method is called when the next button is clicked
     */
    public void nextQuestion(View view) {
        if (currentQuizQuestion < quizLimit) {
            setEnabledOptions();
            setInvisibleOptions();
            clearOptions();
            feedbackTextView.setVisibility(View.GONE);
            checkButton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.GONE);
            setQuizQuestion(currentQuizQuestion += 1);
            //setVisibleOptions();
        } else {
            setEnabledOptions();
            clearOptions();
            infoTextView.setVisibility(View.GONE);
            mainTextView.setText(R.string.finish_text);
            mainTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            scoreTextView.setText(String.valueOf(quizScore));
            scoreTextView.setVisibility(View.VISIBLE);
            setInvisibleOptions();
            feedbackTextView.setVisibility(View.GONE);
            checkButton.setVisibility(View.GONE);
            nextButton.setVisibility(View.GONE);
            restartButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * This method is called to show a new question on the screen
     *
     * @param index is the index of the question to be shown
     */
    private void setQuizQuestion(int index) {
        int resId;

        resId = getResources().getIdentifier("quiz_" + index + "_type", "string", getPackageName());
        questionType = Integer.parseInt(getString(resId));

        infoTextView.setText(getString(R.string.info_text, index, quizLimit));

        switch (questionType) {
            case 1: setRadioButtons(index);
                    break;
            case 2: setCheckBoxes(index);
                    break;
            case 3: setText(index);
                    break;
        }
        setVisibleOptions();
    }

    /**
     * This method sets the options of type unique answer
     * @param index is the index that corresponds to the current question
     */
    private void setRadioButtons(int index) {
        int resId;

        optionsRadioGroup.clearCheck();

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

    /**
     * This method sets the options of type multiple choice
     * @param index is the index that corresponds to the current question
     */
    private void setCheckBoxes(int index) {
        int resId;

        resId = getResources().getIdentifier("quiz_" + index + "_question", "string", getPackageName());
        mainTextView.setText(resId);

        resId = getResources().getIdentifier("quiz_" + index + "_option_1", "string", getPackageName());
        option1CheckBox.setChecked(false);
        option1CheckBox.setText(resId);

        resId = getResources().getIdentifier("quiz_" + index + "_option_2", "string", getPackageName());
        option2CheckBox.setChecked(false);
        option2CheckBox.setText(resId);

        resId = getResources().getIdentifier("quiz_" + index + "_option_3", "string", getPackageName());
        option3CheckBox.setChecked(false);
        option3CheckBox.setText(resId);

        resId = getResources().getIdentifier("quiz_" + index + "_option_4", "string", getPackageName());
        option4CheckBox.setChecked(false);
        option4CheckBox.setText(resId);

        resId = getResources().getIdentifier("quiz_" + index + "_option_5", "string", getPackageName());
        option5CheckBox.setChecked(false);
        option5CheckBox.setText(resId);

        resId = getResources().getIdentifier("quiz_" + index + "_answer", "string", getPackageName());
        correctAnswer = getString(resId);
    }

    /**
     * This method sets the options of type text
     * @param index is the index that corresponds to the current question
     */
    private void setText(int index) {
        int resId;

        optionTextView.setText("");

        resId = getResources().getIdentifier("quiz_" + index + "_answer", "string", getPackageName());
        correctAnswer = getString(resId);
    }

    /**
     * This method enables all unique answer options
     */
    private void setEnabledRadioButtons() {
        optionsRadioGroup.setEnabled(true);
        for (int i = 0; i < optionsRadioGroup.getChildCount(); i++) {
            optionsRadioGroup.getChildAt(i).setEnabled(true);
        }
    }

    /**
     * This method disables all unique answer options
     */
    private void setDisabledRadioButtons() {
        optionsRadioGroup.setEnabled(false);
        for (int i = 0; i < optionsRadioGroup.getChildCount(); i++) {
            optionsRadioGroup.getChildAt(i).setEnabled(false);
        }
    }

    /**
     * This method enables all multiple choice options
     */
    private void setEnabledCheckBoxes() {
        optionsCheckGroup.setEnabled(true);
        for (int i = 0; i < optionsCheckGroup.getChildCount(); i++) {
            optionsCheckGroup.getChildAt(i).setEnabled(true);
        }
    }

    /**
     * This method disables all multiple choice options
     */
    private void setDisabledCheckBoxes() {
        optionsCheckGroup.setEnabled(false);
        for (int i = 0; i < optionsCheckGroup.getChildCount(); i++) {
            optionsCheckGroup.getChildAt(i).setEnabled(false);
        }
    }

    /**
     * This method enables the text field
     */
    private void setEnabledOptionText() {
        optionTextView.setEnabled(true);
    }

    /**
     * /**
     * This method disables the text field
     */
    private void setDisabledOptionText() {
        optionTextView.setEnabled(false);
    }

    /**
     * This method enables a particular type of option
     */
    private void setEnabledOptions() {
        switch (questionType) {
            case 1: setEnabledRadioButtons();
                break;
            case 2: setEnabledCheckBoxes();
                break;
            case 3: setEnabledOptionText();
                break;
        }
    }

    /**
     * This method disables a particular type of option
     */
    private void setDisabledOptions() {
        switch (questionType) {
            case 1: setDisabledRadioButtons();
                break;
            case 2: setDisabledCheckBoxes();
                break;
            case 3: setDisabledOptionText();
                break;
        }
    }

    /**
     * This method makes a particular type of option visible
     */
    private void setVisibleOptions() {
        switch (questionType) {
            case 1: optionsRadioGroup.setVisibility(View.VISIBLE);
                break;
            case 2: optionsCheckGroup.setVisibility(View.VISIBLE);
                break;
            case 3: optionTextView.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * This method makes a particular type of option invisible
     */
    private void setInvisibleOptions() {
        switch (questionType) {
            case 1: optionsRadioGroup.setVisibility(View.GONE);
                break;
            case 2: optionsCheckGroup.setVisibility(View.GONE);
                break;
            case 3: optionTextView.setVisibility(View.GONE);
                break;
        }
    }

}
