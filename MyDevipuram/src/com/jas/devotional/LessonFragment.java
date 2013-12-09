package com.jas.devotional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LessonFragment extends Fragment implements OnClickListener {

	private static int currentLesson = 1;

	private static MediaPlayer mediaPlayer;

	private int currentPosition = 0;

	long userId;

	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

	public void setMediaPlayer(MediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
	}

	Dialog dialog;
	Button play;
	Button btnGotIt;

	@TargetApi(11)
	@Override
	public void onCreate(Bundle savedInstanceState) {

		if (savedInstanceState != null
				&& savedInstanceState.containsKey("currentPosition")) {
			currentPosition = savedInstanceState.getInt("currentPosition");
		}
		super.onCreate(savedInstanceState);

		// setHasOptionsMenu(true);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle b) {

		SharedPreferences prefs = getActivity().getSharedPreferences(
				"user_details", getActivity().MODE_PRIVATE);
		userId = prefs.getLong("userId", -1);

		Log.d(Constants.DEVICE_DEBUG_APP_CODE,
				"Creating view of lessons screen...");
		View v = inflater.inflate(R.layout.fragment_lessons, vg, false);

		play = (Button) v.findViewById(R.id.playPause);

		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			Log.d(Constants.DEVICE_DEBUG_APP_CODE, "Setting pause button");
			play.setBackgroundResource(R.drawable.pause_btn);

		} else {
			play.setBackgroundResource(R.drawable.play_button);
		}

		play.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d(Constants.DEVICE_DEBUG_APP_CODE, "clicked play/pause button");
				if (currentLesson == 1) {
					playAudio(currentLesson, R.raw.lesson01);
				} else if (currentLesson == 2) {
					Log.d(Constants.DEVICE_DEBUG_APP_CODE,
							"Playing video file...");
					/*if (mediaPlayer != null) {
						if (mediaPlayer.isPlaying()) {
							mediaPlayer.stop();
						}

						mediaPlayer.release();
						mediaPlayer = null;
					}*/
					playAudio(currentLesson,R.raw.khadgamala);
					/*
					 * Intent videoIntent = new Intent(getActivity(),
					 * VideoPlayerActivity.class);
					 * 
					 * videoIntent.putExtra("videoFileName", "phonetics");
					 * startActivity(videoIntent);
					 */
				} else {
					Log.d(Constants.DEVICE_DEBUG_APP_CODE, "Showing alert.");

					showCustomDialog();

				}

			}
		});

		Button previous = (Button) v.findViewById(R.id.previous);

		previous.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (currentLesson > 1) {
					currentLesson--;

					TextView lesson = (TextView) LessonFragment.this
							.getActivity().findViewById(R.id.lessonNumber);
					lesson.setText("Lesson#" + currentLesson);
					TextView pageCounter = (TextView) LessonFragment.this
							.getActivity().findViewById(R.id.pageCounter);
					pageCounter.setText(currentLesson + "/10");

				}
				if (mediaPlayer != null) {
					if(mediaPlayer.isPlaying()){
						mediaPlayer.pause();
						mediaPlayer.stop();
						mediaPlayer.release();
					}else{
						mediaPlayer.stop();
						mediaPlayer.release();
					}
					
					mediaPlayer=null;
					play.setBackgroundResource(R.drawable.play_button);
				}

			}
		});

		Button next = (Button) v.findViewById(R.id.next);

		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (currentLesson < 10) {
					currentLesson++;

					TextView lesson = (TextView) LessonFragment.this
							.getActivity().findViewById(R.id.lessonNumber);
					lesson.setText("Lesson#" + currentLesson);
					TextView pageCounter = (TextView) LessonFragment.this
							.getActivity().findViewById(R.id.pageCounter);
					pageCounter.setText(currentLesson + "/10");

				}

				if (mediaPlayer != null) {
					if(mediaPlayer.isPlaying()){
						mediaPlayer.pause();
						mediaPlayer.stop();
						mediaPlayer.release();
					}else{
						mediaPlayer.stop();
						mediaPlayer.release();
					}
					
					mediaPlayer=null;
					play.setBackgroundResource(R.drawable.play_button);
				}

			}
		});

		TextView lessonName = (TextView) v.findViewById(R.id.lessonNumber);
		lessonName.setText("Lesson#" + currentLesson);

		TextView pageCounter = (TextView) v.findViewById(R.id.pageCounter);
		pageCounter.setText(currentLesson + "/10");

		return v;

	}

	@Override
	public void onPause() {
		Log.d(Constants.DEVICE_DEBUG_APP_CODE, "On Pause called");
		super.onPause();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		Log.d(Constants.DEVICE_DEBUG_APP_CODE, "On Stop called");
		/*
		 * if (mediaPlayer != null && mediaPlayer.isPlaying()) {
		 * 
		 * mediaPlayer.stop();
		 * 
		 * }
		 */

		super.onStop();
	}

	protected void showCustomDialog() {

		dialog = new Dialog(LessonFragment.this.getActivity(),
				android.R.style.Theme_DeviceDefault_Dialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		dialog.setCancelable(true);
		dialog.setContentView(R.layout.dialog_course_launch);

		TextView dialogTitle = (TextView) dialog.findViewById(R.id.dialogTitle);
		dialogTitle.setText(R.string.dialog_title);
		TextView dialogContent = (TextView) dialog
				.findViewById(R.id.dialogContent);

		String message = getString(R.string.dialog_message, currentLesson - 1,
				currentLesson);
		dialogContent.setText(message);

		btnGotIt = (Button) dialog.findViewById(R.id.btn_gotit);
		btnGotIt.setText(R.string.net_down_dialog_confirm);
		btnGotIt.setOnClickListener(this);

		dialog.show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.btn_gotit:
			dialog.dismiss();
			break;
		default:
			break;
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub

		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			outState.putInt("currentPosition", mediaPlayer.getCurrentPosition());

		}

		super.onSaveInstanceState(outState);
	}

	private class AuditTask extends AsyncTask<String, Void, String> {

		private String json = "";

		@Override
		protected String doInBackground(String... urls) {
			// TODO Auto-generated method stub

			HttpClient client = new DefaultHttpClient();

			HttpGet get = new HttpGet(urls[0]);

			get.setHeader("Accept", "application/json");
			JSONObject obj = new JSONObject();
			try {

				HttpResponse response = client.execute(get);
				HttpEntity e = response.getEntity();

				// Grab the response
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(
								response.getEntity().getContent(), "UTF-8"));
				json = reader.readLine();

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnknownHostException e) {
				json = "NO_INTERNET";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return json;
		}

	}

	private void playAudio(int lesson, int lessonResource) {

		Log.d(Constants.DEVICE_DEBUG_APP_CODE, "Playing audio file...");
		StringBuilder auditUri = new StringBuilder(Constants.WS_URL
				+ "/users/audit");
		auditUri.append("?userId=" + userId);

		final String tempUri = auditUri.toString();

		if (mediaPlayer == null) {

			mediaPlayer = MediaPlayer.create(LessonFragment.this.getActivity(),
					lessonResource);
			mediaPlayer.setWakeMode(getActivity(),
					PowerManager.PARTIAL_WAKE_LOCK);
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

				public void onCompletion(MediaPlayer mp) {

					Log.d(Constants.DEVICE_DEBUG_APP_CODE,
							"Audio 1 playing completed");

					if (userId > 0) {
						// Invoke the audit task
						AuditTask t = (AuditTask) new AuditTask()
								.execute(tempUri + "&eventId="+2*currentLesson+2);
						String json = "";

						try {
							json = t.get();
						} catch (Exception e) {
							e.printStackTrace();// ignore
												// audit
												// exception.
						}
					}

				}
			});
			Log.d(Constants.DEVICE_DEBUG_APP_CODE, "Playing audio 1");
			if (userId > 0) {
				// Invoke the audit task
				AuditTask t = (AuditTask) new AuditTask().execute(tempUri
						+ "&eventId="+2*currentLesson+1);
				String json = "";

				try {
					json = t.get();
				} catch (Exception e) {
					e.printStackTrace();// ignore audit exception.
				}

			}

			mediaPlayer.start(); // no need to call prepare();
									// create() does that for you

			play.setBackgroundResource(R.drawable.pause_btn);

		} else if (mediaPlayer.isPlaying()) {
			Log.d(Constants.DEVICE_DEBUG_APP_CODE, "Currently playing...setting to pause");
			mediaPlayer.pause();
			play.setBackgroundResource(R.drawable.play_button);
		} else if (!mediaPlayer.isPlaying()) {
			Log.d(Constants.DEVICE_DEBUG_APP_CODE, "Currently Paused...setting to playing");
			mediaPlayer.start();
			play.setBackgroundResource(R.drawable.pause_btn);

		}

	}

}
