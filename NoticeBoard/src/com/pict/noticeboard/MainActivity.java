
package com.pict.noticeboard;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
import com.pict.noticeboard.library.Functions;
import com.pict.noticeboard.library.InternalDatabase;
import com.pict.noticeboard.library.TabsPagerAdapter;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	ListView l;
	InternalDatabase db;
	Functions func;
	ProgressDialog dialog;
	String[] noticeHeaders, noticeDesc, noticeUpload, noticeTimestamp;
	String regId, emailid;
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter, refreshAdapter, pinRefreshAdapter;
	private ActionBar actionBar;
	private String[] tabs = { "Top Notices", "Pinned Notices" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		db = new InternalDatabase(getBaseContext());
		func = new Functions();
		Log.e("Tag", "check");
		Log.e("Tag", "Check: "+toString().valueOf(func.loginCheck(getBaseContext())));
		Log.e("Tag", "checK");
		// GCM Registration
		regId = GCMRegistrar.getRegistrationId(this);
		GCMRegistrar.checkDevice(getBaseContext());
		GCMRegistrar.checkManifest(getBaseContext());
		if (regId.length() == 0) {
			GCMRegistrar.register(getBaseContext(), "1056440846231");
		} else
			func.registerForGCM(getBaseContext(), regId);
		setTitle("Notices");

		if (func.loginCheck(this)) {
			Log.e("Tag", "Check1");
			setContentView(R.layout.activity_main);

			final View firstCustomView = new View(this);
			Log.e("Tag", "Check2");
			viewPager = (ViewPager) findViewById(R.id.pager);
			actionBar = getActionBar();
			mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
			viewPager.setAdapter(mAdapter);
			actionBar.setHomeButtonEnabled(false);
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			// getActionBar().setBackgroundDrawable(
			// new ColorDrawable(Color.parseColor("#B4CDCD")));
			Log.e("Tag", "Check3");
			for (String tab_name : tabs) {
				actionBar.addTab(actionBar.newTab().setText(tab_name)
						.setTabListener(this));
			}
			// actionBar.setStackedBackgroundDrawable(new
			// ColorDrawable(Color.parseColor("#2F4F4F")));
			viewPager
					.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

						@Override
						public void onPageSelected(int position) {
							// on changing the page
							// make respected tab selected

							actionBar.setSelectedNavigationItem(position);
							if (position == 1) {
								db = new InternalDatabase(getBaseContext());
								db.createPinNoticeTable();
								noticeHeaders = db.retrievePinHeaders();
								noticeDesc = db.retrievePinDesc();
								noticeUpload = db.retrievePinUpload();
								noticeTimestamp=db.retrievePinTimestamp();
								final int[] ids = db.retrievePinIDs();
								ListView list1 = (ListView) findViewById(R.id.listPin);
								ListAdapter adap = new ArrayAdapter(
										getBaseContext(),
										android.R.layout.simple_list_item_1,
										noticeHeaders);
								list1.setAdapter(adap);
								list1.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> arg0, View arg1,
											int position, long id) {
										// TODO Auto-generated method stub

										Intent pinDesc = new Intent(
												getBaseContext(),
												PinDescActivity.class);
										pinDesc.putExtra("ID", ids[position]);
										pinDesc.putExtra("Head",
												noticeHeaders[position]);
										pinDesc.putExtra("Desc",
												noticeDesc[position]);
										pinDesc.putExtra("Upload",
												noticeUpload[position]);
										pinDesc.putExtra("Timestamp",
												noticeTimestamp[position]);
										startActivity(pinDesc);

									}
								});
								
								list1.setOnLongClickListener(new OnLongClickListener() {

									public boolean onItemLongClick(
											AdapterView<?> arg0, View arg1,
											int arg2, long arg3) {
										// TODO Auto-generated method stub
										Toast.makeText(getApplicationContext(), "here", Toast.LENGTH_LONG).show();
										final AlertDialog.Builder delete=new AlertDialog.Builder(getBaseContext());
										delete.setMessage("Do you want to Delete this notice?").show();
										delete.setPositiveButton("Yes", new DialogInterface.OnClickListener(){

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
											Toast.makeText(getBaseContext(), "Deleted :P", Toast.LENGTH_LONG).show();	
											}
										});
										delete.setNegativeButton("No", new DialogInterface.OnClickListener() {
											
											@Override
											public void onClick(DialogInterface dialog, int which) {
												// TODO Auto-generated method stub
												
											}
											
										});
										return true;
									}

									@Override
									public boolean onLongClick(View arg0) {
										// TODO Auto-generated method stub
										Toast.makeText(getBaseContext(), "Deleted :P", Toast.LENGTH_LONG).show();
										return false;
									}
								});

							}
						}

						@Override
						public void onPageScrolled(int arg0, float arg1,
								int arg2) {
						}

						@Override
						public void onPageScrollStateChanged(int arg0) {
						}
					});
			Toast.makeText(
					getBaseContext(),
					db.returnBranch() + " " + db.returnYear() + " "
							+ db.returnDiv()+" "+db.returnBatch(), Toast.LENGTH_LONG).show();

		}

		else {
			Intent login = new Intent(this, LoginActivity.class);
			login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(login);
			finish();
		}
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main_actions, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.action_logout:
			goToLogin();
			return true;

		case R.id.action_refresh:
			actionBar.setSelectedNavigationItem(0);
			viewPager.setAdapter(mAdapter);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void goToLogin() {

		if(func.isConnected(getBaseContext()))
		{
			func.logout(this);
			db.logoutFromDb();
			GCMRegistrar.unregister(getBaseContext());
			db.clearNoticeboard();
			db.clearPinnedNoticeboard();
			Intent login = new Intent(this, LoginActivity.class);
			login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(login);
			finish();

		}
		else
			Toast.makeText(getBaseContext(), "Internet Connection Unavailable", Toast.LENGTH_LONG).show();
	}
}