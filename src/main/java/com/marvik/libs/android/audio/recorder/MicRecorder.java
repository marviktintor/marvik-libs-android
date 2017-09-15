package com.marvik.libs.android.audio.recorder;

import android.media.MediaRecorder;

import com.marvik.libs.android.io.handler.FilesHandler;

import java.io.File;
import java.io.IOException;



/**
 * Created by victor on 3/25/2016.
 */
public class MicRecorder {

    private boolean recording;

    private MediaRecorder mediaRecorder;


    public MediaRecorder getMediaRecorder() {
        return mediaRecorder;
    }

    public void recordAudio(String outputFile) {
        mediaRecorder = new MediaRecorder();
        getMediaRecorder().setAudioSource(MediaRecorder.AudioSource.MIC);
        getMediaRecorder().setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        getMediaRecorder().setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
        new FilesHandler().createDirectories(new File(outputFile).getParentFile());
        getMediaRecorder().setOutputFile(outputFile);
        record();
    }

    public void stopRecording() {
        if (getMediaRecorder() != null) {
            setRecording(false);
            getMediaRecorder().stop();
            getMediaRecorder().release();
        }
    }

    private void record() {

        if (mediaRecorder != null) {
            try {
                setRecording(true);
                mediaRecorder.prepare();
                mediaRecorder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public boolean isRecording() {
        return recording;
    }

    public void setRecording(boolean recording) {
        this.recording = recording;
    }
}
