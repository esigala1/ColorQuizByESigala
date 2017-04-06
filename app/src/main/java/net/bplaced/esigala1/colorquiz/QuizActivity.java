package net.bplaced.esigala1.colorquiz;

import android.graphics.Color;
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

    TextView tvTitle;
    View vColor;
    RadioButton rb1, rb2, rb3;
    Button btnSubmit, btnNext;

    // ArrayList to keep All Quiz Colors and the Rest of the Quiz Colors.
    ArrayList<String[]> quizColorsAll, quizColorsRest;
    // Declare a Random object.
    Random r;
    int minColor = 0, maxColor, maxColorCurrent, minRadioBTN = 1, maxRadioBTN = 3;
    int score, currentQuizNum, correctPosition;
    String quizColorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        tvTitle = (TextView) findViewById(R.id.text_view_quiz_title);
        vColor = (View) findViewById(R.id.view_color);
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
        maxColor = quizColorsAll.size();
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
        super.onSaveInstanceState(outState);
        Log.i(MainActivity.TAG_INFO, "onSaveInstanceState");
        outState.putInt(SCORE, score);
        outState.putInt(QUIZ_NUM, currentQuizNum);

        // TODO Include more variables.
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Set the title.
        tvTitle.setText(getResources().getString(R.string.quiz_num, currentQuizNum, maxColor));
        // Current maxCurrent number of colors.
        // Note: Decrease by one, because we start the counting from zero!!
        maxColorCurrent = quizColorsRest.size() - 1;
        // Get a random number [min, maxCurrent] for quiz color.
        int randomColor = r.nextInt(maxColorCurrent - minColor + 1) + minColor;
        // Set the Name of the Quiz Color.
        quizColorName = quizColorsRest.get(randomColor)[0];
        // Set the Value of the Quiz color.
        vColor.setBackgroundColor(Color.parseColor(quizColorsAll.get(randomColor)[1]));
        // Get a random number [min, maxCurrent] for a radio button to put the correct answer.
        correctPosition = r.nextInt(maxRadioBTN - minRadioBTN + 1) + minRadioBTN;
        // Set the texts in the radio buttons.
        setRadioBtnTexts(quizColorName, correctPosition);
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
     * This method is called to set the texts in the radio buttons.
     */
    public void setRadioBtnTexts(String quizColorName, int radioBtnPosition){

        // *** Get a 2nd random color ** //

        // Get a new random number [min, maxCurrent] for another color name.
        int rndColorPosition2 = r.nextInt(maxColor - minColor + 1) + minColor;
        // Set the Name of the new random Color.
        String rndColorName2 = quizColorsAll.get(rndColorPosition2)[0];
        // While the name of the new random Color equals to the quiz color, find a new one...
        while (rndColorName2.equals(quizColorName)){
            // Get a new random number [min, maxCurrent] for other color name.
            rndColorPosition2 = r.nextInt(maxColor - minColor + 1) + minColor;
            // Set the Name of the new random Color.
            rndColorName2 = quizColorsAll.get(rndColorPosition2)[0];
        }

        // *** Get a 3rd random color ** //

        // Get a new random number [min, maxCurrent] for another color name.
        int rndColorPosition3 = r.nextInt(maxColor - minColor + 1) + minColor;
        // Set the Name of the new random Color.
        String rndColorName3 = quizColorsAll.get(rndColorPosition3)[0];
        // While the name of the new random Color equals to the quiz color or to the 2nd random
        // color, then find a new one...
        while (rndColorName3.equals(quizColorName) || rndColorName3.equals(rndColorName2)){
            // Get a new random number [min, maxCurrent] for other color name.
            rndColorPosition3 = r.nextInt(maxColor - minColor + 1) + minColor;
            // Set the Name of the new random Color.
            rndColorName3 = quizColorsAll.get(rndColorPosition3)[0];
        }

        // *** Set the texts in the radio buttons ** //

        // If the quiz color is in the 1st radio button, then...
        if (radioBtnPosition == 1)
        {
            rb1.setText(quizColorName);
            rb2.setText(quizColorsAll.get(rndColorPosition2)[0]);
            rb3.setText(quizColorsAll.get(rndColorPosition3)[0]);
        }
        // If the quiz color is in the 1st radio button, then...
        else if (radioBtnPosition == 2)
        {
            rb1.setText(quizColorsAll.get(rndColorPosition2)[0]);
            rb2.setText(quizColorName);
            rb3.setText(quizColorsAll.get(rndColorPosition3)[0]);
        }
        // The quiz color is in the 3rd radio button, so...
        else
        {
            rb1.setText(quizColorsAll.get(rndColorPosition2)[0]);
            rb2.setText(quizColorsAll.get(rndColorPosition3)[0]);
            rb3.setText(quizColorName);
        }
    }

    /**
     * This method is called to display the correct answer in the TextView "text_view_answer".
     */
    private void displayAnswer(boolean isCorrect){
        if (isCorrect){
            ((TextView) findViewById(R.id.text_view_answer))
                    .setText(getResources().getString(R.string.msg_correct_answer));
        }
        else
        {
            ((TextView) findViewById(R.id.text_view_answer))
                    .setText(getResources().getString(R.string.msg_wrong_answer, quizColorName));
        }
    }

    /**
     * This method is called when the button "Submit" is clicked.
     */
    public void onClickSubmit(View view){
        // Disable the Radio Buttons.
        rb1.setEnabled(false);
        rb2.setEnabled(false);
        rb3.setEnabled(false);
        // Hide the Submit Button.
        btnSubmit.setVisibility(View.GONE);
        // Display the Submit Button.
        btnNext.setVisibility(View.VISIBLE);

        if (!rb1.isChecked() && !rb2.isChecked() && !rb3.isChecked()){
            // Display a message.
            Toast.makeText(this, getResources().getString(R.string.msg_choose_answer), Toast.LENGTH_SHORT).show();
            // Terminate the current method.
            return;
        }

        switch (correctPosition){
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

    }


}
