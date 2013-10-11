package com.jas.devotional;



import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class CourseLaunchActivity extends Activity implements OnClickListener{
	
	Button btnGotIt,submitButton ;
	Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_launch);
		Log.d(Constants.DEVICE_DEBUG_APP_CODE, "Loading course launch screen");
		// TODO: Move this enum sometime

		Typeface regularFace = Typeface.createFromAsset(
				CourseLaunchActivity.this.getAssets(), "fonts/Roboto-Regular.ttf");
		Typeface boldFace = Typeface.createFromAsset(
				CourseLaunchActivity.this.getAssets(), "fonts/Roboto-Bold.ttf");
		Typeface lightFace = Typeface.createFromAsset(
				CourseLaunchActivity.this.getAssets(), "fonts/Roboto-Light.ttf");

		TextView courseHeader = (TextView) findViewById(R.id.CourseLaunchHeaderText);
		courseHeader.setTypeface(boldFace);

		TextView courseValueQuestion = (TextView) findViewById(R.id.CourseValueQuestion);
		courseValueQuestion.setTypeface(boldFace);

		TextView courseValueAnswer = (TextView) findViewById(R.id.CourseValueAnswer);
		courseValueAnswer.setTypeface(lightFace);

		TextView courseDemandsQuestion = (TextView) findViewById(R.id.CourseDemandsQuestion);
		courseDemandsQuestion.setTypeface(boldFace);

		TextView courseDemandsAnswer = (TextView) findViewById(R.id.CourseDemandsAnswer);
		courseDemandsAnswer.setTypeface(lightFace);

		TextView courseInstructionsQuestion = (TextView) findViewById(R.id.CourseInstructionsQuestion);
		courseInstructionsQuestion.setTypeface(boldFace);

		TextView courseInstructionsAnswer = (TextView) findViewById(R.id.CourseInstructionsAnswer);
		courseInstructionsAnswer.setTypeface(lightFace);
		// TODO: Fix the color its not red.
		TextView courseTitle = (TextView) findViewById(R.id.CourseTitle);
		courseTitle.setTextColor(Color.RED);
		courseTitle.setTypeface(boldFace);

		 submitButton = (Button) findViewById(R.id.submitButton);
		submitButton.setTextColor(Color.RED);
		submitButton.setTypeface(boldFace);
		
		submitButton.setOnClickListener(this);

	
		
	}
	
	protected void showCustomDialog() {

		dialog = new Dialog(CourseLaunchActivity.this,
				android.R.style.Theme_DeviceDefault_Dialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		dialog.setCancelable(true);
		dialog.setContentView(R.layout.dialog_course_launch);

		
		btnGotIt = (Button) dialog.findViewById(R.id.btn_gotit);
		btnGotIt.setOnClickListener(this);

		

		dialog.show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.submitButton:
			showCustomDialog();
			break;
		case R.id.btn_gotit:
			
		

					NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
							submitButton.getContext())
							.setSmallIcon(R.drawable.small_logo)
							.setContentTitle(
									getString(R.string.notify_title))
							.setContentText(
									getString(R.string.notify_text))
							.setAutoCancel(true);

					// Creates an explicit intent for an Activity in
					// your app
					Intent resultIntent = new Intent(submitButton
							.getContext(), LearningActivity.class);

					// The stack builder object will contain an
					// artificial back stack for the
					// started Activity.
					// This ensures that navigating backward from
					// the Activity leads out of
					// your application to the Home screen.
					TaskStackBuilder stackBuilder = TaskStackBuilder
							.create(submitButton.getContext());
					// Adds the back stack for the Intent (but not
					// the Intent itself)
					stackBuilder
							.addParentStack(HomeActivity.class);
					// Adds the Intent that starts the Activity to
					// the top of the stack
					stackBuilder.addNextIntent(resultIntent);
					PendingIntent resultPendingIntent = stackBuilder
							.getPendingIntent(
									0,
									PendingIntent.FLAG_UPDATE_CURRENT);
					mBuilder.setContentIntent(resultPendingIntent);
					NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
					// mId allows you to update the notification
					// later on.
					Log.d(Constants.DEVICE_DEBUG_APP_CODE,
							"Sending Notification");
					mNotificationManager.notify(1, mBuilder.build());

			dialog.dismiss();
			break;
		 default:
			break;
			
		}
		
	}

}
