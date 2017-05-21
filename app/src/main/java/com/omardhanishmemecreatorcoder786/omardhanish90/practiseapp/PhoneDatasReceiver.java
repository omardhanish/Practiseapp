package com.omardhanishmemecreatorcoder786.omardhanish90.practiseapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.aykuttasil.callrecord.CallRecord;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by omar on 20-05-2017.
 */


//I tried recording the call but I got some issue , Below commented code are the one which i used to record

public class PhoneDatasReceiver extends BroadcastReceiver {

    static boolean flag = false;
    static long start_time, end_time;
    CallRecord callRecord;
    String duration , outgoingcall;
    SharedPreferences sharedPreferences;

    GsonObject details = new GsonObject();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
//        outgoingcall =intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        if (action.equalsIgnoreCase("android.intent.action.PHONE_STATE")) {
            if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
                    TelephonyManager.EXTRA_STATE_RINGING)) {
                start_time = System.currentTimeMillis();
//                callRecord = new CallRecord.Builder(context)
//                        .setRecordFileName("omar")
//                        .setRecordDirName("dhanish")
//                        .build();
//                callRecord.startCallReceiver();
            }
            if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
                    TelephonyManager.EXTRA_STATE_IDLE)) {
                end_time = System.currentTimeMillis();
                long total_time = end_time - start_time;
//                callRecord.stopCallReceiver();

                 duration = new Long(total_time).toString();

                sharedPreferences = context.getSharedPreferences("MyPrefs", MODE_PRIVATE);
                String tonumber = sharedPreferences.getString(ProfileActivity.To, "nothing");
                String toname = sharedPreferences.getString(ProfileActivity.ToName, "nothing");
                TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
                String fromnumber = tm.getDeviceId();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = df.format(c.getTime());

                details.setFrom(fromnumber);
                details.setTo(tonumber);
                details.setDuration(duration);
                details.setFirstName(toname);
                details.setDatetime(formattedDate);

                Gson gson = new Gson();

                String json = gson.toJson(details);

                writeToFile(context,json,tonumber);

//                Toast.makeText(context, gson.toJson(details), Toast.LENGTH_LONG).show();
            }


        }

    }



    public void writeToFile(Context context ,String data , String number)
    {

        final File path =
                Environment.getExternalStoragePublicDirectory
                        (

                                Environment.DIRECTORY_DOCUMENTS + "/practiseapp/"
                        );


        if(!path.exists())
        {

            path.mkdirs();
        }

        final File file = new File(path, number + "json.txt");

        try
        {
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);

            myOutWriter.close();

            fOut.flush();
            fOut.close();
            Toast.makeText(context,"JSON file successfully saved in the external directory", Toast.LENGTH_SHORT).show();
        }
        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

}
