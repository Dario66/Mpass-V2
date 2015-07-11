package it.project.main;

import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.text.TextUtils;



public class Verify {
	private static final String URL_check = "http://192.168.1.101:8080";
	
	 
	 public static boolean isConnectedToServer() {
	        try{
	            URL myUrl = new URL(URL_check);
	            URLConnection connection = myUrl.openConnection();
	            connection.setConnectTimeout(3000);
	            connection.connect();
	            return true;
	        } catch (Exception e) {
	            // Handle your exceptions
	            return false;
	        }
	    }
	  public static boolean isLocationEnabled(Context context) {
	        int locationMode = 0;
	        String locationProviders;

	        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
	            try {
	                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

	            } catch (SettingNotFoundException e) {
	                e.printStackTrace();
	            }

	            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

	        }else{
	            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
	            return !TextUtils.isEmpty(locationProviders);
	        }


	    } 
}
