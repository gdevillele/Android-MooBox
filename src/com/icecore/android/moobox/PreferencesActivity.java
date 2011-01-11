///////////////////////////////////////////
///
///
///
///
///
////////////////////////////////////////////

package com.icecore.android.moobox;


import com.icecore.android.moobox.R;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;




public class PreferencesActivity extends PreferenceActivity implements OnPreferenceClickListener
{
	//	Attributes
	//-----------------------------------------------------
	public static 	String 				PREF_ALLOW_CLICK = "allow_click";

	private 		Preference 			allow_click;
	private 		SharedPreferences 	sharedPref ;
	public 			boolean 			allowClick;
	
	//	Methods
	//-----------------------------------------------------
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(R.layout.preferences);
        
        this.allow_click 	= (Preference)this.findPreference("allow_click");
        
        sharedPref = getSharedPreferences(PREF_ALLOW_CLICK, 0);
	}


	public boolean onPreferenceClick(Preference p)
	{
		if(p.getKey().compareTo("allow_click") == 0)
		{
			
		}
		return false;
	}

}








