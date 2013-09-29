package com.jas.devotional;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

public class ChannelViewAdapter extends SimpleAdapter {
	 private ImageDownloader imageDownloader = new ImageDownloader();
	
	public ChannelViewAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
	}

	Context context;
	
	@Override
	  public void setViewImage(ImageView v, String value) {
	    imageDownloader.download(value, v);
	  }



	


}
