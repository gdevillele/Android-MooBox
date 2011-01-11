///////////////////////////////////////////
///
///	@file			: PreferencesActivity.java
///	@description	: 
///	@...
///
////////////////////////////////////////////

package com.icecore.android.moobox;


import com.icecore.android.moobox.R;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.widget.Toast;




public class PreferencesActivity extends PreferenceActivity implements OnPreferenceChangeListener
{
	//	Attributes
	//-----------------------------------------------------
	public static final String MOOBOX_PREFS 		= "com.icecore.android.moobox.preferences";
	public static final String MOOBOX_PREF_CLICK 	= "click_enabled";
	public static final String MOOBOX_PREF_VIBRATE 	= "vibrate_enabled";
	
	private 		CheckBoxPreference 		pref_click;
	private 		CheckBoxPreference 		pref_vibrate;
	private 		SharedPreferences 		settings ;
	private			boolean 				click_enabled;
	private			boolean 				vibrate_enabled;
	
	//	Methods
	//-----------------------------------------------------
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(R.layout.preferences);
        
        // Retrieve the preferences from XML layout
        this.pref_click 		= (CheckBoxPreference)this.findPreference(MOOBOX_PREF_CLICK);
        this.pref_vibrate 		= (CheckBoxPreference)this.findPreference(MOOBOX_PREF_VIBRATE);
        
        // Click Listener
        this.pref_click.setOnPreferenceChangeListener(this);
        this.pref_vibrate.setOnPreferenceChangeListener(this);
        
        // Restore preferences
        this.settings 			= getSharedPreferences(MOOBOX_PREFS, 0);
        this.click_enabled 		= this.settings.getBoolean( MOOBOX_PREF_CLICK, true);
        this.vibrate_enabled 	= this.settings.getBoolean( MOOBOX_PREF_VIBRATE, false);
        
	}

	
	
	public boolean onPreferenceChange(Preference p, Object newValue)
	{
		if( p.getKey().compareTo( this.pref_click.getKey() ) == 0 )
		{
	        CheckBoxPreference cbp = (CheckBoxPreference)p;
	        this.click_enabled = !cbp.isChecked();
			SharedPreferences.Editor editor = settings.edit();
		    editor.putBoolean( MOOBOX_PREF_CLICK, this.click_enabled);
		    editor.commit();
			return true;
		}
		else if( p.getKey().compareTo( this.pref_vibrate.getKey() ) == 0 )
		{
			CheckBoxPreference cbp = (CheckBoxPreference)p;
	        this.vibrate_enabled = !cbp.isChecked();
			SharedPreferences.Editor editor = settings.edit();
		    editor.putBoolean( MOOBOX_PREF_VIBRATE, this.vibrate_enabled);
		    editor.commit();
			return true;
		}
		return false;
	}

}








