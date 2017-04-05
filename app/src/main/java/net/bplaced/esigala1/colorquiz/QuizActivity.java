package net.bplaced.esigala1.colorquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {

    TextView tvName;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        tvName = (TextView) findViewById(R.id.text_view_quiz_name);

        // If there is not a saved state, then...
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                name = extras.getString(MainActivity.NAME);
            }
        }
        // There is a saved state, so restore it...
        else
        {
            name = savedInstanceState.getString(MainActivity.NAME);
        }
        // Set the Player's name.
        tvName.setText(getResources().getString(R.string.player) + ": " + name);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(MainActivity.TAG_INFO, "onSaveInstanceState");
        outState.putString(MainActivity.NAME, name);
    }


}
