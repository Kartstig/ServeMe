package com.singh.servemeclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.singh.servemeclient.helper.IntentIntegrator;
import com.singh.servemeclient.helper.IntentResult;

public class LocationActivity extends Activity {
	// Debugging
	private static final String TAG = "LocationActivity";
	private static final boolean D = true;
	
	// GUI
	private Button qrButton;
	private Button manualButton;
	
	// Make an instance of Barcode Scanner
	IntentIntegrator integrator = new IntentIntegrator(this);
	
	private String locationURL = null;
	private String scanResultFormat = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (D)
			Log.e(TAG, "+++ ON CREATE +++");
		setContentView(R.layout.location_activity);
		
		// Initialize GUI
		initializeGUI();

		// Set Click listeners
		setClickListeners();
		
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, intent);
		if (scanResult != null) {
			
			// Verify scan type and get URL
			scanResultFormat = intent.getStringExtra("SCAN_RESULT_FORMAT");
			locationURL = intent.getStringExtra("SCAN_RESULT");
			
			Log.i("Format", scanResultFormat);
			Log.i("URL", locationURL);
			
			if(scanResultFormat == "QR_CODE") {
				Toast.makeText(getApplicationContext(),
						"Successfully Found a QR Code!", Toast.LENGTH_LONG)
						.show();
			} else {
				Toast.makeText(getApplicationContext(),
						"Not a QR Code. Please Try Again.", Toast.LENGTH_LONG)
						.show();
			}
			
			Intent i = new Intent(LocationActivity.this, BeerListActivity.class);
	        i.putExtra("URL", locationURL);
	        startActivity(i);

		} else {
			Toast.makeText(getApplicationContext(),
					"Invalid Barcode. Please Try Again.", Toast.LENGTH_LONG)
					.show();
			Intent myIntent = new Intent(LocationActivity.this,
					LogInActivity.class);
			startActivityForResult(myIntent, 0);
		}
	}
	
	// GUI
	private void initializeGUI() {
		setContentView(R.layout.location_activity);

		qrButton = (Button) findViewById(R.id.buttonQR);
		manualButton = (Button) findViewById(R.id.buttonManual);

	}
	
	// Click Listeners
		private void setClickListeners() {
			qrButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					
					// Start Barcode Scanner
					integrator.initiateScan();
				}
			});

			manualButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					
					

				}
			});
		}

	protected void onResume() {
		super.onResume();
		if (D)
			Log.e(TAG, "+++ ON RESUME +++");

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (D)
			Log.e(TAG, "--- ON DESTROY ---");

	}

}
