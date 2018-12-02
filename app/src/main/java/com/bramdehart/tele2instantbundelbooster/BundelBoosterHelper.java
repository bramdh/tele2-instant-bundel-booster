package com.bramdehart.tele2instantbundelbooster;

import android.telephony.SmsManager;

/**
 * Constants helper class
 */

public class BundelBoosterHelper {

    private static final String DATA_WARNING_500 = "Beste klant, je hebt nog 500 MB van je dagelijkse databundel over.";
    private static final String DATA_WARNING_100 = "Beste klant, je hebt 100% van je dagelijkse databundel gebruikt.";
    private static final String SMS_SENDER = "Tele2"; // Tele2 Data warning
    private static final String SMS_RECEIVER = "1280"; // Tele2 Bundel Booster
    private static final String SMS_BODY = "Nog 1gb";

    public static void sendBundelBoosterSms() {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(SMS_RECEIVER, null, SMS_BODY, null, null);
    }

    public static Boolean isDataWarning(String smsSender, String smsBody) {
        return smsSender.equals(SMS_SENDER) && (smsBody.startsWith(BundelBoosterHelper.DATA_WARNING_500) || smsBody.startsWith(BundelBoosterHelper.DATA_WARNING_100));
    }
}
