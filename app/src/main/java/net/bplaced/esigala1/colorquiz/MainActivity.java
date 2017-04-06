package net.bplaced.esigala1.colorquiz;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String TAG_INFO = "Info";
    public static final String NAME = "name";

    EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Display the ActionBar Icon...
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        setTitle("  " + getString(R.string.app_name));
        // Retrieve the widget from the UI.
        etName = (EditText) findViewById(R.id.edit_text_name);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((TextView)findViewById(R.id.text_view_welcome)).setLetterSpacing((float)0.2);
        }
    }

    // When the button "Quiz" is pressed, execute the following method.
    public void onClickQuiz(View view){
        Log.i(TAG_INFO, "Button Quiz Pressed.");
        // If the name is not entered, then...
        if (etName.length() == 0){
            // Display message.
            Toast.makeText(this, getResources().getString(R.string.msg_enter_name), Toast.LENGTH_SHORT).show();
            // Terminate the current method.
            return;
        }
        // Create an intent to start another activity.
        Intent intent = new Intent(this, StartActivity.class);
        // Add the Name to the intent (ID, value)
        intent.putExtra(NAME, etName.getText().toString());
        // Start an instance of the "StartActivity" specified by the intent.
        startActivity(intent);
    }
}
