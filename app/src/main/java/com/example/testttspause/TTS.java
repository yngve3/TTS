package com.example.testttspause;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class TTS implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;
    private Locale locale;
    private Context context;

    private float speechRate;
    private String signSeparation;
    private int pauseDurationMc;

    private String queueText = "";
    private boolean initialized;

    public static class Builder {
        private Locale locale;
        private Context context;

        private float speechRate = 1.0f;
        private String signSeparation = "!";
        private int pauseDurationMc = 1000;

        public Builder(Context context, Locale locale) {
            this.context = context;
            this.locale = locale;
        }

        public Builder setSpeechRate(float speechRate) {
            this.speechRate = speechRate;
            return this;
        }

        public Builder setSignSeparation(String signSeparation) {
            this.signSeparation = signSeparation;
            return this;
        }

        public Builder setPauseDurationMc(int pauseDurationMc) {
            this.pauseDurationMc = pauseDurationMc;
            return this;
        }

        public TTS build() {
            return new TTS(this);
        }
    }

    private TTS(Builder builder) {
        this.context = builder.context;
        this.locale = builder.locale;
        this.signSeparation = builder.signSeparation;
        this.speechRate = builder.speechRate;
        this.pauseDurationMc = builder.pauseDurationMc;

        tts = new TextToSpeech(this.context, this);
    }

    public String getSignSeparation() {
        return signSeparation;
    }

    @Override
    public void onInit(int i) {
        if (i == TextToSpeech.SUCCESS) {

            tts.setLanguage(locale);
            tts.setSpeechRate(speechRate);

            initialized = true;

            if (queueText != null) {
                speak(queueText);
            }
        } else {
            throw new IllegalArgumentException("TextToSPeach cannot be initialized");
        }
    }

    public void speak(String s) {
        if (!initialized) {
            queueText = s;
            return;
        }
        queueText = null;

        //String[] t = s.split("(?=" + signSeparation + ")");
        //String[] t = s.split("(?=!)");
        String[] t = s.split(signSeparation);

        for (String str : t) {
            if (str.equals(""))
                tts.playSilentUtterance(pauseDurationMc, TextToSpeech.QUEUE_ADD, null);
            else
                tts.speak(str, TextToSpeech.QUEUE_ADD, null, null);
        }
    }
}
