package com.pict.noticeboard.library;

import static com.androidhive.pushnotifications.CommonUtilities.displayMessage;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.androidhive.pushnotifications.MainActivity;
import com.androidhive.pushnotifications.ServerUtilities;
import com.google.android.gcm.GCMBaseIntentService;
import com.pict.noticeboard.RegisterActivity;

public class GCMService extends GCMBaseIntentService {
	
	static final String SERVER_URL = "http://10.0.2.2/gcm_server_php/register.php"; 
    static final String SENDER_ID = "36959615674"; 

	GCMService()
	{
		super(SENDER_ID);
	}
	
	@Override
	protected void onError(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onMessage(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onRegistered(Context context, String registrationId) {
		// TODO Auto-generated method stub
		Log.i(TAG, "Device registered: regId = " + registrationId);
        Log.d("NAME", RegisterActivity.name);
        Functions func=new Functions();
        func.addUser(RegisterActivity.name, RegisterActivity.roll, RegisterActivity.branch, RegisterActivity.year, RegisterActivity.div, RegisterActivity.email, RegisterActivity.password);
	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

}
