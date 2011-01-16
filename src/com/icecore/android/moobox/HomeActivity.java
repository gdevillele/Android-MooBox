/////////////////////////////////////////////////////////////////
///
///	@file			: HomeActivity.java
///	@superclass		: Activity
///	@description 	: App's Home Activity
///	@project		: MooBox
///
///	Copyright 2011 Gaëtan de Villèle
///
/////////////////////////////////////////////////////////////////
/*
	This file is part of Foobar.

    Foobar is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Foobar is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
*/


package com.icecore.android.moobox;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.icecore.android.moobox.R;
import com.icecore.android.moobox.PreferencesActivity;



public class HomeActivity extends Activity implements SensorEventListener, OnClickListener, OnKeyListener
{
	/////////////////////////////////////////////////////
	//	ATTRIBUTES
	/////////////////////////////////////////////////////
	private 	SensorManager		mySensorManager;
	private 	Sensor				accelerometer;
	private 	AudioManager		myAudioManager;
	private 	SoundPool 			mySoundPool;
	private 	int 				sid_meuh;
	private 	LinearLayout		ll;
	private 	boolean				state;
	
	// Vibrator
	private		Vibrator 			vibrator;
	
	// Settings
	private 	SharedPreferences 	settings ;


	////////////////////////////////////////////////////////
	//	METHODS
	////////////////////////////////////////////////////////

	// Activity
	//-----------------------------------------------------
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Set the phone starting state
		this.state = true;

		// Inflate xml views/widgets
		this.ll	= (LinearLayout) this.findViewById(R.id.ll);        

		// Disable the sleeping mode
		this.ll.setKeepScreenOn(true);

		// Set a click listener on the screen
		this.ll.setOnClickListener(this);
		
		// Accelerometer sensor
		this.mySensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
		try
		{
			accelerometer = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			mySensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
		}
		catch(Error e)
		{
			Toast.makeText(this, "Error : "+e.getMessage(), Toast.LENGTH_LONG).show();
		}

		//	SoundPool & sound loading
		this.mySoundPool	= new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		this.myAudioManager = (AudioManager)this.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
		this.sid_meuh 		= this.mySoundPool.load(this.getApplicationContext(), R.raw.moo, 0);   

		// Preferences
		this.settings 		= this.getSharedPreferences( PreferencesActivity.MOOBOX_PREFS, 0);

		// Vibrator
		this.vibrator 		= (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		mySensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
	}
	@Override
	protected void onStop()
	{
		super.onStop();
		mySensorManager.unregisterListener(this);
	}
	
	
	
	//	MOO
	//---------------------------------------------------------
	private void moo()
	{
		this.mySoundPool.play(this.sid_meuh, 1, 1, 0, 0, 1);
		if( this.settings.getBoolean( PreferencesActivity.MOOBOX_PREF_VIBRATE, false) == true )
		{
			this.vibrator.vibrate(1500);
		}
	}
	
	
	
	// SENSORS
	//----------------------------------------------------------
	public void onAccuracyChanged(Sensor sensor, int accuracy){}

	public void onSensorChanged(SensorEvent event)
	{
		float y = event.values[1];
		if( this.settings.getBoolean( PreferencesActivity.MOOBOX_PREF_ACCELEROMETER, true) == true )
		{
			if( y < (-7) && this.state == true )
			{
				this.state = false;
			}
			else if( y > (7) && this.state == false && this.myAudioManager.isMusicActive() == false )
			{
				this.state = true;
				this.moo();
			}
		}
	}
	
	
	
	


	// CLICK
	//------------------------------------------------------------
	public void onClick(View v)
	{
		if( this.myAudioManager.isMusicActive() == false && this.settings.getBoolean( PreferencesActivity.MOOBOX_PREF_CLICK, false) == true )
		{
			this.moo();
		}
	}
	
	
	//	BUTTONS
	//---------------------------------------------
	public boolean onKey(View v, int keyCode, KeyEvent event)
	{
		return false;
	}
    
	public boolean onKeyDown (int keyCode, KeyEvent event)
	{
    	switch( keyCode )
    	{
    		case 24:	// volume UP
    			this.myAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
    			return true;
    		case 25:	// volume DOWN
    			this.myAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
    			return true;
    		case 4:		// RETURN -> close the app
    			this.finish();
    			return true;
    		default:
    			break;
    		// FLAG_VIBRATE	
    	}
		return false;
	}
	
	
	
	
	
	//	OPTION MENU
	//-------------------------------------------------------------
	public boolean onCreateOptionsMenu(Menu menu)
	{
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.home_activity_option_menu, menu);
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
	    switch (item.getItemId())
	    {
	    	case R.id.about:		// Open AboutActivity
	    		Intent i = new Intent(this, AboutActivity.class);
	    		this.startActivity(i);
	    		return true;
	    	case R.id.parameters:	// Open ParametersActivity
	    		Intent j = new Intent(this, PreferencesActivity.class);
	    		this.startActivity(j);
	    		return true;
	    	default:
	    		return super.onOptionsItemSelected(item);
	    }
	}



}