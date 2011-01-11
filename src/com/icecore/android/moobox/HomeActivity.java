/////////////////////////////////////////////////////////////////
///
///	@file			: Home.java
///	@superclass		: Activity
///	@description	: Home Activity
///	@project		: Meuh
///	@author			: Gaëtan de Villèle - Icecore 2010
///
/////////////////////////////////////////////////////////////////

package com.icecore.android.moobox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
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
	//	Attributes
	//-----------------------------------------------------
	private SensorManager		mySensorManager;
	private Sensor				accelerometer;
	private AudioManager		myAudioManager;
	private SoundPool 			mySoundPool;
	private int 				sid_meuh;
	
	private LinearLayout		ll;
	private boolean				state;

	
	//	Methods
	//-----------------------------------------------------
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.state = true;
        
        // Inflate xml widgets
        ll	= (LinearLayout) this.findViewById(R.id.ll);        

        // Disable the sleeping mode
        ll.setKeepScreenOn(true);
        
        // Click // TODO : un bouton MEUH
        ll.setOnClickListener(this);
        
        // Sensor
        mySensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        try
        {
        	accelerometer = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        	mySensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        }
        catch(Error e)
        {
        	Toast.makeText(this, "Error : can't acquire the sensor :(", Toast.LENGTH_LONG).show();
        }
        
        //	SoundPool + sounds loading
        this.mySoundPool	= new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        this.myAudioManager = (AudioManager)this.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        this.sid_meuh 		= this.mySoundPool.load(this.getApplicationContext(), R.raw.boite_meuh, 0);   
	}
	
	
	
	
	// ACTIVITY resume AND stop
	//----------------------------------------------------------
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
	
	
	
	// SENSORS
	//----------------------------------------------------------
	public void onAccuracyChanged(Sensor arg0, int arg1)
	{}

	public void onSensorChanged(SensorEvent event)
	{
		//float x = event.values[0];
		float y = event.values[1];
		//float z = event.values[2];
		
		if( y < (-7) && this.state == true )
		{
			this.state = false;
		}
		else if( y > (7) && this.state == false && this.myAudioManager.isMusicActive() == false )
		{
			this.state = true;
			this.mySoundPool.play(this.sid_meuh, 1, 1, 0, 0, 1);
		}
	}
	
	
	
	


	// CLICK
	//------------------------------------------------------------
	public void onClick(View v)
	{
		if( this.myAudioManager.isMusicActive() == false )
		{
			this.mySoundPool.play(this.sid_meuh, 1, 1, 0, 0, 1);
		}
	}
	
	
	//	BUTTONS (volume)
	//---------------------------------------------
	public boolean onKey(View v, int keyCode, KeyEvent event)
	{
		// TODO Auto-generated method stub
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
    		// FLAG_REMOVE_SOUND_AND_VIBRATE
    		// FLAG_VIBRATE	
    	}
		return false;
	}
	
	
	
	
	
	//	OPTION MENU
	//-------------------------------------------------------------
	public boolean onCreateOptionsMenu(Menu menu)
	{
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.home, menu);
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
	    // Handle item selection
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