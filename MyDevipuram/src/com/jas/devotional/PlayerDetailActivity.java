package com.jas.devotional;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.ooyala.android.OoyalaPlayer;
import com.ooyala.android.OoyalaPlayerLayout;
import com.ooyala.android.OptimizedOoyalaPlayerLayoutController;

public class PlayerDetailActivity extends Activity {
  
  private OoyalaPlayer player = null;
  private Boolean isSuspended = false;

  @Override
  public void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    String embedCode = getIntent().getStringExtra("com.ooyala.embedcode");
    Thread.setDefaultUncaughtExceptionHandler(onUncaughtException);
    try {
      setContentView(R.layout.main);

    } catch (Exception e) {
      e.printStackTrace();
    }
    OptimizedOoyalaPlayerLayoutController layoutController = new OptimizedOoyalaPlayerLayoutController(
        (OoyalaPlayerLayout) findViewById(R.id.player), ChannelFragment.PCODE,
        ChannelFragment.PLAYERDOMAIN);
    player = layoutController.getPlayer();
    if (player.setEmbedCode(embedCode)) {
      Log.d(Constants.DEVICE_DEBUG_APP_CODE, "Playing selected video from chanel");
      player.play();
    } else {
    	 Log.d(Constants.DEVICE_DEBUG_APP_CODE, "Not able to play selected video from chanel");
    }

  }

  @Override
  protected void onStop() {
    super.onStop();
    if (player != null && !isSuspended) {
      player.suspend();
      isSuspended = true;
    }
  }

  @Override
  protected void onRestart() {
    super.onRestart();
    if (player != null) {
      player.resume();
      isSuspended = false;
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    player = null;
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    
    super.onConfigurationChanged(newConfig);
  }

  private Thread.UncaughtExceptionHandler onUncaughtException = new Thread.UncaughtExceptionHandler() {
    public void uncaughtException(Thread thread, Throwable ex) {
      Log.e(Constants.DEVICE_DEBUG_APP_CODE, "Uncaught exception", ex);
      showErrorDialog(ex);
    }
  };

  private void showErrorDialog(Throwable t) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);

    builder.setTitle("Exception!");
    builder.setMessage(t.toString());
    builder.setPositiveButton("OK", null);
    builder.show();
  }
}
