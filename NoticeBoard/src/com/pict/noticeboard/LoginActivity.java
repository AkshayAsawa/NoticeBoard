package com.pict.noticeboard;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gcm.GCMRegistrar;
import com.pict.noticeboard.library.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	Button regScreen, btnLogin;
	EditText etEmail, etPassword;
	String email, password;
	String success, error, jsonuid, jsonname, jsonroll, jsonbranch, jsonyear,
			jsondiv, jsonbatch, jsonemail;
	Functions func;
	AsyncTask<Void, Void, Void> loginAsync;
	ProgressDialog dialog;
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_UID = "uid";
	private static String KEY_NAME = "name";
	private static String KEY_ROLL = "roll";
	private static String KEY_BRANCH = "branch";
	private static String KEY_YEAR = "year";
	private static String KEY_DIV = "div";
	private static String KEY_EMAIL = "email";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		etEmail = (EditText) findViewById(R.id.loginEmail);
		etPassword = (EditText) findViewById(R.id.loginPassword);
		btnLogin = (Button) findViewById(R.id.buttonLogin);
		btnLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				email = etEmail.getText().toString();
				password = etPassword.getText().toString();
				func = new Functions();

				if (func.isConnected(LoginActivity.this)) {
					loginAsync = new AsyncTask<Void, Void, Void>() {

						@Override
						protected void onPreExecute() {
							super.onPreExecute();
							dialog = new ProgressDialog(LoginActivity.this);
							dialog.setMessage("Checking Credentials...");
							dialog.setCancelable(false);
							dialog.show();
						}

						@Override
						protected Void doInBackground(Void... arg0) {
							// TODO Auto-generated method stub
							JSONObject json = func.loginUser(email, password);
							try {
								error = json.getString(KEY_ERROR);
								success = json.getString(KEY_SUCCESS);
								if (Integer.parseInt(success) == 1) {
									JSONObject json_user = json
											.getJSONObject("user");
									jsonname = json_user.getString(KEY_NAME);
									jsonroll = json_user.getString(KEY_ROLL);
									jsonbranch = json_user
											.getString(KEY_BRANCH);
									jsonyear = json_user.getString(KEY_YEAR);
									jsondiv = json_user.getString(KEY_DIV);
									jsonbatch=json_user.getString("batch");
									jsonemail = json_user.getString(KEY_EMAIL);
									
									InternalDatabase db = new InternalDatabase(
											LoginActivity.this);

									db.addUser(jsonname, jsonroll, jsonbranch,
											jsonyear, jsondiv, jsonbatch, jsonemail);
									Intent notice = new Intent(
											LoginActivity.this,
											MainActivity.class);
									startActivity(notice);
									finish();
									if (dialog.isShowing())
										dialog.dismiss();
									

								} else if(Integer.parseInt(error) == 1) {
									Toast.makeText(LoginActivity.this,
											"Provided credentials dont match", Toast.LENGTH_LONG)
											.show();
									if (dialog.isShowing())
										dialog.dismiss();
									
								}

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							return null;
						}

						@Override
						protected void onPostExecute(Void result) {
							super.onPostExecute(result);
							if (dialog.isShowing())
								dialog.dismiss();
						}

					};
					loginAsync.execute(null, null, null);
				} else {
					Toast.makeText(LoginActivity.this,
							"Please Connect To Internet And Try Again",
							Toast.LENGTH_LONG).show();
				}

			}
		});
		regScreen = (Button) findViewById(R.id.registerScreenLink);
		regScreen.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent reg = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(reg);

			}
		});
	}
}
