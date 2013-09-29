package com.jas.devotional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
		//Set the values for the 'Upcoming Events' section
		final ListView listview = (ListView) findViewById(R.id.announcements);
		String[] values = new String[] {"SriVidya online courses now available." };

		final ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < values.length; ++i) {
			list.add(values[i]);
		}
		final StableArrayAdapter adapter = new StableArrayAdapter(this,
				android.R.layout.simple_list_item_1, list);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				final String item = (String) parent.getItemAtPosition(position);
				
				Log.d(Constants.DEVICE_DEBUG_APP_CODE, "user selected to launch course");
				
				Intent learningIntent = new Intent(listview.getContext(),CourseLaunchActivity.class);
				startActivity(learningIntent);

			}

		});

	
		//Set the default video

		playerLayout = (OoyalaPlayerLayout) findViewById(R.id.ooyalaPlayer);
		playerLayoutController = new OoyalaPlayerLayoutController(playerLayout,
				Constants.OOYALA_PLAYER_PCODE, "www.ooyala.com");

		if (player == null) {

			player = playerLayoutController.getPlayer();
			

			if (player.setEmbedCode(Constants.DEFAULT_VIDEO_EMBED_CODE)) {
				
				Log.d(Constants.DEVICE_DEBUG_APP_CODE, "Playing the default video");
				// The Embed Code works
				player.play();

			} else {
				Log.d(Constants.DEVICE_DEBUG_APP_CODE, "Unable to play default video");
			}
		} else {
			player.resume();
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		player.pause();

	}

	@Override
	protected void onStop() {
		super.onStop();
		player.pause();
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
			Intent i2 = new Intent(this,LogScrollActivity.class);
			startActivity(i2);
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	private class StableArrayAdapter extends ArrayAdapter<String> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId,
				List<String> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

	}

	

}
