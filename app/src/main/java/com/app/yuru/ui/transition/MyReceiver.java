package com.app.yuru.ui.transition;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.MediaController;
import android.widget.Toast;

import com.app.yuru.R;
import com.app.yuru.corescheduler.player.video.ui.VideoActivity;
import com.app.yuru.corescheduler.utils.Constants;

import java.io.IOException;

import static androidx.legacy.content.WakefulBroadcastReceiver.startWakefulService;

public class MyReceiver extends BroadcastReceiver {

    private MediaController ctlr;

    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "Alarm Ringing...", Toast.LENGTH_SHORT).show();
        int requestCode = intent.getIntExtra("REQUEST_CODE", -1);

        if (intent.getStringExtra("fragment").equalsIgnoreCase("sleep1")) {

//            Intent i = new Intent(context, VideoActivity.class);
//            //TODO pass URL
//            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            i.putExtra(Constants.VIDEO_LINK, intent.getStringExtra("url"));
//            context.startActivity(i);



//            MediaPlayer mMediaPlayer = MediaPlayer.create(context, R.raw.sleep1);
//            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            mMediaPlayer.setLooping(true);
//            mMediaPlayer.start();

            MediaPlayer mediaPlayer = new MediaPlayer();

            // below line is use to set the audio
            // stream type for our media player.
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            // below line is use to set our
            // url to our media player.
            try {
                String url = intent.getStringExtra("url");
                mediaPlayer.setDataSource(url);
                // below line is use to prepare
                // and start our media player.
                mediaPlayer.prepare();
                mediaPlayer.start();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            try {
                Intent i = new Intent(context, VideoActivity.class);
                //TODO pass URL
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(Constants.VIDEO_LINK, intent.getStringExtra("url"));
                context.startActivity(i);

             /*   String url = intent.getStringExtra("url");
//                MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.whatsapp);
                MediaPlayer mediaPlayer = new MediaPlayer();
                //TODO set URL
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare();
                mediaPlayer.start();*/

                ComponentName comp = new ComponentName(context.getPackageName(),
                        MyReceiver.class.getName());
                startWakefulService(context, (intent.setComponent(comp)));
                setResultCode(Activity.RESULT_OK);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


//        WakeUpProgram inst = WakeUpProgram.instance();
//        inst.setAlarmText("Alarm! Wake up! Wake up!");

        //this will sound the alarm tone
        //this will sound the alarm once, if you wish to
        //raise alarm in loop continuously then use MediaPlayer and setLooping(true)
//        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        if (alarmUri == null) {
//            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        }
//        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
//        ringtone.play();

        //this will send a notification message
        ComponentName comp = new ComponentName(context.getPackageName(),
                MyReceiver.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}
