package com.pict.noticeboard;

import com.pict.noticeboard.library.InternalDatabase;

import android.content.Intent;
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

public class PinnedActivity extends Fragment {

	InternalDatabase db;
	ListView list1;
	String[] noticeHeaders, noticeDesc, noticeUpload, noticeTimestamp;
	int[] ids;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_pin, container,
				false);
		db = new InternalDatabase(getActivity());
		db.createPinNoticeTable();

		noticeHeaders = db.retrievePinHeaders();
		noticeDesc = db.retrievePinDesc();
		noticeUpload=db.retrievePinUpload();
		ids = db.retrievePinIDs();
		noticeTimestamp=db.retrievePinTimestamp();
		list1 = (ListView) rootView.findViewById(R.id.listPin);
		ListAdapter adap = new ArrayAdapter(getActivity(),
				android.R.layout.simple_list_item_1, noticeHeaders);
		list1.setAdapter(adap);
		list1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent pinDesc = new Intent(getActivity(),
						PinDescActivity.class);
				pinDesc.putExtra("ID", ids[position]);
				pinDesc.putExtra("Head", noticeHeaders[position]);
				pinDesc.putExtra("Desc", noticeDesc[position]);
				pinDesc.putExtra("Upload", noticeUpload[position]);
				pinDesc.putExtra("Timestamp", noticeTimestamp[position]);
				startActivity(pinDesc);

			}
		});

		return rootView;
	}
}
