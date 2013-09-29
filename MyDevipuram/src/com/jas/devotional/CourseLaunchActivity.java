package com.jas.devotional;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CourseLaunchActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_launch);
		Log.d(Constants.DEVICE_DEBUG_APP_CODE, "Loading login screen");
		final Button  loginButton = (Button) findViewById(R.id.button1);
		loginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// 1. Instantiate an AlertDialog.Builder with its constructor
				AlertDialog.Builder builder = new AlertDialog.Builder(loginButton.getContext());
				
				// Add the buttons
				builder.setPositiveButton(R.string.got_it, new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				             
				        	   NotificationCompat.Builder mBuilder =
				        		        new NotificationCompat.Builder(loginButton.getContext())
				        		        .setSmallIcon(R.drawable.ic_launcher)
				        		        .setContentTitle(getString(R.string.notify_title))
				        		        .setContentText(getString(R.string.notify_text))
				        		        .setAutoCancel(true);
				        	   			
				        		// Creates an explicit intent for an Activity in your app
				        		Intent resultIntent = new Intent(loginButton.getContext(), LearningActivity.class);

				        		// The stack builder object will contain an artificial back stack for the
				        		// started Activity.
				        		// This ensures that navigating backward from the Activity leads out of
				        		// your application to the Home screen.
				        		TaskStackBuilder stackBuilder = TaskStackBuilder.create(loginButton.getContext());
				        		// Adds the back stack for the Intent (but not the Intent itself)
				        		stackBuilder.addParentStack(LearningActivity.class);
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
				        		Log.d(Constants.DEVICE_DEBUG_APP_CODE, "Sending Notification");
				        		mNotificationManager.notify(1, mBuilder.build());
				        		

				           }
				       });
				// 2. Chain together various setter methods to set the dialog characteristics
				String message = getString(R.string.dialog_cl_message); 
				builder.setMessage(message)
				       .setTitle(R.string.dialog_cl_title);

				// 3. Get the AlertDialog from create()
				AlertDialog dialog = builder.create();
				dialog.show();
				
				
			}
		});
	}

}
