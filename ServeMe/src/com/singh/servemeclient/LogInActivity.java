package com.singh.servemeclient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.singh.servemeclient.R;

public class LogInActivity extends Activity {
	// Debugging
	private static final String TAG = "LogInActivity";
	private static final boolean D = true;

	// GUI elements
	private Button signInButton;
	private Button toSignUpButton;

	private EditText emailEditText = null;
	private EditText passwordEditText = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (D)
			Log.e(TAG, "+++ ON CREATE +++");

		// Initialize GUI
		initializeGUI();

		// Set Click listeners
		setClickListeners();

	}

	// Click Listeners
	private void setClickListeners() {
		signInButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				// Get text field info
				String stringEmail = emailEditText.getText().toString();
				String stringPassword = passwordEditText.getText().toString();

				Log.i(TAG, "E-mail Address:" + stringEmail);
				Log.i(TAG, "Password:" + stringPassword);

				// Don't forget to pass intent information for login
				Intent myIntent = new Intent(v.getContext(),
						BeerListActivity.class);
				startActivityForResult(myIntent, 0);
			}
		});

		toSignUpButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				// Don't forget to pass intent information for login
				Intent myIntent = new Intent(v.getContext(),
						SignUpActivity.class);
				startActivityForResult(myIntent, 0);
			}
		});
	}

	// GUI
	private void initializeGUI() {
		setContentView(R.layout.login_activity);

		signInButton = (Button) findViewById(R.id.signinbutton);
		toSignUpButton = (Button) findViewById(R.id.tosignupbutton);

		emailEditText = (EditText) findViewById(R.id.emailText);
		passwordEditText = (EditText) findViewById(R.id.passwordText);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (D)
			Log.e(TAG, "--- ON DESTROY ---");

	}

}
