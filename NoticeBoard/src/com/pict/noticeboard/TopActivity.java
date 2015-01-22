package com.pict.noticeboard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gcm.GCMRegistrar;
import com.pict.noticeboard.library.Functions;
import com.pict.noticeboard.library.InternalDatabase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TopActivity extends Fragment {

	ProgressDialog dialog;
	static InternalDatabase db;
	static ListView list1;
	static String[] noticeHeaders;
	static String[] noticeDesc, noticeUpload, noticeTimestamp;
	Functions func;
	JSONObject jsonNotice;
	AsyncTask<Void, Void, Void> getNotices;
	String regId;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_top, container,
				false);

		func = new Functions();
		db = new InternalDatabase(getActivity());
		list1 = (ListView) rootView.findViewById(R.id.listNotices);

		if (func.isConnected(getActivity())) {
			getNotices = new AsyncTask<Void, Void, Void>() {

				@Override
				protected void onPreExecute() {
					super.onPreExecute();
					dialog = new ProgressDialog(getActivity());
					dialog.setMessage("Retrieving Notices...");
					dialog.setCancelable(false);
					dialog.show();
				}

				@Override
				protected Void doInBackground(Void... arg0) {
					// TODO Auto-generated method stub
					func.refresh(getActivity());
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					super.onPostExecute(result);
					if (dialog.isShowing())
						dialog.dismiss();
					setList(getActivity());
				}

			};
			getNotices.execute(null, null, null);
		} else {
			Toast.makeText(getActivity(), "Please Connect To Internet And Try Again",
					Toast.LENGTH_LONG).show();
			setList(getActivity());
		}
		list1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				// TODO Auto-generated method stub

				Intent desc = new Intent(getActivity(), NoticeDescription.class);
				desc.putExtra("Head", noticeHeaders[position]);
				desc.putExtra("Desc", noticeDesc[position]);
				desc.putExtra("Upload", noticeUpload[position]);
				desc.putExtra("Timestamp", noticeTimestamp[position]);
				startActivity(desc);

			}
		});
		return rootView;
	}

	public static final void setList(Context con) {
		noticeHeaders = db.retrieveHeaders();
		noticeDesc = db.retrieveDesc();
		noticeUpload=db.retrieveUpload();
		noticeTimestamp=db.retrieveTimestamp();
		ListAdapter adap = new ArrayAdapter(con,
				android.R.layout.simple_list_item_1, noticeHeaders);
		list1.setAdapter(adap);

	}

	public final static void abc(Context ctx)
	{
		Toast.makeText(ctx, "here", Toast.LENGTH_LONG).show();
	}
}
