package com.jas.devotional;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayerActivity extends Activity implements OnCompletionListener {
	
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_video_player);
	        Log.d(Constants.DEVICE_DEBUG_APP_CODE,"Playing video file...");
	      final  VideoView videoView = (VideoView)findViewById(R.id.VideoView);
	       MediaController mediaController = new MediaController(this);
	       videoView.setOnCompletionListener(this);
	        // mediaController.setAnchorView(videoView);
	        videoView.setMediaController(mediaController);
	        
	        
	      String  videoName = this.getIntent().getStringExtra("videoFileName");
	       
			Uri video = Uri.parse("android.resource://com.jas.devotional/raw/"+videoName);
			

	        videoView.setVideoURI(video);
	        videoView.setVisibility(View.VISIBLE);
	       
	        
	        videoView.start();  
	    }

	@Override
	public void onCompletion(MediaPlayer mp) {
		 Log.d(Constants.DEVICE_DEBUG_APP_CODE,"Completed playing video file...");
		 this.finish();
		 
		
	}
	 
	 
	 
	 
	

}
