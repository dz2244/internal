package com.example.internal;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    /**
     * The textEt.
     */
    EditText et;

    /**
     * The displayTv.
     */
    TextView tv;

    /**
     * The FILENAME.
     */
    private final String FILENAME = "inttest.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.et);
        tv = findViewById(R.id.tv);

        String textFromFile = getFileText();
        tv.setText(textFromFile);
    }

    /**
     * Gets text from user and add it to the internal file and displays it in TextView.
     *
     * @param view the view
     */
    public void saveBtn(View view) {
        try {
            FileOutputStream fOS = openFileOutput(FILENAME, MODE_APPEND);
            OutputStreamWriter oSW = new OutputStreamWriter(fOS);
            BufferedWriter bW = new BufferedWriter(oSW);

            bW.write(et.getText().toString());
            bW.close();
            oSW.close();
            fOS.close();

            tv.setText(getFileText());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Resets text in internal file and displays it in a textView.
     *
     * @param view the view
     */
    public void resetBtn(View view) {
        try {
            FileOutputStream fOS = openFileOutput(FILENAME, MODE_PRIVATE);
            OutputStreamWriter oSW = new OutputStreamWriter(fOS);
            BufferedWriter bW = new BufferedWriter(oSW);
            bW.write("");
            bW.close();
            oSW.close();
            fOS.close();

            et.setText("");
            tv.setText("");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves the text from user and closes the app.
     *
     * @param view the view
     */
    public void exitApp(View view) {
        saveBtn(view);
        finish();
    }

    /**
     * Gets text from internal file.
     * @return the internal file text.
     */
    public String getFileText() {
        String text;
        try {
            FileInputStream fIS = openFileInput(FILENAME);
            InputStreamReader iSR = new InputStreamReader(fIS);
            BufferedReader bR = new BufferedReader(iSR);
            StringBuilder sB = new StringBuilder();
            String line;

            while ((line = bR.readLine()) != null) {
                sB.append(line).append("\n");
            }
            text = sB.toString();

            bR.close();
            iSR.close();
            fIS.close();
        } catch (FileNotFoundException e) {
            text = "";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return text;
    }


    /**
     * Creates the options menu on screen
     *
     * @param menu the menu
     * @return ture
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Checks if the clicked menuItem is R.id.menuCred
     *
     * @param item a menuItem
     * @return ture
     */
    @Override
    public boolean onOptionsItemSelected(@Nullable MenuItem item) {
        if (item == null) return false;

        int id = item.getItemId();

        if (id == R.id.credits) {
            Intent si = new Intent(this, Credits.class);
            startActivity(si);
        }
        return true;
    }


}