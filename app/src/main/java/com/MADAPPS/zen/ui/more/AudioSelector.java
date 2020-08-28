package com.MADAPPS.zen.ui.more;

import android.media.MediaPlayer;

import com.MADAPPS.zen.R;

import java.util.ArrayList;

public class AudioSelector extends MoreFragment {
    private static ArrayList<String> names;
    private MediaPlayer player;
    private static int[] audioList;

    public AudioSelector(){

    }

    public static void setAudioArray(){
        //audioList  = new int[]{0, R.raw.rain, R.raw.beach, R.raw.rainforest};
        names = new ArrayList<String>();
        }

    public void startAudio() {
        setAudioArray();
        if (audioPos != 0) {
        if (player == null) {
            player = MediaPlayer.create(moreActivity, audioList[audioPos]);
        }
            playMp3();
        }

    }


    private void playMp3(){
        if(player != null) {
            player.start();
        }
    }

    public void pauseAudio() {
        if (player != null) {
            player.pause();
        }
    }

    /**
     * Stops the audio
     */
    public void stopAudio(){
        if(player != null) {
            player.release();
            player =  null;
        }

    }





}
