package com.jas.devotional;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LessonFragment extends Fragment   {
	
	
	private static int currentLesson = 1;
	
	
	private MediaPlayer mediaPlayer;
	  private MediaController mediaController;
	  private String audioFile;
	

	@TargetApi(11)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle b){
		
		Log.d(Constants.DEVICE_DEBUG_APP_CODE,"Creating view of lessons screen...");
		View v = inflater.inflate(R.layout.fragment_lessons, vg,false);
		
		RelativeLayout playerFrame = (RelativeLayout) v.findViewById(R.id.playerControl);
		
		final Button play = new Button(getActivity());
		 
		 
		 
		 play.setBackgroundResource(R.drawable.play);
		 play.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(currentLesson ==1 ){
					Log.d(Constants.DEVICE_DEBUG_APP_CODE,"Playing audio file...");
					
					if(mediaPlayer == null){
						 mediaPlayer = MediaPlayer.create(LessonFragment.this.getActivity(), R.raw.guruwheel);
						 mediaPlayer.start(); // no need to call prepare(); create() does that for you
						play.setBackgroundResource(R.drawable.pause);
						
					}else if(mediaPlayer.isPlaying()){
						mediaPlayer.pause();
						play.setBackgroundResource(R.drawable.play);
					}else if(!mediaPlayer.isPlaying()) {
						mediaPlayer.start();
						play.setBackgroundResource(R.drawable.pause);
						
						
					}
					
				}else if(currentLesson == 2){
					Log.d(Constants.DEVICE_DEBUG_APP_CODE,"Playing video file...");
					if(mediaPlayer!=null){
						mediaPlayer.stop();
						mediaPlayer.release();
						mediaPlayer = null;
					}
					
					//Uri data = Uri.parse("android.resource://com.jas.devotional/"+R.raw.gupanishad);
					//Intent i  = new Intent(Intent.ACTION_VIEW);
					//i.setDataAndType(data, "video/*");
					
					
					//startActivity(i);
					
					Intent videoIntent = new Intent(getActivity(),VideoPlayerActivity.class);
					
					videoIntent.putExtra("videoFileName", "gurunamavali");
					startActivity(videoIntent);
				}else{
					Log.d(Constants.DEVICE_DEBUG_APP_CODE,"Showing alert.");
					
					// 1. Instantiate an AlertDialog.Builder with its constructor
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					
					// Add the buttons
					builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					           public void onClick(DialogInterface dialog, int id) {
					               // User clicked OK button
					           }
					       });
					// 2. Chain together various setter methods to set the dialog characteristics
					String message = getString(R.string.dialog_message,currentLesson-1,currentLesson); 
					builder.setMessage(message)
					       .setTitle(R.string.dialog_title);

					// 3. Get the AlertDialog from create()
					AlertDialog dialog = builder.create();
					dialog.show();
					
				}
				
			   
				
			}
		});
		 
		 playerFrame.addView(play, 100, 100);
		 RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)play.getLayoutParams();
		 params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		 params.addRule(RelativeLayout.CENTER_VERTICAL);
		 
		 
		 play.setLayoutParams(params); 

		 Button previous = new Button(getActivity());
		 
		 previous.setText(R.string.previous);
		 previous.setWidth(200);
		 playerFrame.addView(previous);
		 
		 previous.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(currentLesson > 1){
					currentLesson--;
					
					
						TextView lesson= (TextView) LessonFragment.this.getActivity().findViewById(R.id.lessonNumber);
						lesson.setText("Lesson#"+currentLesson)	;
					
					
				}
				if(mediaPlayer!=null && mediaPlayer.isPlaying()){
					mediaPlayer.pause();
					play.setBackgroundResource(R.drawable.play);
				}
			}
		});
		 
		 RelativeLayout.LayoutParams prevParams = (android.widget.RelativeLayout.LayoutParams) previous.getLayoutParams();
		
		prevParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		prevParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		previous.setLayoutParams(prevParams);
		
		Button next = new Button(getActivity());
		 
		 next.setText(R.string.next);
		 next.setWidth(200);
		 playerFrame.addView(next);
		 
		 next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(currentLesson < 6){
					currentLesson++;
					
						TextView lesson= (TextView) LessonFragment.this.getActivity().findViewById(R.id.lessonNumber);
						lesson.setText("Lesson#"+currentLesson)	;
					
					
				}
				
				if(mediaPlayer!=null && mediaPlayer.isPlaying()){
					mediaPlayer.pause();
					play.setBackgroundResource(R.drawable.play);
				}
				
			}
		});
		 
		 RelativeLayout.LayoutParams nextParams = (android.widget.RelativeLayout.LayoutParams) next.getLayoutParams();
		
		 nextParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		 nextParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		next.setLayoutParams(nextParams);
		
		TextView lessonName = (TextView) v.findViewById(R.id.lessonNumber);
		lessonName.setText("Lesson#"+currentLesson);
			
			
			
		return v;
		
	}
	
	class ButtonClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			int i = v.getId();
			Log.d(Constants.DEVICE_DEBUG_APP_CODE,"Lesson #"+v.getId()+" selected");
			
			
			switch (i) {
			case 1:
				
				
				
				
				Intent audioIntent = new Intent(getActivity(),AudioPlayerActivity.class);
				
				audioIntent.putExtra("audioFileName", "lingashtakam");
				startActivity(audioIntent);
			return;
			case 2:
				Intent videoIntent = new Intent(getActivity(),VideoPlayerActivity.class);
				
				videoIntent.putExtra("videoFileName", "snowfall");
				startActivity(videoIntent);
				
				return;
			
				
			default:
				
				// 1. Instantiate an AlertDialog.Builder with its constructor
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				
				// Add the buttons
				builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				               // User clicked OK button
				           }
				       });
				// 2. Chain together various setter methods to set the dialog characteristics
				String message = getString(R.string.dialog_message, i-1, i); 
				builder.setMessage(message)
				       .setTitle(R.string.dialog_title);

				// 3. Get the AlertDialog from create()
				AlertDialog dialog = builder.create();
				dialog.show();
				
				
			}
			
			
			
			
		}
		
	}

	
	
	

}
