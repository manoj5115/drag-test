package com.example.notificationapp;

import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;

public class NotificationAlert extends Activity {

	private static final int NOTIFY_ME_ID=1337;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*setContentView(R.layout.activity_main);

		final NotificationManager mgr=(NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification note=new Notification(R.drawable.ic_launcher,
				"Android Example Status message!",
				System.currentTimeMillis());

		// This pending intent will open after notification click
		PendingIntent i=PendingIntent.getActivity(this, 0,
				new Intent(this, NotifyMessage.class),
				0);

		note.setLatestEventInfo(this, "Notification title",
				"Click to open App", i);

		//After uncomment this line you will see number of notification arrived
		//note.number=2;
		mgr.notify(NOTIFY_ME_ID, note);*/
		
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.ic_launcher)
		        .setContentTitle("My notification")
		        .setContentText("Hello World!");
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, NotifyMessage.class);

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(NotifyMessage.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
		    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(NOTIFY_ME_ID, mBuilder.build());


	}
}