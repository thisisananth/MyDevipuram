package com.jas.devotional;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;

public class ChannelListActivity extends FragmentActivity implements ActionBar.TabListener {
	
	 ChannelPagerAdapter mChannelPagerAdapter;
	 ViewPager mViewPager;

	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        setContentView(R.layout.activity_channel);
	        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
			String title = prefs.getString("prefAppname", getString(R.string.app_name));
			setTitle(title);
	        
	        mChannelPagerAdapter = new ChannelPagerAdapter(getSupportFragmentManager());
	        
	        // Set up action bar.
	        final ActionBar actionBar = getActionBar();
	        // Specify that the Home button should show an "Up" caret, indicating that touching the
	        // button will take the user one step up in the application's hierarchy.
	        actionBar.setDisplayHomeAsUpEnabled(true);
	        
	        // Specify that we will be displaying tabs in the action bar.
	        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

	       
	        Log.d("DevipuramApp", "Loading channels...");
	        // Set up the ViewPager, attaching the adapter.
	        mViewPager = (ViewPager) findViewById(R.id.pager);
	        mViewPager.setAdapter(mChannelPagerAdapter);
	        
	        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
	            @Override
	            public void onPageSelected(int position) {
	                // When swiping between different app sections, select the corresponding tab.
	                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
	                // Tab.
	                actionBar.setSelectedNavigationItem(position);
	            }
	        });

	        // For each of the sections in the app, add a tab to the action bar.
	        for (int i = 0; i < mChannelPagerAdapter.getCount(); i++) {
	            // Create a tab with text corresponding to the page title defined by the adapter.
	            // Also specify this Activity object, which implements the TabListener interface, as the
	            // listener for when this tab is selected.
	            actionBar.addTab(
	                    actionBar.newTab()
	                            .setText(mChannelPagerAdapter.getPageTitle(i))
	                            .setTabListener(this));
	        }
	    }
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	            case android.R.id.home:
	                // This is called when the Home (Up) button is pressed in the action bar.
	                // Create a simple intent that starts the hierarchical parent activity and
	                // use NavUtils in the Support Package to ensure proper handling of Up.
	                Intent upIntent = new Intent(this, HomeActivity.class);
	                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
	                    // This activity is not part of the application's task, so create a new task
	                    // with a synthesized back stack.
	                    TaskStackBuilder.from(this)
	                            // If there are ancestor activities, they should be added here.
	                            .addNextIntent(upIntent)
	                            .startActivities();
	                    finish();
	                } else {
	                    // This activity is part of the application's task, so simply
	                    // navigate up to the hierarchical parent activity.
	                    NavUtils.navigateUpTo(this, upIntent);
	                }
	                return true;
	        }
	        return super.onOptionsItemSelected(item);
	    }
	 
	/* public static class ChannelPagerAdapter extends FragmentStatePagerAdapter{

		public ChannelPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int i) {
			Fragment fragment = new ChannelFragment();
            Bundle args = new Bundle();
            args.putInt(ChannelFragment.ARG_TITLE, i + 1); // Our object is just an integer :-P
            fragment.setArguments(args);
            return fragment;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return new Integer(R.string.num_channels).intValue();
		}
		
		@Override
        public CharSequence getPageTitle(int position) {
            return "CHANNEL " + (position%5 + 1);
        }
		 
	 }*/
	 
	 
	 /**
	     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
	     * sections of the app.
	     */
	    public static class ChannelPagerAdapter extends FragmentPagerAdapter {

	        public ChannelPagerAdapter(FragmentManager fm) {
	            super(fm);
	        }

	        @Override
	        public Fragment getItem(int i) {
	        	 Fragment f = new ChannelFragment();
	        	 Bundle bundle = new Bundle();
	        	 bundle.putInt("channelId", i);
	        	 f.setArguments(bundle);
	        	 return f;
	        	 
	        }

	        @Override
	        public int getCount() {
	            return 5;
	        }

	        @Override
	        public CharSequence getPageTitle(int position) {
	        	switch (position){
	        	
	        	case 0:
	        		return "Discourses";
	        	case 1:
	        		return "Devotees";
	        	case 2:
	        		return "Recommended";
	        	case 3:
	        		return "Most Popular";
	        	case 4:
	        		return "Premium Content";
	        	default:
	        		return "Discourses";
	        	
	        	
	        	}
	        }
	    }

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		 // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
		
	}
	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

}
