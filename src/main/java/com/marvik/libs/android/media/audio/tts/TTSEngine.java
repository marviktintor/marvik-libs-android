package com.marvik.libs.android.media.audio.tts;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;

import java.util.Locale;

/**
 * Created by victor on 3/25/2016.
 */
public class TTSEngine implements TextToSpeech.OnInitListener {

    private TextToSpeech textToSpeech;

    private Context context;

    private Locale locale;

    public TTSEngine(Context context, Locale locale) {
        this.context = context;
        this.locale = locale;

        textToSpeech = new TextToSpeech(context, this);
    }

    public Context getContext() {
        return context;
    }

    public Locale getLocale() {
        return locale;
    }

    public void readText(String text) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.ERROR) {
            textToSpeech.setLanguage(getLocale());
        }
    }

    public void onActivityDestroy() {

        if (textToSpeech.isSpeaking())
            textToSpeech.stop();
        textToSpeech.shutdown();
    }

    public void setOnUtteranceListener(UtteranceProgressListener utteranceProgressListener) {
        textToSpeech.setOnUtteranceProgressListener(utteranceProgressListener);
    }

    public boolean isSpeaking() {
        return textToSpeech.isSpeaking();
    }
}
