package com.jas.devotional;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

public class ActivityUnderConstruction extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d(Constants.DEVICE_DEBUG_APP_CODE, "Loading Under Construction screen");
		setContentView(R.layout.activity_underconstruction);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String title = prefs.getString("prefAppname", getString(R.string.app_name));
		setTitle(title);
	}
	

}
