package net.bplaced.esigala1.colorquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String NAME = "name";
    public static final String TAG_INFO = "Info";

    EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Retrieve the widget from the UI.
        etName = (EditText) findViewById(R.id.edit_text_name);
    }

    // When the button "Quiz" is pressed, execute the following method.
    public void onClickStart(View view){
        Log.i(TAG_INFO, "Button Start Pressed.");
        Log.i(TAG_INFO, "Name = " + etName);
        // If the name is not entered, then...
        if (etName.length() == 0){
            // Display message.
            Toast.makeText(this, getResources().getString(R.string.msg_enter_name), Toast.LENGTH_SHORT).show();
            // Terminate the current method.
            return;
        }
        // Create an intent to start another activity.
        Intent intent = new Intent(this, QuizActivity.class);
        // Add the Name the intent (ID, value)
        intent.putExtra(NAME, etName.getText().toString());
        // Start an instance of the "QuizActivity" specified by the intent.
        startActivity(intent);
    }
}
