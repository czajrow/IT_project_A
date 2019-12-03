package com.example.scheduler;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class AboutActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "AboutActivity";

    TextView text;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        text = findViewById(R.id.test_data_view);
        text.setMovementMethod(new ScrollingMovementMethod());

        activateToolbar(true);

        checkBox = findViewById(R.id.checkBox);
        checkBox.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        checkBox.setChecked(IsDebug.isDebug);
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.checkBox) {
            IsDebug.isDebug = checkBox.isChecked();
            Log.d(TAG, "onClick: checked: " + checkBox.isChecked());
            Log.d(TAG, "onClick: debug: " + IsDebug.isDebug);
        }
    }
}
