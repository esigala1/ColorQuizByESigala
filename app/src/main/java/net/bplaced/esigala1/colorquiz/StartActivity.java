package net.bplaced.esigala1.colorquiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


// Note: Inherit the from super class "MainActivity" the constants "TAG_INFO" and "NAME".
public class StartActivity extends MainActivity {

    TextView tvName;
    String name;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG_INFO, "StartActivity onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        // Retrieve the widget from the UI.
        tvName = (TextView) findViewById(R.id.text_view_start_name);
        // If there is no saved state, then...
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                name = extras.getString(NAME);
            }
            score = 0;
        }
        // There is a saved state, so restore it...
        else
        {
            name = savedInstanceState.getString(NAME);
            score = savedInstanceState.getInt(QuizActivity.SCORE);
        }
        // Set the Player's name.
        tvName.setText(getResources().getString(R.string.player) + " :  " + name);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i(TAG_INFO, "StartActivity onSaveInstanceState()");
        super.onSaveInstanceState(outState);
        outState.putString(NAME, name);
        outState.putInt(QuizActivity.SCORE, score);
    }

    // When the button "Start" is pressed, execute the following method.
    public void onClickStart(View view){
        Log.i(TAG_INFO, "Button Start Pressed.");
        // Create an intent to start another activity.
        Intent intent = new Intent(this, QuizActivity.class);
        // Add the Name to the intent (ID, value)
        intent.putExtra(NAME, name);
        // Start an instance of the "StartActivity" specified by the intent.
        startActivity(intent);
    }


}
