package it.project.main;

import com.example.fstest.R;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;

public class SplashActivity extends Activity 
{
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		context=this;
		if (checkConnection())
		{
			//Intent i_newuser=new Intent(SplashActivity.this,MapActivity.class);
		}
		else
		{
			AlertDialog.Builder ad_nonetwork=new AlertDialog.Builder(this);
			ad_nonetwork.setTitle("Internet connection unavailable");
			ad_nonetwork.setMessage("Please, try again later.");
			ad_nonetwork.setPositiveButton("OK", new OnClickListener() 
			{
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{
					finish();
				}
			});
			ad_nonetwork.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}
	
    private boolean firstTime()
    {
 	   boolean first=true;
 	   SharedPreferences pref=this.getSharedPreferences("activity", Context.MODE_PRIVATE);
 	   first=pref.getBoolean("firsttime", true);
 	   return first;
    }
    
    public void changeActivity()
    {
    	if (!firstTime())
        {
        	//Toast.makeText(this, "Prima volta!", Toast.LENGTH_LONG).show();
        	Intent i_newuser=new Intent(SplashActivity.this,MapActivity.class);
        	startActivity(i_newuser);
        }
		else
		{
			Intent i=new Intent(SplashActivity.this, WelcomeActivity.class);
			startActivity(i);
			//finish(); //Se uso il finish, non si vede nulla fra la newprofileactivity e l'activity principale per
			            //qualche secondo
		}
    }
    
    //Restituisce true se il dispositivo � connesso alla rete
    private boolean checkConnection()
    {
 	   boolean online=false;
 	   ConnectivityManager cm=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
 	   NetworkInfo netInfo=cm.getActiveNetworkInfo();
 	   if (netInfo!=null && netInfo.isConnectedOrConnecting())
 	   {
 		   online=true;
 	   }
 	   return online;
    }
}
