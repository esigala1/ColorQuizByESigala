package net.bplaced.esigala1.colorquiz;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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

// Note: Inherit from the super class "MainActivity" the constants "TAG_INFO" and "NAME".
public class QuizActivity extends MainActivity {

    public static final String SCORE = "score";
    public static final String QUIZ_NUM = "quiz_num";
    public static final String QUIZ_ARRAY_ALL = "quiz_array_all";
    public static final String QUIZ_ARRAY_REST = "quiz_array_rest";
    public static final String QUIZ_ITEM_COLOR_NAME = "item_quizColorName";
    public static final String QUIZ_ITEM_COLOR_VALUE = "item_quizColorValue";
    public static final String QUIZ_ITEM_COLOR_NAME_2 = "item_rndColorName2";
    public static final String QUIZ_ITEM_COLOR_NAME_3 = "item_rndColorName3";
    public static final String QUIZ_ITEM_CURRENT_POSITION = "item_currentPosition";

    TextView tvTitle, tvAnswer;
    View vColor;
    RadioGroup rgButtons;
    RadioButton rb1, rb2, rb3;
    Button btnSubmit, btnNext;
    String name;
    Random r;
    QuizItem quizItem;

    // ArrayList to keep All Quiz Colors and the Rest of the Quiz Colors.
    public static ArrayList<String[]> quizColorsAll, quizColorsRest;
    public static int maxColor;
    int minRadioBTN = 1, maxRadioBTN = 3;
    int score, currentQuizNum, radioBtnPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG_INFO, "QuizActivity onCreate()");
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

        // Initialize the random object.
        r = new Random();

        // As max color set the total number of all colors.
        // If there is no saved state, then...
        if (savedInstanceState == null) {
            score = 0;
            currentQuizNum = 1;
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                name = extras.getString(NAME);
            }
            // Initialize All Quiz Colors.
            quizColorsAll = initializeQuizColors();
            // Initialize The "quizColorsRest".
            quizColorsRest = new ArrayList<>();
            // Add the content of "quizColorsAll" into "quizColorsRest".
            quizColorsRest.addAll(quizColorsAll);

            // Note: Decrease by one, because we start the counting from zero!!
            maxColor = quizColorsAll.size() - 1;

            // Initialize the QuizItem object.
            quizItem = new QuizItem();

        }
        // There is a saved state, so restore it...
        else
        {
            name = savedInstanceState.getString(NAME);
            score = savedInstanceState.getInt(SCORE);
            currentQuizNum = savedInstanceState.getInt(QUIZ_NUM);
            // Initialize All Quiz Colors.
            quizColorsAll = (ArrayList<String[]>) savedInstanceState.getSerializable(QUIZ_ARRAY_ALL);
            // Initialize Quiz Rest Colors.
            quizColorsRest = (ArrayList<String[]>) savedInstanceState.getSerializable(QUIZ_ARRAY_REST);

            // Note: Decrease by one, because we start the counting from zero!!
            maxColor = quizColorsAll.size() - 1;
            // Initialize the QuizItem object.
            quizItem = new QuizItem(
                    savedInstanceState.getString(QUIZ_ITEM_COLOR_NAME),
                    savedInstanceState.getString(QUIZ_ITEM_COLOR_VALUE),
                    savedInstanceState.getString(QUIZ_ITEM_COLOR_NAME_2),
                    savedInstanceState.getString(QUIZ_ITEM_COLOR_NAME_3),
                    savedInstanceState.getInt(QUIZ_ITEM_CURRENT_POSITION)
            );
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i(TAG_INFO, "QuizActivity onSaveInstanceState()");
        super.onSaveInstanceState(outState);
        outState.putString(NAME, name);
        outState.putInt(SCORE, score);
        outState.putInt(QUIZ_NUM, currentQuizNum);
        outState.putSerializable(QUIZ_ARRAY_ALL, quizColorsAll);
        outState.putSerializable(QUIZ_ARRAY_REST, quizColorsRest);
        outState.putString(QUIZ_ITEM_COLOR_NAME, quizItem.getQuizColorName());
        outState.putString(QUIZ_ITEM_COLOR_VALUE, quizItem.getQuizColorValue());
        outState.putString(QUIZ_ITEM_COLOR_NAME_2, quizItem.getRndColorName2());
        outState.putString(QUIZ_ITEM_COLOR_NAME_3, quizItem.getRndColorName3());
        outState.putInt(QUIZ_ITEM_CURRENT_POSITION, quizItem.getCurrentPosition());
    }

    @Override
    protected void onResume() {
        Log.i(TAG_INFO, "QuizActivity onResume()");
        super.onResume();
        // Display the quiz.
        displayQuiz();
    }

    @Override
    protected void onPause() {
        Log.i(TAG_INFO, "QuizActivity onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG_INFO, "QuizActivity onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG_INFO, "QuizActivity onDestroy()");
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
        // If the array has at least 3 colors and both arrays have the same length, then...
        if (colorName.length >= 3 && colorName.length == colorValue.length){
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
    private void displayQuiz(){
        Log.i(TAG_INFO, "====================================================");
        Log.i(TAG_INFO, "Quiz = " + currentQuizNum + "/" + quizColorsAll.size());
        Log.i(TAG_INFO, "quizColorsRest.size() = " + quizColorsRest.size());
        // Set the title.
        tvTitle.setText(getResources().getString(R.string.quiz_num, currentQuizNum, maxColor + 1));
        // Set the Value of the Quiz color in the View.
        vColor.setBackgroundColor(Color.parseColor(quizItem.getQuizColorValue()));
        // Set the texts in the radio buttons.
        setRadioBtnTexts();
    }

    /**
     * This method is called to set the texts in the radio buttons.
     */
    public void setRadioBtnTexts(){
        Log.i(TAG_INFO, "Correct color = " + quizItem.getQuizColorName());
        Log.i(TAG_INFO, "Random colors = "
                + quizItem.getRndColorName2() + " & " + quizItem.getRndColorName3());

        // Get a random number [min, maxCurrent] for a radio button to put the correct answer.
        radioBtnPosition = r.nextInt(maxRadioBTN - minRadioBTN + 1) + minRadioBTN;

        // *** Set the texts in the radio buttons ** //

        // If the quiz color is in the 1st radio button, then...
        if (radioBtnPosition == 1)
        {
            rb1.setText(quizItem.getQuizColorName());
            rb2.setText(quizItem.getRndColorName2());
            rb3.setText(quizItem.getRndColorName3());
        }
        // If the quiz color is in the 1st radio button, then...
        else if (radioBtnPosition == 2)
        {
            rb1.setText(quizItem.getRndColorName2());
            rb2.setText(quizItem.getQuizColorName());
            rb3.setText(quizItem.getRndColorName3());
        }
        // The quiz color is in the 3rd radio button, so...
        else
        {
            rb1.setText(quizItem.getRndColorName2());
            rb2.setText(quizItem.getRndColorName3());
            rb3.setText(quizItem.getQuizColorName());
        }
    }

    /**
     * This method is called to display the correct answer in the TextView "text_view_answer".
     * @param isCorrect is the boolean variable to indicate if the answer is correct or not.
     */
    private void displayAnswer(boolean isCorrect){
        if (isCorrect){
            // Increase the score by 1.
            score++;
            // Display message.
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
        switch (radioBtnPosition){
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
        Log.i(TAG_INFO, "Current score = " + score);
    }

    /**
     * This method is called when the button "Next" is clicked.
     */
    public void onClickNext(View view){
        // If this is the last question, then...
        if (currentQuizNum < maxColor + 1){
            // Remove the color from the list with the rest quiz colors.
            quizColorsRest.remove(quizItem.getCurrentPosition());
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
            // Initialize a new QuizItem object.
            quizItem = new QuizItem();
            // Display the quiz.
            displayQuiz();
            // Hide the Next Button.
            btnNext.setVisibility(View.GONE);
            // Display the Submit Button.
            btnSubmit.setVisibility(View.VISIBLE);
        }
        // This is the last question, so...
        else
        {
            String textMessage = "";
            // If the score is above the average number of quiz, then...
            if (score > (float) maxColor / 2)
            {
                textMessage = getResources().getString(R.string.msg_well_done, name, score);
            }
            // The score is below the average number of quiz, so...
            else
            {
                textMessage = getResources().getString(R.string.msg_not_so_good, name, score);
            }
            // Display message.
            messageShow(getResources().getString(R.string.msg_end_title), textMessage);
        }
    }

    /**
     * A method to display an alert dialog.
     * @param title is the Title of the dialog.
     * @param message is the Message of the dialog.
     */
    public void messageShow(String title, String message){
        // Create a builder for an alert dialog, that uses the default alert dialog theme.
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(this);
        // Set the title displayed in the Dialog - OPTIONAL
        aBuilder.setTitle(title);
        // Set the message to display.
        aBuilder.setMessage(message);
        aBuilder.setCancelable(false);
        // Set a listener to be invoked when the POSITIVE button of the dialog is pressed.
        // Note: Set the OnClickListener using the anonymous class.
        aBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Actions to perform when the "OK" button is clicked.
                // Close this activity and go back to the previous activity.
                finish();
            }
        });
        // "AlertDialog" => A subclass of Dialog that can display one, two or three buttons.
        // "aBuilder.create()" => Create an AlertDialog with the arguments supplied to this builder.
        AlertDialog dialog = aBuilder.create();
        // Display the alert dialog.
        dialog.show();
    }
}
