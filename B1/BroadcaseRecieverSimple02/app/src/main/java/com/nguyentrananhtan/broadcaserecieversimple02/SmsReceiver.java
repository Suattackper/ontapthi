package com.nguyentrananhtan.broadcaserecieversimple02;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.Date;

public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        assert  bundle != null;
        Object[] arrMessages = (Object[]) bundle.get("pdus");
        assert  arrMessages != null;
        String phone, time, content;
        long date;
        byte[] bytes;
        for (int i = 0; i< arrMessages.length;i++){
            bytes = (byte[]) arrMessages[i];
            SmsMessage message = SmsMessage.createFromPdu(bytes);
            phone = message.getDisplayOriginatingAddress();
            date = message.getTimestampMillis();
            content = message.getMessageBody();
            Toast.makeText(context, "Phone: " + phone + "\nDate: " + date + "\nContent: " + content, Toast.LENGTH_LONG).show();
        }

    }
}
