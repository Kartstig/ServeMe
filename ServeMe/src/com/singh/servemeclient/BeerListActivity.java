package com.singh.servemeclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.singh.servemeclient.helper.AlertDialogManager;
import com.singh.servemeclient.helper.ConnectionDetector;
import com.singh.servemeclient.helper.IntentIntegrator;
import com.singh.servemeclient.helper.IntentResult;
import com.singh.servemeclient.helper.JSONParser;

public class BeerListActivity extends ListActivity {
	// Debugging
	private static final String TAG = "BeerListActivity";
	private static final boolean D = true;

	// Connection detector
	ConnectionDetector cd;

	// Alert dialog manager
	AlertDialogManager alert = new AlertDialogManager();

	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jsonParser = new JSONParser();

	ArrayList<HashMap<String, String>> beersList;

	// beers JSONArray
	JSONArray beers = null;

	// beers JSON url
	private static final String URL_BEERS = "http://sleepy-sands-8205.herokuapp.com/beers.json";

	// ALL JSON node names
	private static final String TAG_ID = "id";
	private static final String TAG_NAME = "name";
	private static final String TAG_CREATED_AT = "created_at";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (D)
			Log.e(TAG, "+++ ON CREATE +++");

		setContentView(R.layout.activity_beers);

		cd = new ConnectionDetector(getApplicationContext());

		// Check for internet connection
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(BeerListActivity.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}

		// Hashmap for ListView
		beersList = new ArrayList<HashMap<String, String>>();

		// Loading Albums JSON in Background Thread
		new LoadBeers().execute();

		// get listview
		ListView lv = getListView();

		/**
		 * Listview item click listener TrackListActivity will be lauched by
		 * passing album id
		 * */
		lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// TODO stuff on click listen
			}
		});
	}

	/**
	 * Background Async Task to Load all Beers by making http request
	 * */
	class LoadBeers extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(BeerListActivity.this);
			pDialog.setMessage("Getting Menu Items ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting Albums JSON
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			// getting JSON string from URL
			String json = jsonParser.makeHttpRequest(URL_BEERS, "GET", params);

			// Check your log cat for JSON reponse
			Log.d("Beers JSON: ", "> " + json);

			try {
				beers = new JSONArray(json);

				if (beers != null) {
					// looping through All beers
					for (int i = 0; i < beers.length(); i++) {
						JSONObject c = beers.getJSONObject(i);

						// Storing each json item values in variable
						String id = c.getString(TAG_ID);
						String name = c.getString(TAG_NAME);
						String created_at = c.getString(TAG_CREATED_AT);

						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put(TAG_ID, id);
						map.put(TAG_NAME, name);
						map.put(TAG_CREATED_AT, created_at);

						// adding HashList to ArrayList
						beersList.add(map);
					}
				} else {
					Log.d("Beers: ", "null");
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all beers
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(
							BeerListActivity.this, beersList,
							R.layout.list_item_beer, new String[] { TAG_ID,
									TAG_NAME, TAG_CREATED_AT }, new int[] {
									R.id.beer_id, R.id.beer_name,
									R.id.created_at });

					// updating listview
					setListAdapter(adapter);
				}
			});

		}

	}
}