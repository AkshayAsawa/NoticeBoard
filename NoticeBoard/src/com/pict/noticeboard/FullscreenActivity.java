package com.pict.noticeboard;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class FullscreenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startup);
		
		//Showing off the logo for some 3 seconds.
		ActionBar ab=getActionBar();
		ab.hide();
		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(800);
				}catch(InterruptedException e){
					e.printStackTrace();
				}finally{
					Intent openMenu = new Intent(FullscreenActivity.this,MainActivity.class);
					startActivity(openMenu);
				}
			}
		};
		timer.start();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}	
	
}
