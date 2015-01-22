package com.pict.noticeboard;

import com.pict.noticeboard.library.InternalDatabase;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class PinDescActivity extends Activity {

	ActionBar actionBar;
	InternalDatabase db;
	int id;
	TextView headertv, desctv, uploadtv;
	String header, desc, uploaded_by, timestamp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pin_desc);
		
		actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		headertv = (TextView) findViewById(R.id.pinHeader);
		desctv = (TextView) findViewById(R.id.pinDesc);
		uploadtv=(TextView) findViewById(R.id.pinUpload);
		
		id = getIntent().getExtras().getInt("ID");
		header = getIntent().getExtras().getString("Head");
		desc = getIntent().getExtras().getString("Desc");
		uploaded_by= getIntent().getExtras().getString("Upload");
		timestamp=getIntent().getExtras().getString("Timestamp");
		desctv.setMovementMethod(new ScrollingMovementMethod());
		
		headertv.setText(header);
		uploadtv.setText("Notice From : "+uploaded_by);
		desctv.setText(timestamp+" - \n"+desc);
		
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.desc_action, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case android.R.id.home:
			onBackPressed();
			return true;
		case R.id.del_notice:
			delMe();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void delMe() {
		db = new InternalDatabase(getBaseContext());
		db.deletePinnedNotice(id);
		Toast.makeText(getBaseContext(), "Notice deleted", Toast.LENGTH_LONG)
				.show();
		onBackPressed();

	}
}
