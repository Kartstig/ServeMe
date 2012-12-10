package com.singh.servemeclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.singh.servemeclient.helper.IntentIntegrator;
import com.singh.servemeclient.helper.IntentResult;

public class BarcodeScanActivity extends Activity {
	// Debugging
	private static final String TAG = "BarcodeScanActivity";
	private static final boolean D = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (D)
			Log.e(TAG, "+++ ON CREATE +++");
		
		IntentIntegrator integrator = new IntentIntegrator(this);
		integrator.initiateScan();

	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		  IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		  if (scanResult != null) {
			  Intent myIntent = new Intent(BarcodeScanActivity.this, BeerListActivity.class);
				startActivityForResult(myIntent, 0);
		  }
		  Toast.makeText(getApplicationContext(), "Error on Barcode Scan", Toast.LENGTH_LONG).show();
		  Intent myIntent = new Intent(BarcodeScanActivity.this, BarcodeScanActivity.class);
			startActivityForResult(myIntent, 0);
		}
	
	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (D)
			Log.e(TAG, "--- ON DESTROY ---");

	}

}
