package it.project.main;

import it.project.main.utils.GPSTracker;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener;
import com.example.fstest.R;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
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
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD) @SuppressLint("NewApi") 
public class MapActivity extends Activity
{
//kkkk
	private static final String URL_check = "http://192.168.1.101:8080";
	//private static final String SERVER2 = "https://10.100.107.198:8443";
	ProgressBar progressBar;
    private ArrayList<String> results;
    int progressValue = 0;
	TextView ciao;
	private GPSTracker gps;
	private MapView myOpenMapView;
	private MapController myMapController;
	private ArrayList<OverlayItem> Marker;
	private MyLocationOverlay myLocationOverlay = null;
	private ItemizedIconOverlay<OverlayItem> anotherItemizedIconOverlay;
	//private ProgressDialog spinner;
    private Context context;
    private boolean[] preferences;
    private Button btn_notif;
    private TextView tv_notif;
    
    ArrayList<String> sfornato;
	double lon;
	double lat;

	double q=1.12332;

	double yr=0;
	double yr2=0;
    
    
    @TargetApi(Build.VERSION_CODES.GINGERBREAD) 
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        StrictMode.ThreadPolicy policy = 
        		new StrictMode.ThreadPolicy.Builder().permitAll().build();
        	StrictMode.setThreadPolicy(policy);
        	GPSTracker u = new GPSTracker(MapActivity.this);
        	
        	
        
        	this.results=new ArrayList<String>();
        	 myOpenMapView = (MapView)findViewById(R.id.openmapview);
             myOpenMapView.setBuiltInZoomControls(true);
             myMapController = myOpenMapView.getController();
             myMapController.setZoom(5);
             myOpenMapView.setTileSource(TileSourceFactory.MAPQUESTOSM);
             
             
             myLocationOverlay = new MyLocationOverlay (this, myOpenMapView);  
             myOpenMapView.getOverlays().add(myLocationOverlay);
             myOpenMapView.postInvalidate();
             
             if(Verify.isLocationEnabled(MapActivity.this)&&isOnline()||Verify.isConnectedToServer()){
            	 Toast.makeText(MapActivity.this, "gps abilitato", Toast.LENGTH_SHORT).show();
 			//String y = ""+u.getLatitude()+","+u.getLongitude()+"";
         	Location l = u.getLocation();
         	
         	double a=u.getLatitude();
         	double b=u.getLongitude();
         	Marker = new ArrayList<OverlayItem>();
	         Marker.add(new OverlayItem(
	         		"0, 0", "0, 0", new GeoPoint(a, b)));
	         ItemizedIconOverlay<OverlayItem> anotherItemizedIconOverlay 
		     	= new ItemizedIconOverlay<OverlayItem>(
		     			MapActivity.this, Marker, myOnItemGestureListener);
		     myOpenMapView.getOverlays().add(anotherItemizedIconOverlay);
		     ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(MapActivity.this);
		     myOpenMapView.getOverlays().add(myScaleBarOverlay);
             myMapController.setCenter(new GeoPoint(l));        
             myMapController.setZoom(11);
             
             }else{
            	 Toast.makeText(MapActivity.this, "verificare di aver abilitato il GPS e la connessione internet.", Toast.LENGTH_SHORT).show();
             }
        context=this;
        
        
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
				
				
				
				Intent i=new Intent(MapActivity.this, QuizActivity.class);
				startActivity(i);
				
				/*
				
				*/
				//RICHIESTA HTTP PER RICEVERE I DATI DI GEOLOCALIZAZZIONE SOTTOFORMA DI STRINGHE
				//QUESTO ESEMPIO RICEVE UNA STRINGA
		/*		try{
			String s = videoSvc.getVideoList();
			//ciao.setText(s.toString());
			Toast.makeText(getApplicationContext(),s.toString(), Toast.LENGTH_SHORT).show();
			}catch(Exception e){
				//ciao.setText(e.toString());
				Toast.makeText(getApplicationContext(), e.getCause().toString(), Toast.LENGTH_SHORT).show();
			}*/
				
				//tu(arg0);
				
				}
			
		});
		

		
        gps=new GPSTracker(this);
      
        //MAPPA OPEN STREET MAP
        
           
        ViewGroup mapHost = (ViewGroup) findViewById(R.id.mapView);
        mapHost.requestTransparentRegion(mapHost);
    }
    
    OnItemGestureListener<OverlayItem> myOnItemGestureListener
    = new OnItemGestureListener<OverlayItem>(){

		@Override
		public boolean onItemLongPress(int arg0, OverlayItem arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onItemSingleTapUp(int arg0, OverlayItem arg1) {
			// TODO Auto-generated method stub
			return false;
		}

    };
    //funzione per LOCALIZZAZIONE
    public String ConvertPointToLocation(GeoPoint point) {   
        String address = "";
        Geocoder geoCoder = new Geocoder( getBaseContext(), Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocation(
                point.getLatitudeE6()  / 1E6, 
                point.getLongitudeE6() / 1E6, 1);

            if (addresses.size() > 0) {
                for (int index = 0; index < addresses.get(0).getMaxAddressLineIndex(); index++)
                    address += addresses.get(0).getAddressLine(index) + " ";
            }


            Toast.makeText(getBaseContext(), address, Toast.LENGTH_SHORT).show();

        }
        catch (IOException e) {                
            e.printStackTrace();
        }   

        return address;
    } 
    
	
	//PROCEDURA CHE RICEVE UNA STRINGA IN INGRESSO DEL TIPO 123.543,123.123
	// UNA PARTE DELLA FUNZIONE ESTRAPOLA LA PARTE PRIMA DELLA VIRGOLA E LA CONVERTE IN DOUBLE
	//POI AL SUPERAMENTO DELLA VIRGOLA RICAVA L'ALTRA VIENE CREAATO UN aRRAYlIST<dOUBLE> PER 
	//PASSARE I RISPETTIVI lat e long 
    public ArrayList<Double> tu(String a){
   		String com="";
   		int sblocca=0,y = 0;
   		String j="",j2="";
   				for(int s=0;s<a.length();s++){
   					com=""+a.charAt(s);
   				if(com.compareTo("(")==0||com.compareTo(")")==0){}
   				else{
   					if(com.compareTo(",")==0){
   						sblocca=1;
   					}else{
   					if(sblocca==1){
   						j2=j2+""+a.charAt(s);
   						y++;
   					}
   					else{
   						j=j+""+a.charAt(s);
   					 }
   					}
   				}
   		}
   				yr2 = Double.parseDouble(j);
   				yr= Double.parseDouble(j2);
   				ArrayList<Double> piu=new ArrayList<Double>();
   				
   				piu.add(0,yr);
   				piu.add(1,yr2);
   				return piu;
   	}
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map, menu);
    	return true;
    }
    TextView t ;
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        switch (item.getItemId()) 
        {
        	case R.id.item_filter:
        		try{
        		new TaskHttp(MapActivity.this,3){
				
			    public void onPostExecute(ArrayList<String> result) {
			    	
			    		Toast.makeText(MapActivity.this, "ok f", Toast.LENGTH_LONG).show();	
			    	
			    		
			    		
			    	//Toast.makeText(MapActivity.this, ""+result.get(0).toString()+"", Toast.LENGTH_LONG).show();
			    	//MapActivity.this.results=result;
			    	Glob.statusDialog.dismiss();
			     }
			}.execute("");
        		}catch(Exception e){
        			Toast.makeText(MapActivity.this, e.getCause().toString(), Toast.LENGTH_LONG).show();	
        		}
			
			ArrayList<String> prov=new ArrayList<String>();
			prov.add(0,"44.355164");
			prov.add(1,"11.711669");
			//METODO PER CONVERTIRE
    		/*	try{
				sfornato=this.results; 
				}catch(Exception e){
					//ciao.setText(e.toString());
					Toast.makeText(getApplicationContext(), e.getCause().toString(), Toast.LENGTH_SHORT).show();
				}
			for (String e : sfornato) {
				ArrayList<Double> ex=tu(e);
				lon= Double.valueOf(prov.get(0));//ex.get(0);
				lat=Double.valueOf(prov.get(1));//ex.get(1);
				
				 //Marker = new ArrayList<OverlayItem>();
		         this.Marker.add(new OverlayItem(
		         		"0, 0", "0, 0", new GeoPoint(lat, lon)));
		         ItemizedIconOverlay<OverlayItem> anotherItemizedIconOverlay 
			     	= new ItemizedIconOverlay<OverlayItem>(
			     			MapActivity.this, Marker, myOnItemGestureListener);
			     myOpenMapView.getOverlays().add(anotherItemizedIconOverlay);
			     ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(MapActivity.this);
			     myOpenMapView.getOverlays().add(myScaleBarOverlay);
		         
			     
			}
	     myLocationOverlay = new MyLocationOverlay(MapActivity.this, myOpenMapView);
	     myOpenMapView.getOverlays().add(myLocationOverlay);
	     myOpenMapView.postInvalidate();*/
						   break;
        					      
        	case R.id.item_mapmenu:Intent profile_intent=new Intent(MapActivity.this, ProfileActivity.class);
        						   startActivity(profile_intent);
        						   break;
        	case R.id.item_refresh:PopupMenu popup=new PopupMenu(MapActivity.this, findViewById(item.getItemId()));
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
												if(Verify.isConnectedToServer()||Verify.isLocationEnabled(MapActivity.this)||isOnline()){
												try{
									        		new TaskHttp(MapActivity.this,1){
													
												    public void onPostExecute(ArrayList<String> result) {
												    	
												    		
												    		//MapActivity.this.results=result;
												    		sfornato=new ArrayList<String>();
												    		sfornato =result;
												    		 ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
												    		 ArrayList<Double> ex;
												    		 for(String a:sfornato){
												    			 ex=tu(a);
																lon=ex.get(0);
																lat=ex.get(1);
												    	     items.add(new OverlayItem("1", "2", new GeoPoint(lat, lon)));
												    		 }
												    	     DefaultResourceProxyImpl resourceProxy = new DefaultResourceProxyImpl(getApplicationContext());
												    	     
												    	     anotherItemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(items,
												    	             new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
												    	                 @Override
												    	                 public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
												    	                     Toast.makeText( getApplicationContext(), "Overlay Titled: " +
												    	item.mTitle + " Single Tapped" + "\n" + "Description: " + item.mDescription, Toast.LENGTH_LONG).show();
												    	                     return true; 
												    	                 }
												    	                 @Override
												    	                 public boolean onItemLongPress(final int index, final OverlayItem item) {
												    	                     Toast.makeText( getApplicationContext(), "Overlay Titled: " + 
												    	item.mTitle + " Long pressed" + "\n" + "Description: " + item.mDescription ,Toast.LENGTH_LONG).show();
												    	                     return false;
												    	                 }
												    	             }, resourceProxy);
												    	     myOpenMapView.getOverlays().add(anotherItemizedIconOverlay);
												    	     myOpenMapView.invalidate();
															 myMapController.setZoom(5);
												    	     ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
												    	//Toast.makeText(MapActivity.this, ""+result.get(0).toString()+"", Toast.LENGTH_LONG).show();
												    	//MapActivity.this.results=result;
												    	Glob.statusDialog.dismiss();
												     }
												}.execute("");
									        		}catch(Exception e){
									        			Toast.makeText(MapActivity.this, e.getCause().toString(), Toast.LENGTH_LONG).show();	
									        		}}else{
									        			Toast.makeText(MapActivity.this,"server non raggiungibile..", Toast.LENGTH_LONG).show();	
									        		}
															   break;
											case R.id.item_limit:
																
												
												
												//richiesta http
												
												try{
													//sfornato=videoSvc.getAllLocation(); 
													Toast.makeText(getApplicationContext(), "si", Toast.LENGTH_SHORT).show();
													}catch(Exception e){
														//ciao.setText(e.toString());
														Toast.makeText(getApplicationContext(), e.getCause().toString(), Toast.LENGTH_SHORT).show();
													}
												//Toast.makeText(getApplicationContext(), ""+sfornato.get(2).toString()+"", Toast.LENGTH_SHORT).show();
												int conta=0;
												sfornato.add("dsd");
												for (String e : sfornato) {
													if(conta==20){break;}
												
													
													ArrayList<Double> ex=tu(e);
													lon=ex.get(0);
													lat=ex.get(1);
													
													 Marker = new ArrayList<OverlayItem>();
											         Marker.add(new OverlayItem(
											         		"0, 0", "0, 0", new GeoPoint(lat, lon)));
											         ItemizedIconOverlay<OverlayItem> anotherItemizedIconOverlay 
												     	= new ItemizedIconOverlay<OverlayItem>(
												     			MapActivity.this, Marker, myOnItemGestureListener);
												     myOpenMapView.getOverlays().add(anotherItemizedIconOverlay);
												     ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(MapActivity.this);
												     myOpenMapView.getOverlays().add(myScaleBarOverlay);
											         
												     conta =conta+1;
												}
										     myLocationOverlay = new MyLocationOverlay(MapActivity.this, myOpenMapView);
										     myOpenMapView.getOverlays().add(myLocationOverlay);
										     myOpenMapView.postInvalidate();
										     
												
												
												
												
												
													break;
										}
										return false;
									}
        						   });
        						   popup.show();
        						   break;
        	case R.id.item_list:
        		
        		
        		
        		
        	/*Intent list_intent=new Intent(MapActivity.this, VenueListActivity.class);
			   					startActivity(list_intent);*/
			   					break;
        	default:break;
        }
        return true;
    }
   
    @Override
   	protected void onResume() {
   		// TODO Auto-generated method stub
   		super.onResume();
   		//myLocationOverlay.enableMyLocation();
   		//myLocationOverlay.enableCompass();
   	}

   	@Override
   	protected void onPause() {
   		// TODO Auto-generated method stub
   		super.onPause();
   		//myLocationOverlay.disableMyLocation();
   		//myLocationOverlay.disableCompass();
   	}

    //Procedura per settare i marker dei luoghi nella mappa
   /* public void setMarkers(JSONArray venues)
    {
    	String name, ll, fsqid, min_fsqid="", accl;
    	float min_distance=3000; //Ne cerco uno solo se è al massimo distante un tot di metri, in questo caso 3 km
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
    	//Così la pressione del tasto back non provoca nessun'azione e in caso la MapActivity non ritorna
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
