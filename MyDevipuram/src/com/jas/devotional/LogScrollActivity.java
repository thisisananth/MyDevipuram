package com.jas.devotional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class LogScrollActivity extends Activity{
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	         super.onCreate(savedInstanceState);
	         setContentView(R.layout.log_view);
	            try {
	              Process process = Runtime.getRuntime().exec("logcat -d DevipuramApp:D *:S");
	              BufferedReader bufferedReader = new BufferedReader(
	              new InputStreamReader(process.getInputStream()));
	 
	              StringBuilder log=new StringBuilder();
	              String line = "";
	              while ((line = bufferedReader.readLine()) != null) {
	                log.append(line);
	              }
	              TextView tv = (TextView)findViewById(R.id.textView1);
	              tv.setText(log.toString());
	              
	            
	              
	            } catch (IOException e) {
	            }
	          }

}
