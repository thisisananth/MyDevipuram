package com.jas.devotional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	
	
	private EditText emailEditText;

	
	private EditText passwordEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Log.d(Constants.DEVICE_DEBUG_APP_CODE, "Loading login screen");
		Button loginButton = (Button) findViewById(R.id.button1);
		loginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				
				 emailEditText = (EditText) findViewById(R.id.editText1);
				    passwordEditText = (EditText) findViewById(R.id.editText2);
				    

					LoginTask t = (LoginTask) new LoginTask().execute();
					String json="";
					try {
						json = t.get();
						
						if(json!=null && !json.equals("NO_INTERNET")){
							JSONObject jObject = new JSONObject(json);
							 JSONObject status = jObject.getJSONObject("Status");
							    int statusCode = status.getInt("Code");
							    if(statusCode == 200){
							    	JSONObject data = jObject.getJSONObject("Data");
							    	long userId = data.getLong("UserId");
							    	//save user id in prefs
							    	Toast.makeText(getApplicationContext(), "Login Successful...", Toast.LENGTH_LONG).show();
							    	 SharedPreferences prefs = getSharedPreferences("user_details", MODE_PRIVATE);
							    	  SharedPreferences.Editor editor = prefs.edit();
							    	 // editor.putString("email", emailEditText.getText().toString());
							    	 // editor.putString("password", passwordEditText.getText().toString());
							    	  editor.putLong("userId", userId);
							    	  editor.commit(); 
							    	
							    	Intent i = new Intent(getApplicationContext(),HomeActivity.class);
									
									startActivity(i);
							    }else{
							    	JSONObject error = status.getJSONObject("Errors");
							    	String errorMessage = error.getString("Description");
							    	Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
							    }
						}else{
							Toast.makeText(getApplicationContext(), "Internet not available. Please check your connection and try again.", Toast.LENGTH_LONG).show();
						}
						
						
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ExecutionException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
			}
		});
		
		Button registerButton = (Button) findViewById(R.id.registerButton);
		registerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
				
				startActivity(i);
				
				
			}
		});
	}
	
private class LoginTask extends AsyncTask<Void, Void, String> {
		
		private String json = "";

		@Override
		protected String doInBackground(Void...voids)  {
			// TODO Auto-generated method stub
			
			HttpClient client = new DefaultHttpClient();  
		    //HttpPost post = new HttpPost("http://ec2-54-200-201-61.us-west-2.compute.amazonaws.com:8080/DPServices/services/users/create"); 
			
			HttpPost post = new HttpPost(Constants.WS_URL+"/users/login"); 
		    post.setHeader("Content-type", "application/json");
		    post.setHeader("Accept", "application/json");
		    JSONObject obj = new JSONObject();
		    try {
				obj.put("email", emailEditText.getText().toString());
				 obj.put("password", passwordEditText.getText().toString());
				    post.setEntity(new StringEntity(obj.toString(), "UTF-8"));
				    HttpResponse response = client.execute(post); 
				 HttpEntity e  = response.getEntity();
				 
				 // Grab the response
				    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
				     json = reader.readLine();
				    
				   
				   
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
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
