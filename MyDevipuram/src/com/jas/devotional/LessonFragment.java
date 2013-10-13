package com.jas.devotional;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class LessonFragment extends Fragment implements OnClickListener {

	private static int currentLesson = 1;

	private MediaPlayer mediaPlayer;
	 Dialog dialog;
	  Button btnGotIt;

	@TargetApi(11)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//setHasOptionsMenu(true);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle b) {

		Log.d(Constants.DEVICE_DEBUG_APP_CODE,
				"Creating view of lessons screen...");
		View v = inflater.inflate(R.layout.fragment_lessons, vg, false);
		
		final Button play = (Button) v.findViewById(R.id.playPause);

		play.setBackgroundResource(R.drawable.play_button);
		play.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (currentLesson == 1) {
					Log.d(Constants.DEVICE_DEBUG_APP_CODE,
							"Playing audio file...");

					if (mediaPlayer == null) {
						mediaPlayer = MediaPlayer.create(
								LessonFragment.this.getActivity(),
								R.raw.khadgamala);
						mediaPlayer.start(); // no need to call prepare();
												// create() does that for you
						
						play.setBackgroundResource(R.drawable.pause_btn);

					} else if (mediaPlayer.isPlaying()) {
						mediaPlayer.pause();
						play.setBackgroundResource(R.drawable.play_button);
					} else if (!mediaPlayer.isPlaying()) {
						mediaPlayer.start();
						play.setBackgroundResource(R.drawable.pause_btn);

					}

				} else if (currentLesson == 2) {
					Log.d(Constants.DEVICE_DEBUG_APP_CODE,
							"Playing video file...");
					if (mediaPlayer != null) {
						mediaPlayer.stop();
						mediaPlayer.release();
						mediaPlayer = null;
					}

					// Uri data =
					// Uri.parse("android.resource://com.jas.devotional/"+R.raw.gupanishad);
					// Intent i = new Intent(Intent.ACTION_VIEW);
					// i.setDataAndType(data, "video/*");

					// startActivity(i);

					Intent videoIntent = new Intent(getActivity(),
							VideoPlayerActivity.class);

					videoIntent.putExtra("videoFileName", "phonetics");
					startActivity(videoIntent);
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
				if (mediaPlayer != null && mediaPlayer.isPlaying()) {
					mediaPlayer.pause();
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

				if (mediaPlayer != null && mediaPlayer.isPlaying()) {
					mediaPlayer.pause();
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
		// TODO Auto-generated method stub
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
			
		}
		
		super.onPause();
	}

	protected void showCustomDialog() {

  		dialog = new Dialog(LessonFragment.this.getActivity(),
  				android.R.style.Theme_DeviceDefault_Dialog);
  		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

  		dialog.setCancelable(true);
  		dialog.setContentView(R.layout.dialog_course_launch);
  		
  		TextView dialogTitle = (TextView) dialog.findViewById(R.id.dialogTitle);
  		dialogTitle.setText(R.string.dialog_title);
  		TextView dialogContent = (TextView) dialog.findViewById(R.id.dialogContent);
  		
  		String message = getString(R.string.dialog_message,
				currentLesson - 1, currentLesson);
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
	
	
	
	
	    
	    
	  

}
