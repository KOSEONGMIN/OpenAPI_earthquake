package com.example.koseongmin.project01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.Date;

public class SMSBroadcastReceiver extends BroadcastReceiver {
    public static final String TAG = "SMSBroadcastReceiver";
    public SMSBroadcastReceiver(){}

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive() 호출됨");

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Log.i(TAG, "SMS를 수신하였습니다.");
            abortBroadcast();
            Bundle bundle = intent.getExtras();

            Object messages[] = (Object[])bundle.get("pdus");

            SmsMessage smsMessage[] = new SmsMessage[messages.length];

            int smsCount = messages.length;

            for(int i = 0; i < smsCount; i++){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    String format = bundle.getString("format");
                    smsMessage[i] = SmsMessage.createFromPdu((byte[])messages[i], format);
                } else {
                    smsMessage[i] = SmsMessage.createFromPdu((byte[])messages[i]);
                }
            }

            String message = smsMessage[0].getMessageBody().toString();

            boolean isEarthquake = message.matches("\\[기상청\\].*지진발생.*");

            if(isEarthquake){

                Intent myIntent = context.getPackageManager().getLaunchIntentForPackage("com.example.koseongmin.project01");

                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                myIntent.putExtra("message", message);

                myIntent.putExtra("isEarthquake",isEarthquake);

                context.startActivity(myIntent);
            }
        }
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
    }
}
