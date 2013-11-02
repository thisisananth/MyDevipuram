package com.jas.devotional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InvalidObjectException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;


public class RegisterActivity extends Activity implements ValidationListener {
	
	
	
	@Required(order = 1)
	@Email(order = 2, message = "Enter valid email.")
	private EditText emailEditText;

	@Password(order = 3)
	@TextRule(order = 4, minLength = 6, message = "Enter at least 6 characters.")
	private EditText passwordEditText;

	@ConfirmPassword(order = 5)
	private EditText confirmPasswordEditText;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
	
	    
	    
		setContentView(R.layout.activity_register);
		Log.d(Constants.DEVICE_DEBUG_APP_CODE, "Loading Register screen");
		final Validator validator = new Validator(this);
	    validator.setValidationListener(this);
	    emailEditText = (EditText) findViewById(R.id.editText1);
	    passwordEditText = (EditText) findViewById(R.id.editText2);
	    confirmPasswordEditText = (EditText) findViewById(R.id.editText3);
		Button loginButton = (Button) findViewById(R.id.button1);
		loginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				 validator.validate();
			
				
			}
		});
	}



	@Override
	public void preValidation() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onSuccess() {
		// TODO Auto-generated method stub
		
		
		RegisterTask t = (RegisterTask) new RegisterTask().execute();
		
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
				    	Toast.makeText(this, "Registration Successful, please login", Toast.LENGTH_LONG).show();
				    	Intent i = new Intent(getApplicationContext(),LoginActivity.class);
						
						startActivity(i);
				    }else{
				    	JSONObject error = status.getJSONObject("Errors");
				    	String errorMessage = error.getString("Description");
				    	Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
				    }
			}else{
				Toast.makeText(this, "Internet not available. Please check your connection and try again.", Toast.LENGTH_LONG).show();
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



	@Override
	public void onFailure(View failedView, Rule<?> failedRule) {
		String message = failedRule.getFailureMessage();

        if (failedView instanceof EditText) {
            failedView.requestFocus();
            ((EditText) failedView).setError(message);
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
		
	}



	@Override
	public void onValidationCancelled() {
		// TODO Auto-generated method stub
		
	}
	
	
	private class RegisterTask extends AsyncTask<Void, Void, String> {
		
		private String json = "";

		@Override
		protected String doInBackground(Void...voids)  {
			// TODO Auto-generated method stub
			
			HttpClient client = new DefaultHttpClient();  
		    //HttpPost post = new HttpPost("http://ec2-54-200-201-61.us-west-2.compute.amazonaws.com:8080/DPServices/services/users/create"); 
			
			HttpPost post = new HttpPost("http://10.0.0.2:8080/DPServices/services/users/create"); 
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
