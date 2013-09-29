package com.jas.devotional;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DummyFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle b){
		
		Log.d(Constants.DEVICE_DEBUG_APP_CODE,"In onCreateView of the DummyFragment");
		View v = inflater.inflate(R.layout.fragment_dummy, vg,false);
		TextView tv = (TextView) v.findViewById(R.id.textView1);
		tv.setText("This is a dummy text");
		return v;
		
	}
	
	
	

}
