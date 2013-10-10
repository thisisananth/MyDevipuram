package com.jas.devotional;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ooyala.android.OoyalaPlayer;
import com.ooyala.android.OoyalaPlayerLayout;
import com.ooyala.android.OoyalaPlayerLayoutController;

public class HomeActivity extends Activity {

	OoyalaPlayerLayout playerLayout;
	OoyalaPlayerLayoutController playerLayoutController;
	OoyalaPlayer player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		Log.d(Constants.DEVICE_DEBUG_APP_CODE, "Loading home screen");

		// Set the default video
		/*
		 * playerLayout = (OoyalaPlayerLayout) findViewById(R.id.ooyalaPlayer);
		 * playerLayoutController = new
		 * OoyalaPlayerLayoutController(playerLayout,
		 * Constants.OOYALA_PLAYER_PCODE, "www.ooyala.com");
		 * 
		 * if (player == null) {
		 * 
		 * player = playerLayoutController.getPlayer();
		 * 
		 * 
		 * if (player.setEmbedCode(Constants.DEFAULT_VIDEO_EMBED_CODE)) {
		 * 
		 * Log.d(Constants.DEVICE_DEBUG_APP_CODE, "Playing the default video");
		 * // The Embed Code works player.play();
		 * 
		 * } else { Log.d(Constants.DEVICE_DEBUG_APP_CODE,
		 * "Unable to play default video"); } } else { player.resume(); }
		 */

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fragment_channel_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_channels:

			Intent i = new Intent(this, ChannelListActivity.class);

			startActivity(i);
			return true;
		case R.id.menu_item_logs:
			Intent i2 = new Intent(this, LogScrollActivity.class);
			startActivity(i2);
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	public void playVideo(View v) {

		Intent i = new Intent(this, VideoPlayerActivity.class);
		i.putExtra("videoFileName", "phonetics");
		startActivity(i);
	

		
		
	}

	public void launchCourse(View v) {

		Intent i = new Intent(this, CourseLaunchActivity.class);

		startActivity(i);

	}

}
