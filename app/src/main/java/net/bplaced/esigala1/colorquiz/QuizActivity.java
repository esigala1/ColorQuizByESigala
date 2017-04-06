package net.bplaced.esigala1.colorquiz;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    public static final String SCORE = "score";
    public static final String QUIZ_NUM = "quiz_num";

    TextView tvTitle, tvAnswer;
    View vColor;
    RadioGroup rgButtons;
    RadioButton rb1, rb2, rb3;
    Button btnSubmit, btnNext;
    // Declare a Random object.
    Random r;

    QuizItem quizItem;

    // ArrayList to keep All Quiz Colors and the Rest of the Quiz Colors.
    public static ArrayList<String[]> quizColorsAll, quizColorsRest;
    public static int maxColor;
    int minRadioBTN = 1, maxRadioBTN = 3;
    int score, currentQuizNum, posOfCorrectAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(MainActivity.TAG_INFO, "QuizActivity onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


        tvTitle = (TextView) findViewById(R.id.text_view_quiz_title);
        tvAnswer = (TextView) findViewById(R.id.text_view_answer);
        vColor = (View) findViewById(R.id.view_color);
        rgButtons = (RadioGroup)findViewById(R.id.radio_group_buttons);
        rb1 = (RadioButton) findViewById(R.id.radio_button_1);
        rb2 = (RadioButton) findViewById(R.id.radio_button_2);
        rb3 = (RadioButton) findViewById(R.id.radio_button_3);
        btnSubmit = (Button) findViewById(R.id.button_submit);
        btnNext = (Button) findViewById(R.id.button_next);

        // Initialize All Quiz Colors.
        quizColorsAll = initializeQuizColors();
        // Initialize The "quizColorsRest".
        quizColorsRest = new ArrayList<>();
        // Add the content of "quizColorsAll" into "quizColorsRest".
        quizColorsRest.addAll(quizColorsAll);
        // As max color set the total number of all colors.
        // Note: Decrease by one, because we start the counting from zero!!
        maxColor = quizColorsAll.size() - 1;
        // Initialize the random object.
        r = new Random();
        // If there is not a saved state, then...
        if (savedInstanceState == null) {
            score = 0;
            currentQuizNum = 1;
        }
        // There is a saved state, so restore it...
        else
        {
            score = savedInstanceState.getInt(SCORE);
            currentQuizNum = savedInstanceState.getInt(QUIZ_NUM);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i(MainActivity.TAG_INFO, "QuizActivity onSaveInstanceState()");
        super.onSaveInstanceState(outState);
        outState.putInt(SCORE, score);
        outState.putInt(QUIZ_NUM, currentQuizNum);

        // TODO Include more variables.
    }

    @Override
    protected void onResume() {
        Log.i(MainActivity.TAG_INFO, "QuizActivity onResume()");
        super.onResume();
        // Display a new quiz.
        displayNewQuiz();
    }

    @Override
    protected void onPause() {
        Log.i(MainActivity.TAG_INFO, "QuizActivity onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(MainActivity.TAG_INFO, "QuizActivity onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(MainActivity.TAG_INFO, "QuizActivity onDestroy()");
        super.onDestroy();
    }

    /**
     *  Method to get the Quiz Colors.
     *  Note: String[] consists of 2 elements:
     *  String[0] => The Name of the Color
     *  String[1] => The Value of the Color
     */
    public ArrayList<String[]> initializeQuizColors(){
        // An ArrayList to keep the Quiz Colors.
        ArrayList<String[]> quizArrayColors = new ArrayList<>();
        final String[] colorName = getResources().getStringArray(R.array.colorNames);
        final String[] colorValue = getResources().getStringArray(R.array.colorValues);
        // If the array of Strings is not empty and both arrays have the same length, then...
        if (colorName.length != 0 && colorName.length == colorValue.length){
            for (int i = 0; i < colorName.length; i++){
                // Create a 2 dimensional array to keep the Name and the Value of the Color.
                String[] currentColor = new String[2];
                currentColor[0] = colorName[i];   // The Name of the current Color
                currentColor[1] = colorValue[i];  // The Value of the current Color
                // Add the current Color into the ArrayList.
                quizArrayColors.add(currentColor);
            }
        }
        else
        {
            // Display a message.
            Toast.makeText(this, getResources().getString(R.string.msg_error_quiz_colors_list), Toast.LENGTH_SHORT).show();
            // Close this activity and go back to the previous activity.
            finish();
        }
        // Return the ArrayList.
        return quizArrayColors;
    }

    /**
     *  Method to display a new quiz.
     */
    private void displayNewQuiz(){
        Log.i(MainActivity.TAG_INFO, "====================================================");
        Log.i(MainActivity.TAG_INFO, "Quiz = " + currentQuizNum + "/" + quizColorsAll.size());
        Log.i(MainActivity.TAG_INFO, "quizColorsRest.size() = " + quizColorsRest.size());
        // Initialize a QuizItem object.
        quizItem = new QuizItem();
        // Set the title.
        tvTitle.setText(getResources().getString(R.string.quiz_num, currentQuizNum, maxColor + 1));
        // Set the Value of the Quiz color in the View.
        vColor.setBackgroundColor(Color.parseColor(quizItem.getQuizColorValue()));
        // Get a random number [min, maxCurrent] for a radio button to put the correct answer.
        posOfCorrectAnswer = r.nextInt(maxRadioBTN - minRadioBTN + 1) + minRadioBTN;
        // Set the texts in the radio buttons.
        setRadioBtnTexts(quizItem.getQuizColorName(), posOfCorrectAnswer);
    }

    /**
     * This method is called to set the texts in the radio buttons.
     */
    public void setRadioBtnTexts(String quizColorName, int radioBtnPosition){

        // *** Set the texts in the radio buttons ** //

        // If the quiz color is in the 1st radio button, then...
        if (radioBtnPosition == 1)
        {
            rb1.setText(quizColorName);
            rb2.setText(quizItem.getRndColorName2());
            rb3.setText(quizItem.getRndColorName3());
        }
        // If the quiz color is in the 1st radio button, then...
        else if (radioBtnPosition == 2)
        {
            rb1.setText(quizItem.getRndColorName2());
            rb2.setText(quizColorName);
            rb3.setText(quizItem.getRndColorName3());
        }
        // The quiz color is in the 3rd radio button, so...
        else
        {
            rb1.setText(quizItem.getRndColorName2());
            rb2.setText(quizItem.getRndColorName3());
            rb3.setText(quizColorName);
        }
    }

    /**
     * This method is called to display the correct answer in the TextView "text_view_answer".
     */
    private void displayAnswer(boolean isCorrect){
        if (isCorrect){
            tvAnswer.setText(getResources().getString(R.string.msg_correct_answer));
            tvAnswer.setTextColor(ContextCompat.getColor(this, R.color.green));
        }
        else
        {
            tvAnswer.setText(getResources().getString(R.string.msg_wrong_answer, quizItem.getQuizColorName()));
            tvAnswer.setTextColor(ContextCompat.getColor(this, R.color.red));
        }
    }

    /**
     * This method is called when the button "Submit" is clicked.
     */
    public void onClickSubmit(View view){
        // If no radio button is selected, then...
        if (!rb1.isChecked() && !rb2.isChecked() && !rb3.isChecked()){
            // Display a message.
            Toast.makeText(this, getResources().getString(R.string.msg_choose_answer), Toast.LENGTH_SHORT).show();
            // Terminate the current method.
            return;
        }

        // Disable the Radio Buttons.
        rb1.setEnabled(false);
        rb2.setEnabled(false);
        rb3.setEnabled(false);
        // Hide the Submit Button.
        btnSubmit.setVisibility(View.GONE);
        // Display the Next Button.
        btnNext.setVisibility(View.VISIBLE);

        // Check if correct answer is selected.
        switch (posOfCorrectAnswer){
            // The correct answer is in the 1st radio button...
            case 1:
                if (rb1.isChecked()){
                    displayAnswer(true);   // Correct answer.
                }
                else
                {
                    displayAnswer(false);  // Incorrect answer.
                }
                break;
            // The correct answer is in the 2nd radio button...
            case 2:
                if (rb2.isChecked()){
                    displayAnswer(true);   // Correct answer.
                }
                else
                {
                    displayAnswer(false);  // Incorrect answer.
                }
                break;
            // The correct answer is in the 3rd radio button...
            case 3:
                if (rb3.isChecked()){
                    displayAnswer(true);   // Correct answer.
                }
                else
                {
                    displayAnswer(false);  // Incorrect answer.
                }
                break;
            default:
                // Display a message.
                Toast.makeText(this, getResources().getString(R.string.msg_system_error), Toast.LENGTH_SHORT).show();
                // Close this activity and go back to the previous activity.
                finish();
                // break;
        }

    }

    /**
     * This method is called when the button "Next" is clicked.
     */
    public void onClickNext(View view){
        // If this is the last quiz, then...
        if (currentQuizNum == maxColor + 1){
            // Display a message.
            Toast.makeText(this, "END", Toast.LENGTH_SHORT).show();
        }
        // Increase the number of the current quiz.
        currentQuizNum++;
        // Uncheck all radio buttons.
        rgButtons.clearCheck();
        // Enable the Radio Buttons.
        rb1.setEnabled(true);
        rb2.setEnabled(true);
        rb3.setEnabled(true);
        tvAnswer.setText(getResources().getString(R.string.quiz_submit_answer));
        tvAnswer.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        // Display a new quiz.
        displayNewQuiz();
        // Hide the Next Button.
        btnNext.setVisibility(View.GONE);
        // Display the Submit Button.
        btnSubmit.setVisibility(View.VISIBLE);
    }


}
