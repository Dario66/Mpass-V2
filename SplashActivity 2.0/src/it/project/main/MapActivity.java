package it.project.main;

import it.project.main.utils.GPSTracker;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;

import com.example.fstest.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener;
import org.osmdroid.views.overlay.MyLocationOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;

import retrofit.RestAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD) @SuppressLint("NewApi") 
public class MapActivity extends Activity
{

	private static final String SERVER = "http://192.168.1.102:8080";
	private static final String SERVER2 = "https://10.100.107.198:8443";
	ProgressBar progressBar;
    
    int progressValue = 0;

	TextView ciao;
	
	private ClientSvcApi videoSvc = new RestAdapter.Builder()
	
	.setServer(SERVER).build()
	.create(ClientSvcApi.class);
	
	
	
	
	
	private GPSTracker gps;
	//private GoogleMap mMap;
	private MapView myOpenMapView;
	private MapController myMapController;
	ArrayList<OverlayItem> anotherOverlayItemArray;
	
	MyLocationOverlay myLocationOverlay = null;
	
	//private ProgressDialog spinner;
    private Context context;
    private HashMap<Marker, String> markerIdMap;
    private boolean[] preferences;
    private Button btn_notif;
    private TextView tv_notif;
    
    
    
    
    @TargetApi(Build.VERSION_CODES.GINGERBREAD) 
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
       
        StrictMode.ThreadPolicy policy = 
        		new StrictMode.ThreadPolicy.Builder().permitAll().build();
        	StrictMode.setThreadPolicy(policy);
        
        
        
        
        
        
        
        
        
        
        
        context=this;
        setContentView(R.layout.activity_map);
        
        preferences=new boolean[3];
        preferences[0]=true; //Accessibile
        preferences[1]=true; //Parzialmente accessibile
        preferences[2]=true; //Non accessibile
        
       // tv_notif=(TextView)findViewById(R.id.tv_notification);
		btn_notif=(Button)findViewById(R.id.btn_notification);
        
		ImageButton btn_quiz=(ImageButton)findViewById(R.id.btn_quiz);
		btn_quiz.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				//Intent i=new Intent(MapActivity.this, NearbyActivity.class);
				//startActivity(i);
				
				
				
				//RICHIESTA HTTP PER RICEVERE I DATI DI GEOLOCALIZAZZIONE SOTTOFORMA DI STRINGHE
				//QUESTO ESEMPIO RICEVE UNA STRINGA
				try{
			String s = videoSvc.getVideoList();
			//ciao.setText(s.toString());
			Toast.makeText(getApplicationContext(),s.toString(), Toast.LENGTH_SHORT).show();
			}catch(Exception e){
				//ciao.setText(e.toString());
				Toast.makeText(getApplicationContext(), e.getCause().toString(), Toast.LENGTH_SHORT).show();
			}
		
				
				
				
				
				
			}
		});
		
		
		
        gps=new GPSTracker(this);
      
        //MAPPA OPEN STREET MAP
        
        
        
        myOpenMapView = (MapView)findViewById(R.id.openmapview);
        myOpenMapView.setBuiltInZoomControls(true);
        myMapController = myOpenMapView.getController();
        myMapController.setZoom(2);
        
        myOpenMapView.setTileSource(TileSourceFactory.MAPQUESTOSM);
        
        //Questo codice permette di far vedere sulla mappa solo i luoghi vicini presenti nella fusion table
    
        ViewGroup mapHost = (ViewGroup) findViewById(R.id.mapView);
        mapHost.requestTransparentRegion(mapHost);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map, menu);
    	return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        switch (item.getItemId()) 
        {
        	case R.id.item_filter:showFilterDialog();
        					      break;
        	case R.id.item_mapmenu:Intent profile_intent=new Intent(MapActivity.this, ProfileActivity.class);
        						   startActivity(profile_intent);
        						   break;
        	case R.id.item_refresh:/*spinner.show();
        						   ftclient.setQuery(query_all);
            					   ftclient.queryOnNewThread("setmarkers");*/
        						   PopupMenu popup=new PopupMenu(MapActivity.this, findViewById(item.getItemId()));
        						   MenuInflater inflater=popup.getMenuInflater();
        						   inflater.inflate(R.menu.refresh, popup.getMenu());
        						   popup.setOnMenuItemClickListener(new OnMenuItemClickListener() 
        						   {
									@Override
									public boolean onMenuItemClick(MenuItem item) 
									{
										switch (item.getItemId()) 
										{
											case R.id.item_all:
															   //spinner.show();
			        						   				   
															   break;
											case R.id.item_limit:
																
													break;
										}
										return false;
									}
        						   });
        						   popup.show();
        						   break;
        	case R.id.item_list:Intent list_intent=new Intent(MapActivity.this, VenueListActivity.class);
			   					startActivity(list_intent);
			   					break;
        	default:break;
        }
        return true;
    }

   
    //Procedura per settare i marker dei luoghi nella mappa
   /* public void setMarkers(JSONArray venues)
    {
    	String name, ll, fsqid, min_fsqid="", accl;
    	float min_distance=3000; //Ne cerco uno solo se � al massimo distante un tot di metri, in questo caso 3 km
    	double lat = 0, lng = 0;
    	
    	
    	//spinner.dismiss();
    	
    	markerIdMap=new HashMap<Marker, String>(); //associa ad ogni marker un foursquare id, utilizzato per fare query per i singoli luoghi
    	//Manca la gestione di marker doppi
    	if (venues==null)
    	{
    		Log.d("Debug","Errore nella query");
    		Toast.makeText(this, "Errore nel caricamento della mappa", Toast.LENGTH_LONG).show();
    	}
    	else
    	{
	    	for(int i=0;i<venues.length();i++)
	    	{
	    		try 
	    		{
					JSONArray row=venues.getJSONArray(i);
					fsqid=row.get(1).toString();
					name=row.get(2).toString();
					ll=row.get(3).toString();
					accl=row.get(4).toString();
					String[] lls=ll.split("\\,");
					lat=Double.parseDouble(lls[0]);
					lng=Double.parseDouble(lls[1]);
					Log.d("Test",name);
					
					float temp_distance=distFrom(lat,lng,gps.getLatitude(),gps.getLongitude());
					if (temp_distance<=min_distance)
					{
						
						min_fsqid=fsqid;
						min_distance=temp_distance;
						Log.d("Debug",name +" "+min_distance+" "+min_fsqid);
					}
					if (accl.equals("A"))
					{
						
					}
					else if (accl.equals("P"))
					{
						
					}
					else if (accl.equals("N"))
					{
					}
					else
					{
						
					}
				} 
	    		catch (JSONException e) 
				{
					e.printStackTrace();
				}
	    	}
	    	if (!min_fsqid.equals(""))
    		{
	    		//Gestione notifica
	    		AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
	    		Intent alarmIntent=new Intent(this, NotificationService.class);
	    		PendingIntent pendingIntent=PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
	    		alarmManager.set(AlarmManager.RTC_WAKEUP, 3000, pendingIntent);
	    		
    			btn_notif.setVisibility(View.VISIBLE);
    			
    			btn_notif.setOnClickListener(new OnClickListener() 
    			{
					@Override
					public void onClick(View arg0) 
					{
						DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() 
						{
				            @Override
				            public void onClick(DialogInterface dialog, int which) 
				            {
				                switch (which)
				                {
				                	case DialogInterface.BUTTON_POSITIVE:
				                		//Yes button clicked
				                		Intent quiz_intent=new Intent(MapActivity.this, QuizActivity.class);
				                		
				        				startActivity(quiz_intent);
				        				btn_notif.setVisibility(View.GONE);
				        			    tv_notif.setVisibility(View.GONE);
				        				btn_notif.setEnabled(false);
				                		tv_notif.setEnabled(false);
				                		break;
	
				                	case DialogInterface.BUTTON_NEGATIVE:
				                		//No button clicked
				                		btn_notif.setVisibility(View.GONE);
				        			    tv_notif.setVisibility(View.GONE);
				        				btn_notif.setEnabled(false);
				                		tv_notif.setEnabled(false);
				                		break;
				                }
				            }
						};

				        AlertDialog.Builder builder = new AlertDialog.Builder(context);
				        builder.setMessage("Ti va di fare un quiz?")
				               .setPositiveButton("Yes", dialogClickListener)
				               .setNegativeButton("No", dialogClickListener)
				               .show();
					}
				});
    		}
    	}
    	
    
    }*/

    @Override
    public void onBackPressed() 
    {
    	//Cos� la pressione del tasto back non provoca nessun'azione e in caso la MapActivity non ritorna
    	//all'activity di creazione dell'utente
    }
    
    //Callback eseguito dopo il completamento della query, mostra le info sul luogo
    public void showInfoDialog (JSONArray venues, String acl)
    {
    	//spinner.dismiss();
    	InfoDialog idialog = null;
    	if (acl.equals("A"))
    	{
    		idialog=new InfoDialog((Activity)context, venues, R.style.InfoDialogGreen);
    	}
    	else if (acl.equals("P"))
    	{
    		idialog=new InfoDialog((Activity)context, venues, R.style.InfoDialogYellow);
    	}
    	else if (acl.equals("N"))
    	{
    		idialog=new InfoDialog((Activity)context, venues, R.style.InfoDialogRed);
    	}
    	else
    	{
    		idialog=new InfoDialog((Activity)context, venues, R.style.InfoDialogGray);
    	}
		idialog.show();
    }
    
    private void showFilterDialog()
    {
    	FilterDialog fdialog=new FilterDialog((Activity)context, preferences);
    	fdialog.setCanceledOnTouchOutside(false);
    	fdialog.show();
    }
    
    public static float distFrom(double lat1, double lng1, double lat2, double lng2) 
    {
        double earthRadius = 3958.75;
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;

        int meterConversion = 1609;

        return Float.valueOf((float) (dist * meterConversion));
    }
    
   
    public void applyFilter(boolean[] new_preferences, String category)
    {
    	//Categorie: aclevel, doorways, elevator, escalator, parking
    	preferences=new_preferences;
    	//Creazione stringa di access level da inserire nella stringa
    	String values="";
    	if (preferences[0]==true) 
    	{
    		if (category=="aclevel") values="'A'";
    		else values="'Yes'";
    		if (preferences[1]==true) values=values+",";
    		Log.d("Debug", values);
    	}
    	if (preferences[1]==true) 
    	{
    		//values=values+"'P'";
    		if (category=="aclevel") values=values+"'P'";
    		else values=values+"'No'";
    		if (preferences[2]==true && category=="aclevel") values=values+",";
    	}
    	if (preferences[2]==true && category=="aclevel")
    	{
    		values=values+"'N'";
    	}
    	if (values.equals("")) values="''";
    	
    
    	
    	//Esecuzione query
    	//spinner.show();
    
    }
    
	public void createNewVenue(String _maxid)
	{
		//Creazione nuovo id, successivo al maxid ottenuto con la query
		String maxid=_maxid.substring(4);
		maxid=maxid.substring(0, maxid.length()-2);
		int newid=Integer.parseInt(maxid)+1;
		final String snewid="NF"+Integer.toString(newid);
		
		AlertDialog.Builder ad_venue=new AlertDialog.Builder(this);
		ad_venue.setTitle("Nuovo luogo");
		ad_venue.setMessage("Inserisci il nome del luogo");
		final EditText input=new EditText(this);
		ad_venue.setView(input);
		ad_venue.setPositiveButton("Ok", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int whichButton) 
			{
				GPSTracker mGPS = new GPSTracker(MapActivity.this);
			
				Intent quiz_intent=new Intent(MapActivity.this, QuizActivity.class);
				
				MapActivity.this.startActivity(quiz_intent);
				//String value = input.getText();
			}
		});

		ad_venue.setNegativeButton("Cancel", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int whichButton) 
			{
			     //Canceled.
			}
		});
		ad_venue.show();
	}
}