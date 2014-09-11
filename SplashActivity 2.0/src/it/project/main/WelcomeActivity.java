package it.project.main;

import it.project.main.log.LogDbManager;
import it.project.main.utils.ImageDownloader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import com.example.fstest.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class WelcomeActivity extends Activity 
{
	//schermata di avvio dell'app
		@Override
		protected void onCreate(Bundle savedInstanceState) 
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_welcome);
			
			
	        
	        
	       
	        //REGISTRAZIONE NUOVO UTENTE
			
	        Button btn_reg=(Button)findViewById(R.id.btn_regnew);
	        btn_reg.setOnClickListener(new OnClickListener() 
	        {
				@Override
				public void onClick(View arg0) 
				{
					Intent i=new Intent(WelcomeActivity.this,NewProfileActivity.class);
					startActivity(i);
					WelcomeActivity.this.finish();
				}
			});
	        
	        //CHERMATA DI LOGIN PER UN UTENTE GIA REGISTRATO
	        
	        Button btn_newreg=(Button)findViewById(R.id.btn_regdb);
	        btn_newreg.setOnClickListener(new OnClickListener() 
	        {
				@Override
				public void onClick(View arg0) 
				{
					Intent i=new Intent(WelcomeActivity.this,LoginActivity.class);
					startActivity(i);
					//WelcomeActivity.this.finish();
				}
			});
	        
	        //ENTRATA IN MANIERA ANONIMA
	        
	        Button btn_anon=(Button)findViewById(R.id.btn_reganon);
	        btn_anon.setOnClickListener(new OnClickListener() 
	        {
				@Override
				public void onClick(View arg0) 
				{
					Intent i=new Intent(WelcomeActivity.this,MapActivity.class);
					startActivity(i);
					//WelcomeActivity.this.finish();
				}
			});
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.welcome, menu);
			return true;
		}

		@Override
		protected void onDestroy() 
		{
			super.onDestroy();
			SharedPreferences pref=this.getSharedPreferences("activity", Context.MODE_PRIVATE);
			//pref.getBoolean("firsttime", true);
			Editor editor = pref.edit();
			editor.putBoolean("firsttime", false);
			editor.commit();
		}
	}
