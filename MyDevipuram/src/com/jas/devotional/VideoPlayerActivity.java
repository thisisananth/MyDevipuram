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

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoPlayerActivity extends Activity implements OnCompletionListener {
	
	long userId  = 0;
	String tempUri = "";
	
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
	        SharedPreferences prefs1 = PreferenceManager.getDefaultSharedPreferences(this);
			String title = prefs1.getString("prefAppname", getString(R.string.app_name));
			setTitle(title);
	        
	        SharedPreferences prefs = getSharedPreferences(
					"user_details", MODE_PRIVATE);
			  userId = prefs.getLong("userId", -1);
			  
			  StringBuilder auditUri = new StringBuilder(Constants.WS_URL+
						"/users/audit?userId="+userId);
			
			 tempUri = auditUri.toString();
			
	        
	        
	      String  videoName = this.getIntent().getStringExtra("videoFileName");
	       
			Uri video = Uri.parse("android.resource://com.jas.devotional/raw/"+videoName);
			

	        videoView.setVideoURI(video);
	        videoView.setVisibility(View.VISIBLE);
	        
	        if(userId>0){
	        	  String tempUri = auditUri.toString();
	     	     // Invoke the audit task
	     			AuditTask t = (AuditTask) new AuditTask()
	     					.execute(tempUri + "&eventId=4");
	     			String json = "";

	     			try {
	     				json = t.get();
	     			} catch (Exception e) {
	     				e.printStackTrace();// ignore audit exception.
	     			}
	        }
	       
	       
	        Log.d(Constants.DEVICE_DEBUG_APP_CODE,"Starting video 1");
	        videoView.start();  
	    }

	@Override
	public void onCompletion(MediaPlayer mp) {
		 Log.d(Constants.DEVICE_DEBUG_APP_CODE,"Completed playing video file...");
		// Toast.makeText(getApplicationContext(), "Video 1 Completed", Toast.LENGTH_SHORT).show();
		 if(userId>0){
       	 
    	     // Invoke the audit task
    			AuditTask t = (AuditTask) new AuditTask()
    					.execute(tempUri + "&eventId=6");
    			String json = "";

    			try {
    				json = t.get();
    			} catch (Exception e) {
    				e.printStackTrace();// ignore audit exception.
    			}
       }
		 this.finish();
		 
		
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
	 
	 
	

}
