package net.bplaced.esigala1.colorquiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


// Note: Inherit the from super class "MainActivity" the constants "TAG_INFO" and "NAME".
public class StartActivity extends MainActivity {

    CheckBox cbStart;
    TextView tvName;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG_INFO, "StartActivity onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        // Retrieve the widgets from the UI.
        cbStart = (CheckBox) findViewById(R.id.checkbox_start);
        tvName = (TextView) findViewById(R.id.text_view_start_name);
        // If there is no saved state, then...
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                name = extras.getString(NAME);
            }
        }
        // There is a saved state, so restore it...
        else
        {
            name = savedInstanceState.getString(NAME);
        }
        // Set the Player's name.
        tvName.setText(getResources().getString(R.string.player) + " :  " + name);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i(TAG_INFO, "StartActivity onSaveInstanceState()");
        super.onSaveInstanceState(outState);
        outState.putString(NAME, name);
    }

    /**
     * When the button "Start" is pressed, execute the following method.
     */
    public void onClickStart(View view){
        Log.i(TAG_INFO, "Button Start Pressed.");
        // If the checkbox is not checked, then...
        if (!cbStart.isChecked()){
            // Display message.
            Toast.makeText(this, getResources().getString(R.string.msg_checkbox_start), Toast.LENGTH_SHORT).show();
            // Terminate the current method.
            return;
        }
        // Create an intent to start another activity.
        Intent intent = new Intent(this, QuizActivity.class);
        // Add the Name to the intent (ID, value)
        intent.putExtra(NAME, name);
        // Start an instance of the "StartActivity" specified by the intent.
        startActivity(intent);
        // Un-check the checkbox.
        cbStart.setChecked(false);
    }
}
