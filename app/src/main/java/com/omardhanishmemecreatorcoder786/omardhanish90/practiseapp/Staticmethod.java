package com.omardhanishmemecreatorcoder786.omardhanish90.practiseapp;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by omar on 21-05-2017.
 */
//this method take care of recording and stop recording
public class Staticmethod {

   static MediaRecorder recorder;

    public static void startrecording(){
        recorder = new MediaRecorder();

        final File path =
                Environment.getExternalStoragePublicDirectory
                        (

                                Environment.DIRECTORY_DOCUMENTS + "/recordaudio/"
                        );
        String filepath = path.toString();

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(filepath);

//        recorder.setOnErrorListener(errorListener);
//        recorder.setOnInfoListener(infoListener);

        try {
            recorder.prepare();
            recorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void stoprecording(Context context){
        recorder.stop();
        recorder.reset();
        recorder.release();
        Toast.makeText(context,"audio recording file saved",Toast.LENGTH_SHORT);
        recorder = null;
    }

}
