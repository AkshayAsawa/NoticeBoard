package com.pict.noticeboard.library;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class Functions {

	private final static String URL = "http://10.0.2.2/noticeboard/";
	private static String register_tag = "register";
	private static String login_tag = "login";

	private JSONParser jsonParser;

	public Functions() {
		jsonParser = new JSONParser();
	}

	public JSONObject loginUser(String email, String password) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));

		JSONObject json = jsonParser.getJSONFromUrl(URL, params);
		return json;
	}

	public JSONObject addUser(String name, String roll, String branch,
			String year, String div, String batch, String email, String password) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", register_tag));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("roll", roll));
		params.add(new BasicNameValuePair("branch", branch));
		params.add(new BasicNameValuePair("year", year));
		params.add(new BasicNameValuePair("div", div));
		params.add(new BasicNameValuePair("batch", batch));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));

		JSONObject json = jsonParser.getJSONFromUrl(URL, params);
		return json;
	}

	public void registerForGCM(Context con, String regId) {
		InternalDatabase db = new InternalDatabase(con);
		String email = db.returnEmail();
		String year = db.returnYear();
		String division = db.returnDiv();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", "gcm"));
		params.add(new BasicNameValuePair("regId", regId));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("year", year));
		params.add(new BasicNameValuePair("division", division));
		JSONObject json = jsonParser.getJSONFromUrl(URL, params);
	}

	public JSONObject retrieveNotices(String year, String div, String batch) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", "notice"));
		params.add(new BasicNameValuePair("year", year));
		params.add(new BasicNameValuePair("div", div));
		params.add(new BasicNameValuePair("batch", batch));
		JSONObject json = jsonParser.getJSONFromUrl(URL, params);

		return json;
	}

	public boolean loginCheck(Context con) {
		int rowCount;
		InternalDatabase db = new InternalDatabase(con);
		rowCount = db.getRowCount();
		if (rowCount > 0)
			return true;
		else
			return false;
	}

	// public String myBranch(Context con) {
	//
	// InternalDatabase db = new InternalDatabase(con);
	// String branch = db.returnBranch();
	// String myBr = null;
	// if (branch.equals("Computers"))
	// myBr = "comp";
	// else if (branch.equals("ENTC"))
	// myBr = "entc";
	// else if (branch.equals("IT"))
	// myBr = "it";
	// else if (branch.equals("Common(FE)"))
	// myBr = "fe";
	// else
	// myBr = "Invalid Branch Selected";
	//
	// return myBr;
	// }

	public String myBranch(String branch) {
		String myBr = null;
		if (branch.equals("Computers"))
			myBr = "comp";
		else if (branch.equals("ENTC"))
			myBr = "entc";
		else if (branch.equals("IT"))
			myBr = "it";
		else if (branch.equals("Common(FE)"))
			myBr = "fe";
		else
			myBr = "Invalid Branch Selected";

		return myBr;
	}

	// public String myYear(Context con) {
	//
	// InternalDatabase db = new InternalDatabase(con);
	// String year = db.returnYear();
	// String myYear = null;
	// if (year.equals("SE"))
	// myYear = "se";
	// else if (year.equals("TE"))
	// myYear = "te";
	// else if (year.equals("BE"))
	// myYear = "be";
	// else if (year.equals("FE"))
	// myYear = "fe";
	// else
	// myYear = "Invalid Year Selected";
	//
	// return myYear;
	// }

	public String myYear(String year) {

		String myYear = null;
		if (year.equals("SE"))
			myYear = "se";
		else if (year.equals("TE"))
			myYear = "te";
		else if (year.equals("BE"))
			myYear = "be";
		else if (year.equals("FE"))
			myYear = "fe";
		else
			myYear = "Invalid Year Selected";

		return myYear;
	}

	public String myBatch(String batch) {

		String myBatch = null;
		if (batch.equals("Batch No. 1"))
			myBatch = "b1";
		else if (batch.equals("Batch No. 2"))
			myBatch = "b2";
		else if (batch.equals("Batch No. 3"))
			myBatch = "b3";
		else if (batch.equals("Batch No. 4"))
			myBatch = "b4";
		else
			myBatch = "Invalid Batch Selected";

		return myBatch;
	}

	public void refresh(Context con) {

		InternalDatabase db = new InternalDatabase(con);
		db.clearNoticeboard();
		db.createNoticeTable();
		JSONObject jsonNotice = retrieveNotices(db.returnYear(),
				db.returnDiv(), db.returnBatch());
		try {
			JSONArray array = jsonNotice.getJSONArray("notice");
			for (int i = array.length() - 1; i >= 0; i--) {
				JSONObject obj = array.getJSONObject(i);
				String headtemp = obj.getString("header");
				String desctemp = obj.getString("description");
				String uploadtemp = obj.getString("uploaded_by");
				String datetime = obj.getString("created_at");
				db.addNotices(headtemp, desctemp, uploadtemp, datetime);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isConnected(Context con) {
		ConnectivityManager connMgr = (ConnectivityManager) con
				.getSystemService(con.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected())
			return true;
		else
			return false;
	}

	public void logout(Context con) {
		InternalDatabase db=new InternalDatabase(con);
		List<NameValuePair> logout=new ArrayList<NameValuePair>();
		logout.add(new BasicNameValuePair("tag", "logout"));
		logout.add(new BasicNameValuePair("email", db.returnEmail()));
		logout.add(new BasicNameValuePair("year", db.returnYear()));
		logout.add(new BasicNameValuePair("division", db.returnDiv()));
		Log.e("My", db.returnEmail());
		Log.e("My", db.returnYear());
		Log.e("My", db.returnDiv());
		jsonParser.getJSONFromUrl(URL, logout);
	}

}
