package com.pict.noticeboard.library;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class InternalDatabase extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "noticeboard";
	private static final String TABLE_LOGIN = "login";
	private static final String TABLE_NOTICE = "notice";
	private static final String TABLE_PIN = "pin";

	private static final String KEY_NAME = "name";
	private static final String KEY_ROLL = "roll";
	private static final String KEY_BRANCH = "branch";
	private static final String KEY_YEAR = "year";
	private static final String KEY_DIV = "div";
	private static final String KEY_BATCH = "batch";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_HEADER = "header";
	private static final String KEY_DESC = "desc";
	private static final String KEY_UPLOAD = "uploaded_by";

	public InternalDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
				+ KEY_NAME + " TEXT," + KEY_ROLL + " TEXT," + KEY_BRANCH
				+ " TEXT," + KEY_YEAR + " TEXT," + KEY_DIV + " TEXT,"
				+ KEY_BATCH + " TEXT," + KEY_EMAIL + " TEXT UNIQUE" + ")";
		db.execSQL(CREATE_LOGIN_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTICE);
		onCreate(db);
	}

	public void createNoticeTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		String CREATE_NOTICE_TABLE = "CREATE TABLE " + TABLE_NOTICE + "("
				+ KEY_HEADER + " TEXT," + KEY_DESC + " TEXT," + KEY_UPLOAD
				+ " TEXT, created_at TEXT)";
		db.execSQL(CREATE_NOTICE_TABLE);

	}

	public long addNotices(String header, String desc, String uploaded_by,
			String datetime) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(KEY_HEADER, header);
		cv.put(KEY_DESC, desc);
		cv.put(KEY_UPLOAD, uploaded_by);
		cv.put("created_at", datetime);
		long temp = db.insert(TABLE_NOTICE, null, cv);
		db.close();
		return temp;

	}

	public String[] retrieveHeaders() {

		int rowCount = getNoticeCount();
		SQLiteDatabase db = this.getReadableDatabase();
		String[] headers = new String[rowCount];
		for (int i = 0; i < rowCount; i++) {
			headers[i] = toString().valueOf(i);
		}
		String headerQuery = "SELECT * FROM " + TABLE_NOTICE;
		Cursor cur = db.rawQuery(headerQuery, null);
		cur.moveToFirst();
		if (cur.getCount() > 0) {
			int i = 0;
			headers[i] = cur.getString(0);
			while (cur.moveToNext()) {
				i++;
				headers[i] = cur.getString(0);
			}
		}

		return headers;
	}

	public String[] retrieveDesc() {

		int rowCount = getNoticeCount();

		SQLiteDatabase db = this.getReadableDatabase();
		String[] desc = new String[rowCount];
		for (int i = 0; i < rowCount; i++) {
			desc[i] = toString().valueOf(i);
		}
		String headerQuery = "SELECT * FROM " + TABLE_NOTICE;
		Cursor cur = db.rawQuery(headerQuery, null);
		cur.moveToFirst();
		if (cur.getCount() > 0) {
			int i = 0;
			desc[i] = cur.getString(1);
			while (cur.moveToNext()) {
				i++;
				desc[i] = cur.getString(1);
			}
		}

		return desc;
	}

	public String[] retrieveUpload() {

		int rowCount = getNoticeCount();

		SQLiteDatabase db = this.getReadableDatabase();
		String[] upload = new String[rowCount];
		for (int i = 0; i < rowCount; i++) {
			upload[i] = toString().valueOf(i);
		}
		String uploadQuery = "SELECT * FROM " + TABLE_NOTICE;
		Cursor cur = db.rawQuery(uploadQuery, null);
		cur.moveToFirst();
		if (cur.getCount() > 0) {
			int i = 0;
			upload[i] = cur.getString(2);
			while (cur.moveToNext()) {
				i++;
				upload[i] = cur.getString(2);
			}
		}

		return upload;
	}

	public String[] retrieveTimestamp() {

		int rowCount = getNoticeCount();

		SQLiteDatabase db = this.getReadableDatabase();
		String[] datetime = new String[rowCount];
		for (int i = 0; i < rowCount; i++) {
			datetime[i] = toString().valueOf(i);
		}
		String uploadQuery = "SELECT * FROM " + TABLE_NOTICE;
		Cursor cur = db.rawQuery(uploadQuery, null);
		cur.moveToFirst();
		if (cur.getCount() > 0) {
			int i = 0;
			datetime[i] = cur.getString(3);
			while (cur.moveToNext()) {
				i++;
				datetime[i] = cur.getString(3);
			}
		}

		return datetime;
	}

	public int getNoticeCount() {
		String countQuery = "SELECT  * FROM " + TABLE_NOTICE;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		db.close();
		cursor.close();
		return rowCount;
	}

	public void createPinNoticeTable() {
		SQLiteDatabase db = this.getWritableDatabase();
		String CREATE_PIN_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PIN
				+ "( id INTEGER PRIMARY KEY, " + KEY_HEADER + " TEXT, "
				+ KEY_DESC + " TEXT," + KEY_UPLOAD + " TEXT, created_at TEXT )";
		db.execSQL(CREATE_PIN_TABLE);

	}

	public long addPin(String header, String desc, String upload,
			String timestamp) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		Log.e("MyTag", "Here " + upload + " " + KEY_UPLOAD);
		cv.put(KEY_HEADER, header);
		cv.put(KEY_DESC, desc);
		cv.put(KEY_UPLOAD, upload);
		cv.put("created_at", timestamp);
		long temp = db.insert(TABLE_PIN, null, cv);
		db.close();
		return temp;

	}

	public String[] retrievePinHeaders() {
		int rowCount = getPinCount();
		SQLiteDatabase db = this.getReadableDatabase();
		String[] headers = new String[rowCount];
		for (int i = 0; i < rowCount; i++) {
			headers[i] = " ";
		}
		String headerQuery = "SELECT * FROM " + TABLE_PIN;
		Cursor cur = db.rawQuery(headerQuery, null);
		cur.moveToFirst();
		if (cur.getCount() > 0) {
			int i = 0;
			headers[i] = cur.getString(1);
			while (cur.moveToNext()) {
				i++;
				headers[i] = cur.getString(1);
			}
		}

		return headers;
	}

	public String[] retrievePinDesc() {
		int rowCount = getPinCount();
		SQLiteDatabase db = this.getReadableDatabase();
		String[] desc = new String[rowCount];
		for (int i = 0; i < rowCount; i++) {
			desc[i] = " ";
		}
		String headerQuery = "SELECT * FROM " + TABLE_PIN;
		Cursor cur = db.rawQuery(headerQuery, null);
		cur.moveToFirst();
		if (cur.getCount() > 0) {
			int i = 0;
			desc[i] = cur.getString(2);
			while (cur.moveToNext()) {
				i++;
				desc[i] = cur.getString(2);
			}
		}

		return desc;
	}

	public String[] retrievePinUpload() {
		Log.e("MyTag", "Inside");
		int rowCount = getPinCount();
		SQLiteDatabase db = this.getReadableDatabase();
		String[] upload = new String[rowCount];
		for (int i = 0; i < rowCount; i++) {
			upload[i] = " ";
		}
		String headerQuery = "SELECT * FROM " + TABLE_PIN;
		Cursor cursory = db.rawQuery(headerQuery, null);
		cursory.moveToFirst();
		if (cursory.getCount() > 0) {
			int i = 0;
			upload[i] = cursory.getString(3);
			Log.e("MyTag", upload[i]);
			while (cursory.moveToNext()) {
				i++;
				upload[i] = cursory.getString(3);
				Log.e("MyTag", upload[i]);
			}
		}

		return upload;
	}

	public String[] retrievePinTimestamp() {
		int rowCount = getPinCount();
		SQLiteDatabase db = this.getReadableDatabase();
		String[] desc = new String[rowCount];
		for (int i = 0; i < rowCount; i++) {
			desc[i] = " ";
		}
		String headerQuery = "SELECT * FROM " + TABLE_PIN;
		Cursor cur = db.rawQuery(headerQuery, null);
		cur.moveToFirst();
		if (cur.getCount() > 0) {
			int i = 0;
			desc[i] = cur.getString(4);
			while (cur.moveToNext()) {
				i++;
				desc[i] = cur.getString(4);
			}
		}

		return desc;
	}

	public int[] retrievePinIDs() {

		int rowCount = getPinCount();

		SQLiteDatabase db = this.getReadableDatabase();
		int[] id = new int[rowCount];
		for (int i = 0; i < rowCount; i++) {
			id[i] = 0;
		}
		String headerQuery = "SELECT * FROM " + TABLE_PIN;
		Cursor cur = db.rawQuery(headerQuery, null);
		cur.moveToFirst();
		if (cur.getCount() > 0) {
			int i = 0;
			id[i] = cur.getInt(0);
			while (cur.moveToNext()) {
				i++;
				id[i] = cur.getInt(0);
			}
		}

		return id;
	}

	public void deletePinnedNotice(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_PIN, "id = ?", new String[] { toString().valueOf(id) });
		db.close();
	}

	public int getPinCount() {
		String countQuery = "SELECT  * FROM " + TABLE_PIN;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		db.close();
		cursor.close();
		return rowCount;
	}

	public String returnName() {

		SQLiteDatabase db = this.getReadableDatabase();
		String name = null;
		Cursor c = db.rawQuery("SELECT * from " + TABLE_LOGIN, null);
		c.moveToFirst();
		if (c.getCount() > 0)
			name = c.getString(0);
		return name;

	}

	public String returnEmail() {

		SQLiteDatabase db = this.getReadableDatabase();
		String email = null;
		Cursor c = db.rawQuery("SELECT * from " + TABLE_LOGIN, null);
		c.moveToFirst();
		if (c.getCount() > 0)
			email = c.getString(6);
		return email;

	}

	public String returnBatch() {

		SQLiteDatabase db = this.getReadableDatabase();
		String batch = null;
		Cursor c = db.rawQuery("SELECT * from " + TABLE_LOGIN, null);
		c.moveToFirst();
		if (c.getCount() > 0)
			batch = c.getString(5);
		return batch;

	}
	
	public String returnBranch() {

		SQLiteDatabase db = this.getReadableDatabase();
		String branch = null;
		Cursor c = db.rawQuery("SELECT * from " + TABLE_LOGIN, null);
		c.moveToFirst();
		if (c.getCount() > 0)
			branch = c.getString(2);
		return branch;

	}

	public String returnYear() {

		SQLiteDatabase db = this.getReadableDatabase();
		String year = null;
		Cursor c = db.rawQuery("SELECT * from " + TABLE_LOGIN, null);
		c.moveToFirst();
		if (c.getCount() > 0)
			year = c.getString(3);
		return year;

	}

	public String returnDiv() {

		SQLiteDatabase db = this.getReadableDatabase();
		String div = null;
		Cursor c = db.rawQuery("SELECT * from " + TABLE_LOGIN, null);
		c.moveToFirst();
		if (c.getCount() > 0)
			div = c.getString(4);
		return div;

	}

	public long addUser(String name, String roll, String branch, String year,
			String div, String batch, String email) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, name);
		values.put(KEY_ROLL, roll);
		values.put(KEY_BRANCH, branch);
		values.put(KEY_YEAR, year);
		values.put(KEY_DIV, div);
		values.put(KEY_BATCH, batch);
		values.put(KEY_EMAIL, email);
		long t = db.insert(TABLE_LOGIN, null, values);
		db.close();
		return t;
	}

	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			user.put("name", cursor.getString(0));
			user.put("roll", cursor.getString(1));
			user.put("branch", cursor.getString(2));
			user.put("year", cursor.getString(3));
			user.put("div", cursor.getString(4));
			user.put("email", cursor.getString(5));
		}
		cursor.close();
		db.close();
		return user;
	}

	public int getRowCount() {
		String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int rowCount = cursor.getCount();
		db.close();
		cursor.close();
		return rowCount;
	}

	public void clearPinnedNoticeboard() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PIN);
		db.close();
	}

	public void clearNoticeboard() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTICE);
		db.close();
	}

	public void logoutFromDb() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_LOGIN, null, null);
		db.close();
	}

}