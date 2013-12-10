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
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashScreenActivity extends Activity{
	 // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
    
  private long userId=0;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
 
        new Handler().postDelayed(new Runnable() {
 
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
 
            @Override
            public void run() {
            	
            	SharedPreferences prefs = getSharedPreferences("user_details",
            			  MODE_PRIVATE); 
            	 userId = prefs.getLong("userId", -1);
            	
            	Log.d(Constants.DEVICE_DEBUG_APP_CODE, "value of userId is:"+SplashScreenActivity.this.userId);
            	
            	if(userId>0){
            		//login details available, so go to home page. 
            		// This method will be executed once the timer is over
                    // Start your app main activity
            		
            		AuditTask t = (AuditTask) new AuditTask().execute();
            		String json="";
            		
            		try {
						json = t.get();
						}catch(Exception e){
							e.printStackTrace();//ignore audit exception.
						}
            		
            		
                    Intent i = new Intent(SplashScreenActivity.this, HomeActivity.class);
                    startActivity(i);
     
            	}else{
            		//redirect to login page.
            		// This method will be executed once the timer is over
                    // Start your app main activity
                    Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(i);
     
            	}
            	
                
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
private class AuditTask extends AsyncTask<Void, Void, String> {
		
		private String json = "";

		@Override
		protected String doInBackground(Void...voids)  {
			// TODO Auto-generated method stub
			
			HttpClient client = new DefaultHttpClient();  
		    //HttpPost post = new HttpPost("http://ec2-54-200-201-61.us-west-2.compute.amazonaws.com:8080/DPServices/services/users/create"); 
			
			HttpGet get = new HttpGet(Constants.WS_URL+"/users/audit?userId="+userId+"&eventId=2"); //2 is login
		   // post.setHeader("Content-type", "application/json");
		    get.setHeader("Accept", "application/json");
		    JSONObject obj = new JSONObject();
		    try {
				
				    HttpResponse response = client.execute(get); 
				 HttpEntity e  = response.getEntity();
				 
				 // Grab the response
				    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
				     json = reader.readLine();
				     
				     Log.d(Constants.DEVICE_DEBUG_APP_CODE, json);
				    
				   
				   
			}  catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(UnknownHostException e){
				json = "NO_INTERNET";
			}
		    catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return json;
		}
		
		
		

	    
	 }

}
