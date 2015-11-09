package com.example.notificationapp;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
 
public class NotifyMessage extends Activity {
	
	private static final int NOTIFY_ME_ID=1337;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView txt=new TextView(this);
         
        txt.setText("Activity after click on notification");
        setContentView(txt);
        final NotificationManager mgr=(NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        mgr.cancel(NOTIFY_ME_ID);
    }
}