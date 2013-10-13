package com.jas.devotional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.NavUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.ooyala.android.Channel;
import com.ooyala.android.ContentItem;
import com.ooyala.android.ContentTreeCallback;
import com.ooyala.android.OoyalaAPIClient;
import com.ooyala.android.OoyalaException;
import com.ooyala.android.Video;




public class ChannelFragment extends ListFragment implements OnItemClickListener,OnClickListener {
	

	  public static final String PCODE = Constants.PARTNER_CODE;
	  public static final String APIKEY = Constants.API_KEY;
	  public static final String SECRETKEY = Constants.API_SECRET;
	  public static final String PLAYERDOMAIN = "www.ooyala.com";

	  Dialog dialog;
	  Button btnGotIt;
	
	  public static OoyalaAPIClient api = new OoyalaAPIClient(APIKEY, SECRETKEY, PCODE, PLAYERDOMAIN);

	  private String[] embedCodes = { Constants.CHANNEL_DEVOTEES_CODE };
	  
	  
	 
	  
	  
	  
	  
	  
	  
	
	
	  private Channel rootItem = null;
	
	  class MyContentTreeCallback implements ContentTreeCallback {
		    private ChannelFragment _self;

		    public MyContentTreeCallback(ChannelFragment self) {
		      this._self = self;
		    }

		    @Override
		    public void callback(ContentItem item, OoyalaException ex) {
		    	
		    
		    	
		    	
		    	
		      if (ex != null) {
		        Log.e(Constants.DEVICE_DEBUG_APP_CODE, "can not find content tree from api");
		        return;
		      }
		      if (item != null && item instanceof Channel) {
		        rootItem = (Channel) item;
		        setListAdapter(new ChannelViewAdapter(_self.getActivity(), getData(), R.layout.embed_list_item, new String[] {
		            "title", "thumbnail", "duration" }, new int[] { R.id.asset_title, R.id.asset_thumbnail,
		            R.id.asset_duration }));
		        getListView().setTextFilterEnabled(false);

		      } else {
		        Log.e(Constants.DEVICE_DEBUG_APP_CODE, "Error.!! Not a channel.");
		      }
		    }
		  }

	
	
	public static final String ARG_TITLE="title";
	

	@TargetApi(11)
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        	if (NavUtils.getParentActivityName(getActivity()) != null) {
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        	}
        }*/
        
        setHasOptionsMenu(true);
        
       Bundle b =  getArguments();
       
       
       int channelId = b.getInt("channelId");
       
       Log.d(Constants.DEVICE_DEBUG_APP_CODE,"User selected ChannelId is:" +channelId);
       
       if(channelId%2 == 0){
    	 
    	   embedCodes[0] = Constants.CHANNEL_DISCOURSES_CODE;
       }
        
      
       if(isNetworkAvailable()){
    	   api.contentTree(Arrays.asList(embedCodes), new MyContentTreeCallback(this));
       }else{
    	   showCustomDialog();
       }
        

}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	        	if (NavUtils.getParentActivityName(getActivity()) != null) {
	                NavUtils.navigateUpFromSameTask(getActivity());
	            }
	            
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	


    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	
    	super.onActivityCreated(savedInstanceState);
        
       
       
        getListView().setOnItemClickListener(this);
        
      
    }

    
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
    	 Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
    	    Intent intent = (Intent) map.get("intent");
    	    startActivity(intent);
    }
    
    
    protected List<Map<String, Object>> getData() {
        if (rootItem == null) return null;
        List<Map<String, Object>> myData = new ArrayList<Map<String, Object>>();

        for (Video v : rootItem.getVideos()) {
          addItem(myData, v.getTitle(), v.getDuration(), v.getPromoImageURL(50, 50),
              browseIntent(v.getEmbedCode()));
        }
        return myData;
      }

      protected Intent browseIntent(String embedCode) {
        Intent result = new Intent();
        result.setClass(this.getActivity(), PlayerDetailActivity.class);
        result.putExtra("com.ooyala.embedcode", embedCode);
        return result;
      }

      protected void addItem(List<Map<String, Object>> data, String name, int duration, String thumbnail,
          Intent intent) {
        Map<String, Object> temp = new HashMap<String, Object>();
        temp.put("title", name);
        temp.put("duration", timeStringFromMillis(duration, true));
        temp.put("thumbnail", thumbnail);
        temp.put("intent", intent);
        data.add(temp);
      }
      
      private String timeStringFromMillis(int millis, boolean includeHours) {
    	    return DateUtils.formatElapsedTime(millis / 1000);
    	  }
      
      
      
      private boolean isNetworkAvailable() {
    	    ConnectivityManager connectivityManager 
    	          = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
    	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    	}
      
      
      protected void showCustomDialog() {

  		dialog = new Dialog(ChannelFragment.this.getActivity(),
  				android.R.style.Theme_DeviceDefault_Dialog);
  		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

  		dialog.setCancelable(true);
  		dialog.setContentView(R.layout.dialog_course_launch);
  		
  		TextView dialogTitle = (TextView) dialog.findViewById(R.id.dialogTitle);
  		dialogTitle.setText(R.string.net_down_dialog_title);
  		TextView dialogContent = (TextView) dialog.findViewById(R.id.dialogContent);
  		dialogContent.setText(R.string.net_down_dialog_content);
  		
  		
  		
  		btnGotIt = (Button) dialog.findViewById(R.id.btn_gotit);
  		btnGotIt.setText(R.string.net_down_dialog_confirm);
  		btnGotIt.setOnClickListener(this);

  		

  		dialog.show();
  	}
    
      
      @Override
  	public void onClick(View v) {
  		// TODO Auto-generated method stub
  		
  		switch (v.getId()) {
  		case R.id.btn_gotit:
  			Intent i = new Intent(getActivity(), HomeActivity.class);
  			startActivity(i);
  			break;
  		default:
  			break;
  		}
      }
}


