package net.bplaced.esigala1.colorquiz;

import android.util.Log;

import java.util.Random;

/**
 * Created by Effie Sigala on 6/4/2017.
 */

public class QuizItem {

    // Declare a Random object.
    Random r;
    String quizColorName, quizColorValue, rndColorName2 = "", rndColorName3 = "";

    int minColor = 0, maxColorRest, currentPosition;


    // Constructor
    public QuizItem(){
        // Initialize the random object.
        r = new Random();
        // Current maxCurrent number of colors.
        // Note: Decrease by one, because we start the counting from zero!!
        maxColorRest = QuizActivity.quizColorsRest.size() - 1;
        // Get a random number [min, maxCurrent] for quiz color.
        currentPosition = r.nextInt(maxColorRest - minColor + 1) + minColor;
        // Set the Name of the Quiz Color.
        quizColorName = QuizActivity.quizColorsRest.get(currentPosition)[0];
        Log.i(MainActivity.TAG_INFO, "Quiz Color = " + quizColorName + " ~ Position from rest list = " + currentPosition);
        // Set the Value of the Quiz Color.
        quizColorValue = QuizActivity.quizColorsRest.get(currentPosition)[1];
        // Get extra two random colors.
        getExtraRandomColors();
    }

    // Getters.
    public String getQuizColorName(){
        return quizColorName;
    }

    public String getQuizColorValue(){
        return quizColorValue;
    }

    public String getRndColorName2(){
        return rndColorName2;
    }

    public String getRndColorName3(){
        return rndColorName3;
    }
    public int getCurrentPosition(){
        return currentPosition;
    }


    /**
     * This method is called to get extra random colors.
     */
    public void getExtraRandomColors(){

        // *** Get a 2nd random color ** //

        // Get a new random number [min, maxCurrent] for another color name.
        int rndColorPosition2 = r.nextInt(QuizActivity.maxColor - minColor + 1) + minColor;
        // Set the Name of the new random Color.
        rndColorName2 = QuizActivity.quizColorsAll.get(rndColorPosition2)[0];
        // While the name of the new random Color equals to the quiz color, find a new one...
        while (rndColorName2.equals(quizColorName)){
            // Get a new random number [min, maxCurrent] for other color name.
            rndColorPosition2 = r.nextInt(QuizActivity.maxColor - minColor + 1) + minColor;
            // Set the Name of the new random Color.
            rndColorName2 = QuizActivity.quizColorsAll.get(rndColorPosition2)[0];
        }

        // *** Get a 3rd random color ** //

        // Get a new random number [min, maxCurrent] for another color name.
        int rndColorPosition3 = r.nextInt(QuizActivity.maxColor - minColor + 1) + minColor;
        // Set the Name of the new random Color.
        rndColorName3 = QuizActivity.quizColorsAll.get(rndColorPosition3)[0];
        // While the name of the new random Color equals to the quiz color or to the 2nd random
        // color, then find a new one...
        while (rndColorName3.equals(quizColorName) || rndColorName3.equals(rndColorName2)){
            // Get a new random number [min, maxCurrent] for other color name.
            rndColorPosition3 = r.nextInt(QuizActivity.maxColor - minColor + 1) + minColor;
            // Set the Name of the new random Color.
            rndColorName3 = QuizActivity.quizColorsAll.get(rndColorPosition3)[0];
        }
    }

}
