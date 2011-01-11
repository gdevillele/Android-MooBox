/////////////////////////////////////////////////////////////////
///
///	@file			: AboutActivity.java
///	@superclass		: Activity
///	@description	: About Activity
///	@author			: Gaëtan de Villèle - Icecore 2010
///
/////////////////////////////////////////////////////////////////

package com.icecore.android.moobox;

import com.icecore.android.moobox.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AboutActivity extends Activity implements OnClickListener
{
	//	Attributes
	//-----------------------------------------------------
	private Button bt_close;
	private Button bt_contact;
	
			
	//	Methods
	//-----------------------------------------------------
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.about);
        
        this.bt_close 	= (Button) this.findViewById(R.id.bt_close);
        this.bt_contact = (Button) this.findViewById(R.id.bt_contact);
        
        this.bt_close.setOnClickListener(this);
        this.bt_contact.setOnClickListener(this);
    }


	public void onClick(View view)
	{
		switch( view.getId() )
		{
			case R.id.bt_close :
				this.finish();
				break;
				
			case R.id.bt_contact :
				// EMAIL
				Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
				emailIntent.setType("plain/text");
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"icecore.labs@gmail.com"});
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "[MooBox] - Feedback");
				startActivity(Intent.createChooser(emailIntent, "Send mail..."));
				break;
				
			default :
				break;
		
		}
	}
	
	
	
	
}
