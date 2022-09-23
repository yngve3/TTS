package com.example.testttspause;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button speakBtn;
    private Button pauseBtn;
    private EditText et;
    private TTS tts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speakBtn = findViewById(R.id.speakBtn);
        pauseBtn = findViewById(R.id.pauseBtn);
        et = findViewById(R.id.editText);

        tts = new TTS.Builder(this, getResources().getConfiguration().locale)
                .build();

        speakBtn.setOnClickListener(view -> tts.speak(et.getText().toString()));
        pauseBtn.setOnClickListener(view -> et.append(tts.getSignSeparation()));
    }

}