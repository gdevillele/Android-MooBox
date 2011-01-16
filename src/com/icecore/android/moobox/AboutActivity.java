/////////////////////////////////////////////////////////////////
///
///	@file			: AboutActivity.java
///	@superclass		: Activity
///	@description	: About Activity
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


import com.icecore.android.moobox.R;
import android.app.Activity;
import android.app.Dialog;
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
	private Button bt_licence;
			
	//	Methods
	//-----------------------------------------------------
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.about);
        
        this.bt_close 	= (Button) this.findViewById(R.id.bt_close);
        this.bt_contact = (Button) this.findViewById(R.id.bt_contact);
        this.bt_licence = (Button) this.findViewById(R.id.bt_licence);
        
        this.bt_close.setOnClickListener(this);
        this.bt_contact.setOnClickListener(this);
        this.bt_licence.setOnClickListener(this);
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
			case R.id.bt_licence :
				// Afficher un dialog avec la GPL
				Dialog dialog = new LicenceDialog(this);
				dialog.setOwnerActivity(this);
				dialog.setTitle("GPL Licence");
        		dialog.show();
				break;
			default :
				break;
		
		}
	}
	
	
}
