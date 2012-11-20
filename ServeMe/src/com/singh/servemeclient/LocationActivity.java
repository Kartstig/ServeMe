package com.singh.servemeclient;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;

public class LocationActivity extends ListActivity {
	// Debugging
	private static final String TAG = "LocationActivity";
	private static final boolean D = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (D)
			Log.e(TAG, "+++ ON CREATE +++");
	}

}
