package com.bramdehart.tele2instantbundelbooster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * A broadcast receiver who listens for incoming SMS
 */
public class SmsBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            String smsSender = "";
            String smsBody = "";
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                smsBody += smsMessage.getMessageBody();
                smsSender = smsMessage.getOriginatingAddress();
            }

            if (BundelBoosterHelper.isDataWarning(smsSender, smsBody)) {
                BundelBoosterHelper.sendBundelBoosterSms();
                Toast.makeText(context, "Activated Tele2 Unlimited Bundel Booster", Toast.LENGTH_LONG).show();
            }
        }
    }
}
