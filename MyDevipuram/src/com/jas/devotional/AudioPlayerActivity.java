package com.jas.devotional;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;
import android.widget.TextView;

public class AudioPlayerActivity extends Activity implements OnPreparedListener,MediaPlayerControl {

	

	  public static final String AUDIO_FILE_NAME = "audioFileName";

	  private MediaPlayer mediaPlayer;
	  private MediaController mediaController;
	  private String audioFile;

	  private Handler handler = new Handler();

	  
	  public void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.activity_audio_player);
		    Log.d(Constants.DEVICE_DEBUG_APP_CODE,"Playing audio file...");
		    audioFile = this.getIntent().getStringExtra(AUDIO_FILE_NAME);
		    ((TextView)findViewById(R.id.now_playing_text)).setText(audioFile);

		    mediaPlayer = new MediaPlayer();
		    mediaPlayer.setOnPreparedListener(this);

		    mediaController = new MediaController(this);

		    try {
		    	
		      mediaPlayer.setDataSource(getApplicationContext(),Uri.parse("android.resource://com.jas.devotional/raw/"+audioFile));
		      mediaPlayer.prepare();
		      mediaPlayer.start();
		    } catch (IOException e) {
		      Log.e(Constants.DEVICE_DEBUG_APP_CODE, "Could not open file " + audioFile + " for playback.", e);
		    }

		  } 
	 
	  
	  
	  @Override
	  protected void onStop() {
	    super.onStop();
	    mediaController.hide();
	    mediaPlayer.stop();
	    mediaPlayer.release();
	  }

	  @Override
	  public boolean onTouchEvent(MotionEvent event) {
	    //the MediaController will hide after 3 seconds - tap the screen to make it appear again
	    mediaController.show();
	    return false;
	  }

	  
	@Override
	public boolean canPause() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean canSeekBackward() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean canSeekForward() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getAudioSessionId() {
		return mediaPlayer.getAudioSessionId();
	}

	@Override
	public int getBufferPercentage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCurrentPosition() {
		return mediaPlayer.getCurrentPosition();
	}

	@Override
	public int getDuration() {
		return mediaPlayer.getDuration();
	}

	@Override
	public boolean isPlaying() {
		// TODO Auto-generated method stub
		return mediaPlayer.isPlaying();
	}

	@Override
	public void pause() {
		mediaPlayer.pause();
		
	}

	@Override
	public void seekTo(int pos) {
		// TODO Auto-generated method stub
		mediaPlayer.seekTo(pos);
	}

	@Override
	public void start() {
		mediaPlayer.start();
		
	}

	@Override
	public void onPrepared(MediaPlayer arg0) {
		
	    mediaController.setMediaPlayer(this);
	    mediaController.setAnchorView(findViewById(R.id.main_audio_view));

	    handler.post(new Runnable() {
	      public void run() {
	        mediaController.setEnabled(true);
	        mediaController.show();
	      }
	    });
		
	}

}
