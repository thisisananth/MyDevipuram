package com.jas.devotional;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayerActivity extends Activity  {
	
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_video_player);
	        Log.d(Constants.DEVICE_DEBUG_APP_CODE,"Playing video file...");
	        VideoView videoView = (VideoView)findViewById(R.id.VideoView);
	       MediaController mediaController = new MediaController(this);
	        // mediaController.setAnchorView(videoView);
	        videoView.setMediaController(mediaController);
	        
	      String  videoName = this.getIntent().getStringExtra("videoFileName");
	       
			Uri video = Uri.parse("android.resource://com.jas.devotional/raw/"+videoName);
			

	        videoView.setVideoURI(video);
	        videoView.start();  
	    }
	 
	 
	

}
