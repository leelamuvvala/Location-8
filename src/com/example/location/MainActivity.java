package com.example.location;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements LocationListener {

	private LocationManager locationManager = null;
	private LocationListener locationListener = null;
	private String provider;
	private String PhoneNo, Text;
	private boolean set = false;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (!set) {
			getSettings();
		}
		
		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location loc = locationManager.getLastKnownLocation(provider);
		
		if(loc != null) {
			onLocationChanged(loc);
		}
		else {
			Toast.makeText(MainActivity.this, "Provider is " + provider, Toast.LENGTH_SHORT).show();
			Toast.makeText(MainActivity.this, "Error getting GPS coords", Toast.LENGTH_SHORT).show();
		}
		
	
	}
	
	private void getSettings() {
		
		Button mButton;
		TextView tText;
		
		tText = (TextView)findViewById(R.id.EntName);
		mButton = (Button)findViewById(R.id.ButtonSend);
		
		mButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				EditText eName,eNum;
				
				eNum = (EditText)findViewById(R.id.EditNum);
				eName = (EditText)findViewById(R.id.EditName);
				
				
//				Toast.makeText(getBaseContext(), "eName is "+ eName.getText().toString() +"eNum is "+ eNum.getText().toString(), Toast.LENGTH_LONG).show();
				PhoneNo = eNum.getText().toString();
				set = true;
			}
		});
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
    protected void onResume() {
    	super.onResume();
    	locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 400, 1, this);
    }
  
    protected void onPause() {
    	super.onPause();
    	locationManager.removeUpdates(this);
    }

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		String city = null;
		Geocoder gcd = new Geocoder(MainActivity.this, Locale.getDefault());
		
		Toast.makeText(MainActivity.this, "Lat:" + location.getLatitude() + "Long:" + location.getLongitude(), Toast.LENGTH_SHORT).show();
		
		List<Address> addresses;
		try {
			addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
			Toast.makeText(MainActivity.this, "No of add is "+addresses.size(), Toast.LENGTH_LONG).show();
			if(addresses.size() > 0 ) {
				for(int i=0; i < addresses.size(); i++) {
				   if(addresses.get(0).getThoroughfare() != null) {
				     Toast.makeText(MainActivity.this, addresses.get(0).getThoroughfare() + addresses.get(0).getAddressLine(0), Toast.LENGTH_LONG).show();
				 
				   }  
				}
			}
		}catch (IOException e){
			e.printStackTrace();
		}
		
		
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
