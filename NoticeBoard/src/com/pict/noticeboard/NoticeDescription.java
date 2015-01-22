package com.pict.noticeboard;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.pict.noticeboard.library.InternalDatabase;

public class NoticeDescription extends Activity {

	ActionBar actionBar;
	InternalDatabase db;
	TextView headertv, desctv, uploadtv;
	String header, desc, uploaded_by, timestamp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice_description);
		
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		headertv = (TextView) findViewById(R.id.noticeHeader);
		desctv = (TextView) findViewById(R.id.noticeDesc);
		uploadtv = (TextView) findViewById(R.id.noticeUpload);

		header = getIntent().getExtras().getString("Head");
		desc = getIntent().getExtras().getString("Desc");
		uploaded_by = getIntent().getExtras().getString("Upload");
		timestamp = getIntent().getExtras().getString("Timestamp");

		desctv.setMovementMethod(new ScrollingMovementMethod());

		headertv.setText(header);
		desctv.setText(timestamp + " - \n" + desc);
		uploadtv.setText("Notice From : " + uploaded_by);

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.pin_action, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case android.R.id.home:
			onBackPressed();
			return true;

		case R.id.pin_notice:
			pinMe();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void pinMe() {
		db = new InternalDatabase(getBaseContext());
		long t = db.addPin(header, desc, uploaded_by, timestamp);
		Toast.makeText(getBaseContext(),
				"Notice Pinned" + toString().valueOf(t), Toast.LENGTH_LONG)
				.show();
	}

}
