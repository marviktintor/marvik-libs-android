package com.marvik.libs.android.audio.player;

import android.media.MediaPlayer;

/**
 * Created by victor on 3/26/2016.
 */
public class AudioPlayer {

    private MediaPlayer mediaPlayer;

    public AudioPlayer() {
        mediaPlayer = new MediaPlayer();

    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void playAudio(String filename) throws Exception {
        getMediaPlayer().setDataSource(filename);
        getMediaPlayer().prepare();
        getMediaPlayer().start();
    }

    public void pausePlayer() {
        getMediaPlayer().pause();
    }

    public void resetPlayer() {
        getMediaPlayer().reset();
    }

    public void stopPlayer() {
        if (mediaPlayer.isPlaying()) {
            pausePlayer();
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }

    public void shutdownPlayer() {
        stopPlayer();
        getMediaPlayer().release();
    }

    public void restartMediaPlayer() {
        new AudioPlayer();
    }
}
