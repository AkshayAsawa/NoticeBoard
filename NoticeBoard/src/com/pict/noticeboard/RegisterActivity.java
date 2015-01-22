package com.pict.noticeboard;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gcm.GCMRegistrar;
import com.pict.noticeboard.library.Functions;
import com.pict.noticeboard.library.InternalDatabase;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.Sampler.Value;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private String array_Branch[], array_Year[], array_Div[], array_Batch[];
	Button register;
	EditText etName, etEmail, etPassword, etConfirmPassword;
	Spinner sBranch, sYear, sDiv, sBatch;
	String name, roll="3333";
	String email;
	String password;
	String confirmpassword;
	String branch;
	String year;
	String div;
	String regId;
	String batch;
	String success, error, jsonuid, jsonname, jsonroll, jsonbranch, jsonyear,
			jsondiv, jsonbatch, jsonemail;
	AsyncTask<Void, Void, Void> regAsync;
	ProgressDialog dialog;
	Functions func = new Functions();
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_UID = "uid";
	private static String KEY_NAME = "name";
	private static String KEY_ROLL = "roll";
	private static String KEY_BRANCH = "branch";
	private static String KEY_YEAR = "year";
	private static String KEY_DIV = "div";
	private static String KEY_BATCH = "batch";
	private static String KEY_EMAIL = "email";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		etName = (EditText) findViewById(R.id.regName);
		etEmail = (EditText) findViewById(R.id.regEmail);
		etPassword = (EditText) findViewById(R.id.regPassword);
		etConfirmPassword = (EditText) findViewById(R.id.regConfirmPassword);

		array_Branch = new String[5];
		array_Branch[0] = "Select Branch:";
		array_Branch[1] = "Common(FE)";
		array_Branch[2] = "Computers";
		array_Branch[3] = "ENTC";
		array_Branch[4] = "IT";

		sBranch = (Spinner) findViewById(R.id.spinnerBranch);
		ArrayAdapter adapter = new ArrayAdapter(this,
				android.R.layout.simple_list_item_1, array_Branch);
		sBranch.setAdapter(adapter);
		sBranch.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
					long id) {
				// TODO Auto-generated method stub
				if (pos == 0) {
					array_Year = new String[1];
					array_Year[0] = "Select Year:";

					sYear = (Spinner) findViewById(R.id.spinnerYear);
					ArrayAdapter adapter1 = new ArrayAdapter(getBaseContext(),
							android.R.layout.simple_list_item_1, array_Year);
					sYear.setAdapter(adapter1);

					array_Div = new String[1];
					array_Div[0] = "Select Division:";

					sDiv = (Spinner) findViewById(R.id.spinnerDiv);
					ArrayAdapter adapter2 = new ArrayAdapter(getBaseContext(),
							android.R.layout.simple_list_item_1, array_Div);
					sDiv.setAdapter(adapter2);

					array_Batch = new String[1];
					array_Batch[0] = "Select Batch:";

					sBatch = (Spinner) findViewById(R.id.spinnerBatch);
					ArrayAdapter adapter3 = new ArrayAdapter(getBaseContext(),
							android.R.layout.simple_list_item_1, array_Batch);
					sBatch.setAdapter(adapter3);

				} else if (pos == 1) {
					array_Year = new String[2];
					array_Year[0] = "Select Year:";
					array_Year[1] = "FE";

					sYear = (Spinner) findViewById(R.id.spinnerYear);
					ArrayAdapter adapter1 = new ArrayAdapter(getBaseContext(),
							android.R.layout.simple_list_item_1, array_Year);
					sYear.setAdapter(adapter1);

					array_Div = new String[15];
					array_Div[0] = "Select Division:";
					for (int i = 1; i < 15; i++)
						array_Div[i] = Integer.toString(i);
					sDiv = (Spinner) findViewById(R.id.spinnerDiv);
					ArrayAdapter adapter2 = new ArrayAdapter(getBaseContext(),
							android.R.layout.simple_list_item_1, array_Div);
					sDiv.setAdapter(adapter2);

					array_Batch = new String[5];
					array_Batch[0] = "Select Batch:";
					array_Batch[1] = "Batch No. 1";
					array_Batch[2] = "Batch No. 2";
					array_Batch[3] = "Batch No. 3";
					array_Batch[4] = "Batch No. 4";

					sBatch = (Spinner) findViewById(R.id.spinnerBatch);
					ArrayAdapter adapter3 = new ArrayAdapter(getBaseContext(),
							android.R.layout.simple_list_item_1, array_Batch);
					sBatch.setAdapter(adapter3);

				} else if (pos == 2 || pos == 3 || pos == 4) {
					array_Year = new String[4];
					array_Year[0] = "Select Year:";
					array_Year[1] = "SE";
					array_Year[2] = "TE";
					array_Year[3] = "BE";

					sYear = (Spinner) findViewById(R.id.spinnerYear);
					ArrayAdapter adapter1 = new ArrayAdapter(getBaseContext(),
							android.R.layout.simple_list_item_1, array_Year);
					sYear.setAdapter(adapter1);

					array_Div = new String[15];
					array_Div[0] = "Select Division:";
					for (int i = 1; i < 15; i++)
						array_Div[i] = Integer.toString(i);

					sDiv = (Spinner) findViewById(R.id.spinnerDiv);
					ArrayAdapter adapter2 = new ArrayAdapter(getBaseContext(),
							android.R.layout.simple_list_item_1, array_Div);
					sDiv.setAdapter(adapter2);

					array_Batch = new String[5];
					array_Batch[0] = "Select Batch:";
					array_Batch[1] = "Batch No. 1";
					array_Batch[2] = "Batch No. 2";
					array_Batch[3] = "Batch No. 3";
					array_Batch[4] = "Batch No. 4";

					sBatch = (Spinner) findViewById(R.id.spinnerBatch);
					ArrayAdapter adapter3 = new ArrayAdapter(getBaseContext(),
							android.R.layout.simple_list_item_1, array_Batch);
					sBatch.setAdapter(adapter3);

				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		final Functions func = new Functions();
		register = (Button) findViewById(R.id.buttonReg);
		register.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (func.isConnected(getBaseContext())) {
					regAsync = new AsyncTask<Void, Void, Void>() {

						@Override
						protected void onPreExecute() {
							super.onPreExecute();
							dialog = new ProgressDialog(RegisterActivity.this);
							dialog.setMessage("Please Wait...");
							dialog.setCancelable(false);
							dialog.show();
						}

						@Override
						protected Void doInBackground(Void... arg0) {
							// TODO Auto-generated method stub
							Log.e("my tag", "here 1");
							name = etName.getText().toString();
							roll = "3333";
							branch = sBranch.getSelectedItem().toString();
							year = sYear.getSelectedItem().toString();
							div = sDiv.getSelectedItem().toString();
							batch = sBatch.getSelectedItem().toString();
							email = etEmail.getText().toString();
							password = etPassword.getText().toString();
							confirmpassword = etConfirmPassword.getText()
									.toString();
							Log.e("my tag", "here 2 "+func.myBatch(batch));
							// if(confirmpassword.equals(password))
							// {
							// if(name.length()!=0&&roll.length()!=0&&branch!="Select Branch:"&&year!="Select Year:"&&div!="Select Division:"&&email.length()!=0&&password.length()!=0&&confirmpassword.length()!=0)
							// {

							JSONObject json = func.addUser(name, roll,
									func.myBranch(branch), func.myYear(year),
									div, func.myBatch(batch), email, password);
							Log.e("my tag", "here 3");
							try {
								error = json.getString(KEY_ERROR);
								success = json.getString(KEY_SUCCESS);
								jsonuid = json.getString(KEY_UID);
								JSONObject json_user = json
										.getJSONObject("user");
								jsonname = json_user.getString(KEY_NAME);
								jsonroll = json_user.getString(KEY_ROLL);
								jsonbranch = json_user.getString(KEY_BRANCH);
								jsonyear = json_user.getString(KEY_YEAR);
								jsondiv = json_user.getString(KEY_DIV);
								jsonbatch = json_user.getString(KEY_BATCH);
								jsonemail = json_user.getString(KEY_EMAIL);
								Log.e("my tag", "here 4");
							} catch (JSONException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							if (Integer.parseInt(success) == 1) {
								Log.e("My tag", "Here 1");
								InternalDatabase db = new InternalDatabase(
										RegisterActivity.this);
								func.logout(RegisterActivity.this);
								Log.e("My tag", "Here 2");
								long t = db.addUser(jsonname, jsonroll,
										jsonbranch, jsonyear, jsondiv,
										jsonbatch, jsonemail);
								Log.e("My tag", "Here 3");
								Intent dashboard = new Intent(
										RegisterActivity.this,
										MainActivity.class);
								startActivity(dashboard);
								Log.e("My tag", "Here 4");
								finish();
								Log.e("My tag", "Here 5");
								if (dialog.isShowing())
									dialog.dismiss();
								Log.e("My tag", "Here 6");

							}

							else if (Integer.parseInt(error) == 2) {
								if (dialog.isShowing())
									dialog.dismiss();
								Toast.makeText(RegisterActivity.this,
										"This EMAIL ID Is Already Registered!",
										Toast.LENGTH_LONG).show();
							}

							else {
								if (dialog.isShowing())
									dialog.dismiss();
								Toast.makeText(RegisterActivity.this,
										"Registration Unsuccessful",
										Toast.LENGTH_LONG).show();
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
					regAsync.execute(null, null, null);
				} else {
					Toast.makeText(getBaseContext(),
							"Please Connect To Internet And Try Again",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}
}
