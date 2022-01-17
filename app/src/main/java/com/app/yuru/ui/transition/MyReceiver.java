package com.app.yuru.ui.transition;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.MediaController;
import android.widget.Toast;

import com.app.yuru.R;
import com.app.yuru.corescheduler.player.video.ui.VideoActivity;

import static androidx.legacy.content.WakefulBroadcastReceiver.startWakefulService;

public class MyReceiver extends BroadcastReceiver {

    private MediaController ctlr;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm Ringing...", Toast.LENGTH_SHORT).show();
        int requestCode = intent.getIntExtra("REQUEST_CODE", -1);

        if (!intent.getStringExtra("fragment").equalsIgnoreCase("sleep_enhancer_2")) {

            Intent i = new Intent(context, VideoActivity.class);
            //TODO pass URL
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("ques", "");
            context.startActivity(i);

            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.sleep1);
            mediaPlayer.isLooping();
            mediaPlayer.start();

        } else {
            try {
//                MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.whatsapp);
                MediaPlayer mediaPlayer = new MediaPlayer();
                //TODO set URL
                mediaPlayer.setDataSource("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4");
                mediaPlayer.prepare();
                mediaPlayer.start();
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
