/*
 * ELEC390 and COEN390: TI SensorTag Library for Android
 * Example application: Weather Station
 * Author: Marc-Alexandre Chan <marcalexc@arenthil.net>
 * Institution: Concordia University
 */
package ca.concordia.sensortag.weather;

import java.text.DecimalFormat;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import ca.concordia.sensortag.SensorTagListener;
import ca.concordia.sensortag.SensorTagLoggerListener;
import ca.concordia.sensortag.SensorTagManager;
import ca.concordia.sensortag.SensorTagManager.ErrorType;
import ca.concordia.sensortag.SensorTagManager.StatusType;
import ti.android.ble.sensortag.Sensor;
import ca.concordia.sensortag.weather.R;
import android.view.View;
//Chris--------------------------------------------
import android.view.View.OnClickListener;
import android.widget.Button;
//-------------------------------------------------
 

//import android.view.MenuItem;

/**
 * Main WeatherStation activity. This class controls the main activity ("window") for the Weather
 * Station, that shows the temperature, humidity and pressure from the SensorTag sensors. It is
 * responsible for managing a connection to the SensorTag and receiving sensor measurements from it
 * (via the {@link SensorTagManager} class), as well as converting it to the right unit and showing
 * it on the screen.
 */
public class WeatherStationActivity extends Activity implements OnClickListener{
	//Chris--Tristan--------------------------------------
	private Button NotesButton;
	private Button mChecklistButton;
	//----------------------------------------------
	
	/**
	 * Handles events from the SensorTagManager: SensorTag status updates, sensor measurements, etc.
	 */
	public class ManagerListener extends SensorTagLoggerListener implements SensorTagListener {

		/*
		 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		 * Remember: if you want to use other sensors here, you have to enable them in onCreate and
		 * then add the onUpdate method corresponding to that sensor! See the SensorTagListener
		 * interface for the list of all the onUpdate methods.
		 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		 */

		/**
		 * Called on receiving a SensorTag-related error. Displays a Toast showing a message to the
		 * user.
		 * 
		 * @todo Reserve an area on the GUI instead so that errors are displayed statically?
		 * @see ca.concordia.sensortag.SensorTagBaseListener#onError(ca.concordia.sensortag.SensorTagManager,
		 *      ca.concordia.sensortag.SensorTagManager .ErrorType, java.lang.String)
		 */
		@Override
		public void onError(SensorTagManager mgr, ErrorType type, String msg) {
			super.onError(mgr, type, msg);
			
			// This was not in the Minimal example, but it could have been added there (if it were
			// not, you know, a minimal example!). This onError() method is called by
			// SensorTagManager if an error happens related to the SensorTag; it is not attached to
			// a sensor measurement, unlike all the other methods.
			//
			// This lets an application take some action when an error happens. In this case,
			// we just pick a "user-friendly" string to show depending on the error type (the
			// "type" parameter), and then show that on the screen as a Toast.
			//
			// Quick note: a Toast is a small text box near the bottom of the screen that disappears
			// on its own after a short amount of time.
			String text = null;
			switch (type) {
			case GATT_REQUEST_FAILED:
				text = "Error: Request failed: " + msg;
				break;
			case GATT_UNKNOWN_MESSAGE:
				text = "Error: Unknown GATT message (Programmer error): " + msg;
				break;
			case SENSOR_CONFIG_FAILED:
				text = "Error: Failed to configure sensor: " + msg;
				break;
			case SERVICE_DISCOVERY_FAILED:
				text = "Error: Failed to discover sensors: " + msg;
				break;
			case UNDEFINED:
				text = "Error: Unknown error: " + msg;
				break;
			default:
				break;
			}
			if (text != null)
				Toast.makeText(WeatherStationActivity.this, text, Toast.LENGTH_LONG).show();
		}

		/**
		 * Called on receiver a SensorTag-related status message. Displays a Toast showing a message
		 * to the user, if relevant.
		 * 
		 * @see ca.concordia.sensortag.SensorTagBaseListener#onStatus(ca.concordia.sensortag.SensorTagManager,
		 *      ca.concordia.sensortag.SensorTagManager .StatusType, java.lang.String)
		 */
		@Override
		public void onStatus(SensorTagManager mgr, StatusType type, String msg) {
			super.onStatus(mgr, type, msg);
			
			// This is similar to onError() above, and we do the same thing: we pick a user-friendly
			// message and (if we have one we want to show) we show the message in a Toast.
			String text = null;
			switch (type) {
			case SERVICE_DISCOVERY_COMPLETED:
				// This message doesn't really need to be announced to the user.
				break;
			case SERVICE_DISCOVERY_STARTED:
				text = "Preparing SensorTag";
				break;
			case UNDEFINED:
				text = "Unknown status";
				break;
			default: // ignore other cases
				break;

			}
			if (text != null)
				Toast.makeText(WeatherStationActivity.this, text, Toast.LENGTH_SHORT).show();
		}
		
		/**
		 * Called on receiving a new ambient temperature measurement. Displays the new value.
		 * 
		 * @see ca.concordia.sensortag.SensorTagLoggerListener#onUpdateAmbientTemperature(ca.concordia.sensortag.SensorTagManager,
		 *      double)
		 */
		@Override
		public void onUpdateAmbientTemperature(SensorTagManager mgr, double temp) {
			super.onUpdateAmbientTemperature(mgr, temp);

			boolean uninitialised = false;
			
			try
			{
				Double.parseDouble (max_temp.getText().toString());
			}
			catch (NumberFormatException s)
			{
				uninitialised = true;
			}
			
			try
			{
				Double.parseDouble (min_temp.getText().toString());
			}
			catch (NumberFormatException s)
			{
				uninitialised = true;
			}
					
			if (!uninitialised)
			{
				notearray[0].adjust_setpoint(Double.parseDouble (max_temp.getText().toString()));
				notearray[1].adjust_setpoint(Double.parseDouble (min_temp.getText().toString()));
			}
					
					
					
			
			
			
			for (int i = 0 ; i < number_of_notifications ; i++)
			{
				if (notearray[i] != null)
					if (notearray[i].scan(temp))
						issue_alert (notearray[i]);
			}
			
			
			
			mLastTemperature = temp;
			final String tempText = tempFormat.format(mLastTemperature);
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// Change the text in the temperature TextView to the new value
					//mTemperatureView.setText(tempText);
					updateDisplayedUnits();
				}
 
			});

		}

	

		
		@Override
		public void onUpdateHumidity(SensorTagManager mgr, double rh) {
			super.onUpdateHumidity(mgr, rh);

			
			mLastHumidity = rh;
			
boolean uninitialised = false;

			try
			{
				Double.parseDouble (max_hum.getText().toString());
			}
			catch (NumberFormatException s)
			{
				uninitialised = true;
			}
			
			try
			{
				Double.parseDouble (min_hum.getText().toString());
			}
			catch (NumberFormatException s)
			{
				uninitialised = true;
			}
					
			if (!uninitialised)
			{
				humidity_notearray[0].adjust_setpoint(Double.parseDouble (max_hum.getText().toString()));
				humidity_notearray[1].adjust_setpoint(Double.parseDouble (min_hum.getText().toString()));
			}
					
			
			for (int i = 0 ; i < number_of_humidity_notifications ; i++)
			{
				if (humidity_notearray[i] != null)
					if (humidity_notearray[i].scan(rh))
						issue_alert (humidity_notearray[i]);
			}
			
			
			final String humiText = humiFormat.format(rh);
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					mHumidityView.setText(humiText);
				}

			});
		}

	}

	static public final String TAG = "WeatherSt"; // Tag for Android's logcat
	static protected final int UPDATE_PERIOD_MS = 60000; // How often measurements should be taken
	// Define formatters for converting the sensor measurement into a string to show on the screen
	private final static DecimalFormat tempFormat = new DecimalFormat("###0.0;-##0.0");

	private final static DecimalFormat humiFormat = new DecimalFormat("##0.0");
	
	// GUI elements - TextView is the Java class for text-box elements on the screen, and
	// Switch is the Java class for the on-off switch element
	private TextView mTemperatureView;     
	private TextView mTemperatureUnitView;
	
	private TextView mHumidityView;

	private TextView temp_title;
	@SuppressWarnings("unused")
	private TextView mHumidityUnitView;
	
		// These are "cached" copies of the last value received from the SensorTag.
	// We save this so that, if the user changes the unit, we can immediately update the GUI
	// (if we didn't have the last value, we'd have to wait until a new value is received to
	// calculate and show it in the correct unit).
	private double mLastTemperature = Double.NaN;
	@SuppressWarnings("unused")
	private double mLastHumidity = Double.NaN;
	
    
    private final int number_of_notifications = 8;
    private final int number_of_humidity_notifications = 8;
    
    Humidity_notification[] humidity_notearray = new Humidity_notification [number_of_humidity_notifications];
	Notification[] notearray = new Notification [number_of_notifications]; 
   
	
    private EditText max_temp ;
    private EditText min_temp ;
    private EditText max_hum ;
    private EditText min_hum ;
   
   
    

	
	
	
	

	
	// Bluetooth communication with the SensorTag
	private BluetoothDevice mBtDevice;
	private SensorTagManager mStManager;

	private SensorTagListener mStListener;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather_station);
		
		//Chris---Tristan----------------------------------
		NotesButton = (Button) findViewById(R.id.notes); 
		NotesButton.setOnClickListener(this);
		mChecklistButton = (Button) findViewById(R.id.checklistButton);
		mChecklistButton.setOnClickListener(this);
		//----------------------------------------------
		notearray [0] = new Notification ("High temp", "temperature above setpoint" , true,false,2,45);
		notearray [1] = new Notification ("Low temp", "temp below setpoint" , false,true,3,25);
		notearray [2] = new Notification ("Dangerously high temp", "permanent damage may occur", true, false,1,120);
		notearray [3] = new Notification ("Dangerously low temp", "permanent damage may occur", false, true, 1, -20);
		notearray [4] = new Notification ("Frost warning", "frost damage likely", false,true,1,4);
		notearray [5] = new Notification ("Very warm outside", "consider watering", true,false,3,30);
		
		
		
		
		humidity_notearray [0] = new Humidity_notification ("High humidity", "humidity above setpoint",true,false,4,50);
		humidity_notearray [1] = new Humidity_notification ("Low humidity", "humidity below setpoint",false,true,5,20);		
		humidity_notearray [2] = new Humidity_notification ("Dry conditions", "consider watering",false,true,5,20);
		
		
		
		max_temp = (EditText) findViewById (R.id.max_temp_tb); 
		min_temp = (EditText) findViewById (R.id.low_temp_tb); 
		max_hum = (EditText) findViewById (R.id.high_humidity_tb); 
		min_hum = (EditText) findViewById (R.id.low_humidity_tb); 
		
		
		
		
		// Get the Bluetooth device selected by the user - should be set by DeviceSelectActivity
		Intent receivedIntent = getIntent();
		mBtDevice = (BluetoothDevice) receivedIntent
				.getParcelableExtra(ti.android.ble.sensortag.DeviceSelectActivity.EXTRA_DEVICE);

		/*
		//-----------------------------------------------------------------------------------------------------------------------
		// If we didn't get a device, we can't do anything! Warn the user, log and exit.
		if (mBtDevice == null) {
			Log.e(TAG, "No BluetoothDevice extra [" + ti.android.ble.sensortag.DeviceSelectActivity.EXTRA_DEVICE
					+ "] provided in Intent.");
			Toast.makeText(this, "No Bluetooth Device selected", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		//------------------------------------------------------------------------------------------------------------------------
		 */
		
		// Prepare the SensorTag
		mStManager = new SensorTagManager(this, mBtDevice);
		mStListener = new ManagerListener();
		mStManager.addListener(mStListener);

		mStManager.initServices();
		if (!mStManager.isServicesReady()) { // initServices failed or took too long
			Log.e(TAG, "Discover failed - exiting");
			finish();
			return;
		}

		/* This section is different from the minimal example, and so warrants explanations.
		 * 
		 * There are two things going on in this code:
		 * 		1) We check whether or not enableSensor() worked.
		 * 		2) We try to set the update period (how often measurements are sent from the sensor).
		 *
		 * 1. mStManager.enableSensor() returns true if enabling the sensor succeeded, but if the
		 * SensorTag takes too long to respond or returns an error it returns false. If you want to
		 * use a sensor, it makes sense that you want to know whether enabling the sensor worked or
		 * not: if it didn't work but you NEED it, you can show the user an error and exit; if it's
		 * not vital, you probably want to tell the user something broke anyway and then keep going.
		 * 
		 * In this case, what we do is use a boolean variable "res" to store the result of
		 * enableSensor(): for every enableSensor() call, we take the logical AND of the new
		 * enableSensor() result and the old "res" value. That way, if ANY of the enableSensor()
		 * calls fail, "res" will be false and then at the end we know one of the sensors failed:
		 * therefore, we show an error to the user and exit.
		 * 
		 * 2. Some of the sensors are capable of having the update period (how often a measurement
		 * is taken from the sensor) changed when you enable the sensor. This depends on the
		 * firmware version of the SensorTag, therefore the SensorTagManager has an
		 * isPeriodSupported(Sensor) that checks whether or not your specific SensorTag supports
		 * setting the period of the Sensor specified.
		 * 
		 * If the period is supported, we set it to UPDATE_PERIOD_MS; if not, it's not vital to
		 * the app, so we just enable the sensor without setting a period value (in this case, a
		 * default value is used: usually 1000ms or 2000ms, depending on the sensor).
		 */
		boolean res = true;
		if (mStManager.isPeriodSupported(Sensor.IR_TEMPERATURE))
			res = res && mStManager.enableSensor(Sensor.IR_TEMPERATURE, UPDATE_PERIOD_MS);
		else
			res = res && mStManager.enableSensor(Sensor.IR_TEMPERATURE);
			

		if (mStManager.isPeriodSupported(Sensor.BAROMETER))
			res = res && mStManager.enableSensor(Sensor.BAROMETER, UPDATE_PERIOD_MS);
		else
			res = res && mStManager.enableSensor(Sensor.BAROMETER);

		if (mStManager.isPeriodSupported(Sensor.HUMIDITY))
			res = res && mStManager.enableSensor(Sensor.HUMIDITY, UPDATE_PERIOD_MS);
		else
			res = res && mStManager.enableSensor(Sensor.HUMIDITY);

		// If any of the enableSensor() calls failed, show/log an error and exit.
		if (!res) {
			Log.e(TAG, "Sensor configuration failed - exiting");
			Toast.makeText(this, "Sensor configuration failed - exiting", Toast.LENGTH_LONG).show();
			finish();
		}
		
		//if (mStManager.isPeriodSupported(Sensor.IR_TEMPERATURE))
		//	Toast.makeText(WeatherStationActivity.this, "period supported", Toast.LENGTH_LONG).show();
		

		// Get references to the GUI text box objects
		mTemperatureView = (TextView) findViewById(R.id.value_temp);
		mTemperatureUnitView = (TextView) findViewById(R.id.unit_temp);
		mHumidityView = (TextView) findViewById(R.id.value_humi);
		mHumidityUnitView = (TextView) findViewById(R.id.unit_humi);
		temp_title = (TextView)findViewById(R.id.temperature_title);
		
		
	
		
		
		// Initial values for the measurements on the GUI: before the SensorTag is all ready to go
		// and has sent its first sensor values, we want to show dashes as a placeholder.
		mTemperatureView.setText("--.-");
		mHumidityView.setText("--.-");
	}

	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mStManager != null) mStManager.close();
		issue_alert ("MONITORING STOPPED", "Verify sensor connection",1);
	}

	
	@Override
	protected void onPause() {
		super.onPause();
		//try running app in background
		if (mStManager != null) mStManager.enableUpdates();
	}

	
	@Override
	protected void onResume() {
		super.onResume();
		if (mStManager != null) mStManager.enableUpdates();
		

					
		}
	

	@Override
	protected void onStart() {
		super.onStart();
		
					
		
	}

	
	@Override
	protected void onStop() {
		super.onStop();
		if (mStManager != null) mStManager.disableUpdates();
	}

	/**
	 * Updates the units shown on the screen to match the current unit settings, and recalculates
	 * the measurement values in the correct unit. This method must be called from the main (UI)
	 * thread, as it modifies the GUI directly.
	 */
	
	
	private void updateDisplayedUnits() {
		double displayTemp;
			
			mTemperatureUnitView.setText(getString(R.string.temperature_c_unit));
			temp_title.setText("temperature");
			displayTemp = mLastTemperature;
			
			
			
		
		
		mTemperatureView.setText(tempFormat.format(displayTemp));
		
	}
		
		
		
		
		
		
		
		
		

	
	
 
	void issue_alert (String name, String text, int priority) // this function issues the alert
	{
	
	NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(this)
		.setSmallIcon(R.drawable.icon)
        .setContentTitle(name)
        .setAutoCancel (true)
        .setContentText(text);
	Intent resultIntent = new Intent(this, WeatherStationActivity.class);
	PendingIntent resultPendingIntent =
		    PendingIntent.getActivity(
		    this,
		    0,
		    resultIntent,
		    PendingIntent.FLAG_UPDATE_CURRENT);
		    
	mbuilder.setContentIntent(resultPendingIntent);
	int mNotificationId = priority;
	NotificationManager mNotifyMgr = 
	        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	mNotifyMgr.notify(mNotificationId, mbuilder.build());
	Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	long [] pattern = {0,2000,500,2000,500,2000,500,2000,500,2000,500,2000,500,2000,500,2000};
	v.vibrate (pattern,-1);	
	
	} 
	
	void issue_alert (Notification n1)  // this function issues the alert
	{
		
	NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(this)
		.setSmallIcon(R.drawable.icon)
        .setContentTitle(n1.getname())
        .setAutoCancel (true)
        .setContentText(n1.get_Text());
	
	
	
	Intent resultIntent = new Intent(this, WeatherStationActivity.class);
	PendingIntent resultPendingIntent =
		    PendingIntent.getActivity(
		    this,
		    0,
		    resultIntent,
		    PendingIntent.FLAG_UPDATE_CURRENT);
		    
	mbuilder.setContentIntent(resultPendingIntent);
	int mNotificationId = n1.getpriority();
	NotificationManager mNotifyMgr = 
	        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	mNotifyMgr.notify(mNotificationId, mbuilder.build());
	Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	v.vibrate (n1.getpattern(),-1);	
	
	
	

	
	
	
	} 
	
	

	
/// class for all notifications
	public class Notification //extends WeatherStationActivity
	{
		private final String alert_name,alert_message;
		private boolean alert_if_above, alert_if_below;
		private int alert_priority;
		double alert_point;
		private boolean alert_issued = false;
		private Vibrator v;
		//the vibrator in the example phone is easily missed; lets try adding a pattern to make it more distinctive
		long [] pattern = {0,2000,500,2000,500,2000};
		
		
		private final double sensor_max_temp = 60; 
		private final double sensor_min_temp = 0; // these are the sensors optimal range, outside of which accuracy suffers
		private final double sanity_check_max = 40;
		private final double sanity_check_min = 5; // expected values
		private final double sensor_absolute_max_temp = 125;
		private final double sensor_absolute_min_temp = -25; // absolute max values of the sensor
		
		
		
		
	//constructor call, also sets up alert	
	Notification (String name, String message, boolean above, boolean below, int priority, double setpoint)
	{
		alert_name = name;
		alert_message = message;
		alert_if_above = above;
		alert_if_below = below;
		alert_priority = priority;
		alert_point = setpoint;
		
	}
	
	void adjust_setpoint (double setpoint)  // this function allows the user to adjust the desired alert range
	{
		if (alert_point != setpoint)
		{
		alert_point = setpoint;
		
		if (setpoint > sensor_absolute_max_temp || setpoint < sensor_absolute_min_temp)
			Toast.makeText(WeatherStationActivity.this, "Temperature range outside sensor ratings; permanent damage may occur", Toast.LENGTH_LONG).show();
			
		if ((setpoint > sensor_max_temp && setpoint < sensor_absolute_max_temp) || (setpoint < sensor_min_temp && setpoint > sensor_absolute_min_temp) )
			Toast.makeText(WeatherStationActivity.this, "This temperature range outside thermometer's optimal range", Toast.LENGTH_LONG).show();
		
		if ((setpoint > sanity_check_max && setpoint < sensor_max_temp) || (setpoint < sanity_check_min && setpoint > sensor_min_temp) )
			Toast.makeText(WeatherStationActivity.this, "This is an unusual temperature range", Toast.LENGTH_LONG).show();
			
		}
		
		
	}
	
	
	
	boolean scan (double sensor_value)  // this is the core function. It scans if the values are within tolerances, and invokes the issue_alert 
	{
		if (!alert_issued)
		{
			if (((alert_if_above) && (sensor_value > alert_point)) || ((alert_if_below) && (sensor_value < alert_point)))
			{
				alert_issued = true;
				return true;
			}
		}
		if (alert_issued) // this is the reset logic, and has a 5% hysterisis loop to avoid multiple alerts
		{
			if (alert_if_above)
				if (sensor_value < alert_point * 0.95)
					{
					alert_issued = false;
					return false;
					}
			if (alert_if_below)
				if (sensor_value > alert_point * 1.05)
				{
					alert_issued = false;
					return false;
				}
		}
		return false;
	}
	
	
	
	// various getters for the issue_alert (notification) function
	String getname ()
	{
		return alert_name;
	}
	String get_Text ()
	{
		return alert_message;
	}
	int getpriority ()
	{
		return alert_priority;
	}
	long [] getpattern()
	{
		return pattern;
	}
	
	
   }

public class Humidity_notification extends Notification  // this class exists for humididty notifications, their sanity check is different from temperature
{
	
	Humidity_notification (String name, String message, boolean above, boolean below, int priority, double setpoint)
		{
		super (name, message, above, below, priority, setpoint); // no need for a sepearate constructor
		}
	void adjust_setpoint (double setpoint)
	{
		
		final double sanity_check_max = 50; // highest likely value of humidity
		final double sanity_check_min = 20; 
		final double sensor_absolute_max_hum = 100; // humidity is a %, so it should be between 0 and 100
		final double sensor_absolute_min_hum = 0;
		
		
		
		super.alert_point = setpoint;
		
		if (setpoint > sensor_absolute_max_hum || setpoint < sensor_absolute_min_hum)
			Toast.makeText(WeatherStationActivity.this, "Humidity range outside measureable region", Toast.LENGTH_LONG).show();
			
		if ((setpoint > sanity_check_max && setpoint < sensor_absolute_max_hum) || (setpoint < sanity_check_min && setpoint > sensor_absolute_min_hum) )
			Toast.makeText(WeatherStationActivity.this, "Unusual humidity range; consider revising", Toast.LENGTH_LONG).show();
		
		
			
		
		
		
	}
	
	
	
	
	
}

//Chris----------------------------------------------------------------
@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	if(v.getId() == R.id.notes){
	Intent noteListIntent = new Intent(this, NotesListActivity.class);
    startActivity(noteListIntent);
	}
	else if(v.getId() == R.id.checklistButton){
		Intent checklistIntent = new Intent(this, CheckListActivity.class);
		startActivity(checklistIntent);
	}
//----------------------------------------------------------------------
}


}

